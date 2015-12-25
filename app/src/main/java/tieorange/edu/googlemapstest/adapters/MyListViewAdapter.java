package tieorange.edu.googlemapstest.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.pojo.MyMarker;

/**
 * Created by tieorange on 24/12/15.
 */
public class MyListViewAdapter extends RecyclerView.Adapter<MyListViewAdapter.ViewHolder> {
    private ArrayList<MyMarker> mDataset;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView txtHeader;
        public TextView txtFooter;

        public ViewHolder(View v) {
            super(v);
            txtHeader = (TextView) v.findViewById(R.id.listview_item_firstLine);
            txtFooter = (TextView) v.findViewById(R.id.listview_item_secondLine);
            //v.setOnClickListener(this);
        }
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

    public MyMarker getMarkerByPosition(int position){
        return mDataset.get(position);
    }

    public MyListViewAdapter(ArrayList<MyMarker> myDataSet) {
        this.mDataset = myDataSet;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        // set the view's size, margins, padding and layout parameters
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
// - get element from your dataset at this position
        // - replace the contents of the view with that element
        final MyMarker name = mDataset.get(position);
        holder.txtHeader.setText(mDataset.get(position).getLabel());


        Random random = new Random();
        final int randomMeters = random.nextInt(100);
        holder.txtFooter.setText(randomMeters + " meters from you");

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
