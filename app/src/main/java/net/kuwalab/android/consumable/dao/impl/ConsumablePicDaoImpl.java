package net.kuwalab.android.consumable.dao.impl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import net.kuwalab.android.consumable.dao.ConsumablePicDao;
import net.kuwalab.android.consumable.entity.ConsumablePic;

public class ConsumablePicDaoImpl implements ConsumablePicDao {
    private SQLiteDatabase db;

    public ConsumablePicDaoImpl(SQLiteDatabase db) {
        this.db = db;
    }

    @Nullable
    @Override
    public ConsumablePic selectByKey(long consumablePicId) {
        Cursor cursor = db.query(ConsumablePic.NAME, null,
            ConsumablePic.CONSUMABLE_PIC_ID + "=?", new String[]{String.valueOf(consumablePicId)},
            null, null, null);
        if (cursor.moveToNext()) {
            return toConsumablePic(cursor);
        }

        return null;
    }

    @NonNull
    private ConsumablePic toConsumablePic(@NonNull Cursor cursor) {
        ConsumablePic consumablePic = new ConsumablePic();
        consumablePic.setConsumablePicId(cursor.getLong(0));
        consumablePic.setConsumableId(cursor.getLong(1));
        consumablePic.setConsumablePic(cursor.getBlob(2));

        return consumablePic;
    }

}
