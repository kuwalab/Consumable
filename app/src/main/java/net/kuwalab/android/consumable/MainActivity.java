package net.kuwalab.android.consumable;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import net.kuwalab.android.consumable.dao.ConsumableDao;
import net.kuwalab.android.consumable.dao.impl.ConsumableDaoImpl;
import net.kuwalab.android.consumable.entity.Consumable;
import net.kuwalab.android.consumable.helper.AppOpenHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    private ListView consumableListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        findViewById(R.id.newButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClassName("net.kuwalab.android.consumable",
                    "net.kuwalab.android.consumable.NewActivity");

                startActivity(intent);
            }
        });

        consumableListView = (ListView) findViewById(R.id.consumableListView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AppOpenHelper appOpenHelper = new AppOpenHelper(this);
        SQLiteDatabase db = appOpenHelper.getReadableDatabase();
        ConsumableDao consumableDao = new ConsumableDaoImpl(db);
        List<Consumable> consumableList = consumableDao.selectAll();

        List<Map<String, String>> data = new ArrayList<>();
        for (Consumable consumable : consumableList) {
            data.add(consumable.toMap());
        }
        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.consumable_row,
            new String[]{"consumableName", "consumableDate", "consumablePrice"},
            new int[]{R.id.consumableName, R.id.consumableDate, R.id.consumablePrice});
        consumableListView.setAdapter(simpleAdapter);

        db.close();
        appOpenHelper.close();
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
