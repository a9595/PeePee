package tieorange.edu.googlemapstest;

import android.app.Activity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

import tieorange.edu.googlemapstest.adapters.MarkerInfoWindowAdapter;
import tieorange.edu.googlemapstest.pojo.MyMarker;

import static java.lang.Double.*;

/**
 * Created by tieorange on 23/12/15.
 */
public class MarkersFactory {
    private HashMap<Marker, MyMarker> mMarkersHashMap;
    private ArrayList<MyMarker> mMyMarkersArray;
    private final GoogleMap mMap;
    private final Activity mActivity;

    public MarkersFactory(Activity activity, GoogleMap map) {
        this.mActivity = activity;
        this.mMap = map;

        mMyMarkersArray = new ArrayList<>();
        mMarkersHashMap = new HashMap<>();
    }


    public void initMarkers() {
        mMyMarkersArray = MyMarker.getDummyMarkersFromDatabase();
        moveMapCameraTo(mMyMarkersArray.get(1));
    }

    private void moveMapCameraTo(MyMarker markerMoveTo) {
        // Move camera
        final int zoomLevel = 15;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                markerMoveTo.getLatLng()
                , zoomLevel);

        final int durationAnimationZoomMs = 3000;
        mMap.animateCamera(cameraUpdate, durationAnimationZoomMs, null);
    }

    public void plotMarkers() {
        if (mMyMarkersArray.size() > 0) { // check if is not empty
            for (MyMarker myMarker : mMyMarkersArray) {
                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getLatitude(), myMarker.getLongitude()));

                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation_icon));

                Marker currentMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(mMarkersHashMap, mActivity)); // TODO: create a window for marker.click()112
            }
        }
    }
}
