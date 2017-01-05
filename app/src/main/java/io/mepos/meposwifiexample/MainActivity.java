package io.mepos.meposwifiexample;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Space;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.uniquesecure.meposconnect.EncryptionMode;
import com.uniquesecure.meposconnect.MePOS;
import com.uniquesecure.meposconnect.MePOSConnectionType;

import io.mepos.meposwifiexample.networkconfig.GetWifiInfo;
import io.mepos.meposwifiexample.networkconfig.NetworkConfigAsync;
import io.mepos.meposwifiexample.networkconfig.NetworkConfiguration;
import io.mepos.meposwifiexample.persistence.ConfigurationNetModes;
import io.mepos.meposwifiexample.persistence.MePOSSingleton;

public class MainActivity extends MePOSAbstractActivity implements NetworkConfigAsync.OnNetworkConfigCompleted {

    Switch mWifiUSBSwitch;
    GetWifiInfo mGetWifiInfo;
    TextView mNetModeText;
    TextView mSSIDText;
    TextView mIpAddressText;
    TextView mPasswordWifiText;
    TextView mTxtPasswordWifiClient;
    TextView mLabelSSIDwc;
    Button mBtnWifiDefault;
    Button mBtnWifiAP;
    Button mBtnWifiEth;
    Button mBtnWifiWC;
    Button mButtomGoAP;
    Button mButtomGoWC;
    EditText mTxtSsidapmode;
    EditText mTxtPasswordapmode;
    String smtxtSsidapmode;
    String smtxtPasswordapmode;
    String mssid;
    Spinner mSpinnerEncryptionClient;
    Space mCardsSpace;
    Space mCardsSpaceWc;
    CardView mCard_view_config_wifi;
    CardView mCard_view__wifi_client;

    NetworkConfiguration networkConfig = new NetworkConfiguration();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWifiUSBSwitch = (Switch) findViewById(R.id.wifiUSBSwitch);
        mNetModeText = (TextView) findViewById(R.id.netModeText);
        mSSIDText = (TextView) findViewById(R.id.ssidText);
        mIpAddressText = (TextView) findViewById(R.id.ipAddressText);
        mPasswordWifiText = (TextView) findViewById(R.id.passwordWifiText);
        mTxtPasswordWifiClient = (TextView) findViewById(R.id.txtPasswordWifiClient);
        mLabelSSIDwc = (TextView) findViewById(R.id.labelSSIDwc);
        mBtnWifiDefault = (Button) findViewById(R.id.btnWifiDefault);
        mBtnWifiAP = (Button) findViewById(R.id.btnWifiAP);
        mBtnWifiEth = (Button) findViewById(R.id.btnWifiEth);
        mBtnWifiWC = (Button) findViewById(R.id.btnWifiWC);
        mButtomGoAP = (Button) findViewById(R.id.buttomGoAP);
        mButtomGoWC = (Button) findViewById(R.id.buttomGoWC);
        mTxtSsidapmode = (EditText) findViewById(R.id.txtSsidapmode);
        mTxtPasswordapmode = (EditText) findViewById(R.id.txtPasswordapmode);
        mSpinnerEncryptionClient = (Spinner) findViewById(R.id.spinnerEncryptionClient);
        mCardsSpace = (Space) findViewById(R.id.cardsSpace);
        mCardsSpaceWc = (Space) findViewById(R.id.cardsSpaceWc);
        mCard_view_config_wifi = (CardView) findViewById(R.id.card_view_config_wifi);
        mCard_view__wifi_client = (CardView) findViewById(R.id.card_view__wifi_client);

        mGetWifiInfo = new GetWifiInfo(getApplicationContext());

        mssid = mGetWifiInfo.getWifiName();
        mLabelSSIDwc.setText(mssid);
        String capabilities = mGetWifiInfo.getCapabilitiesFor(mssid);
        try {
            int posCapabilityMostQualified = EncryptionMode.valueOf(capabilities).ordinal();
            mSpinnerEncryptionClient.setSelection(posCapabilityMostQualified);
        } catch (Exception e) {
            mSpinnerEncryptionClient.setSelection(EncryptionMode.NONE.ordinal());
        }


        mWifiUSBSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean select) {
                if (select) {
                    Toast.makeText(MainActivity.this, "WIFI configured", Toast.LENGTH_SHORT).show();
                    MePOSSingleton.createInstance(getApplicationContext(), MePOSConnectionType.WIFI);
                    MePOSSingleton.getInstance().getConnectionManager().setConnectionIPAddress(testInfo.getTestInfo("wifiipaddress"));
                } else {
                    Toast.makeText(MainActivity.this, "USB configured", Toast.LENGTH_SHORT).show();
                    CreateUSBInstance();
                }
            }
        });



        mBtnWifiDefault.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CreateUSBInstance()) {
                    networkConfig.setMode(ConfigurationNetModes.DEFAULT);
                    new NetworkConfigAsync(MainActivity.this, MePOSSingleton.getInstance(), MainActivity.this).execute(networkConfig);
                }
            }
        });

        mBtnWifiAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardsSpace.setVisibility(View.VISIBLE);
                mCard_view_config_wifi.setVisibility(View.VISIBLE);
                HideShowButtons(View.INVISIBLE);
            }
        });

        mButtomGoAP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CreateUSBInstance()) {
                    mCardsSpace.setVisibility(View.GONE);
                    mCard_view_config_wifi.setVisibility(View.GONE);
                    HideShowButtons(View.VISIBLE);
                    testInfo.saveTestInfo("wifipassword", mTxtPasswordapmode.getText().toString());
                    smtxtSsidapmode = mTxtSsidapmode.getText().toString();
                    smtxtPasswordapmode = mTxtPasswordapmode.getText().toString();
                    NetworkConfigAsync networkConfigAsync = new NetworkConfigAsync(MainActivity.this, MePOSSingleton.getInstance(), MainActivity.this);
                    NetworkConfiguration config = new NetworkConfiguration();
                    config.setMode(ConfigurationNetModes.AP);
                    config.setSsid(smtxtSsidapmode);
                    config.setEncryption(EncryptionMode.WPA2_AES.toString());
                    config.setPassword(smtxtPasswordapmode);
                    networkConfigAsync.execute(config);
                }
            }
        });

        mBtnWifiEth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CreateUSBInstance()) {
                    networkConfig.setMode(ConfigurationNetModes.ETHERNET_CLIENT);
                    new NetworkConfigAsync(MainActivity.this, MePOSSingleton.getInstance(), MainActivity.this).execute(networkConfig);
                }
            }
        });

        mBtnWifiWC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCardsSpaceWc.setVisibility(View.VISIBLE);
                mCard_view__wifi_client.setVisibility(View.VISIBLE);
                HideShowButtons(View.INVISIBLE);
            }
        });

        mButtomGoWC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String encryption = mSpinnerEncryptionClient.getSelectedItem().toString();
                if (CreateUSBInstance()) {
                    mCard_view__wifi_client.setVisibility(View.GONE);
                    mCardsSpaceWc.setVisibility(View.GONE);
                    HideShowButtons(View.VISIBLE);
                    NetworkConfiguration networkConfig = new NetworkConfiguration();
                    networkConfig.setMode(ConfigurationNetModes.WIFI_CLIENT);
                    networkConfig.setSsid(mssid);
                    networkConfig.setIp("DHCP"); //This is used for automatic mode
                    networkConfig.setNetmask("255.255.255.0"); //This is used for automatic mode
                    networkConfig.setEncryption(encryption);
                    networkConfig.setPassword(mTxtPasswordWifiClient.getText().toString());
                    new NetworkConfigAsync(MainActivity.this, MePOSSingleton.getInstance(), MainActivity.this).execute(networkConfig);
                }
            }
        });

    }

    @Override
    public void onComplete(Boolean success) {
        mWifiUSBSwitch.setVisibility(View.VISIBLE);

        mNetModeText.setVisibility(View.VISIBLE);
        mSSIDText.setVisibility(View.VISIBLE);
        mIpAddressText.setVisibility(View.VISIBLE);
        mPasswordWifiText.setVisibility(View.VISIBLE);

        mNetModeText.setText(testInfo.getTestInfo("currentNetMode"));
        mSSIDText.setText(testInfo.getTestInfo("wifissid"));
        mIpAddressText.setText(testInfo.getTestInfo("wifiipaddress"));
        mPasswordWifiText.setText(testInfo.getTestInfo("wifipassword"));
    }

    public boolean CreateUSBInstance() {
        try {
            MePOSSingleton.createInstance(getApplicationContext(), MePOSConnectionType.USB);
            MePOSSingleton.getInstance().getConnectionManager().MePOSGetAssignedIP();
            return true;
        } catch (Exception e) {
            Toast.makeText(MainActivity.this, this.getString(R.string.dockMePOS), Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public void HideShowButtons(int visibility) {
        mBtnWifiDefault.setVisibility(visibility);
        mBtnWifiAP.setVisibility(visibility);
        mBtnWifiEth.setVisibility(visibility);
        mBtnWifiWC.setVisibility(visibility);
    }

}
