package tieorange.edu.googlemapstest.models.Metro;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tieorange on 09/01/16.
 */
public class MetroStationsList {

    @SerializedName("data")
    @Expose
    public List<Metro> metroStationsList;

}
