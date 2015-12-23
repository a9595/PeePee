package tieorange.edu.googlemapstest;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap; // object of a map
    HashMap<Marker, MyMarker> mMarkersHashMap;
    private ArrayList<MyMarker> mMyMarkersArray = new ArrayList<MyMarker>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mMap = mapFragment.getMap();
        //initMarkers();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.showInfoWindow();
                return true;
            }
        });

        initMarkers(); // create places on map
        plotMarkers(mMyMarkersArray); // put them to the map
    }


    private void initMarkers() {
        // Initialize the HashMap for Markers and MyMarker object
        mMarkersHashMap = new HashMap<>();
        // Init markers
        MyMarker markerKinoteka = new MyMarker("Kinoteka", "icon5",
                Double.parseDouble("52.2309919"), Double.parseDouble("21.00669907"));
        MyMarker markerBurgerKing = new MyMarker("BurgerKing", "icon3", Double.parseDouble("52.22773123"), Double.parseDouble("21.01449105"));
        MyMarker markerZloteTarasy = new MyMarker("Zlote tarasy", "icon6", Double.parseDouble("52.2294632"), Double.parseDouble("21.0036817"));
        MyMarker markerBasen = new MyMarker("Basen", "icon7", Double.parseDouble("52.22837277"), Double.parseDouble("21.00359623"));

        // add markers to my array
        mMyMarkersArray.add(markerBurgerKing);
        mMyMarkersArray.add(markerKinoteka);
        mMyMarkersArray.add(markerZloteTarasy);
        mMyMarkersArray.add(markerBasen);

        // Move camera
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(markerKinoteka.getmLatitude(), markerKinoteka.getmLongitude())));
    }

    private void plotMarkers(ArrayList<MyMarker> markers) {
        if (markers.size() > 0) { // check if is not empty
            for (MyMarker myMarker : markers) {

                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getmLatitude(), myMarker.getmLongitude()));
                markerOption.icon(BitmapDescriptorFactory.fromResource(R.drawable.currentlocation_icon));

                Marker currentMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

                mMap.setInfoWindowAdapter(new MarkerInfoWindowAdapter(this)); // TODO: create a window for marker.click()
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://tieorange.edu.googlemapstest/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Maps Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://tieorange.edu.googlemapstest/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }




}
