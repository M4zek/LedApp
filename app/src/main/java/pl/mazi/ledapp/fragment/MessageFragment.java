package pl.mazi.ledapp.fragment;

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
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.adapter.MessageListAdapter;
import pl.mazi.ledapp.bluetooth.MyBluetoothInfo;
import pl.mazi.ledapp.intf.What;
import pl.mazi.ledapp.model.MessageInfoModel;

public class MessageFragment extends Fragment implements What {

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private RecyclerView recyclerView;
    private MessageListAdapter messageListAdapter;
    private static Handler messageHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        initMethods(view);
        return view;
    }


    /////////////////////////////////////////////////////////////////
    //// MY METHODS
    /////////////////////////////////////////////////////////////////
    private void initMethods(View view) {
        assignVariables(view);
        initRecyclerView();
        initHandler();
    }



    private void assignVariables(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_message);
        messageListAdapter = new MessageListAdapter(getContext());
    }

    private void initHandler() {
        // Create a new handler to capture messages
        messageHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                // Adding a new message by type to the list in messageAdapter
                switch (msg.what){
                    case MESSAGE_SEND:
                        messageListAdapter.addNewMessage(msg.obj.toString(), MessageInfoModel.MessageTyp.MESSAGE_SENDER);
                        break;

                    case MESSAGE_READ:
                        messageListAdapter.addNewMessage(msg.obj.toString(), MessageInfoModel.MessageTyp.MESSAGE_RECEIVER);
                        break;

                    case MESSAGE_ERROR:
                        messageListAdapter.addNewMessage(msg.obj.toString(), MessageInfoModel.MessageTyp.MESSAGE_ERROR);
                        break;
                }
                // Scroll to the bottom of the list to a new message
                recyclerView.smoothScrollToPosition(messageListAdapter.getItemCount());
            }
        };
    }

    private void initRecyclerView() {
        // Set adapter in recyclerview
        recyclerView.setAdapter(messageListAdapter);

        // Set layout manager in recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }


    public static Handler getMessageHandler() {
        // Return handler to adding message
        return messageHandler;
    }
}