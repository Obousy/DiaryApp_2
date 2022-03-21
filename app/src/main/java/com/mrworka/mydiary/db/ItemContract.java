package com.mrworka.mydiary.db;

import android.provider.BaseColumns;

public class ItemContract {

    public ItemContract() {
    }

    public static final class DiaryEntry implements BaseColumns {

        public static final String TABLE_NAME = "diaryList";

        public static final String COLUMN_TITLE = "diaryTitle";

                   public static final String COLUMN_DESCRIPTION = "diaryDescription";
        public static final String COLUMN_TIMESTAMP = "diaryTimeStamp";
    }
}