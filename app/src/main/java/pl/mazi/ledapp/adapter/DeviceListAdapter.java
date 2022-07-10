package pl.mazi.ledapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import pl.mazi.ledapp.MainActivity;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.bluetooth.MyBluetoothInfo;
import pl.mazi.ledapp.intf.What;
import pl.mazi.ledapp.model.DeviceInfoModel;

import java.util.ArrayList;
import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private Context context;
    private List<Object> deviceList;
    private MyBluetoothInfo myBluetoothInfo;
    private boolean isClickable;
    private int row_index;

    /////////////////////////////////////////////////////////////////
    //// ViewHolder class
    /////////////////////////////////////////////////////////////////
    public static class ViewHolder extends RecyclerView.ViewHolder{
        // Variables
        private TextView tv_Name;
        private TextView tv_Address;
        private LinearLayout linearLayout;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tv_Name = itemView.findViewById(R.id.textViewDeviceName);
            tv_Address = itemView.findViewById(R.id.textViewDeviceAddress);
            linearLayout = itemView.findViewById(R.id.linearLayoutDeviceInfo);
        }
    }

    /////////////////////////////////////////////////////////////////
    //// CONSTRUCTORS
    /////////////////////////////////////////////////////////////////
    public DeviceListAdapter() {
        deviceList = new ArrayList<>();
        myBluetoothInfo = MyBluetoothInfo.getInstance();
        isClickable = true;
    }

    public DeviceListAdapter(Context context) {
        this.context = context;
        this.deviceList = new ArrayList<>();
        myBluetoothInfo = MyBluetoothInfo.getInstance();
        isClickable = true;
    }

    /////////////////////////////////////////////////////////////////
    //// OVERRIDE METHODS
    /////////////////////////////////////////////////////////////////
    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Create item view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_info_layout,parent,false);
        // Return view holder with view
        return new ViewHolder(view);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        // Get item holder
        ViewHolder itemHolder = (ViewHolder) holder;

        // Get device from device list
        final DeviceInfoModel deviceInfoModel = (DeviceInfoModel) deviceList.get(position);

        // Set information about device to itemHolder in recycler view
        itemHolder.tv_Name.setText(deviceInfoModel.getDeviceName());
        itemHolder.tv_Address.setText(deviceInfoModel.getDeviceAddress());

        // Set on click on item in recycler view
        itemHolder.linearLayout.setOnClickListener(v -> {

            if(!isClickable){
                Toast.makeText(context, context.getResources().getString(R.string.toast_cannot_change_device), Toast.LENGTH_SHORT).show();
                return;
            }

            // Assigning new device data to fields
            myBluetoothInfo.setDeviceName(itemHolder.tv_Name.getText().toString());
            myBluetoothInfo.setDeviceAddress(itemHolder.tv_Address.getText().toString());

            // Assign position to row index
            row_index = position;

            // Sending a message to the handler in MainActivity to update the GUI
            MainActivity.getMainHandler().obtainMessage(What.UPDATE_DEVICE_INFO).sendToTarget();

            // Update date in recycler view
            notifyDataSetChanged();
        });

        // Change the color of the text in the item when you click on it
        if(row_index == position){
            itemHolder.tv_Address.setTextColor(Color.parseColor("#058ED9"));
            itemHolder.tv_Name.setTextColor(Color.parseColor("#058ED9"));
        } else {
            itemHolder.tv_Address.setTextColor(Color.parseColor("#FFFFFF"));
            itemHolder.tv_Name.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

    @Override
    public int getItemCount() {
        // Return size of device list
        return deviceList.size();
    }

    /////////////////////////////////////////////////////////////////
    //// MY METHODS
    /////////////////////////////////////////////////////////////////
    public void clear(){
        int size = deviceList.size();
        deviceList.clear();
        notifyItemRangeRemoved(0,size);
    }

    /////////////////////////////////////////////////////////////////
    //// SETTERS
    /////////////////////////////////////////////////////////////////
    @SuppressLint("NotifyDataSetChanged")
    public void setDeviceList(List<Object> deviceList) {
        this.deviceList = deviceList;
        notifyDataSetChanged();
    }


    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }
}
