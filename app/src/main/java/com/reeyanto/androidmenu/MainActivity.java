package com.reeyanto.androidmenu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ActionMode action;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView textView = findViewById(R.id.textView);
        Button btnPopup   = findViewById(R.id.btn_popup);

        btnPopup.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(this, btnPopup);
            popupMenu.getMenuInflater().inflate(R.menu.popup_menu, popupMenu.getMenu());
            popupMenu.setOnMenuItemClickListener(menuItem -> {
                Toast.makeText(this, menuItem.getTitle().toString(), Toast.LENGTH_SHORT).show();
                return true;
            });
            popupMenu.show();
        });


        //registerForContextMenu(textView);

        ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
                actionMode.getMenuInflater().inflate(R.menu.context_actionbar_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
                if (menuItem.getItemId() == R.id.menu_context_actionbar_pertama) {
                    Toast.makeText(MainActivity.this, "Context actionbar pertama diklik!", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intentData = new Intent(Intent.ACTION_SEND);
                    intentData.setType("text/plain");
                    intentData.putExtra(Intent.EXTRA_TEXT, textView.getText().toString());

                    Intent intentChooser = new Intent(Intent.createChooser(intentData, null));
                    startActivity(intentChooser);
                }

                action.finish();
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode actionMode) {
                action = null;
            }
        };


        textView.setOnLongClickListener(view -> {
            if (action != null) return false;

            action = startActionMode(actionModeCallback);
            return true;
        });

        // tahapan pembuatan optionsmenu
        // 1. buat resource file baru berisi menu options
        // 2. tambahkan method onCreateOptionsMenu()
        // 3. untuk menghandle aksi user, tambahkan method onOptionsItemSelected()


        // tahapan pembuatan contextmenu
        // 1. buat resource file baru (file xml) berisi menu untuk context
        // 2. daftarkan view yang akan menjadi trigger contextmenu menggunakan registerForContextMenu(view)
        // 3. tambahkan method onCreateContextMenu()
        // 4. tangkap aksi user dengan menambahkan method onContextItemSelected()


        // tahapan pembuatan contextual actionbar
        // 1. buat resource file baru
        // 2. tambahkan listener pada view yang akan menjadi objek trigger [view.setOnLongClickListener] (pastikan view ini tidak didaftarkan pada contextmenu)
        // 3. ...?
    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_context_greetings) {
            Toast.makeText(this, "Hi, there! good morning :)", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Bye!", Toast.LENGTH_SHORT).show();
            finish();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.context_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_options_favourite) {
            //Toast.makeText(this, "Menu favourite selected", Toast.LENGTH_SHORT).show();

        } else if (item.getItemId() == R.id.menu_options_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else {
            Toast.makeText(this, "Menu about selected", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
//
//        MenuItem menuItem = menu.findItem(R.id.menuFavourite);
//        SearchView searchView = (SearchView) menuItem.getActionView();
//        searchView.setOnSearchClickListener(view -> {
//            Toast.makeText(this, searchView.getQuery().toString(), Toast.LENGTH_SHORT).show();
//        });

        return super.onCreateOptionsMenu(menu);
    }
}