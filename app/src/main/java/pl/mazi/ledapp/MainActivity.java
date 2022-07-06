package pl.mazi.ledapp;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import pl.mazi.ledapp.adapter.VPAdapter;
import pl.mazi.ledapp.fragment.BluetoothFragment;
import pl.mazi.ledapp.fragment.HomeFragment;
import pl.mazi.ledapp.fragment.MessageFragment;
import pl.mazi.ledapp.fragment.SettingsFragment;

public class MainActivity extends AppCompatActivity {

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TextView tv_bt_status;
    private TextView tv_bt_name;
    private TextView tv_bt_address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        assignVariables();
        initViewPager();
    }


    /////////////////////////////////////////////////////////////////
    //// MY METHODS
    /////////////////////////////////////////////////////////////////
    private void assignVariables() {
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);
        tv_bt_address =findViewById(R.id.tv_bt_address);
        tv_bt_name = findViewById(R.id.tv_bt_name);
        tv_bt_status = findViewById(R.id.tv_con_status);
    }

    private void initViewPager() {
        // Set view paget to tablayout
        tabLayout.setupWithViewPager(viewPager);

        // Set selected tab layout icon color
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:

                        break;

                    case 1:
                        break;

                    case 2:

                        break;

                    case 3:

                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // Initialize viewpager adapter
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager(),this);

        // Initialize adapter fragments
        vpAdapter.addNewFragment(new BluetoothFragment(),getResources().getString(R.string.title_fg_bluetooth),R.drawable.ic_bluetooth_connected_white);
        vpAdapter.addNewFragment(new HomeFragment(),getResources().getString(R.string.title_fg_home),R.drawable.ic_home_white);
        vpAdapter.addNewFragment(new MessageFragment(),getResources().getString(R.string.title_fg_message),R.drawable.ic_message_white);
        vpAdapter.addNewFragment(new SettingsFragment(),getResources().getString(R.string.title_fg_settings),R.drawable.ic_settings_white);

        // Set adapter to viewPager
        viewPager.setAdapter(vpAdapter);
    }
}