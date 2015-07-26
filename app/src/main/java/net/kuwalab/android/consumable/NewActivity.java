package net.kuwalab.android.consumable;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import net.kuwalab.android.consumable.dao.ConsumableDao;
import net.kuwalab.android.consumable.dao.impl.ConsumableDaoImpl;
import net.kuwalab.android.consumable.entity.Consumable;
import net.kuwalab.android.consumable.helper.AppOpenHelper;


public class NewActivity extends AppCompatActivity {
    private AppCompatEditText nameEditText;
    private AppCompatEditText furiganaEditText;
    private AppCompatEditText noteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        nameEditText = (AppCompatEditText) findViewById(R.id.nameEditText);
        furiganaEditText = (AppCompatEditText) findViewById(R.id.furiganaEditText);
        noteEditText = (AppCompatEditText) findViewById(R.id.noteEditText);
    }

    @Override
    protected void onPause() {
        super.onPause();

        String name = nameEditText.getText().toString();
        String furigana = furiganaEditText.getText().toString();
        String note = noteEditText.getText().toString();

        nameEditText.setText("");
        furiganaEditText.setText("");
        noteEditText.setText("");

        if (name.length() != 0) {
            AppOpenHelper appOpenHelper = new AppOpenHelper(this);
            SQLiteDatabase db = appOpenHelper.getWritableDatabase();
            ConsumableDao consumableDao = new ConsumableDaoImpl(db);

            Consumable consumable = new Consumable();
            consumable.setConsumableName(name);
            consumable.setConsumableFurigana(furigana);
            consumable.setConsumableNote(note);

            consumableDao.insertInit(consumable);
            db.close();
            appOpenHelper.close();

            Toast.makeText(this, "消耗品を登録しました", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
