package tieorange.edu.googlemapstest.pojo;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import java.util.ArrayList;

import static java.lang.Double.parseDouble;

/**
 * Created by tieorange on 11/12/15.
 */
public class MyMarker {
    private String mLabel;
    private String mIcon;
    private Double mLatitude;
    private Double mLongitude;
    public static ArrayList<MyMarker> markersArray = new ArrayList<>();


    public MyMarker(String label, String icon, Double latitude, Double longitude) {
        this.mLabel = label;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mIcon = icon;
    }

    public static ArrayList<MyMarker> getDummyMarkersFromDatabase()
    {
        // TODO: CSV reader from file
        MyMarker markerKinoteka = new MyMarker("Kinoteka", "icon5", parseDouble("52.2309919"), parseDouble("21.00669907"));
        MyMarker markerBurgerKing = new MyMarker("BurgerKing", "icon3", parseDouble("52.22773123"), parseDouble("21.01449105"));
        MyMarker markerZloteTarasy = new MyMarker("Zlote tarasy", "icon6", parseDouble("52.2294632"), parseDouble("21.0036817"));
        MyMarker markerBasen = new MyMarker("Basen", "icon7", parseDouble("52.22837277"), parseDouble("21.00359623"));

        // add markers to my array

        markersArray.add(markerBurgerKing);
        markersArray.add(markerKinoteka);
        markersArray.add(markerZloteTarasy);
        markersArray.add(markerBasen);

        return  markersArray;
    }



    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String mLabel) {
        this.mLabel = mLabel;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        this.mIcon = icon;
    }

    public Double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(Double mLatitude) {
        this.mLatitude = mLatitude;
    }

    public Double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(Double mLongitude) {
        this.mLongitude = mLongitude;
    }

    public LatLng getLatLng()
    {
        return new LatLng(mLatitude, mLongitude);
    }

    @Override
    public String toString() {
        return mLabel;
    }
}