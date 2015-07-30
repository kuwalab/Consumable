package net.kuwalab.android.consumable.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.test.RenamingDelegatingContext;

import net.kuwalab.android.consumable.dao.impl.ConsumablePicDaoImpl;
import net.kuwalab.android.consumable.entity.ConsumablePic;
import net.kuwalab.android.consumable.helper.AppOpenHelper;
import net.kuwalab.android.consumable.util.IoUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

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

    @Test
    public void キーによる取得が正しく動作すること() throws Exception {
        prepareData();

        ConsumablePicDao consumablePicDao = new ConsumablePicDaoImpl(db);
        ConsumablePic consumablePic = consumablePicDao.selectByKey(1);

        assertThat("検索結果がnullでないこと", consumablePic, is(notNullValue()));

        assertThat(consumablePic.getConsumablePicId(), is(1L));
        assertThat(consumablePic.getConsumableId(), is(1L));

        byte[] blob = consumablePic.getConsumablePic();

        assertThat("画像データがnullでないこと", blob, is(notNullValue()));
        assertThat("画像データのサイズが正しいこと", blob.length, is(8778));
    }
}
