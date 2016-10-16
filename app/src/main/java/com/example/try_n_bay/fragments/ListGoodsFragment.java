package com.example.try_n_bay.fragments;

import android.app.Fragment;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.try_n_bay.ListGoodsAdapter;
import com.example.try_n_bay.R;
import com.example.try_n_bay.MainActivity;
import com.example.try_n_bay.database.DBHelper;

public class ListGoodsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list_goods, container, false);
        lv = (ListView) view.findViewById(R.id.lvList);

        Cursor cursor = DBHelper.getGoods(((MainActivity) getActivity()).dbHelper.getReadableDatabase());
        ListGoodsAdapter mAdapter = new ListGoodsAdapter(getActivity(), cursor, false);
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(this);
        return view;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //mSelectedCategory = getArguments().getString(ARG_POS);
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (getActivity() instanceof ListFragmentListener)
            ((ListFragmentListener) getActivity()).onClickItem(id);
    }

    public interface ListFragmentListener {
        void onClickItem(long l);
    }
}
