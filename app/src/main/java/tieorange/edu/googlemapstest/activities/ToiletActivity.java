package tieorange.edu.googlemapstest.activities;

import android.animation.Animator;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaView;
import com.google.android.gms.maps.model.LatLng;

import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.pojo.MyMarker;

public class ToiletActivity extends AppCompatActivity {

    private StreetViewPanoramaView mStreetViewPanoramaView;
    private StreetViewPanorama mPanorama;

    private TextView mUiTextViewName;
    private TextView mUiTextViewDescription;
    private MyMarker myMarker;
    private Toolbar mUiToolbar;
    private ImageView mUiImageIcon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toilet);

        Intent intent = getIntent();
        myMarker = (MyMarker) intent.getSerializableExtra("name");
        findViews();


        mUiTextViewName.setText(myMarker.getLabel());
        mUiTextViewDescription.setText("Opened: 8:00 - 23:00");
        mUiImageIcon.setImageResource(myMarker.getIconBlackWhite());

        setupStreetViewPanorama(savedInstanceState);

    }

    private void findViews() {
        //        mUiToolbar = (Toolbar) findViewById(R.id.toilet_toolbar);
        mUiTextViewName = (TextView) findViewById(R.id.toilet_name);
        mUiTextViewDescription = (TextView) findViewById(R.id.toilet_description);
        mUiImageIcon = (ImageView) findViewById(R.id.toilet_imageView_icon);
    }

    private void setupStreetViewPanorama(Bundle savedInstanceState) {
        // STREET VIEW init
        mStreetViewPanoramaView = (StreetViewPanoramaView) findViewById(R.id.steet_view_panorama);
        mStreetViewPanoramaView.onCreate(savedInstanceState);
        mStreetViewPanoramaView.getStreetViewPanoramaAsync(new OnStreetViewPanoramaReadyCallback() {
            @Override
            public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                revealPanorama(mStreetViewPanoramaView);
                panorama.setPosition(new LatLng(55.758818, 37.620587));
                mPanorama = panorama;
            }
        });
    }

    private void setupToolbar() {
        setSupportActionBar(mUiToolbar);
        // Show menu icon
        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_app_icon);
        ab.setDisplayHomeAsUpEnabled(true);
    }

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
        mStreetViewPanoramaView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStreetViewPanoramaView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mStreetViewPanoramaView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mStreetViewPanoramaView.onLowMemory();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mStreetViewPanoramaView.onSaveInstanceState(outState);
    }

//    private void setupFab() {
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
//    }


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
