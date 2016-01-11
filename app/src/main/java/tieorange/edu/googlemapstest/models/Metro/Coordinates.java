package tieorange.edu.googlemapstest.models.Metro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by tieorange on 08/01/16.
 */

public class Coordinates {
    @SerializedName("lat")
    @Expose
    public String lat;

    @SerializedName("lon")
    @Expose
    public String lon;
}