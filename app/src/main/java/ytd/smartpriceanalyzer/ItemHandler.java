package ytd.smartpriceanalyzer;

import java.util.ArrayList;
import java.util.List;

public class ItemHandler {
    static ArrayList<Item> items = new ArrayList<>();
    
    public static void addItem(Item newItem){
        items.add(newItem);
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
}
