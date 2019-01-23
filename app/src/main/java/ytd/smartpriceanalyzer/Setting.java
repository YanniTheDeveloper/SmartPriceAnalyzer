package ytd.smartpriceanalyzer;

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

        doneSetting.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rateSetting.getText().toString().isEmpty()) Toast.makeText(Setting.this, "Please add the rate!", Toast.LENGTH_LONG).show();
                else {
                    Currency.setRate(Double.parseDouble(rateSetting.getText().toString()));
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
                    if(Currency.getCurrencyTwoId()!=null){
                        rateSetting.setVisibility(View.GONE);
                        rateSettingT.setVisibility(View.GONE);
                    }
                    return;
                }
                String currency = parent.getItemAtPosition(position).toString();
                Currency.setCurrencyOneId(currency.substring(0,3));
                Toast.makeText(Setting.this,currency, Toast.LENGTH_LONG).show();
                if(Currency.getCurrencyTwoId()!=null && Currency.getCurrencyOneId()!=null){
                    rateSettingT.setVisibility(View.VISIBLE);
                    rateSetting.setVisibility(View.VISIBLE);
                    rateSettingT.setText("How much is 1 "+Currency.getCurrencyOneId()+" is in "+ Currency.getCurrencyTwoId()+" ? ");
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
                    if(Currency.getCurrencyTwoId()!=null){
                        rateSetting.setVisibility(View.GONE);
                        rateSettingT.setVisibility(View.GONE);
                    }
                    return;
                }
                String currency = parent.getItemAtPosition(position).toString();
                Currency.setCurrencyTwoId(currency.substring(0,3));
                Toast.makeText(Setting.this,"Implement it", Toast.LENGTH_LONG).show();
                if(Currency.getCurrencyTwoId()!=null && Currency.getCurrencyOneId()!=null){
                    rateSettingT.setVisibility(View.VISIBLE);
                    rateSetting.setVisibility(View.VISIBLE);
                    rateSettingT.setText("How much is 1 "+Currency.getCurrencyOneId()+" is in "+ Currency.getCurrencyTwoId()+" ? ");
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        if(Currency.getCurrencyTwoId()!=null&&Currency.getCurrencyOneId()!=null&&Currency.getRate()!=0.0){
            super.onBackPressed();

        }
        else{
            Toast.makeText(this, "Please choose  currency",Toast.LENGTH_LONG).show();
        }
    }
}

