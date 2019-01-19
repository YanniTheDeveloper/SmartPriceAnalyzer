package ytd.smartpriceanalyzer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class AddItemActivity extends AppCompatActivity {

    EditText buyR ;
    EditText profitR ;
    EditText shippingR;
    EditText shippingYC ;
    EditText otherR ;
    EditText otherYC ;
    EditText agentR;
    EditText rate;
    View baseLayout;
    AdView advert;

    Button saveImageIntentBtn;
    Button analyze ;
    TextView sellPrice ;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);
        initAd();
        initView();
        analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resignKeyboard();
                //gets value from all edit text in AddItem Activity and sets them to their corresponding varialbles
                updateValue();
                String sellText = "You should sell it: "+ItemHandler.getItem().getItemPrice().getPrice()+" birr";
                sellPrice.setText(sellText);
            }

        });

        saveImageIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent saveImageIntent = new Intent(AddItemActivity.this, SaveItemActivity.class);
                startActivity(saveImageIntent);
            }
        });
        baseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    resignKeyboard();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        if(ItemHandler.isItemSaved()){
            ItemHandler.toggleItemSaved();
            AddItemActivity.super.finish();
        }
    }

    double getValue(EditText editText){
        double value;
        try{
            value = Double.parseDouble(editText.getText().toString());
        }catch(Exception e){
            value = 0.0;
        }
        return value;
    }
    void resignKeyboard(){
        try {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch(Exception e){}
    }
    void initAd(){
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        advert = findViewById(R.id.advert);
        AdRequest adRequest = new AdRequest.Builder().build();
        advert.loadAd(adRequest);
    }
    void initView(){
        buyR =  findViewById(R.id.buyR);
        profitR =  findViewById(R.id.profitR);
        shippingR =  findViewById(R.id.shippingR);
        shippingYC =  findViewById(R.id.shippingYC);
        otherR =  findViewById(R.id.otherR);
        otherYC =  findViewById(R.id.otherYC);
        agentR =  findViewById(R.id.agentR);
        rate =  findViewById(R.id.rate);
        analyze =  findViewById(R.id.analyze);
        sellPrice =  findViewById(R.id.sellPrice);
        baseLayout =  findViewById(R.id.baseLayout);
        saveImageIntentBtn = findViewById(R.id.saveItemIntentBtn);
        initViewText(ItemHandler.getItem().getItemPrice());
    }
    void initViewText(ItemPrice itemPrice){
        initViewText(buyR,itemPrice.getBuyRN());
        initViewText(profitR,itemPrice.getProfitRN());
        initViewText(shippingR,itemPrice.getShippingRN());
        initViewText(shippingYC,itemPrice.getShippingYCN());
        initViewText(otherR,itemPrice.getOtherRN());
        initViewText(otherYC,itemPrice.getOtherYCN());
        initViewText(agentR,itemPrice.getAgentRN());
        initViewText(rate,itemPrice.getRateN());
    }
    void initViewText(EditText editText, double value){
        if(value>0){
            String valueInString = ""+value;
            editText.setText(valueInString);
        }
    }
    void updateValue(){
        ItemPrice itemPrice = new ItemPrice();
        itemPrice.setBuyRN(getValue(buyR));
        itemPrice.setProfitRN(getValue(profitR));
        itemPrice.setShippingRN(getValue(shippingR));
        itemPrice.setShippingYCN(getValue(shippingYC));
        itemPrice.setOtherRN(getValue(otherR));
        itemPrice.setOtherYCN(getValue(otherYC));
        itemPrice.setAgentRN(getValue(agentR));
        itemPrice.setRateN(getValue(rate));
        ItemHandler.getItem().setItemPrice(itemPrice);
    }
}
