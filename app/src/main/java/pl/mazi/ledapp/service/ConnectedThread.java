package pl.mazi.ledapp.service;

import android.bluetooth.BluetoothSocket;
import pl.mazi.ledapp.bluetooth.MyBluetoothInfo;
import pl.mazi.ledapp.fragment.MessageFragment;
import pl.mazi.ledapp.intf.What;
import pl.mazi.ledapp.task.StatusMonitorTask;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// TODO Rebuild the entire bluetooth connection!
public class ConnectedThread extends Thread{

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////

    // Connected or connecting bluetooth socket
    private static BluetoothSocket mySocket;

    // Abstract class using to input stream of bytes
    private InputStream myInputStream;

    // Abstract class using to output stream of bytes
    private OutputStream myOutputStream;

    // An instance of this class used to create a singleton class
    private static ConnectedThread instance;

    // Create a singleton class and return or return already instance class
    public static ConnectedThread getInstance(){
        if(instance == null){
            instance = new ConnectedThread();
        }
        return instance;
    }

    public ConnectedThread(){
        mySocket = null;
    }


    /////////////////////////////////////////////////////////////////
    //// RUN
    /////////////////////////////////////////////////////////////////
    public void run() {
        // Create a new byte array for incoming bluetooth data
        byte[] buffer = new byte[1024];

        // Created int variable to indexing buffer array
        int bytes = 0;

        // While loop to reading data from bluetooth
        while(true){
            try{
                // Read byte from bluetooth
                buffer[bytes] = (byte) myInputStream.read();

                // String variable for all bluetooth messaging
                String readMessage;

                // If the incoming character is an end-of-line character
                // then send the message to messageFragment
                if(buffer[bytes] == '\n'){

                    // Create new string by decoding byte array
                    readMessage = new String(buffer,0,bytes);

                    // Sending the read message to the messageFragment
                    MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_READ,readMessage).sendToTarget();

                    bytes = 0;
                } else {
                    bytes++;
                }
            } catch (Exception e){
                // Send exception message to message fragment
                String msg_error = "ConnectedThread [run] : " + e.getMessage();
                MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_ERROR,msg_error).sendToTarget();
                break;
            }
        }
    }

    /////////////////////////////////////////////////////////////////
    //// MY METHOD
    /////////////////////////////////////////////////////////////////
    public void initThread(BluetoothSocket socket){
        try{
            // Assign new socket
            mySocket = socket;

            // Create temporary output and input stream
            InputStream tmpInputStream;
            OutputStream tmpOutputStream;

            // Try to get the input stream associated with this socket
            tmpInputStream = socket.getInputStream();

            // Try to get the output stream associated with this socket
            tmpOutputStream = socket.getOutputStream();

            // Assign the socket stream into thread variables
            myInputStream = tmpInputStream;
            myOutputStream = tmpOutputStream;
        } catch (Exception e){
            // Send exception message to message fragment
            String msg_error = "ConnectedThread [initThread] : " + e.getMessage();
            MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_ERROR,msg_error).sendToTarget();
            destroyThread();
        }
    }

    public void write(String input){
        try{
            // Encoding input value to array bytes
            byte[] bytes = input.getBytes();

            // Write array byte to bluetooth device
            myOutputStream.write(bytes);
        }catch (Exception e){
            String msg_error = "ConnectedThread [write] : " + e.getMessage();
            MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_ERROR,msg_error).sendToTarget();
        }
    }

    public void destroyThread(){
        try{
            myInputStream.close();
            myInputStream = null;

            myOutputStream.close();
            myOutputStream = null;

            mySocket.close();
            mySocket = null;

            instance = null;
        } catch (Exception e){
            String msg_error = "ConnectedThread [cancel] : " + e.getMessage();
            MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_ERROR, msg_error).sendToTarget();
        }
    }


    public static BluetoothSocket getMySocket() {
        return mySocket;
    }
}
