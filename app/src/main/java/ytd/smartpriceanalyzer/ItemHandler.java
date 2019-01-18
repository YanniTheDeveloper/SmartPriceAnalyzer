package ytd.smartpriceanalyzer;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class ItemHandler {
    private static final String TAG = "ItemHandler";
    private static ArrayList<Item> items = new ArrayList<>();
    private static boolean itemSaved = false;
    public static void addItem(Item newItem){
        items.add(newItem);
    }
    public static void toggleItemSaved(){
        itemSaved = !itemSaved;
    }
    public static boolean isItemSaved(){
        return itemSaved;
    }
    public static Item getItem(int position){
        return items.get(position);
    }
    public static int totalItems(){
        return items.size();
    }
    public static ArrayList<Item> getList(){
        return items;
    }
    public static void removeItem(Item itemToBeRemoved){
        if(itemToBeRemoved!=null) items.remove(itemToBeRemoved);
    }
    public static void removeItem(int position){
        Log.e(TAG, "removeItem "+position);
        if(position>=0) items.remove(position);
    }
    public static ArrayList<Item> searchItem(String name){
        ArrayList<Item> searchResultItems = new ArrayList<>();
        for (Item item: items) {
            if(item.getName().contains(name)){
                searchResultItems.add(item);
            }
        }
        return searchResultItems;
    }
}
