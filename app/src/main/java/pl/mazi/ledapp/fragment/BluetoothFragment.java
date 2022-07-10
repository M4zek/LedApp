package pl.mazi.ledapp.fragment;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.mazi.ledapp.MainActivity;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.adapter.DeviceListAdapter;
import pl.mazi.ledapp.intf.Status;
import pl.mazi.ledapp.intf.What;
import pl.mazi.ledapp.model.DeviceInfoModel;
import pl.mazi.ledapp.model.MessageInfoModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class BluetoothFragment extends Fragment implements Status {

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private RecyclerView recyclerView;
    private DeviceListAdapter deviceListAdapter;
    private static Handler bluetoothHandler;

    /////////////////////////////////////////////////////////////////
    //// OVERRIDE METHODS
    /////////////////////////////////////////////////////////////////
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bluetooth, container, false);
        initMethods(view);
        Log.d("BluetoothFragment","Initialize");
        return view;
    }


    /////////////////////////////////////////////////////////////////
    //// MY METHODS
    /////////////////////////////////////////////////////////////////
    private void initMethods(View view) {
        assignVariables(view);
        initHandler();
        initRecyclerView();
    }




    private void assignVariables(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_bluetooth);
        deviceListAdapter = new DeviceListAdapter(getContext());
    }



    private void initRecyclerView() {
        // Set adapter to recycler view
        recyclerView.setAdapter(deviceListAdapter);

        // Set layout manager into recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }




    private void initHandler(){
        bluetoothHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    // Status was change on disconnect
                    case DISABLE:
                        setGuiDisable();
                        break;

                    // Status was change on disconnect
                    case DISCONNECT:
                        setGuiDisconnect();
                        break;

                    // Status was change on connected
                    case CONNECTED:
                        setGuiConnected();
                        break;

                    case CANNOT_CONNECTED:
                        setGuiCannotConnected();
                        break;
                }
            }
        };
    }




    private void initTestList(){
        List<Object> testList = new ArrayList<>();
        testList.add(new DeviceInfoModel("Test 0", "Test 0"));
        testList.add(new DeviceInfoModel("Test 1", "Test 1"));
        testList.add(new DeviceInfoModel("Test 2", "Test 2"));
        testList.add(new DeviceInfoModel("Test 3", "Test 3"));

        // Set test list to device list adapter
        deviceListAdapter.setDeviceList(testList);
    }



    private void initDeviceList(){
        // Get bluetooth adapter
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        ArrayList<Object> deviceList = new ArrayList<>();

        // Checking if your device supports bluetooth
        if(bluetoothAdapter != null){
            // Get paired devices
            Set<BluetoothDevice> deviceSet = bluetoothAdapter.getBondedDevices();

            // Add paired device to list
            for(BluetoothDevice item : deviceSet){
                deviceList.add(new DeviceInfoModel(item.getName(),item.getAddress()));
            }
        }

        // Assign new device list to adapter
        deviceListAdapter.setDeviceList(deviceList);
    }


    private void setGuiDisable() {
        // Clear device list
        clearDeviceList();
    }

    private void setGuiCannotConnected() {
        // Set clickable item in recycler view
        deviceListAdapter.setClickable(true);
    }



    private void setGuiDisconnect() {
        // Set clickable item in recycler view
        deviceListAdapter.setClickable(true);

        // Get paired devices

        // TODO TESTING BT LIST
//        initTestList();
        initDeviceList();
    }


    private void setGuiConnected() {
        // Set cannot click on the item in recycler view
        deviceListAdapter.setClickable(false);
    }




    private void clearDeviceList(){
        // Clear device list in adapter and update data
        deviceListAdapter.clear();
    }




    public static Handler getBluetoothHandler() {
        // Return gui handler
        return bluetoothHandler;
    }
}