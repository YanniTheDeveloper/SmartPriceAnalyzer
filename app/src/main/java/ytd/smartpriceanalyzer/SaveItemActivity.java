package ytd.smartpriceanalyzer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileDescriptor;
import java.io.IOException;

public class SaveItemActivity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 3;
    Button saveItemBtn;
    ImageView itemPhotoImageView;
    EditText itemNameEditText;
    EditText itemDescriptionEditText;
    TextView priceView;
    Uri selectedImage;
    Bitmap selectedImageBitmap;
    Item newItem ;
    Boolean imageFinished = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_item);

        init();

        itemPhotoImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resignKeyboard();
                getPhotoFromGallery();
            }
        });

        saveItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveItem();
                if(ItemHandler.isItemSaved())SaveItemActivity.super.finish();
            }
        });

    }

    void resignKeyboard(){
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch(Exception e){}
    }
    void saveItem(){
        String tempName = itemNameEditText.getText().toString();
        if(tempName.isEmpty()){
            Toast.makeText(this, "You didn't set the name of item!", Toast.LENGTH_LONG).show();
            return;
        }
        newItem.setName(tempName);
        newItem.setDescription(itemDescriptionEditText.getText().toString());
        new GetBitmapFromUri().execute(selectedImage, null, null);
        ItemHandler.addItem();
        while(!ItemHandler.isItemSaved())
            if(imageFinished)ItemHandler.toggleItemSaved();
    }
    void getPhotoFromGallery(){
        Intent choosePhotoIntent =  new Intent(Intent.ACTION_OPEN_DOCUMENT);
        choosePhotoIntent.addCategory(Intent.CATEGORY_OPENABLE);
        choosePhotoIntent.setType("image/*");
        choosePhotoIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(choosePhotoIntent, RC_PHOTO_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_PHOTO_PICKER && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                selectedImage = data.getData();
                itemPhotoImageView.setImageURI(selectedImage);
                imageFinished = false;
            }
        }
    }


    private class GetBitmapFromUri extends AsyncTask<Uri, Void, Void>{
        @Override
        protected Void doInBackground(Uri... uris) {
            try {
                newItem.setPhoto(getBitmapFromUri(uris[0]));
            } catch (IOException e) {
                e.printStackTrace();
                newItem.setPhoto(null);
            }
            imageFinished = true;
            return null;

        }

        private Bitmap getBitmapFromUri(Uri uri) throws IOException {
            // TODO: 1/16/2019 put this function on async thread
            if(uri == null) return null;
            ParcelFileDescriptor parcelFileDescriptor =
                    getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();

            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            float aspectRatio = image.getWidth() / (float) image.getHeight();
            int width = 960;
            int height = Math.round(width / aspectRatio);
            image = Bitmap.createScaledBitmap(image, width, height, false);
            parcelFileDescriptor.close();
            return image;
        }
    }

    void init(){
        newItem = ItemHandler.getItem();
        saveItemBtn = findViewById(R.id.saveItemBtn);
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemDescriptionEditText = findViewById(R.id.itemDescriptionEditText);
        priceView = findViewById(R.id.priceView);
        itemPhotoImageView = findViewById(R.id.itemPhotoImageView);
        selectedImage = null;

        priceView.setText(newItem.getItemPrice().getPrice()+" birr");
        if(!newItem.getName().isEmpty()){
            itemNameEditText.setText(newItem.getName());
            itemDescriptionEditText.setText(newItem.getDescription());
            if(newItem.getPhoto()!=null)itemPhotoImageView.setImageBitmap(newItem.getPhoto());
        }
    }

}
