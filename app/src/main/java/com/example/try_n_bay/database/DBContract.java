package com.example.try_n_bay.database;

import android.provider.BaseColumns;

public final class DBContract {
    public static final String DATABASE_NAME = "MyDB";
    public static final int DATABASE_VERSION = 1;

    public static final class Roles implements BaseColumns {
        public static final String TABLE_NAME = "Roles";

        public static final String COLUMN_NAME = "name";
    }

    public static final class Goods implements BaseColumns {
        public static final String TABLE_NAME = "Goods";//catalog

        public static final String COLUMN_ICON_ID = "iconID";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "description";
    }

    public static final class Users implements BaseColumns {
        public static final String TABLE_NAME = "Users";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_ROLE_ID = "roleID";
    }

    public static final class Orders implements BaseColumns {
        public static final String TABLE_NAME = "Orders";

        public static final String COLUMN_USER_ID = "userID";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_STATE = "state";
    }

    public static final class OrdersGoods implements BaseColumns {
        public static final String TABLE_NAME = "OrdersGoods";

        public static final String COLUMN_GOOD_ID = "goodID";
        public static final String COLUMN_QTY = "qty";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_SIZE_ID = "sizeID";
    }

    public static final class Sizes implements BaseColumns {
        public static final String TABLE_NAME = "Sizes";

        public static final String COLUMN_NAME = "name";
    }

    public static final class SizesOfGoods implements BaseColumns {
        public static final String TABLE_NAME = "SizesOfGoods";

        public static final String COLUMN_SIZE_ID = "sizeID";
        public static final String COLUMN_GOOD_ID = "goodID";
    }
}
