package ytd.smartpriceanalyzer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingleItemView extends AppCompatActivity {

    ImageView singleItemPhotoImageView;
    TextView singleItemName, singleItemDescription, singleItemPrice;
    TextView buyRsingle, profitRSingle, otherRSingle, otherYCSingle, shippingRSingle, shippingYCSingle,
             rateSingle, agentRSingle;
    LinearLayout moreInfo;
    Button more;
    Button delete, edit;
    Item clickedItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_item_view);


        init();
        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(moreInfo.getVisibility()== View.GONE){
                    moreInfo.setVisibility(View.VISIBLE);
                    more.setText("less");
                }else{
                    moreInfo.setVisibility(View.GONE);
                    more.setText("more");
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemHandler.removeItem(getIntent().getIntExtra("itemIndex", 0));
                onBackPressed();
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ItemHandler.editItem(getIntent().getIntExtra("itemIndex", 0));
                    Intent editItemIntent = new Intent(SingleItemView.this, AddItemActivity.class);
                    startActivity(editItemIntent);
            }
        });




    }
    void init(){
        singleItemDescription = findViewById(R.id.singleItemDescription);
        singleItemPhotoImageView = findViewById(R.id.singleItemPhotoImageView);
        singleItemName = findViewById(R.id.singleItemName);
        singleItemPrice =  findViewById(R.id.singleItemPrice);

        delete = findViewById(R.id.deleteBtn);
        edit = findViewById(R.id.editBtn);
        more = findViewById(R.id.more);
        moreInfo = findViewById(R.id.moreInfo);

        buyRsingle = findViewById(R.id.buyRSingle);
        profitRSingle = findViewById(R.id.profitRSingle);
        otherRSingle = findViewById(R.id.otherRSingle);
        otherYCSingle = findViewById(R.id.otherYCSingle);
        shippingRSingle = findViewById(R.id.shippingRSingle);
        shippingYCSingle = findViewById(R.id.shippingYCSingle);
        rateSingle = findViewById(R.id.rateSingle);
        agentRSingle = findViewById(R.id.agentRSingle);

        clickedItem = ItemHandler.getItem(getIntent().getIntExtra("itemIndex", 0));
        singleItemName.setText(clickedItem.getName());

        String temp;
        if(clickedItem.getItemPrice().getPrice()==0) temp = "for free";
        else temp = clickedItem.getItemPrice().getPrice()+" "+Currency.getCurrencyTwoId();
        singleItemPrice.setText(temp);
        if(clickedItem.getPhoto()!=null) singleItemPhotoImageView.setImageBitmap(clickedItem.getPhoto());
        singleItemDescription.setText(clickedItem.getDescription());

        buyRsingle.setText(concatnate(getResources().getString(R.string.buy_r), clickedItem.getItemPrice().getBuyRN(), Currency.getCurrencyOneId()));
        profitRSingle.setText(concatnate(getResources().getString(R.string.profit_r), clickedItem.getItemPrice().getProfitRN(),Currency.getCurrencyOneId()));
        otherYCSingle.setText(concatnate(getResources().getString(R.string.other_yc), clickedItem.getItemPrice().getOtherYCN(), Currency.getCurrencyTwoId()));
        otherRSingle.setText(concatnate(getResources().getString(R.string.other_rmb), clickedItem.getItemPrice().getOtherRN(),Currency.getCurrencyOneId()));
        shippingYCSingle.setText(concatnate(getResources().getString(R.string.shipping_yc), clickedItem.getItemPrice().getShippingYCN(), Currency.getCurrencyTwoId()));
        shippingRSingle.setText(concatnate(getResources().getString(R.string.shipping_rmb), clickedItem.getItemPrice().getShippingRN(), Currency.getCurrencyOneId()));
        temp = getResources().getString(R.string.rate)+"  " +Currency.getRate();
        rateSingle.setText(temp);
        agentRSingle.setText(concatnate(getResources().getString(R.string.agent_rmb), clickedItem.getItemPrice().getAgentRN(), Currency.getCurrencyOneId()));
    }
    @Override
    protected void onResume() {
        super.onResume();
        init();
    }
    String concatnate(String one, Double two, String three){
        one += " :  "+ two + " "+ three;
        return one;
    }
}
