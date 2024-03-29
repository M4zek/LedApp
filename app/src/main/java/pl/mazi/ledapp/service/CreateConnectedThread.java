package pl.mazi.ledapp.service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import pl.mazi.ledapp.MainActivity;
import pl.mazi.ledapp.fragment.MessageFragment;
import pl.mazi.ledapp.intf.Status;
import pl.mazi.ledapp.intf.What;

import java.io.IOException;
import java.util.UUID;


// TODO Rebuild the entire bluetooth connection!
public class CreateConnectedThread extends Thread{

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////

    // Connected or connecting bluetooth socket
    private static BluetoothSocket mySocket;

    // Thread to write and read data from bluetooth
    private ConnectedThread connectedThread;

    // An instance of this thread used to create a singleton class
    private static CreateConnectedThread instance;

    // Create a singleton thread and return or return already instance class
    public static CreateConnectedThread getInstance() {
        if (instance == null) {
            instance = new CreateConnectedThread();
        }
        return instance;
    }

    public CreateConnectedThread() {
        // Get singleton of thread to write and read data
        connectedThread = ConnectedThread.getInstance();
    }

    /////////////////////////////////////////////////////////////////
    //// RUN
    /////////////////////////////////////////////////////////////////
    public void run() {
        try{
            // Get default bluetooth adapter
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            // Cancel the discovering device process
            bluetoothAdapter.cancelDiscovery();

            // Try to connect with device
            mySocket.connect();
        } catch (Exception e){
            try{
                mySocket.close();
                String msg_error = "CreateConnectedThread [run e0] : " + e.getMessage();
                MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_ERROR, msg_error).sendToTarget();
            } catch (Exception ex){
                String msg_error = "CreateConnectedThread [run e1] : " + ex.getMessage();
                MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_ERROR,msg_error).sendToTarget();
            }
            return;
        }

        // Init connected thread
        connectedThread.initThread(mySocket);

        // Run connected thread
        connectedThread.run();
    }


    /////////////////////////////////////////////////////////////////
    //// MY METHODS
    /////////////////////////////////////////////////////////////////
    public void initThread(String deviceAddress){
        try{
            // Get default bluetooth adapter
            BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            // Get bluetooth device using address
            BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(deviceAddress);

            // Creating temporary bluetooth socket
            BluetoothSocket tmpSocket = null;

            // Get UUID of the remote device
            UUID uuid = bluetoothDevice.getUuids()[0].getUuid();

            // Creating socket to start connection to this remote device
            tmpSocket = bluetoothDevice.createInsecureRfcommSocketToServiceRecord(uuid);

            // Assign thread socket variable
            mySocket = tmpSocket;
        } catch (Exception e){
            // Send exception message to message fragment
            String msg_error = "CreateConnectedThread [initThread] : " + e.getMessage();
            MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_ERROR,msg_error).sendToTarget();
        }
    }

    public void destroyThread(){
        try{
            connectedThread.destroyThread();
            connectedThread = null;

            mySocket.close();
            mySocket = null;

            instance = null;
        } catch (Exception e){
            String msg_error = "CreateConnectedThread [destroyThread] : " + e.getMessage();
            MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_ERROR,msg_error).sendToTarget();
        }
    }

    public static BluetoothSocket getMySocket() {
        return mySocket;
    }
}