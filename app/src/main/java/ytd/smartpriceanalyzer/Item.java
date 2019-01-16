package ytd.smartpriceanalyzer;

import android.graphics.Bitmap;
import android.net.Uri;

public class Item {
    private String id;
    private String name;
    private double price;
    private Bitmap photo;
    private String description;

    public Item() {
    }

    public Item(String name, double price, Bitmap photo, String description) {
        this.name = name;
        this.price = price;
        this.photo = photo;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
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
