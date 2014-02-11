package br.com.tonismar.p2p;

import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class DeviceListFragment {

	private View mContentView;
	private WifiP2pDevice device;

	public void updateThisDevice(WifiP2pDevice device) {
		// TODO Auto-generated method stub
		this.device = device;
		TextView view = (TextView) mContentView.findViewById(R.id.my_name);
		view = (TextView) mContentView.findViewById(R.id.my_status);
		view.setText(getDeviceStatus(device.status));
	}

	private static String getDeviceStatus(int deviceStatus) {
		// TODO Auto-generated method stub
		Log.d(MainActivity.TAG, "Peer status :" + deviceStatus);
		switch (deviceStatus) {
		case WifiP2pDevice.AVAILABLE:
			return "Available";
		case WifiP2pDevice.INVITED:
			return "Invited";
		case WifiP2pDevice.CONNECTED:
			return "Connected";
		case WifiP2pDevice.FAILED:
			return "Failed";
		case WifiP2pDevice.UNAVAILABLE:
			return "Unavailable";
		default:
			return "Unknown";
		}
	}

}
