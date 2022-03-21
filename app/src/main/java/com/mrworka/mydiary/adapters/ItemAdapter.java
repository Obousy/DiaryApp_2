package com.mrworka.mydiary.adapters;

import android.annotation.SuppressLint;

import android.content.Context;


import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mrworka.mydiary.R;
import com.mrworka.mydiary.db.ItemContract;



public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.SessionViewHolder> {

            private final Context mContext;
                 private Cursor mCursor; 
    private final OnTapListener mOnTapListener;

    public ItemAdapter(Context context, Cursor cursor, OnTapListener onTapListener) {
        mContext = context;
        mCursor = cursor;
        mOnTapListener = onTapListener;
    }

    public static class SessionViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewTitle;
        public ImageView imageViewEdit, imageViewDelete;

        public SessionViewHolder(View itemView) {
            super(itemView);

                  textViewTitle = itemView.findViewById(R.id.text_view_title);

            imageViewEdit = itemView.findViewById(R.id.image_view_edit);

            imageViewDelete = itemView.findViewById(R.id.image_view_delete);
        }
    }


    @NonNull
    @Override
    public SessionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

               LayoutInflater inflater = LayoutInflater.from(mContext);

        View view = inflater.inflate(R.layout.layout_item, parent, false);

        return new SessionViewHolder(view);
    }

    @SuppressLint("Range")
    @Override
    public void onBindViewHolder(@NonNull SessionViewHolder holder, int position) {
        

        if (!mCursor.moveToPosition(position)) {
            return;
        }

    
        String title = mCursor.getString(mCursor.getColumnIndex(ItemContract.DiaryEntry.COLUMN_TITLE));
        holder.textViewTitle.setText(title);

        long id = mCursor.getLong(mCursor.getColumnIndex(ItemContract.DiaryEntry._ID));

        holder.itemView.setOnClickListener(v -> {

            mOnTapListener.onTap(id);
        });

        holder.imageViewEdit.setOnClickListener(v -> {

            mOnTapListener.onEdit(id);
        });

        holder.imageViewDelete.setOnClickListener(v -> {

            mOnTapListener.onDelete(id);
        });
    }

    
      @return 
     

    @Override
    public int getItemCount() {


        return mCursor.getCount();
    }

    public interface OnTapListener {

        void onTap(long id);

        void onEdit(long id);
        void onDelete(long id);
    }

    public void changeCursor(Cursor cursor) {


        if(cursor != null) {

            mCursor = cursor;

            notifyDataSetChanged();
        }
    }
}