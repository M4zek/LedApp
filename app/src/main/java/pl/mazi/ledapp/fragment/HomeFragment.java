package pl.mazi.ledapp.fragment;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.adapter.PatternListAdapter;
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



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        assignVariables(view);
        initRecyclerView();
        initSeekBar();
        return view;
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

    private void assignVariables(View view) {
        recyclerView = view.findViewById(R.id.rv_Patterns);
        brightnessBar = view.findViewById(R.id.seekbar_Brightness);
        tv_brightness = view.findViewById(R.id.tv_brightness);
    }



    private void initRecyclerView() {
        ArrayList<PatternModel> patternModels = new ArrayList<>();
        patternModels.add(new PatternModel(PatternModel.Type.SINGLE, "One color"));
        patternModels.add(new PatternModel(PatternModel.Type.CUSTOM, "Knight rider", Arrays.asList("Red","Colorful")));

        recyclerView.setAdapter(new PatternListAdapter(patternModels));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}