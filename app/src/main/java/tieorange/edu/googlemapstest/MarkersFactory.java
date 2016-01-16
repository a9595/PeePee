package tieorange.edu.googlemapstest;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tieorange.edu.googlemapstest.activities.ToiletActivity;
import tieorange.edu.googlemapstest.adapters.MarkerInfoWindowAdapter;
import tieorange.edu.googlemapstest.pojo.MyMarker;

/**
 * Created by tieorange on 23/12/15.
 */
public class MarkersFactory {
    private HashMap<Marker, MyMarker> mMarkersHashMap;
    private ArrayList<MyMarker> mMyMarkersArray;
    private final GoogleMap mMap;
    private final Activity mActivity;

    public MarkersFactory(Activity activity, GoogleMap map, ArrayList<MyMarker> dummyMarkersFromDatabase) {
        this.mActivity = activity;
        this.mMap = map;

        mMyMarkersArray = dummyMarkersFromDatabase;
        mMarkersHashMap = new HashMap<>();
    }


    public void initMarkers() {
        //moveMapCameraTo(mMyMarkersArray.get(1));
    }

    private void moveMapCameraTo(MyMarker markerMoveTo) {
        // Move camera
        final int zoomLevel = 15;
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                markerMoveTo.getLatLng()
                , zoomLevel);

//        final int durationAnimationZoomMs = 0;
//        mMap.animateCamera(cameraUpdate, durationAnimationZoomMs, null);
        mMap.animateCamera(cameraUpdate);
    }

    public HashMap<Marker, MyMarker> plotMarkers() {
        if (mMyMarkersArray.size() > 0) { // check if is not empty
            for (MyMarker myMarker : mMyMarkersArray) {
                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getLatitude(), myMarker.getLongitude()));

                //get Drawable from vector
                final BitmapDescriptor iconByMarkerType = getBitmapDescriptorByMarkerType(myMarker);
//                final BitmapDescriptor iconByMarkerType = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_other_working_png);

                markerOption.icon(iconByMarkerType);
//                markerOption.alpha(0.9f);

                Marker currentMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);

            }
            setupMarkerInfoWindow();
        }
        return mMarkersHashMap;
    }


    // TODO: mock
    public HashMap<Marker, MyMarker> plotMarkersWithOtherIcons() {
        mMap.clear();

        if (mMyMarkersArray.size() > 0) { // check if is not empty
            for (MyMarker myMarker : mMyMarkersArray) {
//                if (filter_selected_items.contains(myMarker.getType())) {
                // Create user marker with custom icon and other options
                MarkerOptions markerOption = new MarkerOptions().position(new LatLng(myMarker.getLatitude(), myMarker.getLongitude()));

                //get Drawable from vector
                final BitmapDescriptor markerIcon = getBitmapDescriptorByMarkerType(myMarker);
//                final BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_other_working_png);

                markerOption.icon(markerIcon);

                Marker currentMarker = mMap.addMarker(markerOption);
                mMarkersHashMap.put(currentMarker, myMarker);
//                }
            }
            setupMarkerInfoWindow();
        }
        return mMarkersHashMap;
    }

    private BitmapDescriptor getBitmapDescriptorByMarkerType(MyMarker myMarker) {
        BitmapDescriptor icon_marker_result = getBitmapDescriptor(R.drawable.ic_marker_other_working, mActivity.getApplicationContext());

        switch (myMarker.getType()) {
            case MyMarker.MARKER_TYPE_RESTAURANT:
                if (myMarker.isFree())
                    icon_marker_result = getBitmapDescriptor(R.drawable.ic_marker_restaurant_working, mActivity.getApplicationContext());
                else
                    icon_marker_result = getBitmapDescriptor(R.drawable.ic_marker_restaurant_payed, mActivity.getApplicationContext());
                break;
            case MyMarker.MARKER_TYPE_TOI_TOI:
                icon_marker_result = getBitmapDescriptor(R.drawable.ic_marker_toi_toi_working, mActivity.getApplicationContext());
                break;
            case MyMarker.MARKER_TYPE_OTHER:
                if (myMarker.isFree())
                    icon_marker_result = getBitmapDescriptor(R.drawable.ic_marker_other_working, mActivity.getApplicationContext());
                else
                    icon_marker_result = getBitmapDescriptor(R.drawable.ic_marker_other_payed, mActivity.getApplicationContext());
                break;
            case MyMarker.MARKER_TYPE_METRO:
                icon_marker_result = getBitmapDescriptor(R.drawable.ic_marker_toi_toi_working, mActivity.getApplicationContext());

                break;
            case MyMarker.MARKER_TYPE_HOTEL:
                if (myMarker.getLabel().contains("Hotel"))
                    icon_marker_result = getBitmapDescriptor(R.drawable.ic_marker_other_payed, mActivity.getApplicationContext());
                else
                    icon_marker_result = getBitmapDescriptor(R.drawable.ic_marker_other_working, mActivity.getApplicationContext());

                break;

            case MyMarker.MARKER_TYPE_SWIMMING_POOL:
                if (myMarker.getLabel().contains("Fitness"))
                    icon_marker_result = getBitmapDescriptor(R.drawable.ic_marker_restaurant_payed, mActivity.getApplicationContext());
                else
                    icon_marker_result = getBitmapDescriptor(R.drawable.ic_marker_restaurant_working, mActivity.getApplicationContext());
                break;
            default:
                break;
        }


        return icon_marker_result;
    }


    public void setupMarkerInfoWindow() {
        final MarkerInfoWindowAdapter infoWindowAdapter = new MarkerInfoWindowAdapter(mMarkersHashMap, mActivity);
        mMap.setInfoWindowAdapter(infoWindowAdapter); // TODO: create a window for marker.click()112
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            //            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onInfoWindowClick(Marker marker) {
                Log.i("MY", String.valueOf(marker.getPosition().latitude));

                MyMarker myMarker = mMarkersHashMap.get(marker);
                Log.i("MY", myMarker.getLabel());

                Intent i = new Intent(mActivity, ToiletActivity.class);
                i.putExtra(ToiletActivity.EXTRA_MY_MARKER, myMarker);

                // Shared element transition
                //final ImageView markerIconImageView = infoWindowAdapter.getMarkerIconImageView();
//                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(mActivity,
//                        markerIconImageView, mActivity.getString(R.string.transition_name_list_view));


                mActivity.startActivity(i);
//                mActivity.startActivity(i, activityOptions.toBundle());
            }
        });
    }

    //    @TargetApi(Build.VERSION_CODES.M)
    private BitmapDescriptor getBitmapDescriptor(int id, Context context) {
        Drawable vectorDrawable = context.getDrawable(id);
//        int color =  ContextCompat.getColor(mActivity, R.color.accent);

        //vectorDrawable.setColorFilter(color, PorterDuff.Mode.SRC_IN);

//        int vectorDrawableIntrinsicHeight = ((int) Utils.convertDpToPixel(42, context));
//        int vectorDrawableIntrinsicWidth = ((int) Utils.convertDpToPixel(25, context));
//        final int icon_resize_const = 50;
//        int vectorDrawableIntrinsicHeight = vectorDrawable.getIntrinsicHeight() + icon_resize_const;
//        int vectorDrawableIntrinsicWidth = vectorDrawable.getIntrinsicWidth() + icon_resize_const;

        //original size
        int vectorDrawableIntrinsicHeight = vectorDrawable.getIntrinsicHeight();
        int vectorDrawableIntrinsicWidth = vectorDrawable.getIntrinsicWidth();

        vectorDrawable.setBounds(0, 0, vectorDrawableIntrinsicWidth, vectorDrawableIntrinsicHeight);
        Bitmap bm = Bitmap.createBitmap(vectorDrawableIntrinsicWidth, vectorDrawableIntrinsicHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bm);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bm);
    }
}
