package net.kuwalab.android.consumable.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import net.kuwalab.android.consumable.dao.ShopDao;
import net.kuwalab.android.consumable.entity.Shop;

import java.util.ArrayList;
import java.util.List;

public class ShopDaoImpl implements ShopDao {
    private SQLiteDatabase db;

    public ShopDaoImpl(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long insert(@NonNull Shop shop) {
        ContentValues values = new ContentValues();
        values.put(Shop.SHOP_NAME, shop.getShopName());
        values.put(Shop.SHOP_FURIGANA, shop.getShopFurigana());

        return db.insert(Shop.NAME, "", values);
    }

    @Override
    @NonNull
    public List<Shop> selectAll() {
        List<Shop> shopList = new ArrayList<>();

        Cursor cursor = db.query(Shop.NAME, null, null, null, null, null, Shop.SHOP_FURIGANA);
        while (cursor.moveToNext()) {
            shopList.add(toShop(cursor));
        }

        return shopList;
    }

    @NonNull
    private Shop toShop(@NonNull Cursor cursor) {
        Shop shop = new Shop();
        shop.setShopId(cursor.getInt(0));
        shop.setShopName(cursor.getString(1));
        shop.setShopFurigana(cursor.getString(2));
        return shop;
    }
}
