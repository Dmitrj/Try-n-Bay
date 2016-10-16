package com.example.try_n_bay;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.try_n_bay.database.DBContract;

public class ListGoodsAdapter extends CursorAdapter {

    private LayoutInflater mInflater;

    public ListGoodsAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.goods_list_item, parent, false);

        ImageView ivIcon = (ImageView) v.findViewById(R.id.ivImage);
        ivIcon.setImageResource(cursor.getInt(cursor.getColumnIndex(DBContract.Goods.COLUMN_ICON_ID)));
        TextView tvTitle = (TextView) v.findViewById(R.id.tvTitle);
        tvTitle.setText(cursor.getString(cursor.getColumnIndex(DBContract.Goods.COLUMN_TITLE)));
        TextView tvAuthor = (TextView) v.findViewById(R.id.tvPrice);
        double price = cursor.getDouble(cursor.getColumnIndex(DBContract.Goods.COLUMN_PRICE));
        tvAuthor.setText(String.valueOf(price));

        return v;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
