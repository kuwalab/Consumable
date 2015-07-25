package net.kuwalab.android.consumable.dao.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import net.kuwalab.android.consumable.dao.ConsumableDao;
import net.kuwalab.android.consumable.entity.Consumable;

import java.util.ArrayList;
import java.util.List;

public class ConsumableDaoImpl implements ConsumableDao {
    private SQLiteDatabase db;

    public ConsumableDaoImpl(SQLiteDatabase db) {
        this.db = db;
    }

    @Override
    public long insert(@NonNull Consumable consumable) {
        ContentValues values = new ContentValues();
        values.put(Consumable.CONSUMABLE_NAME, consumable.getConsumableName());
        values.put(Consumable.CONSUMABLE_FURIGANA, consumable.getConsumableFurigana());
        values.put(Consumable.CONSUMABLE_NOTE, consumable.getConsumableNote());
        values.put(Consumable.CONSUMABLE_PRICE, consumable.getConsumablePrice());
        values.put(Consumable.CONSUMABLE_DATE, consumable.getConsumableDate());
        values.put(Consumable.CONSUMABLE_COUNT, consumable.getConsumableCount());

        return db.insert(Consumable.NAME, "", values);
    }

    @Override
    @NonNull
    public List<Consumable> selectAll() {
        List<Consumable> consumableList = new ArrayList<>();

        Cursor cursor = db.query(Consumable.NAME, null, null, null, null, null,
            Consumable.CONSUMABLE_FURIGANA);
        while (cursor.moveToNext()) {
            consumableList.add(toConsumable(cursor));
        }

        return consumableList;
    }

    @NonNull
    private Consumable toConsumable(@NonNull Cursor cursor) {
        Consumable consumable = new Consumable();
        consumable.setConsumableId(cursor.getInt(0));
        consumable.setConsumableName(cursor.getString(1));
        consumable.setConsumableFurigana(cursor.getString(2));
        consumable.setConsumableNote(cursor.getString(3));
        consumable.setConsumablePrice(cursor.getInt(4));
        consumable.setConsumableDate(cursor.getString(5));
        consumable.setConsumableCount(cursor.getInt(6));

        return consumable;
    }
}
