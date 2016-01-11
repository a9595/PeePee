package tieorange.edu.googlemapstest.models.Metro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Geometry {

    @SerializedName("coordinates")
    @Expose
    public Coordinates coordinates;

}
