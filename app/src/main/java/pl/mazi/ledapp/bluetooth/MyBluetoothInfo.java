package pl.mazi.ledapp.bluetooth;

import android.bluetooth.BluetoothSocket;
import pl.mazi.ledapp.intf.Status;

import java.util.ArrayList;

public class MyBluetoothInfo {

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private static MyBluetoothInfo instance;
    private ArrayList<Integer> statusList;
    private String deviceName;
    private String deviceAddress;

    private BluetoothSocket mySocket;


    /////////////////////////////////////////////////////////////////
    //// SINGLETON CREATE
    /////////////////////////////////////////////////////////////////

    // Get instance
    public static MyBluetoothInfo getInstance(){
        if(instance == null){
            instance = new MyBluetoothInfo();
        }
        return instance;
    }

    // Constructor
    public MyBluetoothInfo(){
        this.statusList = new ArrayList<Integer>();
        this.statusList.add(Status.DISABLE);
        this.statusList.add(Status.DISABLE);
        this.statusList.add(Status.DISABLE);
    }

    /////////////////////////////////////////////////////////////////
    //// MY METHODS
    /////////////////////////////////////////////////////////////////

    // Status update method
    public void updateStatus(Integer newStatus){
        statusList.set(1,statusList.get(0));
        statusList.set(0,newStatus);

        // Update current status when status was change
        if(statusWasChange()) statusList.set(2,statusList.get(0));
    }

    // Method to check if the status has been changed
    public boolean statusWasChange(){
        return statusList.get(1) != statusList.get(0);
    }


    /////////////////////////////////////////////////////////////////
    //// GETTERS AND SETTERS
    /////////////////////////////////////////////////////////////////

    // Method returned current bluetooth status
    public int getCurrentStatus(){
        return statusList.get(2);
    }

    // Return device name
    public String getDeviceName() {
        return deviceName;
    }

    // Assign new device name
    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    // Return device address
    public String getDeviceAddress() {
        return deviceAddress;
    }

    //Assign new device address
    public void setDeviceAddress(String deviceAddress) {
        this.deviceAddress = deviceAddress;
    }

    // Assign new device socket
    public BluetoothSocket getMySocket() {
        return mySocket;
    }

    // Get device socket
    public void setMySocket(BluetoothSocket mySocket) {
        this.mySocket = mySocket;
    }
}
