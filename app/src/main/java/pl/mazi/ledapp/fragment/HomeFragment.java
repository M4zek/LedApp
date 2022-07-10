package pl.mazi.ledapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.intf.What;
import pl.mazi.ledapp.service.ConnectedThread;

import java.util.zip.CheckedOutputStream;

public class HomeFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        assignVariables(view);
        return view;
    }

    private void assignVariables(View view) {
        button = view.findViewById(R.id.test_button);

        button.setOnClickListener((v)->{

            // TEST
            ConnectedThread connectedThread = ConnectedThread.getInstance();
            connectedThread.write("0");
            MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_SEND,"Zero").sendToTarget();
        });
    }
}