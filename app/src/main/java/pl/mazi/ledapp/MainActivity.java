package pl.mazi.ledapp;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import pl.mazi.ledapp.adapter.VPAdapter;
import pl.mazi.ledapp.bluetooth.MyBluetoothInfo;
import pl.mazi.ledapp.fragment.BluetoothFragment;
import pl.mazi.ledapp.fragment.HomeFragment;
import pl.mazi.ledapp.fragment.MessageFragment;
import pl.mazi.ledapp.fragment.SettingsFragment;
import pl.mazi.ledapp.intf.Status;
import pl.mazi.ledapp.intf.What;
import pl.mazi.ledapp.service.CreateConnectedThread;
import pl.mazi.ledapp.settings.MySettings;
import pl.mazi.ledapp.task.StatusMonitorTask;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements Status, What {

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MyBluetoothInfo myBluetoothInfo;
    private static Handler mainHandler;
    private TextView tv_bt_status;
    private TextView tv_bt_name;
    private TextView tv_bt_address;
    private Button btn_connecting;
    private ProgressBar progressBar;
    private static CreateConnectedThread createConnectedThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignVariables();
        initButtonClick();
        initViewPager();
        initStatusMonitor();
        initHandler();
    }

    /////////////////////////////////////////////////////////////////
    //// MY METHODS
    /////////////////////////////////////////////////////////////////
    private void assignVariables() {
        btn_connecting = findViewById(R.id.btn_connect);
        progressBar = findViewById(R.id.progressBar);
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tv_bt_address =findViewById(R.id.tv_bt_address);
        tv_bt_name = findViewById(R.id.tv_bt_name);
        tv_bt_status = findViewById(R.id.tv_con_status);
        createConnectedThread = CreateConnectedThread.getInstance();
        myBluetoothInfo = MyBluetoothInfo.getInstance();
    }

    private void initButtonClick() {

        btn_connecting.setOnClickListener((event)->{
            if(btn_connecting.getText().equals(getResources().getString(R.string.btn_connect))){
                if(myBluetoothInfo.getDeviceAddress().length() > 5 ){
                    progressBar.setVisibility(View.VISIBLE);
                    createConnectedThread = CreateConnectedThread.getInstance();
                    createConnectedThread.initThread(myBluetoothInfo.getDeviceAddress());
                    createConnectedThread.start();
                }
            } else {
                progressBar.setVisibility(View.VISIBLE);
                createConnectedThread.destroyThread();
            }
        });
    }

    private void initViewPager() {
        // Set view pager to tabLayout
        tabLayout.setupWithViewPager(viewPager);

        // Initialize viewpager adapter
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(), this);

        // Adding fragments to adapter
        vpAdapter.addNewFragment(new BluetoothFragment(),getResources().getString(R.string.title_fg_bluetooth),R.drawable.ic_bluetooth_connected_white);
        vpAdapter.addNewFragment(new HomeFragment(),getResources().getString(R.string.title_fg_home),R.drawable.ic_home_white);
        vpAdapter.addNewFragment(new MessageFragment(),getResources().getString(R.string.title_fg_message),R.drawable.ic_message_white);
        vpAdapter.addNewFragment(new SettingsFragment(),getResources().getString(R.string.title_fg_settings),R.drawable.ic_settings_white);

        // Set 4 pages as active
        viewPager.setOffscreenPageLimit(4);

        // Set adapter to viewPager
        viewPager.setAdapter(vpAdapter);
    }


    private void initStatusMonitor() {
        // Initialize status monitor task
        StatusMonitorTask statusMonitorTask = new StatusMonitorTask();

        // Create timer to task
        Timer statusTimer = new Timer();

        // Start bluetooth status monitoring every one second and with 2sec delay
        statusTimer.scheduleAtFixedRate(statusMonitorTask,2000,1000);

        // Start checking bluetooth status was change
        statusTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(myBluetoothInfo.statusWasChange()){
                    mainHandler.obtainMessage(myBluetoothInfo.getCurrentStatus()).sendToTarget();
                }
            }
        },2000,1000);
    }

    private void initHandler(){
        // Initiate a new handler to watch for new messages
        mainHandler = new Handler(Looper.getMainLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {

                // Checking the type of message received by all bluetooth statuses and device info status
                switch (msg.what){

                    // Status was change on disable
                    case DISABLE:
                        initGuiDisable();
                        break;

                    // Status was change on disconnect
                    case DISCONNECT:
                        initGuiDisconnected();
                        break;

                    // Status was change on connected
                    case CONNECTED:
                        initGuiConnected();
                        break;

                    case CANNOT_CONNECTED:
                        initGuiCannotConnected();
                        break;

                    // Update device info in gui
                    case UPDATE_DEVICE_INFO:
                        updateDeviceInfo();
                        break;
                }
            }
        };
    }

    private void initGuiCannotConnected() {
        BluetoothFragment.getBluetoothHandler().obtainMessage(Status.CANNOT_CONNECTED).sendToTarget();
        tv_bt_status.setText(getResources().getString(R.string.bt_connected));
        btn_connecting.setText(getResources().getString(R.string.btn_disconnect));
        tv_bt_address.setText(getResources().getString(R.string.bt_not_supported));
        tv_bt_name.setText(getResources().getString(R.string.bt_not_supported));
        btn_connecting.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void updateDeviceInfo() {
        tv_bt_name.setText(myBluetoothInfo.getDeviceName());
        tv_bt_address.setText(myBluetoothInfo.getDeviceAddress());
    }

    private void initGuiConnected() {
        BluetoothFragment.getBluetoothHandler().obtainMessage(Status.CONNECTED).sendToTarget();
        HomeFragment.getHomeHandler().obtainMessage(Status.CONNECTED).sendToTarget();
        tv_bt_status.setText(getResources().getString(R.string.bt_connected));
        btn_connecting.setText(getResources().getString(R.string.btn_disconnect));
        btn_connecting.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void initGuiDisable(){
        BluetoothFragment.getBluetoothHandler().obtainMessage(Status.DISABLE).sendToTarget();
        HomeFragment.getHomeHandler().obtainMessage(Status.DISABLE).sendToTarget();
        tv_bt_status.setText(getResources().getString(R.string.bt_disable));
        btn_connecting.setText(getResources().getString(R.string.btn_connect));
        btn_connecting.setEnabled(false);
        progressBar.setVisibility(View.INVISIBLE);
        clearGuiInfoDevice();
    }

    private void initGuiDisconnected(){
        BluetoothFragment.getBluetoothHandler().obtainMessage(Status.DISCONNECT).sendToTarget();
        HomeFragment.getHomeHandler().obtainMessage(Status.DISCONNECT).sendToTarget();
        tv_bt_status.setText(getResources().getString(R.string.bt_disconnected));
        btn_connecting.setText(getResources().getString(R.string.btn_connect));
        btn_connecting.setEnabled(true);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void clearGuiInfoDevice(){
        tv_bt_name.setText("...");
        tv_bt_address.setText("...");
    }

    // Get handler
    public static Handler getMainHandler() {
        return mainHandler;
    }
}