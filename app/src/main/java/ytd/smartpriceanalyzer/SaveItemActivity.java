package ytd.smartpriceanalyzer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class SaveItemActivity extends AppCompatActivity {

    Button saveItemBtn;
    ImageView itemPhotoImageView;
    EditText itemNameEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_item);

        saveItemBtn = findViewById(R.id.saveItemBtn);
        itemNameEditText = findViewById(R.id.itemNameEditText);
        itemPhotoImageView = findViewById(R.id.itemPhotoImageView);

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

    }
    void getPhotoFromGallery(){

    }
}
