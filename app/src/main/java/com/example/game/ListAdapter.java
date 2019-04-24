package com.example.game;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class ListAdapter extends CursorAdapter {
    public ListAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(R.layout.list_row,viewGroup,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String user = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TempHistory.COLUMN_NAME_USER));
        String date = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TempHistory.COLUMN_NAME_DATE));
        int score = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.TempHistory.COLUMN_NAME_SCORE));

        ((TextView) view.findViewById(R.id.textViewScore)).setText(String.valueOf(score));
        ((TextView) view.findViewById(R.id.textViewDate)).setText(date);
        ((TextView) view.findViewById(R.id.textViewUser)).setText(user);
    }
}
