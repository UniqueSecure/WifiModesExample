package io.mepos.meposwifiexample;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.uniquesecure.meposconnect.MePOSConnectionType;

import java.util.HashMap;

import io.mepos.meposwifiexample.persistence.MePOSSingleton;
import io.mepos.meposwifiexample.persistence.TestSharedPreferences;


public abstract class MePOSAbstractActivity extends AppCompatActivity {

    protected TestSharedPreferences testInfo;
    private static final String TAG = MePOSAbstractActivity.class.getSimpleName();

    public static final int MEPOS_VENDOR_ID = 11406;
    public static final int MEPOS_PRODUCT_ID = 9220;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        testInfo= new TestSharedPreferences(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    protected boolean isAMePOS(UsbDevice device) {
        return device.getVendorId() == MEPOS_VENDOR_ID &&
                device.getProductId() == MEPOS_PRODUCT_ID;
    }

    protected void findMePOSUSB() {
        UsbManager manager = (UsbManager) getSystemService(Context.USB_SERVICE);

        HashMap<String, UsbDevice> deviceList = manager.getDeviceList();
        for (UsbDevice device : deviceList.values()) {
            if (isAMePOS(device)) {
                MePOSSingleton.createInstance(getApplicationContext(), MePOSConnectionType.USB);
                MePOSSingleton.lastStateUsbAttached = true;
            }
        }
    }
}
