package com.example.try_n_bay;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.example.try_n_bay.R;
import com.example.try_n_bay.database.DBHelper;
import com.example.try_n_bay.fragments.ItemFragment;
import com.example.try_n_bay.fragments.ListGoodsFragment;
import com.example.try_n_bay.fragments.StartFragment;

public class MainActivity extends AppCompatActivity implements ListGoodsFragment.ListFragmentListener {

    private static final String SELECTED_BOOK = "selected_good";
    public DBHelper dbHelper;
    public int mSelectedGoodID = -1;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putString(SELECTED_CATEGORY, mSelectedCategory);
        outState.putInt(SELECTED_BOOK, mSelectedGoodID);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        //MyTask mt = new MyTask();
        //mt.execute();

        if (savedInstanceState != null) {
            mSelectedGoodID = savedInstanceState.getInt(SELECTED_BOOK, -1);
        }

        if (mSelectedGoodID == -1) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.flFragmentContaner, new StartFragment(), "start")
                    .commit();
        } else if (mSelectedGoodID == 0) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.flFragmentContaner, new ListGoodsFragment(), "list")
                    .commit();
        } else {
            getFragmentManager().beginTransaction()
                    .replace(R.id.flFragmentContaner, ItemFragment.newInstance(mSelectedGoodID), "item")
                    .commit();
        }
    }

    @Override
    public void onClickItem(long l) {
        mSelectedGoodID = (int) l;
        getFragmentManager().beginTransaction()
                .replace(R.id.flFragmentContaner, ItemFragment.newInstance(mSelectedGoodID))
                .addToBackStack("ListFragment")
                .commit();
    }

    class MyTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //tvInfo.setText("Begin");
        }

        @Override
        protected Void doInBackground(Void... params) {
            dbHelper = new DBHelper(getApplicationContext());
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //tvInfo.setText("End");
        }
    }
}
