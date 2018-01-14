package com.chucnq.retrofit_practice_vcn.data.model.model.database;

import android.provider.BaseColumns;

/**
 * Created by User on 1/14/2018.
 */

public class ProductContract  {

    public static final class ProductEntry implements BaseColumns{

        public static final String TABLE_NAME = "Product";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";

    }

}
