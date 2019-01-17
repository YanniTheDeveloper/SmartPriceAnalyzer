package ytd.smartpriceanalyzer;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
            }
        });
        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent toSingleItemView = new Intent(MainActivity.this, SingleItemView.class);
                toSingleItemView.putExtra("itemIndex", position);
                startActivity(toSingleItemView);
            }
        });
        itemListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return false;
            }
        });

        // TODO: 1/16/2019 save items to persistent storage, maybe #sqlite
        // TODO: 1/16/2019 autoupdate refresh() when item is added
        refresh();
    }

    void refresh(){
        itemAdapter = new ItemAdapter(this,ItemHandler.getList());
        itemListView.setAdapter(itemAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }
}
