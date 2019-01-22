package ytd.smartpriceanalyzer;

import android.provider.BaseColumns;

public final class ItemDatabaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private ItemDatabaseContract() {}

    /* Inner class that defines the table contents */
    public static class ItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "itemEntry";
        public static final String COLUMN_NAME = "itemName";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_PHOTO = "photo";
        public static final String COLUMN_BUYR = "buyR";
        public static final String COLUMN_PROFITR = "profitR";
        public static final String COLUMN_SHIPPINGR = "shippingR";
        public static final String COLUMN_SHIPPINGYC = "shippingYC";
        public static final String COLUMN_OTHERR = "otherR";
        public static final String COLUMN_OTHERYC = "otherYC";
        public static final String COLUMN_AGENTR = "agentR";
        public static final String COLUMN_RATE = "rate";
    }
}