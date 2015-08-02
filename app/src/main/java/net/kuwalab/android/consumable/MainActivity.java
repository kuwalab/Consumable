package net.kuwalab.android.consumable;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ListView;

import net.kuwalab.android.consumable.dao.ConsumableDao;
import net.kuwalab.android.consumable.dao.impl.ConsumableDaoImpl;
import net.kuwalab.android.consumable.entity.Consumable;
import net.kuwalab.android.consumable.helper.AppOpenHelper;

import java.util.List;

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
        db.close();
        appOpenHelper.close();

        ConsumableAdapter consumableAdapter = new ConsumableAdapter(this, 0, consumableList);
        consumableListView.setAdapter(consumableAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_lisence) {
            FragmentManager manager = getSupportFragmentManager();
            LisenceDialogFragment d = new LisenceDialogFragment();
            d.show(manager, "dialog");
        }

        return super.onOptionsItemSelected(item);
    }

    public static class LisenceDialogFragment extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.webview_lisence);
            dialog.setTitle("オープンソースライセンス");
            dialog.setCancelable(true);
            WebView webView = (WebView) dialog.findViewById(R.id.lisenceWebView);

            webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webView.loadUrl("file:///android_asset/lisence.html");
            return dialog;
        }
    }
}
