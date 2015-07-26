package net.kuwalab.android.consumable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;


public class NewActivity extends AppCompatActivity {
    private AppCompatEditText nameEditText;
    private AppCompatEditText noteEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        nameEditText = (AppCompatEditText) findViewById(R.id.nameEditText);
        noteEditText = (AppCompatEditText) findViewById(R.id.noteEditText);
    }

    @Override
    protected void onPause() {
        super.onPause();

        String name = nameEditText.getText().toString();
        String note = noteEditText.getText().toString();

        if (name.length() != 0) {
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
