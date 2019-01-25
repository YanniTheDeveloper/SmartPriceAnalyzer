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

    EditText buyR,profitR,shippingR,shippingYC,otherR,otherYC,agentR;
    TextView buyRt,profitRt,shippingRt,shippingYCt,otherRt,otherYCt,agentRt;
    TextView rate;
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
                //gets value from all edit text in AddItem Activity and sets them to their corresponding variables
                updateValue();
                String sellText;
                if(ItemHandler.getItem().getItemPrice().getPrice()==0) sellText = "You should sell it: for free";
                else sellText = "You should sell it: "+ItemHandler.getItem().getItemPrice().getPrice()+" "+Currency.getCurrencyTwoId();
                sellPrice.setText(sellText);
            }

        });

        saveImageIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resignKeyboard();
                //gets value from all edit text in AddItem Activity and sets them to their corresponding variables
                updateValue();
                String sellText;
                if(ItemHandler.getItem().getItemPrice().getPrice()==0) sellText = "You should sell it: for free";
                else sellText = "You should sell it: "+ItemHandler.getItem().getItemPrice().getPrice()+" "+Currency.getCurrencyTwoId();
                sellPrice.setText(sellText);
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
    public void onBackPressed() {
        super.onBackPressed();
        ItemHandler.setEditItemPosition(-1);
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
        buyRt =  findViewById(R.id.buyRt);
        profitRt =  findViewById(R.id.profitRt);
        shippingRt =  findViewById(R.id.shippingRt);
        shippingYCt =  findViewById(R.id.shippingYCt);
        otherRt =  findViewById(R.id.otherRt);
        otherYCt =  findViewById(R.id.otherYCt);
        agentRt =  findViewById(R.id.agentRt);
        rate =  findViewById(R.id.rate);
        analyze =  findViewById(R.id.analyze);
        sellPrice =  findViewById(R.id.sellPrice);
        baseLayout =  findViewById(R.id.baseLayout);
        saveImageIntentBtn = findViewById(R.id.saveItemIntentBtn);
        initViewText(ItemHandler.getItem().getItemPrice());
        buyRt.setText(buyRt.getText()+" "+Currency.getCurrencyOneId());
        profitRt.setText(profitRt.getText()+" "+Currency.getCurrencyOneId());
        shippingRt.setText(shippingRt.getText()+" "+Currency.getCurrencyOneId());
        shippingYCt.setText(shippingYCt.getText()+" "+Currency.getCurrencyTwoId());
        otherRt.setText(otherRt.getText()+" "+Currency.getCurrencyOneId());
        otherYCt.setText(otherYCt.getText()+" "+Currency.getCurrencyTwoId());
        agentRt.setText(agentRt.getText()+" "+Currency.getCurrencyOneId());
    }
    void initViewText(ItemPrice itemPrice){
        initViewText(buyR,itemPrice.getBuyRN());
        initViewText(profitR,itemPrice.getProfitRN());
        initViewText(shippingR,itemPrice.getShippingRN());
        initViewText(shippingYC,itemPrice.getShippingYCN());
        initViewText(otherR,itemPrice.getOtherRN());
        initViewText(otherYC,itemPrice.getOtherYCN());
        initViewText(agentR,itemPrice.getAgentRN());
        rate.setText(""+Currency.getRate());
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
        ItemHandler.getItem().setItemPrice(itemPrice);
    }
}
