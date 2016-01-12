package tieorange.edu.googlemapstest.activities;

import android.Manifest;
import android.animation.Animator;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import tieorange.edu.googlemapstest.MarkersFactory;
import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.pojo.MyMarker;

public class ToiletActivity extends AppCompatActivity {

    public static final String EXTRA_MY_MARKER = "my_marker";
    //    private StreetViewPanoramaView mStreetViewPanoramaView;
    private StreetViewPanorama mPanorama;

    private TextView mUiTextViewName;
    private TextView mUiTextViewDescription;
    private MyMarker mMyMarker;
    private ImageView mUiImageIcon;
    private MapView mapView;
    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toilet);

        Intent intent = getIntent();
        mMyMarker = (MyMarker) intent.getSerializableExtra(EXTRA_MY_MARKER);
        findViews();


        mUiTextViewName.setText(mMyMarker.getLabel());
        mUiTextViewDescription.setText("Opened: 8:00 - 23:00");
        mUiImageIcon.setImageResource(mMyMarker.getIconBlackWhite());

        setupMap(savedInstanceState, this);
        //setupStreetViewPanorama(savedInstanceState);


        //image map:
        String url = "http://maps.google.com/maps/api/staticmap?center=" + mMyMarker.getLatitude() + "," + mMyMarker.getLongitude() + "&zoom=15&size=200x200&sensor=false";

        Toolbar mUiToolbar = (Toolbar) findViewById(R.id.toilet_toolbar);
        setSupportActionBar(mUiToolbar);

        final ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setHomeAsUpIndicator(R.drawable.ic_toobar_white_drop);
        supportActionBar.setDisplayHomeAsUpEnabled(true);

        supportActionBar.setDisplayShowTitleEnabled(false);

    }

    private void setupMap(Bundle savedInstanceState, Context context) {
        MapsInitializer.initialize(this);
        mapView = (MapView) findViewById(R.id.toilet_map);
        mapView.onCreate(savedInstanceState);
        // Gets to GoogleMap from the MapView and does initialization stuff
        if (mapView != null) {

            mMap = mapView.getMap();

            mMap.getUiSettings().setMyLocationButtonEnabled(true);


            //mMap.setMyLocationEnabled(true);

            MarkerOptions markerOption = new MarkerOptions().position(new LatLng(this.mMyMarker.getLatitude(),
                    mMyMarker.getLongitude()));

            mMap.addMarker(markerOption);


//Disable Map Toolbar:
            mMap.getUiSettings().setMapToolbarEnabled(false);

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                public boolean onMarkerClick(Marker marker) {
//                    mMap.onMapClick(marker.getPosition());
                    return false;
                }
            });

        }

        moveMapCameraTo(mMyMarker);


    }

    private void moveMapCameraTo(MyMarker markerMoveTo) {
        // Move camera
        final int zoomLevel = 15;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                markerMoveTo.getLatLng()
                , zoomLevel);

        final int durationAnimationZoomMs = 1;
//        mMap.animateCamera(cameraUpdate, durationAnimationZoomMs, null);

        mMap.animateCamera(cameraUpdate);
    }

    private void findViews() {
        //        mUiToolbar = (Toolbar) findViewById(R.id.toilet_toolbar);
        mUiTextViewName = (TextView) findViewById(R.id.toilet_name);
        mUiTextViewDescription = (TextView) findViewById(R.id.toilet_description);
        mUiImageIcon = (ImageView) findViewById(R.id.toilet_imageView_icon);
    }

//    private void setupStreetViewPanorama(Bundle savedInstanceState) {
//        // STREET VIEW init
//        mStreetViewPanoramaView = (StreetViewPanoramaView) findViewById(R.id.toilet_street_view_panorama);
//        mStreetViewPanoramaView.onCreate(savedInstanceState);
//        mStreetViewPanoramaView.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
//            @Override
//            public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
//                revealPanorama(mStreetViewPanoramaView);
//                panorama.setPosition(new LatLng(55.758818, 37.620587));
//                mPanorama = panorama;
//            }
//        });
//    }


    public void revealPanorama(View view) {
        if (view.getVisibility() == View.VISIBLE) return;
        int center_x = view.getWidth() / 2;
        int center_y = view.getHeight() / 2;
        // Get the center of the figure:

        // Get the final radius for the clipping circle
        int finalRadius = Math.max(view.getWidth(), view.getHeight());

        // Create the animator for the view
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            try {
                Animator animator = ViewAnimationUtils.createCircularReveal(view, center_x, center_y, 0, finalRadius);
                animator.start();

            } catch (Exception e) {

            }

            view.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        //mStreetViewPanoramaView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        //mStreetViewPanoramaView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mStreetViewPanoramaView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
        //mStreetViewPanoramaView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        mStreetViewPanoramaView.onSaveInstanceState(outState);
    }


//    private void showStreetView(LatLng latLng) {
//        if (mStreetViewPanorama == null)
//            return;
//
////        StreetViewPanoramaCamera.Builder builder = new StreetViewPanoramaCamera.Builder( mStreetViewPanorama.getPanoramaCamera() );
////        builder.tilt( 0.0f );
////        builder.zoom( 0.0f );
////        builder.bearing( 0.0f );
////        mStreetViewPanorama.animateTo( builder.build(), 0 );
//
//        mStreetViewPanorama.setPosition(latLng, 300);
//        //mStreetViewPanorama.setStreetNamesEnabled( true );
//    }
}
