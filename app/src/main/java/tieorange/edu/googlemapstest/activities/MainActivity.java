package tieorange.edu.googlemapstest.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import tieorange.edu.googlemapstest.MarkersFactory;
import tieorange.edu.googlemapstest.fragments.ListViewFragment;
import tieorange.edu.googlemapstest.fragments.MapFragment;
import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.pojo.MyMarker;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {

    MapFragment mapFragment;

    private Toolbar mUiToolbar;

    private MaterialTabHost mUiTabHost;
    private ViewPager mViewPager;
    private MyPagerAdapter mMyPagerAdapter;
    private static final int TAB_MAP = 0;
    private static final int TAP_LISTVIEW = 1;
    private MapFragment fragment_map;
    private ListViewFragment fragment_list_view;

    private GoogleMap mMap;
    private MarkersFactory mMarkersFactory;

    public MarkersFactory getMarkersFactory() {
        return mMarkersFactory;
    }

    public void setMarkersFactory(MarkersFactory mMarkersFactory) {
        this.mMarkersFactory = mMarkersFactory;
    }

    public GoogleMap getMap() {
        return mMap;
    }

    public void setMap(GoogleMap mMap) {
        this.mMap = mMap;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = MapFragment.newInstance("", "");
        setupToolbar();
        setupTab();
        setupDatabase();

    }

    private void setupDatabase() {
        // TODO: get markers from CSV
        ArrayList<MyMarker> dummyMarkersFromDatabase = MyMarker.getDummyMarkersFromDatabase();

        mMarkersFactory = new MarkersFactory(this, mMap, dummyMarkersFromDatabase);

    }

    private void setupTab() {
        mUiTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMyPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                                               @Override
                                               public void onPageSelected(int position) {
                                                   mUiTabHost.setSelectedNavigationItem(position);
                                               }
                                           }
        );

        // insert all tabs from pageAdapter data
        for (int i = 0; i < mMyPagerAdapter.getCount(); i++) {
            mUiTabHost.addTab(
                    mUiTabHost.newTab()
                            .setIcon(mMyPagerAdapter.getIcon(i))
                            .setTabListener(this)
            );
        }
    }


    private void setupToolbar() {
        mUiToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mUiToolbar);
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_basia_app_icon);
        ab.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onTabSelected(MaterialTab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabReselected(MaterialTab tab) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
//        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_map_view_change) {
            Toast.makeText(this, "Map view change", Toast.LENGTH_SHORT);
            Log.i("MY", ",map viw");
            switchMapView();
        }
        if (id == R.id.action_filter) {
            Toast.makeText(this, "Fitler action", Toast.LENGTH_SHORT);
            Log.i("MY", ",filter");
            showFilterDialog();
        }


        return super.onOptionsItemSelected(item);
    }

    public void switchMapView() {
        if (mMap.getMapType() == GoogleMap.MAP_TYPE_NORMAL)
            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        else if (mMap.getMapType() == GoogleMap.MAP_TYPE_SATELLITE)
            mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public void onTabUnselected(MaterialTab tab) {

    }

    public void showFilterDialog() {
        new MaterialDialog.Builder(this)
                .title(R.string.filter_dialog_title)
                .items(R.array.filter_dialog_items)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        /**
                         * If you use alwaysCallMultiChoiceCallback(), which is discussed below,
                         * returning false here won't allow the newly selected check box to actually be selected.
                         * See the limited multi choice dialog example in the sample project for details.
                         **/
//                        ArticleFragment articleFrag = (ArticleFragment)
//                                getSupportFragmentManager().findFragmentById(R.id.article_fragment);

                        mapFragment.markersFactory.plotMarkersWithOtherIcons();
                        return true;
                    }
                })
                .positiveText(R.string.filter_dialog_positive_text)
                .show();
    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        int iconsArray[] = {R.drawable.ic_tab_map, R.drawable.ic_tab_listview};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            fragment_map = null;
            switch (i) {
                case TAB_MAP:
                    fragment_map = MapFragment.newInstance("", "");
//                    fragment = MapFragment.newInstance("", "");
                    fragment = mapFragment;
                    break;
                case TAP_LISTVIEW:
                    fragment = ListViewFragment.newInstance();
                    fragment_list_view = ListViewFragment.newInstance();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return iconsArray.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        private Drawable getIcon(int position) {
            return getDrawable(iconsArray[position]);
        }
    }
}
