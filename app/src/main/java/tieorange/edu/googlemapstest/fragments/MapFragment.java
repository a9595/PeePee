package tieorange.edu.googlemapstest.fragments;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.util.ArrayList;
import java.util.HashMap;

import tieorange.edu.googlemapstest.MarkersFactory;
import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.pojo.MyMarker;

public class MapFragment extends Fragment {
    public HashMap<Marker, MyMarker> mMarkersHashMap;
    static ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();


    private SupportMapFragment mMapView;
    private FloatingActionButton mFAB;

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    MapView mapView;
    GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        // Gets the MapView from the XML layout and creates it

        mFAB = (FloatingActionButton) view.findViewById(R.id.fab);

        setupMap(savedInstanceState, view);


        return view;
    }

    private void setupMap(Bundle savedInstanceState, View view) {
        MapsInitializer.initialize(getActivity());
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        // Gets to GoogleMap from the MapView and does initialization stuff
        if (mapView != null) {
            mMap = mapView.getMap();
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            //mMap.setMyLocationEnabled(true);

        }

        // TODO: get markers from CSV
        ArrayList<MyMarker> dummyMarkersFromDatabase = MyMarker.getDummyMarkersFromDatabase();

        MarkersFactory markersFactory = new MarkersFactory(getActivity(), mMap, dummyMarkersFromDatabase);


        markersFactory.initMarkers(); // create places on mMap
        markersFactory.plotMarkers(); // put them to the mMap
    }

    private void setupFab() {
        mFAB = (FloatingActionButton) getView().findViewById(R.id.fab);
        mFAB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.fab)
                    getUserLocation();
            }
        });
    }
    private void getUserLocation() {
//        Snackbar
//                .make(findViewById(R.id.main_content),
//                        "Finding the nearest Pee Pee :)",
//                        Snackbar.LENGTH_SHORT)
//                .show(); // Do not forget to show!

        //MarkersFactory.initMarkers(this); // create places on mMap

        // TODO: getUserLocation: http://hmkcode.com/material-design-app-android-design-support-library-appcompat/

    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}