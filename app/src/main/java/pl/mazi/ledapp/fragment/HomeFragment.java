package pl.mazi.ledapp.fragment;

import android.os.*;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.adapter.PatternListAdapter;
import pl.mazi.ledapp.intf.Status;
import pl.mazi.ledapp.intf.What;
import pl.mazi.ledapp.model.PatternModel;
import pl.mazi.ledapp.service.ConnectedThread;
import pl.mazi.ledapp.settings.MySettings;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {


    private RecyclerView recyclerView;
    private SeekBar brightnessBar;
    private TextView tv_brightness;
    private static Handler homeHandler;
    private TextView tv_not_connection;
    private ConstraintLayout mainViewLayout;
    private Button btn_on_off;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        assignVariables(view);
        initRecyclerView();
        initSeekBar();
        initHandler();
        initButtonOnClick();
        return view;
    }

    private void initButtonOnClick() {
        btn_on_off.setOnClickListener(event->{
            ConnectedThread connectedThread = ConnectedThread.getInstance();
            if(btn_on_off.getText() == getString(R.string.btn_turn_on)){
                MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_SEND,getString(R.string.turn_on_led)).sendToTarget();
                connectedThread.write("0");
            } else {
                MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_SEND,getString(R.string.turn_off_led)).sendToTarget();
                connectedThread.write("1");
            }
        });
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

                    // Data incoming from arduino
                    case What.MESSAGE_READ:
                        switch(msg.obj.toString()){
                            case "Leds on!\r":
                                setGuiLedsOn();
                                break;

                            case "Leds off!\r":
                                setGuiLedsOff();
                                break;
                        }
                        break;
                }
            }
        };
    }

    private void setGuiLedsOn() {
        btn_on_off.setText(getString(R.string.btn_turn_ff));
        recyclerView.setVisibility(View.VISIBLE);
        tv_brightness.setVisibility(View.VISIBLE);
        brightnessBar.setVisibility(View.VISIBLE);
    }

    private void setGuiLedsOff(){
        btn_on_off.setText(getString(R.string.btn_turn_on));
        recyclerView.setVisibility(View.INVISIBLE);
        tv_brightness.setVisibility(View.INVISIBLE);
        brightnessBar.setVisibility(View.INVISIBLE);
    }

    private void assignVariables(View view) {
        recyclerView = view.findViewById(R.id.rv_Patterns);
        brightnessBar = view.findViewById(R.id.seekbar_Brightness);
        tv_brightness = view.findViewById(R.id.tv_brightness);
        tv_not_connection = view.findViewById(R.id.tv_not_connection);
        mainViewLayout = view.findViewById(R.id.constraintLayout);
        btn_on_off = view.findViewById(R.id.btn_on_off);
    }

    private void initRecyclerView() {
        ArrayList<PatternModel> patternModels = new ArrayList<>();

        patternModels.add(new PatternModel(
                4,
                PatternModel.Style.COLOR,
                R.drawable.color_picker,
                getString(R.string.pattern_one_color),
                getString(R.string.pattern_one_color_description)));

        patternModels.add(new PatternModel(
                5,
                PatternModel.Style.BOX,
                R.drawable.rainbow,
                getString(R.string.pattern_rainbow),
                getString(R.string.pattern_rainbow_description),
                Arrays.asList(getString(R.string.pattern_rainbow_option_1),getString(R.string.pattern_rainbow_option_2))));

        patternModels.add(new PatternModel(
                8,
                PatternModel.Style.BOX,
                R.drawable.rider,
                getString(R.string.pattern_knight_rider),
                getString(R.string.pattern_knight_description),
                Arrays.asList(getString(R.string.pattern_knight_rider_option_1),getString(R.string.pattern_knight_rider_option_2))));

        patternModels.add(new PatternModel(
                7,
                PatternModel.Style.SIMPLE,
                R.drawable.confetti,
                getString(R.string.pattern_confetti),
                getString(R.string.pattern_confetti_description)
        ));

        patternModels.add(new PatternModel(
                10,
                PatternModel.Style.SIMPLE,
                R.drawable.bpm,
                getString(R.string.pattern_bpm),
                getString(R.string.pattern_bpm_description)
        ));

        patternModels.add(new PatternModel(
                11,
                PatternModel.Style.SIMPLE,
                R.drawable.juggle,
                getString(R.string.pattern_juggle),
                getString(R.string.pattern_juggle_description)
        ));

        patternModels.add(new PatternModel(
                12,
                PatternModel.Style.SIMPLE,
                R.drawable.faded,
                getString(R.string.pattern_faded),
                getString(R.string.pattern_faded_description)
        ));

        patternModels.add(new PatternModel(
                13,
                PatternModel.Style.COLOR,
                R.drawable.breathing,
                getString(R.string.pattern_breathing),
                getString(R.string.pattern_breathing_description)
        ));


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
                ConnectedThread connectedThread = ConnectedThread.getInstance();
                String msg = getString(R.string.set_brightness) + " " + seekBar.getProgress() + "%";
                MessageFragment.getMessageHandler().obtainMessage(What.MESSAGE_SEND,msg).sendToTarget();
                connectedThread.write("3");
                int progress = seekBar.getProgress();
                connectedThread.write(String.valueOf(progress));
            }
        });
    }

    private void setGuiDisconnect() {
        btn_on_off.setText(R.string.btn_turn_on);
        tv_not_connection.setVisibility(View.VISIBLE);
        mainViewLayout.setVisibility(View.INVISIBLE);
    }

    private void setGuiConnected() {
        MySettings mySettings = new MySettings(getContext());
        tv_not_connection.setVisibility(View.INVISIBLE);
        mainViewLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.INVISIBLE);
        brightnessBar.setVisibility(View.INVISIBLE);
        tv_brightness.setVisibility(View.INVISIBLE);
        brightnessBar.setProgress(mySettings.initBrightness());
    }

    private void setGuiDisable() {
        btn_on_off.setText(R.string.btn_turn_on);
        tv_not_connection.setVisibility(View.VISIBLE);
        mainViewLayout.setVisibility(View.INVISIBLE);
    }

    public static Handler getHomeHandler() {
        return homeHandler;
    }
}