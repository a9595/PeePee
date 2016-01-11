
package tieorange.edu.googlemapstest.models.Hotel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import tieorange.edu.googlemapstest.pojo.MyMarker;

import static java.lang.Double.parseDouble;

public class Hotel {

    @SerializedName("geometry")
    @Expose
    public Geometry geometry;
    @SerializedName("properties")
    @Expose
    public List<Property> properties = new ArrayList<Property>();


    public String getName() {
        for (Property property : properties) {
            if (property.key.equals("OPIS")) {
                return property.value;
            }
        }
        return "Hotel";
    }

    public Double getLong() {
        return parseDouble(geometry.coordinates.lon);
    }

    public Double getLat() {
        return parseDouble(geometry.coordinates.lat);
    }

    public MyMarker getMyMarker() {
        MyMarker myMarker = new MyMarker(MyMarker.MARKER_TYPE_HOTEL,
                getLong(), getLat(), getName());
        return myMarker;
    }
}
