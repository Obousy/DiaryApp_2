package com.mrworka.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Toast;

import com.mrworka.mydiary.databinding.ActivityAddEntryBinding;
import com.mrworka.mydiary.db.ItemContract;
import com.mrworka.mydiary.db.ItemDBHelper;

import java.util.Objects;

public class AddEntryActivity extends AppCompatActivity {


              private ActivityAddEntryBinding binding;
    private SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

               super.onCreate(savedInstanceState);

        binding = ActivityAddEntryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initViews();
        initListeners();
    }

    private void initViews() {

        ItemDBHelper dbHelper = new ItemDBHelper(this); 

        mDatabase = dbHelper.getWritableDatabase(); 
    }

    private void initListeners() {

        binding.imageButtonBack.setOnClickListener(v -> finish());
        binding.buttonSave.setOnClickListener(v -> saveDataToSQLite());
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

        mDatabase.insert(ItemContract.DiaryEntry.TABLE_NAME, null, cv); 

        Toast.makeText(this, "Entry saved", Toast.LENGTH_SHORT).show();

        finish();

        Intent intent = new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}