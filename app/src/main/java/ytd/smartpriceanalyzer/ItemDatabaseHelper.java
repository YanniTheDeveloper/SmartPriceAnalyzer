package ytd.smartpriceanalyzer;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.BaseColumns;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;


public class ItemDatabaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "ItemDatabase.db";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ItemDatabaseContract.ItemEntry.TABLE_NAME + " (" +
                    ItemDatabaseContract.ItemEntry._ID + " INTEGER PRIMARY KEY," +
                    ItemDatabaseContract.ItemEntry.COLUMN_NAME + " TEXT," +ItemDatabaseContract.ItemEntry.COLUMN_DESCRIPTION + " TEXT," +
                    ItemDatabaseContract.ItemEntry.COLUMN_BUYR + " REAL," +ItemDatabaseContract.ItemEntry.COLUMN_PROFITR + " REAL," +
                    ItemDatabaseContract.ItemEntry.COLUMN_SHIPPINGR + " REAL," +ItemDatabaseContract.ItemEntry.COLUMN_SHIPPINGYC + " REAL," +
                    ItemDatabaseContract.ItemEntry.COLUMN_OTHERR + " REAL," +ItemDatabaseContract.ItemEntry.COLUMN_OTHERYC + " REAL," +
                    ItemDatabaseContract.ItemEntry.COLUMN_AGENTR + " REAL," +ItemDatabaseContract.ItemEntry.COLUMN_RATE + " REAL," +
                    ItemDatabaseContract.ItemEntry.COLUMN_PHOTO + " BLOB)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ItemDatabaseContract.ItemEntry.TABLE_NAME;

    public ItemDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public boolean insertData(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_NAME, item.getName());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_DESCRIPTION, item.getDescription());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_BUYR, item.getItemPrice().getBuyRN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_PROFITR, item.getItemPrice().getProfitRN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_SHIPPINGR, item.getItemPrice().getShippingRN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_SHIPPINGYC, item.getItemPrice().getShippingYCN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_OTHERR, item.getItemPrice().getOtherRN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_OTHERYC, item.getItemPrice().getOtherYCN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_AGENTR, item.getItemPrice().getAgentRN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_RATE, item.getItemPrice().getRateN());
        if(item.getPhoto()!=null) {
            Bitmap yourBitmap = item.getPhoto();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            values.put(ItemDatabaseContract.ItemEntry.COLUMN_PHOTO, bArray);
        }

// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ItemDatabaseContract.ItemEntry.TABLE_NAME, null, values);
        if(newRowId == -1) return false;
        else return true;
    }
    public LinkedList<Item> readData(){
        SQLiteDatabase db = this.getReadableDatabase();

// Define a projection that specifies which columns from the database
// you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                ItemDatabaseContract.ItemEntry.COLUMN_NAME,
                ItemDatabaseContract.ItemEntry.COLUMN_DESCRIPTION,
                ItemDatabaseContract.ItemEntry.COLUMN_BUYR,
                ItemDatabaseContract.ItemEntry.COLUMN_PROFITR,
                ItemDatabaseContract.ItemEntry.COLUMN_SHIPPINGR,
                ItemDatabaseContract.ItemEntry.COLUMN_SHIPPINGYC,
                ItemDatabaseContract.ItemEntry.COLUMN_OTHERR,
                ItemDatabaseContract.ItemEntry.COLUMN_OTHERYC,
                ItemDatabaseContract.ItemEntry.COLUMN_AGENTR,
                ItemDatabaseContract.ItemEntry.COLUMN_RATE,
                ItemDatabaseContract.ItemEntry.COLUMN_PHOTO
        };

// Filter results WHERE "title" = 'My Title'
        String selection = ItemDatabaseContract.ItemEntry._ID + " = ?";
        String[] selectionArgs = { "*" };

// How you want the results sorted in the resulting Cursor
        String sortOrder =
                ItemDatabaseContract.ItemEntry._ID + " DESC";

        Cursor cursor = db.query(
                ItemDatabaseContract.ItemEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        LinkedList<Item> items = new LinkedList<>();
        while(cursor.moveToNext()) {
            long itemId = cursor.getLong(
                    cursor.getColumnIndexOrThrow(ItemDatabaseContract.ItemEntry._ID));
            if(itemId != -1){
                Item item = new Item();
                item.setId(cursor.getInt(0));
                item.setName(cursor.getString(1));
                item.setDescription(cursor.getString(2));
                item.getItemPrice().setBuyRN(cursor.getDouble(3));
                item.getItemPrice().setProfitRN(cursor.getDouble(4));
                item.getItemPrice().setShippingRN(cursor.getDouble(5));
                item.getItemPrice().setShippingYCN(cursor.getDouble(6));
                item.getItemPrice().setOtherRN(cursor.getDouble(7));
                item.getItemPrice().setOtherYCN(cursor.getDouble(8));
                item.getItemPrice().setAgentRN(cursor.getDouble(9));
                item.getItemPrice().setRateN(cursor.getDouble(10));
                if(cursor.getBlob(11)!=null){
                item.setPhoto(BitmapFactory.decodeByteArray( cursor.getBlob(11),
                        0,(cursor.getBlob(11)).length));
                }
                items.add(item);

            }
        }
        cursor.close();
        return items;
    }
    public boolean deleteData(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        // Define 'where' part of query.
        String selection = ItemDatabaseContract.ItemEntry._ID + " = ?";
// Specify arguments in placeholder order.
        String[] selectionArgs = { Integer.toString(id) };
// Issue SQL statement.
        int deletedRows = db.delete(ItemDatabaseContract.ItemEntry.TABLE_NAME, selection, selectionArgs);
        if(deletedRows>0) return true;
        else return false;
    }
    public boolean updateData(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

// New value for one column
        String title = "MyNewTitle";
        ContentValues values = new ContentValues();
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_NAME, item.getName());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_DESCRIPTION, item.getDescription());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_BUYR, item.getItemPrice().getBuyRN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_PROFITR, item.getItemPrice().getProfitRN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_SHIPPINGR, item.getItemPrice().getShippingRN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_SHIPPINGYC, item.getItemPrice().getShippingYCN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_OTHERR, item.getItemPrice().getOtherRN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_OTHERYC, item.getItemPrice().getOtherYCN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_AGENTR, item.getItemPrice().getAgentRN());
        values.put(ItemDatabaseContract.ItemEntry.COLUMN_RATE, item.getItemPrice().getRateN());
        if(item.getPhoto()!=null) {
            Bitmap yourBitmap = item.getPhoto();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] bArray = bos.toByteArray();
            values.put(ItemDatabaseContract.ItemEntry.COLUMN_PHOTO, bArray);
        }

// Which row to update, based on the title
        String selection = ItemDatabaseContract.ItemEntry._ID + " = ?";
        String[] selectionArgs = { Integer.toString(item.getId()) };

        int count = db.update(
                ItemDatabaseContract.ItemEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        if(count>=0) return true;
        else return false;
    }

}

