package ytd.smartpriceanalyzer;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {

    public ItemAdapter(Context context, List<Item> objects){
        super(context, 0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if(itemView == null){
            itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_card_view, parent, false);
        }
        Item currentItem = getItem(position);

        TextView itemName = itemView.findViewById(R.id.itemName);
        itemName.setText(currentItem.getName());

        TextView itemDescription = itemView.findViewById(R.id.itemDescription);
        itemDescription.setText(currentItem.getDescription());

        TextView itemPrice = itemView.findViewById(R.id.itemPrice);
        String price = ""+currentItem.getItemPrice().getPrice()+" "+"birr";
        itemPrice.setText(price);

        ImageView itemPhoto = itemView.findViewById(R.id.itemPhoto);

        if(currentItem.getPhoto()!=null) {
            Bitmap image = currentItem.getPhoto();
            float aspectRatio = image.getWidth() / (float) image.getHeight();
            int width = 240;
            int height = Math.round(width / aspectRatio);
            itemPhoto.setImageBitmap(Bitmap.createScaledBitmap(image, width, height, false));
        }
        else itemPhoto.setImageResource(R.drawable.no_photo_selected);

        return itemView;
    }

}
