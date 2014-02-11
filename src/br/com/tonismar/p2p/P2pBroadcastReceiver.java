package br.com.tonismar.p2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.util.Log;

public class P2pBroadcastReceiver extends BroadcastReceiver {
	
	private WifiP2pManager manager;
	private Channel channel;
	private MainActivity activity;

	public P2pBroadcastReceiver(WifiP2pManager manager, Channel channel,
								MainActivity activity) {
		super();
		this.manager = manager;
		this.channel = channel;
		this.activity = activity;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
			int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, -1);
			if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED) {
				activity.setIsWifiP2pEnabled(true);
			} else {
				activity.setIsWifiP2pEnabled(false);
				activity.resetData();
			}
			Log.d(MainActivity.TAG, "P2P state changed - " + state);
		} else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
			
		} else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
			
		} else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
			DeviceListFragment fragment = (DeviceListFragment) activity.getFragmentManager()
					.findFragmentById(R.id.frag_list);
			fragment.updateThisDevice((WifiP2pManager) intent.getParcelableExtra(
					WifiP2pManager.EXTRA_WIFI_P2P_DEVICE));
		}
	}	
}
