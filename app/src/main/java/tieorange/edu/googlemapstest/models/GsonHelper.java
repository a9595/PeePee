package tieorange.edu.googlemapstest.models;

import android.content.Context;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

import tieorange.edu.googlemapstest.models.Hotel.HotelsList;
import tieorange.edu.googlemapstest.models.Metro.MetroStationsList;
import tieorange.edu.googlemapstest.models.SwimmingPool.SwimmingPoolsList;


public class GsonHelper {
    public static Gson gson = new Gson();

    public static HotelsList getHotels(Context context) {
        HotelsList hotelsList = gson.fromJson(loadJSONFromAsset(context, "hotels.json"), HotelsList.class);
        return hotelsList;
    }

    private MetroStationsList getMetroStations(Context context) {
        MetroStationsList metroStationsList = gson.fromJson(loadJSONFromAsset(context, "metroStations.json"), MetroStationsList.class);
        return metroStationsList;
    }

    private SwimmingPoolsList getSwimmingPools(Context context) {
        SwimmingPoolsList swimmingPoolsList = gson.fromJson(loadJSONFromAsset(context, "swimmingPools.json"), SwimmingPoolsList.class);
        return swimmingPoolsList;
    }

    public static String loadJSONFromAsset(Context context, String fileName) {
        String json = null;
        try {

            InputStream is = context.getAssets().open(fileName);

            int size = is.available();

            byte[] buffer = new byte[size];

            is.read(buffer);

            is.close();

            json = new String(buffer, "UTF-8");


        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;

    }


}
