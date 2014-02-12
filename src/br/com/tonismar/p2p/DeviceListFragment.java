package br.com.tonismar.p2p;

import java.util.List;

import android.app.ListFragment;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.PeerListListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class DeviceListFragment extends ListFragment implements PeerListListener {

	private View mContentView;
	private WifiP2pDevice device;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		this.setListAdapter(new WifiPeerListAdapter(getActivity(), R.layout.row_devices, peers));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState) {
		mContentView = inflater.inflate(R.layout.device_list, null);
		return mContentView;
	}
	
	public WifiP2pDevice getDevice() {
		return device;
	}
	
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
	
	private class WifiPeerListAdapter extends ArrayAdapter<WifiP2pDevice> {
		
		private List<WifiP2pDevice> items;
		
		public WifiPeerListAdapter(Context context, int textViewResourceId,
								   List<WifiP2pDevice> objects) {
			super(context, textViewResourceId, objects);
			items = objects;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.row_devices, null);
			}
			WifiP2pDevice device = items.get(position);
			if (device != null) {
				
			}
		}
	}

}
