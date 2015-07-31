package net.kuwalab.android.consumable;

import android.app.Dialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
            final Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.webview_lisence);
            dialog.setTitle("オープンソースライセンス");
            dialog.setCancelable(true);
            WebView webview = (WebView)dialog.findViewById(R.id.webview);

            webview.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    dialog.show();
                }
            });
            webview.loadUrl("file:///android_asset/lisence.html");
        }

        return super.onOptionsItemSelected(item);
    }
}
