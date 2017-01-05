# MePOS-WIFI-modes

This is a sample code to  integrate the MePOS wifi methods features to your application with a touch of visual interaction. On this example we are showing the four wifi methods.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites


* Go to [Mepos.io](http://mepos.io/developers) and register as developer. There you will find the last SDK file for your project.

### Installing

* Prepare you manifest.xml file (necessary for wifi-printing) and include the following line:
```
<uses-permission android:name="android.permission.INTERNET" />
```
* Create a BroadcastReceiver for the USB device.

```
android.hardware.usb.action.USB_DEVICE_ATTACHED
android.hardware.usb.action.USB_DEVICE_DETACHED
```

## Running the tests

Create a variable as:

```
private MePOS mepos;
```

After that you have to instantiate it.

```
MePOSConnectionManager manager = mepos.getConnectionManager();
```

There are four wifi modes for your MePOS

1.- Default

```
result = manager.MePOSConnectDefault(this);
```

2.- Access point

```
result = manager.MePOSSetAccessPoint(config.getSsid(), config.getEncryption(), config.getPassword(), this);
```

3.- Ethernet client

```
result = manager.MePOSConnectEthernet(this);
```

4.- Wifi client

```
result = manager.MePOSConnectWiFi(config.getSsid(), config.getIp(), config.getNetmask(), config.getEncryption(), config.getPassword(), this);
```

### About the test

The code on this repository is an example from the SDK and for MePOS developers willing to implement it.

## Reference

* [Android SDK guide](http://mepos.io/) - Developers section.


## Contact

Please send us an email: us.unique.secure@gmail.com