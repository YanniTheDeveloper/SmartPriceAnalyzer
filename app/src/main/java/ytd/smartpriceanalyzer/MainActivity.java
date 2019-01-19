package ytd.smartpriceanalyzer;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    ItemAdapter itemAdapter;
    Button addItemIntentBtn;
    private SwipeMenuListView itemListView;
    SwipeMenuCreator creator;
    AdView advert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        itemListView = findViewById(R.id.itemListView);
        addItemIntentBtn = findViewById(R.id.addItemIntentBtn);
        addItemIntentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ItemHandler.createItem();
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
        for(int i = 1; i<10; i++){
            ItemHandler.createItem();
            ItemHandler.getItem().setName("Item "+i);
            ItemHandler.getItem().getItemPrice().setBuyRN(i*10);
            ItemHandler.getItem().getItemPrice().setRateN(20-i);
            ItemHandler.addItem();
        }
        initAd();
        swipeMenuCreator();
        // TODO: 1/16/2019 save items to persistent storage, maybe #sqlite
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

    void swipeMenuCreator(){
        creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem editItem = new SwipeMenuItem(getApplicationContext());
                editItem.setBackground(null);
                editItem.setWidth(200);
                editItem.setTitle("edit");
                editItem.setTitleSize(20);
                editItem.setTitleColor(Color.GREEN);

                menu.addMenuItem(editItem);

                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());

                deleteItem.setBackground(null);
                deleteItem.setWidth(200);
                deleteItem.setTitle("delete");
                deleteItem.setTitleSize(20);
                deleteItem.setTitleColor(Color.RED);

                menu.addMenuItem(deleteItem);


            }
        };
        itemListView.setMenuCreator(creator);
        itemListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch(index){
                    case 0:
                        ItemHandler.editItem(position);
                        Intent editItemIntent = new Intent(MainActivity.this, AddItemActivity.class);
                        startActivity(editItemIntent);
                        break;
                    case 1:
                        ItemHandler.removeItem(position);
                        refresh();
                        break;
                }
                return false;
            }
        });
    }
    void initAd(){
        MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");
        advert = findViewById(R.id.advertInHome);
        AdRequest adRequest = new AdRequest.Builder().build();
        advert.loadAd(adRequest);
    }
}
