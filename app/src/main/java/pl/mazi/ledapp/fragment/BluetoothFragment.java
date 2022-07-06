package pl.mazi.ledapp.fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.adapter.DeviceListAdapter;
import pl.mazi.ledapp.model.DeviceInfoModel;

import java.util.ArrayList;
import java.util.List;

public class BluetoothFragment extends Fragment {

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private RecyclerView recyclerView;
    private DeviceListAdapter deviceListAdapter;

    /////////////////////////////////////////////////////////////////
    //// OVERRIDE METHODS
    /////////////////////////////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        initMethods(view);
        return view;
    }


    /////////////////////////////////////////////////////////////////
    //// MY METHODS
    /////////////////////////////////////////////////////////////////
    private void initMethods(View view) {
        assignVariables(view);
        initRecyclerView();
    }

    private void assignVariables(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_bluetooth);
        deviceListAdapter = new DeviceListAdapter();
    }

    private void initRecyclerView() {

        List<Object> testList = new ArrayList<>();
        testList.add(new DeviceInfoModel("Test 0", "Test 0"));
        testList.add(new DeviceInfoModel("Test 1", "Test 1"));
        testList.add(new DeviceInfoModel("Test 2", "Test 2"));
        testList.add(new DeviceInfoModel("Test 3", "Test 3"));


        // Set test list to device list adapter
        deviceListAdapter.setDeviceList(testList);

        // Set adapter to recycler view
        recyclerView.setAdapter(deviceListAdapter);

        // Set layout manager into recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }
}