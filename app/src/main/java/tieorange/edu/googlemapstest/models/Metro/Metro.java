package tieorange.edu.googlemapstest.models.Metro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import tieorange.edu.googlemapstest.pojo.MyMarker;

import static java.lang.Double.parseDouble;

public class Metro {
    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
//    public Properties properties;

    @Override
    public String toString() {
        return geometry.coordinates.lat;
    }

    public Double getLong() {
        return parseDouble(geometry.coordinates.lon);
    }

    public double getLat() {
        return parseDouble(geometry.coordinates.lat);
    }

    public MyMarker getMyMarker() {
        MyMarker myMarker = new MyMarker(MyMarker.MARKER_TYPE_METRO,
                getLong(), getLat(), "Metro");
        return myMarker;
    }
}
