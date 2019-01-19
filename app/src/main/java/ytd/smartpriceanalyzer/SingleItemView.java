package ytd.smartpriceanalyzer;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class SingleItemView extends AppCompatActivity {

    ImageView singleItemPhotoImageView;
    TextView singleItemName, singleItemDescription, singleItemPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_view);

        singleItemDescription = findViewById(R.id.singleItemDescription);
        singleItemPhotoImageView = findViewById(R.id.singleItemPhotoImageView);
        singleItemName = findViewById(R.id.singleItemName);
        singleItemPrice =  findViewById(R.id.singleItemPrice);

        Item clickedItem = ItemHandler.getItem(getIntent().getIntExtra("itemIndex", 0));
        singleItemName.setText(clickedItem.getName());
        singleItemPrice.setText(clickedItem.getItemPrice().getPrice()+" birr");
        if(clickedItem.getPhoto()!=null) singleItemPhotoImageView.setImageBitmap(clickedItem.getPhoto());
        singleItemDescription.setText(clickedItem.getDescription());
    }
}
