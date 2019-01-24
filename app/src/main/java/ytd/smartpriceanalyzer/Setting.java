package ytd.smartpriceanalyzer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Setting extends AppCompatActivity {

    private Spinner spinner1, spinner2;
    private Button doneSetting;
    TextView rateSettingT;
    EditText rateSetting;
    String currencyOne;
    String currencyTwo;
    View settingDividerLine;

    private List<String> Mlist;
    private Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        final Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        final Spinner spinner2 = (Spinner) findViewById(R.id.planets_spinner2);
        rateSetting = findViewById(R.id.rateSetting);
        rateSettingT = findViewById(R.id.rateSettingT);
        doneSetting = findViewById(R.id.doneSetting);
        settingDividerLine = findViewById(R.id.settingDividerLine);
        currencyOne = Currency.getCurrencyOneId();
        currencyTwo = Currency.getCurrencyTwoId();
        doneSetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rateSetting.getText().toString().isEmpty()) Toast.makeText(Setting.this, "Please add the rate!", Toast.LENGTH_LONG).show();
                else if(Double.parseDouble(rateSetting.getText().toString())<=0.0) Toast.makeText(Setting.this, "invalid rate!", Toast.LENGTH_LONG).show();
                else {
                    Currency.setCurrencyOneId(currencyOne);
                    Currency.setCurrencyTwoId(currencyTwo);
                    Currency.setRate(Double.parseDouble(rateSetting.getText().toString()));
                    SharedPreferences sharedPref = Setting.this.getSharedPreferences(
                            "currency", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("currencyOne", Currency.getCurrencyOneId());
                    editor.putString("currencyTwo", Currency.getCurrencyTwoId());
                    editor.putString("rate", ""+Currency.getRate());
                    editor.apply();
                    onBackPressed();
                }
            }
        });
// Create an ArrayAdapter using the string array and a default spinner layout
//
        ArrayAdapter<CharSequence> adapter = null;
            adapter = ArrayAdapter.createFromResource(this,R.array.currencies_array, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner2.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    if(currencyOne==null){
                        rateSetting.setVisibility(View.GONE);
                        rateSettingT.setVisibility(View.GONE);
                        settingDividerLine.setVisibility(View.GONE);
                    }
                }else{
                String currency = parent.getItemAtPosition(position).toString();
                currencyOne =currency.substring(0,3);
                }
                if(currencyTwo!=null){
                    rateSettingT.setVisibility(View.VISIBLE);
                    rateSetting.setVisibility(View.VISIBLE);
                    settingDividerLine.setVisibility(View.VISIBLE);
                    rateSettingT.setText("Rate 1 "+currencyOne+" to "+ currencyTwo);
                    if(Currency.getRate()>0.0)rateSetting.setText(""+Currency.getRate());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position==0){
                    if(currencyTwo==null){
                        rateSetting.setVisibility(View.GONE);
                        rateSettingT.setVisibility(View.GONE);
                        settingDividerLine.setVisibility(View.GONE);
                    }

                }else{
                String currency = parent.getItemAtPosition(position).toString();
                currencyTwo = currency.substring(0,3);}
                if(currencyOne!=null) {
                    rateSettingT.setVisibility(View.VISIBLE);
                    rateSetting.setVisibility(View.VISIBLE);
                    settingDividerLine.setVisibility(View.VISIBLE);
                    rateSettingT.setText("Rate 1 "+currencyOne+" to "+ currencyTwo);
                    if(Currency.getRate()>0.0)rateSetting.setText(""+Currency.getRate());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        if(Currency.getCurrencyTwoId()!=null&&Currency.getCurrencyOneId()!=null&&Currency.getRate()>=0.0){
            super.onBackPressed();

        }
        else{
            Toast.makeText(this, "Please choose currency and input rate",Toast.LENGTH_LONG).show();
        }
    }
}

