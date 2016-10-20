package com.example.try_n_bay.fragments;


import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.try_n_bay.R;
import com.example.try_n_bay.MainActivity;
import com.example.try_n_bay.database.DBContract;
import com.example.try_n_bay.database.DBHelper;

public class ItemFragment extends android.support.v4.app.Fragment implements View.OnClickListener {

    private static final String ARG_POS = "goodID";
    private int idGood;
    private TextView tvTitle, tvDesc, tvPrice;
    private ImageView iv;
    private Button btnCamera;

    public static ItemFragment newInstance(int selectedGoodID) {
        Log.d("TryNBay.ItemFragment", "newInstance");
        Bundle args = new Bundle();
        args.putInt(ARG_POS, selectedGoodID);
        ItemFragment fragment = new ItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("TryNBay.ItemFragment", "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idGood = getArguments().getInt(ARG_POS);
        } else
            idGood = 0;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("TryNBay.ItemFragment", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_item, container, false);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        tvDesc = (TextView) view.findViewById(R.id.tvDescription);
        tvPrice = (TextView) view.findViewById(R.id.tvPrice);
        btnCamera = (Button) view.findViewById(R.id.btnCamera);
        btnCamera.setOnClickListener(this);
        iv = (ImageView) view.findViewById(R.id.ivImage);
        if (idGood != 0) {
            Cursor cursor = DBHelper.getGoodDetail(((MainActivity) getActivity()).dbHelper.getReadableDatabase(), idGood);
            if (cursor.moveToNext()) {
                tvTitle.setText(cursor.getString(cursor.getColumnIndex(DBContract.Goods.COLUMN_TITLE)));
                tvDesc.setText(cursor.getString(cursor.getColumnIndex(DBContract.Goods.COLUMN_DESCRIPTION)));
                tvPrice.setText(String.valueOf(cursor.getDouble(cursor.getColumnIndex(DBContract.Goods.COLUMN_PRICE))));
                iv.setImageResource(cursor.getInt(cursor.getColumnIndex(DBContract.Goods.COLUMN_ICON_ID)));
            }
            cursor.close();
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        Log.d("TryNBay.ItemFragment", "onClick");
        if (v.getId() == R.id.btnCamera) {
            /*getFragmentManager().beginTransaction()
                    .replace(R.id.flFragmentContaner, new MyCameraFragment(idGood),"cam")
                    .addToBackStack("ItemFragment")
                    .commit();*/
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivity(intent);
        }
    }
}
