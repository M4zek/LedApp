package pl.mazi.ledapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;
import pl.mazi.ledapp.R;
import pl.mazi.ledapp.model.DeviceInfoModel;

import java.util.ArrayList;
import java.util.List;

public class DeviceListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private Context context;
    private List<Object> deviceList;

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
    }

    public DeviceListAdapter(Context context, List<Object> deviceList) {
        this.context = context;
        this.deviceList = deviceList;
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

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        // Get item holder
        ViewHolder itemHolder = (ViewHolder) holder;

        // Get device from device list
        final DeviceInfoModel deviceInfoModel = (DeviceInfoModel) deviceList.get(position);

        // Set information about device to itemHolder in recycler view
        itemHolder.tv_Name.setText(deviceInfoModel.getDeviceName());
        itemHolder.tv_Address.setText(deviceInfoModel.getDeviceAddress());
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
        deviceList.clear();
        notifyItemRangeRemoved(0,deviceList.size());
    }

    /////////////////////////////////////////////////////////////////
    //// SETTERS
    /////////////////////////////////////////////////////////////////
    public void setDeviceList(List<Object> deviceList) {
        this.deviceList = deviceList;
    }
}
