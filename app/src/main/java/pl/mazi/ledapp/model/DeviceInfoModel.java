package pl.mazi.ledapp.model;

public class DeviceInfoModel {
    /////////////////////////////////////////////////////////////////
    //// VARIABLES
    /////////////////////////////////////////////////////////////////
    private String deviceName;
    private String deviceAddress;


    /////////////////////////////////////////////////////////////////
    //// CONSTRUCTORS
    /////////////////////////////////////////////////////////////////
    public DeviceInfoModel(String deviceName, String deviceAddress) {
        this.deviceName = deviceName;
        this.deviceAddress = deviceAddress;
    }

    /////////////////////////////////////////////////////////////////
    //// GETTERS
    /////////////////////////////////////////////////////////////////
    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceAddress() {
        return deviceAddress;
    }
}