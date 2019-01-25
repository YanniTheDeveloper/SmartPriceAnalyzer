package ytd.smartpriceanalyzer;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;

public class ItemHandler {
    private static Item item;
    private static int editItemPosition = -1;

    public static Item getItem() {
        return item;
    }

    public static void editItem(int position) {
        ItemHandler.item = new Item(ItemHandler.getItem(position).getName(),ItemHandler.getItem(position).getItemPrice(),ItemHandler.getItem(position).getPhoto(),ItemHandler.getItem(position).getDescription());
        ItemHandler.item.setId(ItemHandler.getItem(position).getId());
        setEditItemPosition(position);
    }
    public static void createItem() {
        ItemHandler.item = new Item();
    }
    public static int getEditItemPosition() {
        return editItemPosition;
    }

    public static void setEditItemPosition(int editItemPosition) {
        ItemHandler.editItemPosition = editItemPosition;
    }

    private static final String TAG = "ItemHandler";
    public static LinkedList<Item> items = new LinkedList<>();
    private static boolean itemSaved = false;
    public static void addItem(Context m){
        if (getEditItemPosition()>=0){
            if(MainActivity.dbHelper.updateData(item)){
                items.remove(getEditItemPosition());
                items.add(getEditItemPosition(), item);
                Toast.makeText(m,"Item edited", Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(m,"Failed to edit item", Toast.LENGTH_LONG).show();
            setEditItemPosition(-1);
        }else {
            try{
                item.setId(items.getLast().getId()+1);
            }catch (Exception e) {
                item.setId(1);
            }
            if(MainActivity.dbHelper.insertData(item)){
                items.add(0, item);
                Toast.makeText(m,"Item added", Toast.LENGTH_LONG).show();
            }
            else Toast.makeText(m,"Failed to add item", Toast.LENGTH_LONG).show();
        }
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
    public static LinkedList<Item> getList(){
        return items;
    }
    public static void removeItem(Item itemToBeRemoved){
        if(itemToBeRemoved!=null) items.remove(itemToBeRemoved);
    }
    public static void removeItem(int position){
        Log.e(TAG, "removeItem "+position);
        if(position>=0) {
            MainActivity.dbHelper.deleteData(getItem(position).getId());
            items.remove(position);
        }
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
