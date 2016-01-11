package tieorange.edu.googlemapstest.models.Metro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Metro {
    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
//    public Properties properties;

    @Override
    public String toString() {
        return geometry.coordinates.lat;
    }

    public String getLong() {
        return geometry.coordinates.lon;
    }

    public String getLat() {
        return geometry.coordinates.lat;
    }
}
