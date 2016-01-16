package tieorange.edu.googlemapstest.adapters;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;

import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.activities.ToiletActivity;
import tieorange.edu.googlemapstest.pojo.MyMarker;

/**
 * Created by tieorange on 24/12/15.
 */
public class MyListViewAdapter extends RecyclerView.Adapter<MyListViewAdapter.ViewHolder> {
    private final Context mContext;
    private ArrayList<MyMarker> mDataset;
    private final LatLng currentUserLocation;

    public MyListViewAdapter(Context context) {
        this.mContext = context;
        currentUserLocation = tieorange.edu.googlemapstest.fragments.MapFragment.getCurrentUserLocation(mContext);
    }

    public void setDataset(ArrayList<MyMarker> datasetList) {
        mDataset = datasetList;
        SortByNearest();
        notifyDataSetChanged();
    }

    public void add(int position, MyMarker item) {
        mDataset.add(position, item);
        notifyItemInserted(position);
    }

    public void remove(MyMarker item) {
        int position = mDataset.indexOf(item);
        mDataset.remove(position);
        notifyItemRemoved(position);
    }

    public MyMarker getMarkerByPosition(int position) {
        return mDataset.get(position);
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(mContext).inflate(R.layout.listview_item, parent, false);
        // set the view's size, margins, padding and layout parameters
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
// - get element from your dataset at this position
        // - replace the contents of the view with that element
        final MyMarker myMarker = mDataset.get(position);
        holder.mUiTextHeader.setText(myMarker.getLabel());


        //calculate distance to toilet:

        final float distanceInMeters = distFrom((float) currentUserLocation.latitude, (float) currentUserLocation.longitude,
                myMarker.getLatitude().floatValue(), myMarker.getLongitude().floatValue());

        if (distanceInMeters >= 1000) { // kilometers
            final float distanceInKilometers = distanceInMeters / 1000;
            holder.mUiTextDescripton.setText(String.format("%.02f km od Ciebie", distanceInKilometers));

        } else { // meters
            holder.mUiTextDescripton.setText(String.format("%d m od Ciebie", (int) distanceInMeters));

        }

        // set icon
        holder.mUiImageViewMarkerIcon.setImageResource(myMarker.getIconBlackWhite());

    }

    public void SortByAlphabet() {
        Collections.sort(mDataset, new Comparator<MyMarker>() {
            @Override
            public int compare(MyMarker lhs, MyMarker rhs) {
                return lhs.getLabel().compareToIgnoreCase(rhs.getLabel());
            }

        });
    }

    public void SortByNearest() {
        Collections.sort(mDataset, new Comparator<MyMarker>() {
            @Override
            public int compare(MyMarker first, MyMarker second) {
                final float distFromFirst = distFrom((float) currentUserLocation.latitude,
                        (float) currentUserLocation.longitude,
                        first.getLatitude().floatValue(),
                        first.getLongitude().floatValue());

                final float distFromSecond = distFrom((float) currentUserLocation.latitude,
                        (float) currentUserLocation.longitude,
                        second.getLatitude().floatValue(),
                        second.getLongitude().floatValue());
                if(distFromFirst > distFromSecond)
                    return 1;
                else if (distFromFirst < distFromSecond)
                    return -1;
                return 0;
            }

        });
    }

    public static float distFrom(float lat1, float lng1, float lat2, float lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2 - lat1);
        double dLng = Math.toRadians(lng2 - lng1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng / 2) * Math.sin(dLng / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        float dist = (float) (earthRadius * c);

        return dist;
    }

    @Override
    public int getItemCount() {
        return mDataset == null ? 0 : mDataset.size();
    }


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mUiTextHeader;
        public TextView mUiTextDescripton;
        private ImageView mUiImageViewMarkerIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            mUiTextHeader = (TextView) itemView.findViewById(R.id.listview_item_firstLine);
            mUiTextDescripton = (TextView) itemView.findViewById(R.id.listview_item_secondLine);
            mUiImageViewMarkerIcon = (ImageView) itemView.findViewById(R.id.listview_item_icon);
        }

//        @Override
//        public void onClick(View v) {
//            final Intent intent = new Intent(mContext, ToiletActivity.class);
//            MyMarker myMarker = new MyMarker("McDonald's", "icon1", (double) 1, (double) 2); // TODO: mock
//            intent.putExtra("name", myMarker);
//
//            mContext.startActivity(intent);
//        }
    }
}
