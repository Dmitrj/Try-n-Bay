package com.example.try_n_bay;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.example.try_n_bay.R;
import com.example.try_n_bay.database.Constants;
import com.example.try_n_bay.database.DBHelper;
import com.example.try_n_bay.fragments.AboutFragment;
import com.example.try_n_bay.fragments.ItemFragment;
import com.example.try_n_bay.fragments.ListGoodsFragment;
import com.example.try_n_bay.fragments.StartFragment;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements ListGoodsFragment.ListFragmentListener, NavigationView.OnNavigationItemSelectedListener {

    private static final String SELECTED_BOOK = "selected_good";
    public DBHelper dbHelper;
    public int mSelectedGoodID = -1;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private int mSelectedNavItem = Constants.Menus.NAV_ITEM_CATALOG_POSITION;

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
        Log.d("TryNBay." + getLocalClassName(), "OnCreate");

        drawerLayout = (DrawerLayout) findViewById(R.id.nav_drawer);
        navigationView = (NavigationView) findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);

        //set array of navigation items states
        int[][] states = new int[][]{
                new int[]{-android.R.attr.state_checked}, // unchecked
                new int[]{android.R.attr.state_checked}  // checked
        };
        //set array of navigation items colors according to states
        int[] colors = new int[]{
                Color.BLACK,
                Color.BLUE,
        };

        ColorStateList myList = new ColorStateList(states, colors);
        navigationView.setItemTextColor(myList); // set item text color
        navigationView.setItemIconTintList(myList); // set item icon color
        navigationView.getMenu().getItem(mSelectedNavItem).setChecked(true);

        dbHelper = new DBHelper(this);

        //MyTask mt = new MyTask();
        //mt.execute();

        if (savedInstanceState != null) {
            mSelectedGoodID = savedInstanceState.getInt(SELECTED_BOOK, -1);
        }

        if (mSelectedGoodID == -1) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragmentContaner, new StartFragment(), "start")
                    .commit();
        } else if (mSelectedGoodID == 0) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragmentContaner, new ListGoodsFragment(), "list")
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flFragmentContaner, ItemFragment.newInstance(mSelectedGoodID), "item")
                    .commit();
        }
    }

    @Override
    public void onClickItem(long l) {
        Log.d("TryNBay." + getLocalClassName(), "onItemClick");
        mSelectedGoodID = (int) l;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flFragmentContaner, ItemFragment.newInstance(mSelectedGoodID))
                .addToBackStack("ListFragment")
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (!item.isChecked()) { //do nothing on click on checked item
            navigationView.getMenu().getItem(mSelectedNavItem).setChecked(false); //deselect previous item
            item.setChecked(true); // select current item
            switch (item.getItemId()) {
                case R.id.niCatalog:
                    mSelectedNavItem = Constants.Menus.NAV_ITEM_CATALOG_POSITION;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContainer, new ListGoodsFragment())
                            .commit();
                    break;
                case R.id.niLogIn:
                    mSelectedNavItem = Constants.Menus.NAV_ITEM_LOGIN_POSITION;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContainer, new ListGoodsFragment())
                            .commit();
                    break;
                case R.id.niAbout:
                    mSelectedNavItem = Constants.Menus.NAV_ITEM_ABOUT_POSITION;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContainer, new AboutFragment())
                            .commit();
                    break;
                case R.id.niSettings:
                    mSelectedNavItem = Constants.Menus.NAV_ITEM_SETTINGS_POSITION;
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.flContainer, new AboutFragment())
                            .commit();
                    break;
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
