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


    void initMarkers() {
        // Init markers
        MyMarker markerKinoteka = new MyMarker("Kinoteka", "icon5", parseDouble("52.2309919"), parseDouble("21.00669907"));
        MyMarker markerBurgerKing = new MyMarker("BurgerKing", "icon3", parseDouble("52.22773123"), parseDouble("21.01449105"));
        MyMarker markerZloteTarasy = new MyMarker("Zlote tarasy", "icon6", parseDouble("52.2294632"), parseDouble("21.0036817"));
        MyMarker markerBasen = new MyMarker("Basen", "icon7", parseDouble("52.22837277"), parseDouble("21.00359623"));

        // add markers to my array

        mMyMarkersArray.add(markerBurgerKing);

        mMyMarkersArray.add(markerKinoteka);

        mMyMarkersArray.add(markerZloteTarasy);

        mMyMarkersArray.add(markerBasen);
        moveMapCameraTo(markerKinoteka);


    }

    private void moveMapCameraTo(MyMarker markerMoveTo) {
        // Move camera
        final int zoomLevel = 16;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                markerMoveTo.getLatLng()
                , zoomLevel);

        final int durationAnimationZoomMs = 3000;
        mMap.animateCamera(cameraUpdate, durationAnimationZoomMs, null);
    }

    void plotMarkers() {
        if (mMyMarkersArray.size() > 0) { // check if is not empty
            for (MyMarker myMarker : mMyMarkersArray) {
                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));

                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation_icon));

                Marker currentMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(mMarkersHashMap, mActivity)); // TODO: create a window for marker.click()112
            }
        }
    }
}
