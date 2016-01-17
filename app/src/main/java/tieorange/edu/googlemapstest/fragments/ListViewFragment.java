package tieorange.edu.googlemapstest.fragments;


import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

import tieorange.edu.googlemapstest.R;
import tieorange.edu.googlemapstest.activities.MainActivity;
import tieorange.edu.googlemapstest.activities.ToiletActivity;
import tieorange.edu.googlemapstest.adapters.ItemClickSupport;
import tieorange.edu.googlemapstest.adapters.MyListViewAdapter;
import tieorange.edu.googlemapstest.pojo.MyMarker;

public class ListViewFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private MyListViewAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyMarker> dummyMarkersFromDatabase;
    private View view;
    private MainActivity mainActivity;

    public static ListViewFragment newInstance() {
        ListViewFragment fragment = new ListViewFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_listview, container, false);
        mainActivity = (MainActivity) getActivity(); // to get GoogleMap object and share it

        dummyMarkersFromDatabase = mainActivity.markersFromDatabase;
        setupRecycleListView(view);

        return view;
    }

    private void setupRecycleListView(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.listview_my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        final LatLng currentUserLocation = MapFragment.getCurrentUserLocation(getActivity());

        mAdapter = new MyListViewAdapter(view.getContext());
        mAdapter.setDataset(dummyMarkersFromDatabase);

        mRecyclerView.setAdapter(mAdapter);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                final MyMarker myMarker = dummyMarkersFromDatabase.get(position); // clicked marker

//                final View UiTextFirstLine = v.findViewById(R.id.listview_item_firstLine);
                final View UiImageIcon = v.findViewById(R.id.listview_item_icon);

                Intent intent = new Intent(v.getContext(), ToiletActivity.class);
                intent.putExtra(ToiletActivity.EXTRA_MY_MARKER, myMarker);

                ActivityOptions activityOptions =
                        ActivityOptions.makeSceneTransitionAnimation(getActivity(), UiImageIcon,
                                getString(R.string.transition_name_list_view));

                v.getContext().startActivity(intent, activityOptions.toBundle());

            }
        });
    }

    public void notifyDatasetChangedRecyclerView() {
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }


    public void setFilter(List<Integer> selected_filter_options) {

    }

    public void setNewDataSet(ArrayList<MyMarker> markersFromDatabase) {
//        mAdapter.getDataSet().clear();
//        mAdapter.notifyDataSetChanged();
//        mAdapter.getDataSet().addAll(markersFromDatabase);
        mAdapter.notifyDataSetChanged();
    }
}