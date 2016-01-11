package tieorange.edu.googlemapstest.pojo;

import android.content.Context;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;

import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.models.GsonHelper;
import tieorange.edu.googlemapstest.models.Hotel.Hotel;
import tieorange.edu.googlemapstest.models.Hotel.HotelsList;

import static java.lang.Double.parseDouble;

/**
 * Created by tieorange on 11/12/15.
 */
public class MyMarker implements Serializable {
    public static final int MARKER_TYPE_RESTAURANT = 0;
    public static final int MARKER_TYPE_TOI_TOI = 1;
    public static final int MARKER_TYPE_OTHER = 2;

    private String Label;
    private String Icon;
    private boolean isWorkingNow;
    private boolean isFree;
    private Double Latitude;
    private Double Longitude;

    private int Type;


    public MyMarker(String label, int type, boolean isFree, boolean isWorkingNow, Double latitude, Double longitude) {
        this.Label = label;
        this.isWorkingNow = isWorkingNow;
        this.isFree = isFree;
        this.Latitude = latitude;
        this.Longitude = longitude;
        this.Type = type;
    }

    public MyMarker(int type, Double longitude, Double latitude, String label) {
        Type = type;
        Longitude = longitude;
        Latitude = latitude;
        Label = label;
    }

    public static ArrayList<MyMarker> getDummyMarkersFromDatabase(Context context) {
        ArrayList<MyMarker> markersArray = new ArrayList<>();

        // TODO: CSV reader from file

        // other: {free, payed}
        MyMarker markerKinoteka = new MyMarker("Kinoteka free", MyMarker.MARKER_TYPE_OTHER, true, true,
                parseDouble("52.2309919"), parseDouble("21.00669907"));
        MyMarker markerZloteTarasy = new MyMarker("Metro Ratusz Arsenał", MARKER_TYPE_OTHER, false, true,
                parseDouble("52.24501384"), parseDouble("21.00135326"));
        MyMarker markerOtherNotWorking = new MyMarker("Rondo Dmowskiego - podziemia", MARKER_TYPE_OTHER, true, false,
                parseDouble("52.2301036"), parseDouble("21.0116003"));

        // restaurant: {free, payed}
        MyMarker markerBurgerKing = new MyMarker("BurgerKing", MyMarker.MARKER_TYPE_RESTAURANT, true, true,
                parseDouble("52.22773123"), parseDouble("21.01449105"));
        MyMarker markerRestaurantPayed = new MyMarker("Dworzec Centralny - McDonald's", MyMarker.MARKER_TYPE_RESTAURANT, true, true,
                parseDouble("52.228777"), parseDouble("21.003318"));
        MyMarker markerRestaurantNotWorking = new MyMarker("markerRestaurantNotWorking", MyMarker.MARKER_TYPE_RESTAURANT, true, false,
                parseDouble("52.23773123"), parseDouble("21.01449105"));

        // toi toi {free}
        MyMarker markerBasen = new MyMarker("Toi-Toi", MARKER_TYPE_TOI_TOI, true, true,
                parseDouble("52.22837277"), parseDouble("21.02359623"));
        MyMarker markerToiToiNotWorking = new MyMarker("Toi-Toi not working", MARKER_TYPE_TOI_TOI, true, true,
                parseDouble("52.23837277"), parseDouble("21.00359623"));


        // add markers to my array

        markersArray.add(markerBurgerKing);
        markersArray.add(markerKinoteka);
        markersArray.add(markerZloteTarasy);
        markersArray.add(markerBasen);
        markersArray.add(markerOtherNotWorking);
        markersArray.add(markerRestaurantPayed);
        markersArray.add(markerRestaurantNotWorking);
        markersArray.add(markerToiToiNotWorking);

        // Hotels
        final HotelsList hotels = GsonHelper.getHotels(context);
        for (Hotel hotel : hotels.data) {
            MyMarker hotelMarker = hotel.getMyMarker();
            markersArray.add(hotelMarker);
        }

        return markersArray;
    }

    public int getIconBlackWhite() {
        int icon_marker_result = R.drawable.ic_marker_icon;

        switch (getType()) {
            case MyMarker.MARKER_TYPE_RESTAURANT:
                icon_marker_result = R.drawable.ic_marker_restaurant_black_white;
                break;
            case MyMarker.MARKER_TYPE_TOI_TOI:
                icon_marker_result = R.drawable.ic_marker_toi_toi_black_white;
                break;
            case MyMarker.MARKER_TYPE_OTHER:
                icon_marker_result = R.drawable.ic_marker_other_black_white;
                break;
            default:
                break;
        }


        return icon_marker_result;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String mLabel) {
        this.Label = mLabel;
    }

    public String getIcon() {
        return Icon;
    }

    public void setIcon(String icon) {
        this.Icon = icon;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double mLatitude) {
        this.Latitude = mLatitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double mLongitude) {
        this.Longitude = mLongitude;
    }

    public LatLng getLatLng() {
        return new LatLng(Latitude, Longitude);
    }

    @Override
    public String toString() {
        return Label;
    }

    public boolean isWorkingNow() {
        return isWorkingNow;
    }

    public void setWorkingNow(boolean workingNow) {
        isWorkingNow = workingNow;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }
}