package pl.mazi.ledapp.task;

import android.bluetooth.BluetoothAdapter;
import pl.mazi.ledapp.bluetooth.MyBluetoothInfo;
import pl.mazi.ledapp.intf.Status;

import java.util.TimerTask;

public class StatusMonitorTask extends TimerTask {

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private static BluetoothAdapter bluetoothAdapter;
    private static MyBluetoothInfo myBluetoothInfo;


    /////////////////////////////////////////////////////////////////
    //// CONSTRUCTOR
    /////////////////////////////////////////////////////////////////
    public StatusMonitorTask() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        myBluetoothInfo = MyBluetoothInfo.getInstance();
    }

    int index = 0;
    @Override
    public void run() {
        if(index < 5 && index > 0){
            myBluetoothInfo.updateStatus(Status.DISABLE);
        } else if(index < 10 && index > 5){
            myBluetoothInfo.updateStatus(Status.DISCONNECT);
        } else if(index > 20){
            index = 0;
        }
        index++;

//        if(bluetoothAdapter.isEnabled()){
//            myBluetoothInfo.updateStatus(Status.DISCONNECT);
//        } else if (!bluetoothAdapter.isEnabled()){
//            myBluetoothInfo.updateStatus(Status.DISABLE);
//        }
    }
}
