package ytd.smartpriceanalyzer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
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
    Double price = 0.0;
    Item newItem = new Item();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_item);

        price = getIntent().getDoubleExtra("price", 0.0);
        saveItemBtn = findViewById(R.id.saveItemBtn);
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemDescriptionEditText = findViewById(R.id.itemDescriptionEditText);
        priceView = findViewById(R.id.priceView);
        itemPhotoImageView = findViewById(R.id.itemPhotoImageView);
        selectedImage = null;

        priceView.setText(price+" birr");
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
                if(AddItemActivity.itemSaved)SaveItemActivity.super.finish();
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
        newItem.setPrice(price);
        newItem.setDescription(itemDescriptionEditText.getText().toString());
        try {
            newItem.setPhoto(getBitmapFromUri(selectedImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ItemHandler.addItem(newItem);
        AddItemActivity.itemSaved = true;
    }
    void getPhotoFromGallery(){
        // TODO: 1/16/2019 put this function on async thread
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
                Log.i(null, "Uri: " + selectedImage.toString());
                itemPhotoImageView.setImageURI(selectedImage);
            }
        }
    }
    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        if(uri == null) return null;
        ParcelFileDescriptor parcelFileDescriptor =
                getContentResolver().openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }

}
