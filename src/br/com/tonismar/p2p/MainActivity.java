package br.com.tonismar.p2p;

import android.net.wifi.p2p.WifiP2pManager;
import android.net.wifi.p2p.WifiP2pManager.Channel;
import android.net.wifi.p2p.WifiP2pManager.ChannelListener;
import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.view.Menu;

public class MainActivity extends Activity implements ChannelListener, DeviceActionListener {
	
	public static String TAG = "P2P";
	private final IntentFilter intentFilter = new IntentFilter();
    Channel mChannel;
	private WifiP2pManager mManager;
	private P2pBroadcastReceiver receiver;
	private boolean isWifiP2pEnabled;
	
	public void setIsWifiP2pEnabled(boolean isWifiP2pEnabled) {
		this.isWifiP2pEnabled = isWifiP2pEnabled;
	}
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        intentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
        
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	receiver = new P2pBroadcastReceiver(mManager, mChannel, this);
    	registerReceiver(receiver, intentFilter);
    }
    
    @Override
    public void onPause() {
    	super.onPause();
    	unregisterReceiver(receiver);
    }
    
    public void resetData() {
    	DeviceListFragment fragmentList = (DeviceListFragment) getFragmentManager()
    			.findFragmentById(R.id.frag_list);
    	DeviceDetailFragment fragmentDetails = (DeviceDetailFragment) getFragmentManager()
    			.findFragmentById(R.id.frag_detail);
    	if (fragmentDetails != null) {
    		fragmentDetails.resetViews();
    	}
    }
    
}
