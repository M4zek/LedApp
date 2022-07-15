package pl.mazi.ledapp.task;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import pl.mazi.ledapp.bluetooth.MyBluetoothInfo;
import pl.mazi.ledapp.intf.Status;
import pl.mazi.ledapp.service.ConnectedThread;
import pl.mazi.ledapp.service.CreateConnectedThread;

import java.util.TimerTask;

public class StatusMonitorTask extends TimerTask {

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private BluetoothAdapter bluetoothAdapter;
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
        } else if(index < 15 && index > 10){
            myBluetoothInfo.updateStatus(Status.CONNECTED);
        } else if (index > 15){
            index = 0;
        }
        index++;


//        if(!bluetoothAdapter.isEnabled()){
//            myBluetoothInfo.updateStatus(Status.DISABLE);
//            return;
//        }
//
//        BluetoothSocket socket = ConnectedThread.getMySocket();
//
//        if (socket != null){
//            if(socket.isConnected()){
//                myBluetoothInfo.updateStatus(Status.CONNECTED);
//            } else {
//                myBluetoothInfo.updateStatus(Status.CANNOT_CONNECTED);
//            }
//        } else {
//            myBluetoothInfo.updateStatus(Status.DISCONNECT);
//        }
    }
}
