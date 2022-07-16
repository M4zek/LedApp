package pl.mazi.ledapp.fragment;

import android.os.*;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import org.w3c.dom.Text;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.adapter.PatternListAdapter;
import pl.mazi.ledapp.intf.Status;
import pl.mazi.ledapp.intf.What;
import pl.mazi.ledapp.model.PatternModel;
import pl.mazi.ledapp.service.ConnectedThread;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.zip.CheckedOutputStream;

public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private SeekBar brightnessBar;
    private TextView tv_brightness;
    private static Handler homeHandler;

    private TextView tv_not_connection;
    private ConstraintLayout mainViewLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        assignVariables(view);
        initRecyclerView();
        initSeekBar();
        initHandler();
        return view;
    }


    private void initHandler(){
        homeHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                switch (msg.what){
                    // Status was change on disconnect
                    case Status.DISABLE:
                        setGuiDisable();
                        break;

                    // Status was change on disconnect
                    case Status.DISCONNECT:
                        setGuiDisconnect();
                        break;

                    // Status was change on connected
                    case Status.CONNECTED:
                        setGuiConnected();
                        break;
                }
            }
        };
    }

    private void assignVariables(View view) {
        recyclerView = view.findViewById(R.id.rv_Patterns);
        brightnessBar = view.findViewById(R.id.seekbar_Brightness);
        tv_brightness = view.findViewById(R.id.tv_brightness);
        tv_not_connection = view.findViewById(R.id.tv_not_connection);
        mainViewLayout = view.findViewById(R.id.constraintLayout);
    }

    private void initRecyclerView() {
        ArrayList<PatternModel> patternModels = new ArrayList<>();

        patternModels.add(new PatternModel(
                PatternModel.Style.SINGLE,
                R.drawable.rider,
                getString(R.string.pattern_one_color),
                getString(R.string.pattern_rainbow_description)));

        patternModels.add(new PatternModel(
                PatternModel.Style.CUSTOM,
                R.drawable.rainbow,
                getString(R.string.pattern_rainbow),
                getString(R.string.pattern_rainbow_description),
                Arrays.asList(getString(R.string.pattern_rainbow_option_1),getString(R.string.pattern_rainbow_option_2))));

        patternModels.add(new PatternModel(
                PatternModel.Style.CUSTOM,
                R.drawable.rider,
                getString(R.string.pattern_knight_rider),
                getString(R.string.pattern_rainbow_description),
                Arrays.asList(getString(R.string.pattern_knight_rider_option_1),getString(R.string.pattern_knight_rider_option_2))));


        recyclerView.setAdapter(new PatternListAdapter(patternModels));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    private void initSeekBar() {
        brightnessBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_brightness.setText(getResources().getString(R.string.tv_brightness) + " " + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.i("SekBar progress: ", String.valueOf(seekBar.getProgress()));
            }
        });
    }

    private void setGuiDisconnect() {
        tv_not_connection.setVisibility(View.VISIBLE);
        mainViewLayout.setVisibility(View.INVISIBLE);
    }

    private void setGuiConnected() {
        tv_not_connection.setVisibility(View.INVISIBLE);
        mainViewLayout.setVisibility(View.VISIBLE);
    }

    private void setGuiDisable() {
        tv_not_connection.setVisibility(View.VISIBLE);
        mainViewLayout.setVisibility(View.INVISIBLE);
    }

    public static Handler getHomeHandler() {
        return homeHandler;
    }
}