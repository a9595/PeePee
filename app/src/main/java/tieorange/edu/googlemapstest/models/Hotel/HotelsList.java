
package tieorange.edu.googlemapstest.models.Hotel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class HotelsList {

    @SerializedName("data")
    @Expose
    public List<Hotel> data = new ArrayList<Hotel>();

}
