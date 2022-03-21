package com.mrworka.mydiary;

import androidx.appcompat.app.AlertDialog;


import androidx.appcompat.app.AppCompatActivity;


import androidx.recyclerview.widget.LinearLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.RelativeLayout;

import com.mrworka.mydiary.adapters.ItemAdapter;
import com.mrworka.mydiary.databinding.ActivityMainBinding;
import com.mrworka.mydiary.db.ItemContract;
import com.mrworka.mydiary.db.ItemDBHelper;

public class MainActivity extends AppCompatActivity implements ItemAdapter.OnTapListener {

    private ActivityMainBinding binding;

    private SQLiteDatabase mDatabase;

    private ItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        initViews();
        initListeners();
    }

    private void initViews() {

        ItemDBHelper dbHelper = new ItemDBHelper(this); 
        mDatabase = dbHelper.getWritableDatabase(); 

        binding.recyclerViewItem.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new ItemAdapter(this, getAllItems(), this); 
        binding.recyclerViewItem.setAdapter(mAdapter);

        binding.editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {

                mAdapter.changeCursor(getSearchItems());
            }
        });
    }

    private void initListeners() {

        binding.layoutAddEntry.setOnClickListener(v -> {
            Intent intent = new Intent(this, AddEntryActivity.class);
            startActivity(intent);
        });
    }

    
     
      @return
     

    public Cursor getAllItems() {
        return mDatabase.query(
                ItemContract.DiaryEntry.TABLE_NAME,
                null,
                null,
                null, 
                null,
                null,
                ItemContract.DiaryEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    
     
      @return
     

    public Cursor getSearchItems() {
        return mDatabase.query(
                ItemContract.DiaryEntry.TABLE_NAME,
                null,
                ItemContract.DiaryEntry.COLUMN_TITLE + " LIKE ?",
                new String[] { "%" + binding.editTextSearch.getText().toString() + "%" },
                null,
                null,
                ItemContract.DiaryEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }

    @Override
    public void onTap(long id) {

        Intent intent = new Intent(this, EntryDetailActivity.class);

        intent.putExtra("id", id);

        startActivity(intent);
    }

    @Override
    public void onEdit(long id) {

        Intent intent = new Intent(this, EditEntryActivity.class);

        intent.putExtra("id", id);

        startActivity(intent);
    }

    @Override
    public void onDelete(long id) {

        new AlertDialog.Builder(this)

                .setTitle("Delete entry")

                .setMessage("Are you sure you want to delete this entry?")

                
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                           public void onClick(DialogInterface dialog, int which) {

                        mDatabase.delete(ItemContract.DiaryEntry.TABLE_NAME,

                            
                                ItemContract.DiaryEntry._ID + "=?", new String[]{String.valueOf(id)});

                        mAdapter.changeCursor(getAllItems());
                    }
                })
            
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}