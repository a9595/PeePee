package tieorange.edu.googlemapstest;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UpcomingFragment extends Fragment {

    private SupportMapFragment mMapView;
    private FloatingActionButton mFAB;

    public static UpcomingFragment newInstance(String param1, String param2) {
        UpcomingFragment fragment = new UpcomingFragment();
        return fragment;
    }

    MapView mapView;
    GoogleMap map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upcoming, container, false);
        // Gets the MapView from the XML layout and creates it

        mFAB = (FloatingActionButton) view.findViewById(R.id.fab);

        MapsInitializer.initialize(getActivity());
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        // Gets to GoogleMap from the MapView and does initialization stuff
        if (mapView != null) {
            map = mapView.getMap();
            map.getUiSettings().setMyLocationButtonEnabled(false);
            //map.setMyLocationEnabled(true);
            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(43.1, -87.9), 10);
            map.animateCamera(cameraUpdate);
        }


        // Updates the location and zoom of the MapView

        LatLng sydney = new LatLng(-33.867, 151.206);

//        map.setMyLocationEnabled(true);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));

        map.addMarker(new MarkerOptions()
                .title("Sydney")
                .snippet("The most populous city in Australia.")
                .position(sydney));

        return view;
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

        //MarkersFactory.initMarkers(this); // create places on map

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