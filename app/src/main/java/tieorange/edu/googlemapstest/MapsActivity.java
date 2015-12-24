package tieorange.edu.googlemapstest;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends AppCompatActivity implements MaterialTabListener {
    public HashMap<Marker, MyMarker> mMarkersHashMap;
    static ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
    GoogleMap mMap; // object of a map


    private FloatingActionButton mFAB;
    TabLayout tabLayout;
    private Toolbar toolbar;

    private MaterialTabHost mTabHost;
    private ViewPager mViewPager;
    private MyPagerAdapter mMyPagerAdapter;
    private static final int MOVIES_SEARCH_RESULTS = 0;
    private static final int MOVIES_HITS = 1;
    private static final int MOVIES_UPCOMING = 2;
    private static final String TAG_SORT_NAME = "sortName";
    private static final String TAG_SORT_DATE = "sortDate";
    private static final String TAG_SORT_RATINGS = "sortRatings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        //setupMap();
        //setupFab();
        setupToolbar();
        setupTab();
//        setupTablayout();

    }

    private void setupMap() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
//                .findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//        mMap = mapFragment.getMap();
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

//    private void setupTablayout() {
//
//        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 1"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 2"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 3"));
//        tabLayout.addTab(tabLayout.newTab().setText("Tab 4"));
//    }

    private void getUserLocation() {
//        Snackbar
//                .make(findViewById(R.id.main_content),
//                        "Finding the nearest Pee Pee :)",
//                        Snackbar.LENGTH_SHORT)
//                .show(); // Do not forget to show!

        //MarkersFactory.initMarkers(this); // create places on map

        // TODO: getUserLocation: http://hmkcode.com/material-design-app-android-design-support-library-appcompat/

    }

    private void setupFab() {
        mFAB = (FloatingActionButton) findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.fab)
                    getUserLocation();
            }
        });
    }


    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

//        if (toolbar != null) {
//            setSupportActionBar(toolbar);
//            getSupportActionBar().setTitle("Your Maps");
//            // ...
//        }
    }

//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
//            @Override
//            public boolean onMarkerClick(Marker marker) {
//                marker.showInfoWindow();
//                return true;
//            }
//        });
//
//        MarkersFactory.initMarkers(this); // create places on map
//        MarkersFactory.plotMarkers(this); // put them to the map
//    }

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
        int icons[] = {R.drawable.ic_action_location_found, R.drawable.ic_action_location_found, R.drawable.ic_action_location_found};
        //int icons[] = {R.drawable.vector_android, R.drawable.vector_android, R.drawable.vector_android};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i) {
                case MOVIES_SEARCH_RESULTS:
                    fragment = UpcomingFragment.newInstance("", "");
                    break;
                case MOVIES_HITS:
                    fragment = UpcomingFragment.newInstance("", "");
                    break;
                case MOVIES_UPCOMING:
                    fragment = UpcomingFragment.newInstance("", "");
                    break;
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 3;
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
