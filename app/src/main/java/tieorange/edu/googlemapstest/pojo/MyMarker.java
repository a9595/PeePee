package tieorange.edu.googlemapstest.pojo;

import android.content.Context;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.models.GsonHelper;
import tieorange.edu.googlemapstest.models.Hotel.Hotel;
import tieorange.edu.googlemapstest.models.Hotel.HotelsList;
import tieorange.edu.googlemapstest.models.Metro.Metro;
import tieorange.edu.googlemapstest.models.Metro.MetroStationsList;
import tieorange.edu.googlemapstest.models.SwimmingPool.SwimmingPool;
import tieorange.edu.googlemapstest.models.SwimmingPool.SwimmingPoolsList;

/**
 * Created by tieorange on 11/12/15.
 */
public class MyMarker implements Serializable {
    public static final int MARKER_TYPE_RESTAURANT = 0;
    public static final int MARKER_TYPE_TOI_TOI = 1;
    public static final int MARKER_TYPE_METRO = 2;
    public static final int MARKER_TYPE_HOTEL = 3;
    public static final int MARKER_TYPE_SWIMMING_POOL = 4;
    public static final int MARKER_TYPE_OTHER = 5;


    private String Label;
    private String Icon;
    private boolean isWorkingNow;
    private boolean isFree;
    private Double Latitude;
    private Double Longitude;

    List<ToiletRating> ratingList;

    private int Type;

    public static ArrayList<MyMarker> getMarkersList() {
        return markersList;
    }


    private static ArrayList<MyMarker> markersList;


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
        isWorkingNow = true;
        isFree = true;
    }

    public static ArrayList<MyMarker> getMarkersAllMarkersList(Context context) {
        markersList = new ArrayList<>();
        getHotelsMarkers(context);
        getMetroStationsMarkers(context);
        getSwimmingPoolsMarkers(context);
        getRestaurantsDummyMarkers();
        getToiToiDummyMarkers();
        return markersList;
    }

    public static void getSwimmingPoolsMarkers(Context context) {
        // SwimmingPools
        final SwimmingPoolsList swimmingPoolsList = GsonHelper.getSwimmingPools(context);
        for (SwimmingPool swimmingPool : swimmingPoolsList.data) {
            MyMarker metroMarker = swimmingPool.getMyMarker();
            markersList.add(metroMarker);
        }
    }

    public static void getRestaurantsDummyMarkers() {
        ArrayList<MyMarker> restaurantsList = new ArrayList<>();

        restaurantsList.add(new MyMarker("Costa Coffee Plac Bankowy",
                MyMarker.MARKER_TYPE_RESTAURANT, true, true,
                52.244355,
                21.002185));
        restaurantsList.add(new MyMarker("KFC Gruba Kaśka",
                MyMarker.MARKER_TYPE_RESTAURANT, false, true,
                52.245097,
                21.001927));
        restaurantsList.add(new MyMarker("Charlotte",
                MyMarker.MARKER_TYPE_RESTAURANT, true, true,
                52.220075,
                21.018939));
        restaurantsList.add(new MyMarker("Plan.B",
                MyMarker.MARKER_TYPE_RESTAURANT, true, true,
                52.219964,
                21.018574));
        restaurantsList.add(new MyMarker("KFC Piękna",
                MARKER_TYPE_RESTAURANT, false, true,
                52.223429,
                21.016281));
        restaurantsList.add(new MyMarker("AiOli inspired by MINI",
                MARKER_TYPE_RESTAURANT, true, true,
                52.222357,
                21.015263));
        restaurantsList.add(new MyMarker("KFC",
                MARKER_TYPE_RESTAURANT, false, true,
                52.230725,
                21.017522));
        restaurantsList.add(new MyMarker("Cuda na Kiju",
                MARKER_TYPE_RESTAURANT, true, false,
                52.231428,
                21.022354));
        restaurantsList.add(new MyMarker("AiOli inspired by MINI",
                MARKER_TYPE_RESTAURANT, true, false,
                52.222357,
                21.015263));

        markersList.addAll(restaurantsList);
//        return restaurantsList;
    }

    public static void getToiToiDummyMarkers() {
        ArrayList<MyMarker> toiToiList = new ArrayList<>();

        toiToiList.add(new MyMarker("Toi Toi Cud nad Wisłą",
                MyMarker.MARKER_TYPE_TOI_TOI, true, false,
                52.228299,
                21.04285));
        toiToiList.add(new MyMarker("Toi Toi nad Wisłą",
                MyMarker.MARKER_TYPE_TOI_TOI, true, false,
                52.236116,
                21.0374));
        toiToiList.add(new MyMarker("Toi Toi nad Wisłą",
                MyMarker.MARKER_TYPE_TOI_TOI, true, false,
                52.237225,
                21.036056));
        toiToiList.add(new MyMarker("Toi Toi nad Wisłą",
                MyMarker.MARKER_TYPE_TOI_TOI, true, true,
                52.239444,
                21.033217));
        toiToiList.add(new MyMarker("Toi Toi Swietokrzyska",
                MyMarker.MARKER_TYPE_TOI_TOI, true, true,
                52.234715,
                21.007504));

        markersList.addAll(toiToiList);
    }

    public static void getMetroStationsMarkers(Context context) {
        // Metro
        final MetroStationsList metroStationsList = GsonHelper.getMetroStations(context);
        for (Metro metro : metroStationsList.metroStationsList) {
            MyMarker metroMarker = metro.getMyMarker();
            markersList.add(metroMarker);
        }
    }

    public static void getHotelsMarkers(Context context) {
        // Hotels
        final HotelsList hotels = GsonHelper.getHotels(context);
        for (Hotel hotel : hotels.data) {
            MyMarker hotelMarker = hotel.getMyMarker();
            markersList.add(hotelMarker);
        }
    }

    public int getIconBlackWhite() {
        int icon_marker_result = R.drawable.ic_marker_other_black_white;

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

            case MyMarker.MARKER_TYPE_METRO:
                icon_marker_result = R.drawable.ic_marker_other_black_white;
                break;

            case MyMarker.MARKER_TYPE_SWIMMING_POOL:
                icon_marker_result = R.drawable.ic_marker_other_black_white;
                break;

            case MyMarker.MARKER_TYPE_HOTEL:
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

    public Float getDistanceToMe(LatLng current) {

        return 4f;
    }

    public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }
}