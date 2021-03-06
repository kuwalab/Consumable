package net.kuwalab.android.consumable;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import net.kuwalab.android.consumable.dao.ConsumableDao;
import net.kuwalab.android.consumable.dao.ConsumablePicDao;
import net.kuwalab.android.consumable.dao.impl.ConsumableDaoImpl;
import net.kuwalab.android.consumable.dao.impl.ConsumablePicDaoImpl;
import net.kuwalab.android.consumable.entity.Consumable;
import net.kuwalab.android.consumable.entity.ConsumablePic;
import net.kuwalab.android.consumable.helper.AppOpenHelper;
import net.kuwalab.android.consumable.util.ImageUtil;
import net.kuwalab.android.consumable.util.IoUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


public class NewActivity extends AppCompatActivity {
    private AppCompatEditText nameEditText;
    private AppCompatEditText furiganaEditText;
    private AppCompatEditText noteEditText;

    private Uri pictureUri;
    private String imagePath;
    private static final int REQUEST_CODE_TAKE_IMAGE = 0;
    private static final int REQUEST_CODE_IMAGE_CHOOSER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        nameEditText = (AppCompatEditText) findViewById(R.id.nameEditText);
        furiganaEditText = (AppCompatEditText) findViewById(R.id.furiganaEditText);
        noteEditText = (AppCompatEditText) findViewById(R.id.noteEditText);

        findViewById(R.id.cameraButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View v) {

                String[] items = {"写真を撮影", "写真を選択"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(v.getContext());
                dialog.setItems(
                    items,
                    new DialogInterface.OnClickListener() {
                        public void onClick(@NonNull DialogInterface dialog, int which) {
                            switch (which) {
                                case 0:
                                    // ファイルとして画像を保存する
                                    // android.permission.WRITE_EXTERNAL_STORAGEが必要
                                    String filename = System.currentTimeMillis() + ".jpg";
                                    ContentValues values = new ContentValues();
                                    values.put(MediaStore.Images.Media.TITLE, filename);
                                    values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                                    pictureUri = getContentResolver()
                                        .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                                    Intent intent0 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                    intent0.putExtra(MediaStore.EXTRA_OUTPUT, pictureUri);
                                    startActivityForResult(intent0, REQUEST_CODE_TAKE_IMAGE);
                                    break;
                                case 1:
                                    Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                                    intent1.setType("image/*");
                                    intent1.addCategory(Intent.CATEGORY_OPENABLE);
                                    startActivityForResult(intent1, REQUEST_CODE_IMAGE_CHOOSER);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });

                // 表示
                dialog.create().show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            if (pictureUri != null) {
                getContentResolver().delete(pictureUri, null, null);
                pictureUri = null;
            }
            return;
        }

        Uri uri = null;
        if (requestCode == REQUEST_CODE_TAKE_IMAGE) {
            uri = pictureUri;
        } else if (requestCode == REQUEST_CODE_IMAGE_CHOOSER) {
            uri = data.getData();
        }

        // 画像を取得
        ImageView iv = (ImageView) findViewById(R.id.consumableImageView);
        iv.setImageURI(uri);

        // test
        imagePath = ImageUtil.uriToPath(this, uri, requestCode == REQUEST_CODE_IMAGE_CHOOSER);

        pictureUri = null;
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

            long consumableId = consumableDao.insertInit(consumable);

            if (imagePath != null) {
                ConsumablePicDao consumablePicDao = new ConsumablePicDaoImpl(db);
                ConsumablePic consumablePic = new ConsumablePic();
                consumablePic.setConsumableId(consumableId);
                InputStream is = null;
                try {
                     is = new FileInputStream(new File(imagePath));
                    consumablePic.setConsumablePic(IoUtil.readByteAndClose(is));
                    consumablePicDao.insert(consumablePic);
                } catch(IOException e) {
                }
            }

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

        if (id == R.id.action_lisence) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
