package com.mrworka.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;

import android.content.Intent;
      import android.database.Cursor;
         import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
    import android.os.Bundle;

        import com.mrworka.mydiary.databinding.ActivityEntryDetailBinding;
import com.mrworka.mydiary.db.ItemContract;
import com.mrworka.mydiary.db.ItemDBHelper;

public class EntryDetailActivity extends AppCompatActivity {

    private ActivityEntryDetailBinding binding;
            private SQLiteDatabase mDatabase;
    private long id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEntryDetailBinding.inflate(getLayoutInflater());
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

            binding.textViewTitle.setText(title);


            binding.textViewDescription.setText(description);
        }
    }

    private void initListeners() {

           binding.imageButtonBack.setOnClickListener(v -> finish());
        binding.buttonShare.setOnClickListener(view -> composeEmail());
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

    public void composeEmail() {

        Intent intent = new Intent(Intent.ACTION_SENDTO);


        intent.setData(Uri.parse("mailto:")); 

        
        intent.putExtra(Intent.EXTRA_SUBJECT, binding.textViewTitle.getText());
        intent.putExtra(Intent.EXTRA_TEXT, binding.textViewDescription.getText());
        startActivity(intent);
    }
}