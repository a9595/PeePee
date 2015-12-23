package tieorange.edu.googlemapstest;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;

import static java.lang.Double.*;

/**
 * Created by tieorange on 23/12/15.
 */
public class MarkersFactory {

    static void initMarkers(MapsActivity activity) {
        // Initialize the HashMap for Markers and MyMarker object
        activity.mMarkersHashMap = new HashMap<>();
        // Init markers
        MyMarker markerKinoteka = new MyMarker("Kinoteka", "icon5", parseDouble("52.2309919"), parseDouble("21.00669907"));
        MyMarker markerBurgerKing = new MyMarker("BurgerKing", "icon3", parseDouble("52.22773123"), parseDouble("21.01449105"));
        MyMarker markerZloteTarasy = new MyMarker("Zlote tarasy", "icon6", parseDouble("52.2294632"), parseDouble("21.0036817"));
        MyMarker markerBasen = new MyMarker("Basen", "icon7", parseDouble("52.22837277"), parseDouble("21.00359623"));

        // add markers to my array
        activity.mMyMarkersArray.add(markerBurgerKing);
        activity.mMyMarkersArray.add(markerKinoteka);
        activity.mMyMarkersArray.add(markerZloteTarasy);
        activity.mMyMarkersArray.add(markerBasen);
        moveMapCameraTo(activity, markerKinoteka);


    }

    private static void moveMapCameraTo(MapsActivity activity, MyMarker markerMoveTo) {
        // Move camera
        final int zoomLevel = 16;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                markerMoveTo.getLatLng()
                , zoomLevel);

        final int durationAnimationZoomMs = 5000;
        activity.mMap.animateCamera(cameraUpdate, durationAnimationZoomMs, null);
    }

    static void plotMarkers(MapsActivity activity) {
        if (activity.mMyMarkersArray.size() > 0) { // check if is not empty
            for (MyMarker myMarker : activity.mMyMarkersArray) {
                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));

                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation_icon));

                Marker currentMarker = activity.mMap.addMarker(markerOption);
                activity.mMarkersHashMap.put(currentMarker, myMarker);

                activity.mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(activity)); // TODO: create a window for marker.click()112
            }
        }
    }
}
