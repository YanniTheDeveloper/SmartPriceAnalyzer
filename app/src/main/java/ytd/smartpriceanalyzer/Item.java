package ytd.smartpriceanalyzer;

import android.graphics.Bitmap;

public class Item {
    private String name;
    private ItemPrice itemPrice;
    private Bitmap photo ;
    private String description;

    public Item() {
        photo = null;
        itemPrice = new ItemPrice();
        name = "";
        description = "";
    }

    public Item(String name, ItemPrice itemPrice, Bitmap photo, String description) {
        this.name = name;
        this.itemPrice = itemPrice;
        this.photo = photo;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemPrice getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(ItemPrice itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
