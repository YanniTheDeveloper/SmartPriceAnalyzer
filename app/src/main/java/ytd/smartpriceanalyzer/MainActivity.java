package ytd.smartpriceanalyzer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ItemAdapter itemAdapter;
    Button addItemIntentBtn;
    private ListView itemListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemListView = findViewById(R.id.itemListView);
        addItemIntentBtn = findViewById(R.id.addItemIntentBtn);
        addItemIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addItemIntent = new Intent(MainActivity.this, AddItemActivity.class);
                startActivity(addItemIntent);
                if(AddItemActivity.itemSaved) {
                    AddItemActivity.itemSaved = false;
                    refresh();
                }
            }
        });

        // TODO: 1/13/2019 card view, with recyler view and list items
        refresh();
    }

    void refresh(){
        itemAdapter = new ItemAdapter(this,ItemHandler.getList());
        itemListView.setAdapter(itemAdapter);
    }
}
