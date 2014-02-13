package br.com.tonismar.p2p;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

public class FileTransferService extends IntentService {
	
	public static final int SOCKET_TIMEOUT = 5000;
	public static final String EXTRAS_GROUP_OWNER_PORT = "go_port";
	public static final String EXTRAS_FILE_PATH = "file_url";
	public static final String EXTRAS_GROUP_OWNER_ADDRESS = "go_host";
	public static final String ACTION_SEND_FILE = "br.com.tonismar.p2p.SEND_FILE";

	public FileTransferService(String name) {
		super(name);
	}
	
	public FileTransferService() {
		super("FileTransferService");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Context context = getApplicationContext();
		if (intent.getAction().equals(ACTION_SEND_FILE)) {
			String fileUri = intent.getExtras().getString(EXTRAS_FILE_PATH);
			String host = intent.getExtras().getString(EXTRAS_GROUP_OWNER_ADDRESS);
			Socket socket = new Socket();
			int port = intent.getExtras().getInt(EXTRAS_GROUP_OWNER_PORT);
			
			try {
				Log.d(MainActivity.TAG, "Opening client socket - ");
				socket.bind(null);
				socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);
				
				Log.d(MainActivity.TAG, "Client socket - " + socket.isConnected());
				OutputStream stream = socket.getOutputStream();
				ContentResolver cr = context.getContentResolver();
				InputStream is = null;
				try {
					is = cr.openInputStream(Uri.parse(fileUri));
				} catch (FileNotFoundException e) {
					Log.d(MainActivity.TAG, e.toString());
				}
				DeviceDetailFragment.copyFile(is, stream);
				Log.d(MainActivity.TAG, "Client: Data written");
			} catch (IOException e) {
				Log.e(MainActivity.TAG, e.getMessage());
			} finally {
				if (socket != null) {
					if (socket.isConnected()) {
						try {
							socket.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

}
