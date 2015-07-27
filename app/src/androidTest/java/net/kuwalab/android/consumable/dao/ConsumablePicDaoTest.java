package net.kuwalab.android.consumable.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import net.kuwalab.android.consumable.entity.ConsumablePic;
import net.kuwalab.android.consumable.helper.AppOpenHelper;
import net.kuwalab.android.consumable.util.IoUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

@RunWith(AndroidJUnit4.class)
public class ConsumablePicDaoTest {
    private AppOpenHelper helper;
    private SQLiteDatabase db;

    @Before
    public void setUp() throws Exception {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();

        helper = new AppOpenHelper(new RenamingDelegatingContext(context, "test_"));

        db = helper.getWritableDatabase();
    }

    @After
    public void tearDown() {
        db.close();
        helper.close();
    }

    @Test
    public void test() throws Exception {
        prepareData();
    }

    private void prepareData() throws Exception {
        String sql = "INSERT INTO consumable_pic(consumable_id, consumable_pid) " +
            "VALUES(?, ?)";

        ContentValues values = new ContentValues();
        values.put(ConsumablePic.CONSUMABLE_ID, 1);
        Context context = InstrumentationRegistry.getInstrumentation().getContext();
        InputStream is = context.getAssets().open("test1.jpg");
        values.put(ConsumablePic.CONSUMABLE_PIC, IoUtil.readByteAndClose(is));

        db.insert(ConsumablePic.NAME, null, values);
    }
}
