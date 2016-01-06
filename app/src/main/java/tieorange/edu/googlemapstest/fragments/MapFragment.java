package tieorange.edu.googlemapstest.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

import tieorange.edu.googlemapstest.MarkersFactory;
import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.activities.MainActivity;
import tieorange.edu.googlemapstest.pojo.MyMarker;

public class MapFragment extends Fragment {
    private View view;
    private MainActivity mainActivity;
    public MarkersFactory markersFactory;

    public MapFragment() {
    }

    public static MapFragment newInstance(String param1, String param2) {
        MapFragment fragment = new MapFragment();
        return fragment;
    }

    MapView mapView;
    GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_map, container, false);


        mainActivity = (MainActivity) getActivity(); // to get GoogleMap object and share it
        setupMap(savedInstanceState, view);


        return view;
    }

    private void setupMap(Bundle savedInstanceState, final View view) {
        MapsInitializer.initialize(getActivity());
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        // Gets to GoogleMap from the MapView and does initialization stuff
        if (mapView != null) {
//            mMap = mapView.getMap();
            mainActivity.setMap(mapView.getMap());
            mMap = mainActivity.getMap();

            mMap.getUiSettings().setMyLocationButtonEnabled(true);


            //mMap.setMyLocationEnabled(true);


        }

//        // TODO: get markers from CSV
        ArrayList<MyMarker> dummyMarkersFromDatabase = MyMarker.getDummyMarkersFromDatabase();

        markersFactory = new MarkersFactory(getActivity(), mMap, dummyMarkersFromDatabase);

        markersFactory.initMarkers(); // create places on mMap
        markersFactory.plotMarkers(); // put them to the mMap

//        mainActivity.getMarkersFactory().initMarkers(); // create places on mMap
//        mainActivity.getMarkersFactory().plotMarkers(); // put them to the mMap


//        // TODO: infowindow
//        mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(mMarkersHashMap, getActivity())); // TODO: create a window for marker.click()112
//        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Log.i("MY", String.valueOf(marker.getPosition().latitude));
//
//                MyMarker myMarker = mMarkersHashMap.get(marker);
//                Log.i("MY", myMarker.getLabel());
//
//                Intent i = new Intent(getActivity(), ToiletActivity.class);
//                i.putExtra("name", myMarker);
//
//                final ImageView UiMarkerIcon = (ImageView) view.findViewById(R.id.info_layout_marker_icon);
//                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(getActivity(),
//                        UiMarkerIcon, getString(R.string.transition_name_list_view));
//
//                getActivity().startActivity(i, activityOptions.toBundle());
//            }
//        });

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


    public GoogleMap getMap() {
        return mMap;
    }
}