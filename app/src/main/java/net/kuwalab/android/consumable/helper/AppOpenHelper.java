package net.kuwalab.android.consumable.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import net.kuwalab.android.consumable.entity.Consumable;
import net.kuwalab.android.consumable.entity.Shop;

public class AppOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "app";

    private static final int LATEST_VERSION = 1;

    public AppOpenHelper(Context context) {
        this(context, LATEST_VERSION);
    }

    public AppOpenHelper(Context context, int version) {
        super(context, DB_NAME, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Shop.NAME +"(" +
            Shop.SHOP_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Shop.SHOP_NAME + " TEXT NOT NULL, " +
            Shop.SHOP_FURIGANA + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE " + Consumable.NAME + "(" +
            Consumable.CONSUMABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Consumable.CONSUMABLE_NAME + " TEXT NOT NULL, " +
            Consumable.CONSUMABLE_FURIGANA + " TEXT NOT NULL, " +
            Consumable.CONSUMABLE_NOTE + " TEXT DEFAULT '' NOT NULL, " +
            Consumable.CONSUMABLE_PRICE + " INTEGER DEFAULT -1 NOT NULL, " +
            Consumable.CONSUMABLE_DATE + " TEXT DEFAULT '' NOT NULL, " +
            Consumable.CONSUMABLE_COUNT + " INTEGER DEFAULT -1 NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table " + Consumable.NAME);
        db.execSQL("drop table " + Shop.NAME);

        onCreate(db);
    }
}
