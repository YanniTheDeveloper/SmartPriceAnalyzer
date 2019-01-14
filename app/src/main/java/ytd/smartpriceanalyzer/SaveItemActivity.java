package ytd.smartpriceanalyzer;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SaveItemActivity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 3;
    Button saveItemBtn;
    ImageView itemPhotoImageView;
    EditText itemNameEditText;
    EditText itemDescriptionEditText;
    Uri selectedImage;
    Double price = 0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_item);

        price = getIntent().getDoubleExtra("price", 0.0);
        saveItemBtn = findViewById(R.id.saveItemBtn);
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemDescriptionEditText = findViewById(R.id.itemDescriptionEditText);
        itemPhotoImageView = findViewById(R.id.itemPhotoImageView);
        selectedImage = null;

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
        // TODO: 1/13/2019 setting value to the item and saving it
        Item newItem = new Item();
        newItem.setName(itemNameEditText.getText().toString());
        newItem.setPrice(price);
        newItem.setDescription(itemDescriptionEditText.getText().toString());
        newItem.setPhotoUri(selectedImage);

        ItemHandler.addItem(newItem);
        AddItemActivity.itemSaved = true;
    }
    void getPhotoFromGallery(){
        // TODO: 1/13/2019 getting the photo from library
        Intent choosePhotoIntent =  new Intent(Intent.ACTION_GET_CONTENT);
        choosePhotoIntent.setType("image/jpeg");
        choosePhotoIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(choosePhotoIntent, "Complete action using"), RC_PHOTO_PICKER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_PHOTO_PICKER){
            selectedImage = data.getData();
            itemPhotoImageView.setImageURI(selectedImage);
        }
    }

}
