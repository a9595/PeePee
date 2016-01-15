package tieorange.edu.googlemapstest.fragments;


import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import tieorange.edu.googlemapstest.MarkersFactory;
import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.activities.MainActivity;
import tieorange.edu.googlemapstest.pojo.MyMarker;

public class MapFragment extends Fragment {
    private View view;
    private MainActivity mainActivity;
    public MarkersFactory markersFactory;
    public ArrayList<MyMarker> markersFromDatabase;

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
            mainActivity.setMap(mapView.getMap());
            mMap = mainActivity.getMap();
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setMyLocationEnabled(true);

            setCurrentUserPositionOnMap();
        }

//        // TODO: get markers from CSV
        markersFromDatabase = mainActivity.markersFromDatabase;

        markersFactory = new MarkersFactory(getActivity(), mMap, markersFromDatabase);

        markersFactory.initMarkers(); // create places on mMap
        markersFactory.plotMarkers(); // put them to the mMap
    }

    private void moveMapCameraTo(LatLng currentUserLocation) {
        final int zoomLevel = 14;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentUserLocation, zoomLevel);
        mMap.animateCamera(cameraUpdate);
    }

    private void setCurrentUserPositionOnMap() {
        LatLng currentUserLocation = getCurrentUserLocation(getActivity());
        moveMapCameraTo(currentUserLocation);

        MarkerOptions markerOption = new MarkerOptions().position(currentUserLocation);
//        markerOption.icon()


        mMap.addMarker(new MarkerOptions().
                position(getCurrentUserLocation(getActivity())).title("myLocation")); // add marker
    }

    public static LatLng getCurrentUserLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_MEDIUM);
        String bestProvider = locationManager.getBestProvider(criteria, false);
        Location location = locationManager.getLastKnownLocation(bestProvider);
        LatLng currentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        return currentLatLng;
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
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