package tieorange.edu.googlemapstest.fragments;


import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;


import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;
import pl.tajchert.nammu.PermissionListener;
import tieorange.edu.googlemapstest.MarkersFactory;
import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.activities.MainActivity;
import tieorange.edu.googlemapstest.pojo.MyMarker;

public class MapFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private View view;
    private MainActivity mainActivity;
    public MarkersFactory markersFactory;
    public ArrayList<MyMarker> markersFromDatabase;

    private GoogleApiClient mGoogleApiClient;
    private Location mCurrentLocation;

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
        Nammu.init(getActivity().getApplicationContext());


        mainActivity = (MainActivity) getActivity(); // to get GoogleMap object and share it
        setupMap(savedInstanceState, view);


        return view;
    }

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loc = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.addMarker(new MarkerOptions().position(loc));
            if (mMap != null) {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 16.0f));
            }
        }
    };

    private void setupMap(Bundle savedInstanceState, final View view) {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        MapsInitializer.initialize(getActivity());
        mapView = (MapView) view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        // Gets to GoogleMap from the MapView and does initialization stuff
        if (mapView != null) {
//            mMap = mapView.getMap();
            mainActivity.setMap(mapView.getMap());
            mMap = mainActivity.getMap();

            mMap.getUiSettings().setMyLocationButtonEnabled(true);
//            mMap.setOnMyLocationChangeListener(myLocationChangeListener);


//            mMap.snapshot(new GoogleMap.SnapshotReadyCallback() {
//                @Override
//                public void onSnapshotReady(Bitmap bitmap) {
//
//                }
//            });

            //mMap.setMyLocationEnabled(true);


        }

//        // TODO: get markers from CSV
//        markersFromDatabase = MyMarker.getMarkersAllMarkersList(getActivity());
        markersFromDatabase = mainActivity.markersFromDatabase;


        markersFactory = new MarkersFactory(getActivity(), mMap, markersFromDatabase);

        markersFactory.initMarkers(); // create places on mMap
        markersFactory.plotMarkers(); // put them to the mMap


    }

    private void moveMapCameraTo(Location location) {
        // Move camera
        final int zoomLevel = 15;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                new LatLng(location.getLatitude(), location.getLongitude())
                , zoomLevel);

//        final int durationAnimationZoomMs = 0;
//        mMap.animateCamera(cameraUpdate, durationAnimationZoomMs, null);
        mMap.animateCamera(cameraUpdate);
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();

        Nammu.permissionCompare(new PermissionListener() {
            @Override
            public void permissionsChanged(String permissionRevoke) {
                //Toast is not needed as always either permissionsGranted() or permissionsRemoved() will be called
                //Toast.makeText(MainActivity.this, "Access revoked = " + permissionRevoke, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void permissionsGranted(String permissionGranted) {
                Toast.makeText(getActivity().getApplicationContext(), "Access granted = " + permissionGranted, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void permissionsRemoved(String permissionRemoved) {
                Toast.makeText(getActivity().getApplicationContext(), "Access removed = " + permissionRemoved, Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    public GoogleMap getMap() {
        return mMap;
    }

    //    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 123);

            return;
        }
//        getCurrentUserLocation();

        //moveMapCameraTo(mCurrentLocation);
    }

    public static boolean accessLocation(Context context) {
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        String bestProvider = locationManager.getBestProvider(criteria, false);
        //Location location = locationManager.getLastKnownLocation(bestProvider);
        if (bestProvider == null) {
            //No android.permission-group.LOCATION
            return false;
        }
        return true;
    }

//    public void clickButtLocation() {
//        if (Nammu.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
//            boolean hasAccess = accessLocation(getActivity().getApplicationContext());
//            Toast.makeText(getActivity().getApplicationContext(), "Access granted fine= " + hasAccess, Toast.LENGTH_SHORT).show();
//        } else {
//            if (Nammu.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
//                //User already refused to give us this permission or removed it
//                //Now he/she can mark "never ask again" (sic!)
//                Snackbar.make(getView(), "Here we explain user why we need to know his/her location.",
//                        Snackbar.LENGTH_INDEFINITE)
//                        .setAction("OK", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                Nammu.askForPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, permissionLocationCallback);
//                            }
//                        }).show();
//            } else {
//                //First time asking for permission
//                // or phone doesn't offer permission
//                // or user marked "never ask again"
//                Nammu.askForPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION, permissionLocationCallback);
//            }
//        }
//    }

    /**
     * Used to handle result of askForPermission for Location, in better way than onRequestPermissionsResult() and handling with big switch statement
     */
//    final PermissionCallback permissionLocationCallback = new PermissionCallback() {
//        @Override
//        public void permissionGranted() {
//            boolean hasAccess = accessLocation(getActivity());
//            Toast.makeText(getActivity(), "Access granted = " + hasAccess, Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void permissionRefused() {
//            boolean hasAccess = accessLocation(getActivity());
//            Toast.makeText(getActivity(), "Access granted = " + hasAccess, Toast.LENGTH_SHORT).show();
//        }
//    };

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Nammu.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}