package ytd.smartpriceanalyzer;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class MainActivity extends AppCompatActivity {
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

    Button analyze ;
    TextView sellPrice ;

    double buyRN = 0.0, profitRN = 0.0, shippingRN = 0.0, shippingYCN = 0.0, otherRN = 0.0, otherYCN = 0.0, agentRN = 0.0, rateN = 0.0;
    double sumR = 0.0, sumYC = 0.0 , price = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, "ca-app-pub-3506026502812641~3462154596");
        advert = findViewById(R.id.advert);
        AdRequest adRequest = new AdRequest.Builder().build();
        advert.loadAd(adRequest);

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
        analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resignKeyboard();
                buyRN = getValue(buyR);
                profitRN = getValue(profitR);
                shippingRN = getValue(shippingR);
                shippingYCN = getValue(shippingYC);
                otherRN = getValue(otherR);
                otherYCN = getValue(otherYC);
                agentRN = getValue(agentR);
                rateN = getValue(rate);

                sumR = buyRN + profitRN + shippingRN + otherRN + agentRN;
                sumYC = shippingYCN + otherYCN;
                price = (sumR * rateN) + sumYC;

                sellPrice.setText("You should sell it: "+price+" birr");
            }

        });

        baseLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    resignKeyboard();
            }
        });

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
}
