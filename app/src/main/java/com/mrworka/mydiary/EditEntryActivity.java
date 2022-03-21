package com.mrworka.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.mrworka.mydiary.databinding.ActivityEditEntryBinding;
import com.mrworka.mydiary.db.ItemContract;
import com.mrworka.mydiary.db.ItemDBHelper;

import java.util.Objects;

public class EditEntryActivity extends AppCompatActivity {

    private ActivityEditEntryBinding binding;
           private SQLiteDatabase mDatabase;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();
    }

    @SuppressLint("Range")
    private void initViews() {

        ItemDBHelper dbHelper = new ItemDBHelper(this); 
        mDatabase = dbHelper.getWritableDatabase(); 

        id = getIntent().getLongExtra("id", 0);

        Cursor cursor = getItem();

        if (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndex(ItemContract.DiaryEntry.COLUMN_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(ItemContract.DiaryEntry.COLUMN_DESCRIPTION));

            binding.editTextTitle.setText(title);
            binding.editTextDescription.setText(description);
        }
    }

    private void initListeners() {

        binding.imageButtonBack.setOnClickListener(v -> finish());
        binding.buttonModify.setOnClickListener(view -> saveDataToSQLite());
    }



    private void saveDataToSQLite() {

        ContentValues cv = new ContentValues();

        String title = Objects.requireNonNull(binding.editTextTitle.getText()).toString().trim();


        String description = Objects.requireNonNull(binding.editTextDescription.getText()).toString().trim();

        if (title.isEmpty()) {

                 binding.editTextTitle.setError("Title is required");
            binding.editTextTitle.requestFocus();
            return;
        }

        if (description.isEmpty()) {

            binding.editTextDescription.setError("Description is required");
            binding.editTextDescription.requestFocus();
            return;
        }

             cv.put(ItemContract.DiaryEntry.COLUMN_TITLE, title); 
        cv.put(ItemContract.DiaryEntry.COLUMN_DESCRIPTION, description); 

        mDatabase.update(ItemContract.DiaryEntry.TABLE_NAME, cv,
                ItemContract.DiaryEntry._ID + "=?", new String[]{String.valueOf(id)}); 

        Toast.makeText(this, "Entry modified", Toast.LENGTH_SHORT).show();

        finish();
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    
      @return
     

    public Cursor getItem() {
        return mDatabase.query(
                       ItemContract.DiaryEntry.TABLE_NAME,
                null,
                       ItemContract.DiaryEntry._ID + "=?",
                new String[]{String.valueOf(id)},
                      null,
                null,
                ItemContract.DiaryEntry.COLUMN_TIMESTAMP + " DESC"
        );
    }
}