
package tieorange.edu.googlemapstest.models.SwimmingPool;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("coordinates")
    @Expose
    public Coordinates coordinates;

}
