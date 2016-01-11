
package tieorange.edu.googlemapstest.models.SwimmingPool;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SwimmingPool {

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
        return "Basen";
    }

    public String getLong() {
        return geometry.coordinates.lon;
    }

    public String getLat() {
        return geometry.coordinates.lat;
    }
}
