package ytd.smartpriceanalyzer;

import android.net.Uri;

public class Item {
    private String name;
    private double price;
    private Uri photoUri;
    private String description;

    public Item() {
    }

    public Item(String name, double price, Uri photoUri, String description) {
        this.name = name;
        this.price = price;
        this.photoUri = photoUri;
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

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
