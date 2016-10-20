package com.example.try_n_bay.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.try_n_bay.R;

public class DBHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;

    public DBHelper(Context context) {
        super(context, DBContract.DATABASE_NAME, null, DBContract.DATABASE_VERSION);
    }

    public static Cursor getGoods(SQLiteDatabase sqLiteDatabase) {
        Log.d("TryNBay.DBHelper", "getGoods");
        //db = sqLiteDatabase;
        //Cursor c = db.rawQuery("select books._id, books.iconID, books.Title, Authors.name from books, authors where books.authorID=Authors._id"+strWhere,null);
        Cursor c = sqLiteDatabase.query(DBContract.Goods.TABLE_NAME, new String[]{DBContract.Goods._ID, DBContract.Goods.COLUMN_TITLE, DBContract.Goods.COLUMN_ICON_ID, DBContract.Goods.COLUMN_PRICE}, null, null, null, null, null);

        return c;
    }

    public static Cursor getGoodDetail(SQLiteDatabase sqLiteDatabase, int id) {
        Log.d("TryNBay.DBHelper", "getGoodDetail");
        Cursor c = sqLiteDatabase.query(DBContract.Goods.TABLE_NAME,
                new String[]{DBContract.Goods._ID, DBContract.Goods.COLUMN_TITLE, DBContract.Goods.COLUMN_ICON_ID, DBContract.Goods.COLUMN_PRICE, DBContract.Goods.COLUMN_DESCRIPTION},
                "_id=" + String.valueOf(id), null, null, null, null);
        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("TryNBay.DBHelper", "onCreate");
        createTables(sqLiteDatabase);

        db = sqLiteDatabase;
        initData();
    }

    private void initData() {
        Log.d("TryNBay.DBHelper", "initData");
        insertGood("Dress 1", R.drawable.test1, "Dress 1", 10.0, "10;12");
        insertGood("Dress 2", R.drawable.test2, "Dress 2", 12.0, "12;15;18");
        insertGood("Dress 3", R.drawable.test3, "Dress 3", 13.0, "10;15");
        insertGood("Dress 4", R.drawable.test4, "Dress 4", 24.0, "15;18;20");
        insertGood("Dress 5", R.drawable.test5, "Dress 5", 17.0, "10;18");

        insertUser("Admin", "admin@mail.net", "admin");
        insertUser("Guest", "guest@mail.net", "guest");
    }

    private void insertRole(String role) {
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Roles.COLUMN_NAME, role);
        db.insert(DBContract.Roles.TABLE_NAME, null, cv);
    }

    public int getRoleID(String role) {

        int id = 0;
        Cursor c = db.query(DBContract.Roles.TABLE_NAME,
                new String[]{DBContract.Roles._ID},
                DBContract.Roles.COLUMN_NAME + "='" + role + "'", null, null, null, null);

        if (c.moveToNext()) {
            id = c.getInt(c.getColumnIndex(DBContract.Roles._ID));
        } else {
            ContentValues cv = new ContentValues();
            cv.put(DBContract.Roles.COLUMN_NAME, role);
            id = (int) db.insert(DBContract.Roles.TABLE_NAME, null, cv);
        }
        return id;
    }

    private void insertUser(String name, String email, String role) {
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Users.COLUMN_NAME, name);
        cv.put(DBContract.Users.COLUMN_EMAIL, email);
        cv.put(DBContract.Users.COLUMN_ROLE_ID, getRoleID(role));
        db.insert(DBContract.Users.TABLE_NAME, null, cv);
    }

    private void insertGood(String description, int iconID, String title, double price, String sizesStr) {
        ContentValues cv = new ContentValues();
        cv.put(DBContract.Goods.COLUMN_PRICE, price);
        cv.put(DBContract.Goods.COLUMN_DESCRIPTION, description);
        cv.put(DBContract.Goods.COLUMN_ICON_ID, iconID);
        cv.put(DBContract.Goods.COLUMN_TITLE, title);
        int goodId = (int) db.insert(DBContract.Goods.TABLE_NAME, null, cv);
        String[] sizes = sizesStr.split(";");
        for (String size : sizes) {
            cv = new ContentValues();
            cv.put(DBContract.SizesOfGoods.COLUMN_GOOD_ID, goodId);
            cv.put(DBContract.SizesOfGoods.COLUMN_SIZE_ID, getSizeID(size));
            db.insert(DBContract.SizesOfGoods.TABLE_NAME, null, cv);
        }
    }

    private int getSizeID(String size) {
        int id = 0;
        Cursor c = db.query(DBContract.Sizes.TABLE_NAME,
                new String[]{DBContract.Sizes._ID},
                DBContract.Roles.COLUMN_NAME + "='" + size + "'", null, "", "", "");

        if (c.moveToNext()) {
            id = c.getInt(c.getColumnIndex(DBContract.Sizes._ID));
        } else {
            ContentValues cv = new ContentValues();
            cv.put(DBContract.Sizes.COLUMN_NAME, size);
            id = (int) db.insert(DBContract.Sizes.TABLE_NAME, null, cv);
        }
        return id;
    }

    private void createTables(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + DBContract.Roles.TABLE_NAME + "(" +
                DBContract.Roles._ID + " integer primary key autoincrement," +
                DBContract.Roles.COLUMN_NAME + " text);");

        sqLiteDatabase.execSQL("create table " + DBContract.Goods.TABLE_NAME + "(" +
                DBContract.Goods._ID + " integer primary key autoincrement," +
                DBContract.Goods.COLUMN_ICON_ID + " integer," +
                DBContract.Goods.COLUMN_DESCRIPTION + " text," +
                DBContract.Goods.COLUMN_PRICE + " real," +
                DBContract.Goods.COLUMN_TITLE + " text);");

        sqLiteDatabase.execSQL("create table " + DBContract.Users.TABLE_NAME + "(" +
                DBContract.Users._ID + " integer primary key autoincrement," +
                DBContract.Users.COLUMN_ROLE_ID + " integer REFERENCES " + DBContract.Roles.TABLE_NAME + " (" + DBContract.Roles._ID + ")," +
                DBContract.Users.COLUMN_NAME + " text," +
                DBContract.Users.COLUMN_EMAIL + " text);");

        sqLiteDatabase.execSQL("create table " + DBContract.Sizes.TABLE_NAME + "(" +
                DBContract.Sizes._ID + " integer primary key autoincrement," +
                DBContract.Sizes.COLUMN_NAME + " text);");

        sqLiteDatabase.execSQL("create table " + DBContract.Orders.TABLE_NAME + "(" +
                DBContract.Orders._ID + " integer primary key autoincrement," +
                DBContract.Orders.COLUMN_USER_ID + " integer REFERENCES " + DBContract.Users.TABLE_NAME + " (" + DBContract.Users._ID + ")," +
                DBContract.Orders.COLUMN_DATE + " integer," +
                DBContract.Orders.COLUMN_STATE + " text);");

        sqLiteDatabase.execSQL("create table " + DBContract.OrdersGoods.TABLE_NAME + "(" +
                DBContract.OrdersGoods._ID + " integer primary key autoincrement," +
                DBContract.OrdersGoods.COLUMN_GOOD_ID + " integer REFERENCES " + DBContract.Goods.TABLE_NAME + " (" + DBContract.Goods._ID + ")," +
                DBContract.OrdersGoods.COLUMN_SIZE_ID + " integer REFERENCES " + DBContract.Sizes.TABLE_NAME + " (" + DBContract.Sizes._ID + ")," +
                DBContract.OrdersGoods.COLUMN_PRICE + " real," +
                DBContract.OrdersGoods.COLUMN_QTY + " integer);");

        sqLiteDatabase.execSQL("create table " + DBContract.SizesOfGoods.TABLE_NAME + "(" +
                DBContract.SizesOfGoods._ID + " integer primary key autoincrement," +
                DBContract.SizesOfGoods.COLUMN_GOOD_ID + " integer REFERENCES " + DBContract.Goods.TABLE_NAME + " (" + DBContract.Goods._ID + ")," +
                DBContract.SizesOfGoods.COLUMN_SIZE_ID + " integer REFERENCES " + DBContract.Sizes.TABLE_NAME + " (" + DBContract.Sizes._ID + "));");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        // drope old version database

        //create new version database

    }
}
