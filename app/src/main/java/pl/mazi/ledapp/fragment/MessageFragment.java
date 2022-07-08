package pl.mazi.ledapp.fragment;

import android.os.Bundle;
import android.util.Log;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.adapter.MessageListAdapter;
import pl.mazi.ledapp.bluetooth.MyBluetoothInfo;
import pl.mazi.ledapp.model.MessageInfoModel;

public class MessageFragment extends Fragment {

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private RecyclerView recyclerView;
    private MessageListAdapter messageListAdapter;
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
    }

    private void assignVariables(View view) {
        recyclerView = view.findViewById(R.id.recycler_view_message);
        messageListAdapter = new MessageListAdapter(getContext());
    }

    private void initRecyclerView() {

        // Add temporary message
        messageListAdapter.addNewMessage("Hello", MessageInfoModel.MessageTyp.MESSAGE_SENDER);
        messageListAdapter.addNewMessage("Hi, what's up?", MessageInfoModel.MessageTyp.MESSAGE_RECEIVER);
        messageListAdapter.addNewMessage("Wrong message!", MessageInfoModel.MessageTyp.MESSAGE_ERROR);


        // Set adapter in recyclerview
        recyclerView.setAdapter(messageListAdapter);

        // Set layout manager in recyclerview
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false));
    }
}