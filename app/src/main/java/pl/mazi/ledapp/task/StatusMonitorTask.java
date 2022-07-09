package pl.mazi.ledapp.task;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothSocket;
import pl.mazi.ledapp.bluetooth.MyBluetoothInfo;
import pl.mazi.ledapp.intf.Status;
import pl.mazi.ledapp.service.ConnectedThread;

import java.util.TimerTask;

public class StatusMonitorTask extends TimerTask {

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private BluetoothAdapter bluetoothAdapter;
    private static MyBluetoothInfo myBluetoothInfo;
    private BluetoothSocket mySocket;


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
//        if(index < 5 && index > 0){
//            myBluetoothInfo.updateStatus(Status.DISABLE);
//        } else if(index < 10 && index > 5){
//            myBluetoothInfo.updateStatus(Status.DISCONNECT);
//        } else if(index < 15 && index > 10){
//            myBluetoothInfo.updateStatus(Status.CONNECTED);
//        }else if(index > 15){
//            index = 0;
//        }
//        index++;

        if(!bluetoothAdapter.isEnabled()){
            myBluetoothInfo.updateStatus(Status.DISABLE);
        } else if (ConnectedThread.getMySocket() != null){
            if(ConnectedThread.getMySocket().isConnected()){
                myBluetoothInfo.updateStatus(Status.CONNECTED);
            }
        } else {
            myBluetoothInfo.updateStatus(Status.DISCONNECT);
        }

//        if(!bluetoothAdapter.isEnabled()){
//            myBluetoothInfo.updateStatus(Status.DISABLE);
//        } else if(ConnectedThread.getMySocket() != null){
//            if(ConnectedThread.getMySocket().isConnected()){
//                myBluetoothInfo.updateStatus(Status.CONNECTED);
//            } else if(!ConnectedThread.getMySocket().isConnected()){
//                myBluetoothInfo.updateStatus(Status.DISCONNECT);
//            }
//        }
    }

    public void setMySocket(BluetoothSocket mySocket) {
        this.mySocket = mySocket;
    }
}
