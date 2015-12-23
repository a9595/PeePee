package tieorange.edu.googlemapstest;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

/**
 * Manipulates the map once available.
 * This callback is triggered when the map is ready to be used.
 * This is where we can add markers or lines, add listeners or move the camera. In this case,
 * we just add a marker near Sydney, Australia.
 * If Google Play services is not installed on the device, the user will be prompted to install
 * it inside the SupportMapFragment. This method will only be triggered once the user has
 * installed Google Play services and returned to the app.
 */


public class MarkerInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
    private MapsActivity mapsActivity;

    public MarkerInfoWindowAdapter(MapsActivity mapsActivity) {
        this.mapsActivity = mapsActivity;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View v = mapsActivity.getLayoutInflater().inflate(R.layout.infowindow_layout, null);

        MyMarker myMarker = mapsActivity.mMarkersHashMap.get(marker);

        ImageView markerIcon = (ImageView) v.findViewById(R.id.marker_icon);

        TextView markerLabel = (TextView) v.findViewById(R.id.marker_label);

        TextView anotherLabel = (TextView) v.findViewById(R.id.another_label);

        markerIcon.setImageResource(manageMarkerIcon(myMarker.getmIcon()));

        markerLabel.setText(myMarker.getmLabel());
        anotherLabel.setText("Opened: \n8:00 - 23:00");

        return v;
    }
    private int manageMarkerIcon(String markerIcon) {
        if (markerIcon.equals("icon1"))
            return R.drawable.icon1;
        else if (markerIcon.equals("icon2"))
            return R.drawable.icon2;
        else if (markerIcon.equals("icon3"))
            return R.drawable.icon3;
        else if (markerIcon.equals("icon4"))
            return R.drawable.icon4;
        else if (markerIcon.equals("icon5"))
            return R.drawable.icon5;
        else if (markerIcon.equals("icon6"))
            return R.drawable.icon6;
        else if (markerIcon.equals("icon7"))
            return R.drawable.icon7;
        else
            return R.drawable.icondefault;
    }
}
