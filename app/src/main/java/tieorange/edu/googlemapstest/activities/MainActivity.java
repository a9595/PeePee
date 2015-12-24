package tieorange.edu.googlemapstest.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.GoogleMap;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import tieorange.edu.googlemapstest.fragments.ListViewFragment;
import tieorange.edu.googlemapstest.fragments.MapFragment;
import tieorange.edu.googlemapstest.R;

public class MainActivity extends AppCompatActivity implements MaterialTabListener {
    GoogleMap mMap; // object of a mMap
    public static final int pizda = 3;

    private FloatingActionButton mFAB;
    TabLayout tabLayout;
    private Toolbar toolbar;

    private MaterialTabHost mTabHost;
    private ViewPager mViewPager;
    private MyPagerAdapter mMyPagerAdapter;
    private static final int TAB_MAP = 0;
    private static final int TAP_LISTVIEW = 1;
    private static final int MOVIES_UPCOMING = 2;
    private static final String TAG_SORT_NAME = "sortName";
    private static final String TAG_SORT_DATE = "sortDate";
    private static final String TAG_SORT_RATINGS = "sortRatings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbar();
        setupTab();
    }


    private void setupTab() {
        mTabHost = (MaterialTabHost) findViewById(R.id.materialTabHost);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);

        mMyPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mMyPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                                               @Override
                                               public void onPageSelected(int position) {
                                                   mTabHost.setSelectedNavigationItem(position);
                                               }
                                           }
        );

        // insert all tabs from pageAdapter data
        for (int i = 0; i < mMyPagerAdapter.getCount(); i++) {
            mTabHost.addTab(
                    mTabHost.newTab()
                            .setIcon(mMyPagerAdapter.getIcon(i))
                            .setTabListener(this)
            );
        }
    }


    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
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
    public void onTabUnselected(MaterialTab tab) {

    }


    class MyPagerAdapter extends FragmentPagerAdapter {

        //String[] tabText = getResources().getStringArray(R.array.tabs);
        int icons[] = {R.drawable.ic_tab_map, R.drawable.ic_tab_listview};
        //int icons[] = {R.drawable.vector_android, R.drawable.vector_android, R.drawable.vector_android};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case TAB_MAP:
                    fragment = MapFragment.newInstance("", "");
                    break;
                case TAP_LISTVIEW:
                    fragment = ListViewFragment.newInstance();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getResources().getStringArray(R.array.tabs)[position];
        }

        private Drawable getIcon(int position) {
            return getResources().getDrawable(icons[position]);
        }
    }
}
