package edu.rosehulman.csse.cardsofdiscord;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeSet;

import edu.rosehulman.csse.cardsofdiscord.util.BluetoothChatService;
import edu.rosehulman.csse.cardsofdiscord.util.BluetoothHandler;

public class BluetoothLanActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = BluetoothLanActivity.class.getSimpleName();

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    private ListView mFoundDevicesListView;
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mFoundDevicesAdapter;

    // Intent request codes
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static final int REQUEST_ENABLE_BT = 2;

    private boolean mIsServer;
    private Timer mPairedDevicesTimer = new Timer();
    private StringBuffer mOutStringBuffer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lan_bluetooth);
        showProgressBar(false);

        mFoundDevicesAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        mFoundDevicesListView = (ListView) findViewById(R.id.found_devices_listview);
        mFoundDevicesListView.setAdapter(mFoundDevicesAdapter);
        mFoundDevicesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mIsServer) {
                    // Cancel discovery because it's costly and we're about to connect
                    mBtAdapter.cancelDiscovery();

                    // Get the device MAC address, which is the last 17 chars in the View
                    String info = ((TextView) view).getText().toString();
                    String address = info.substring(info.length() - 17);

                    // Get the BLuetoothDevice object
                    BluetoothDevice device = mBtAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mBtChatService.connect(device);

                }
            }
        });

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        this.registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        this.registerReceiver(mReceiver, filter);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        TextView deviceNameTextView = (TextView) findViewById(R.id.bt_device_name_textview);
        deviceNameTextView.setText("Device name: " + mBtAdapter.getName() + "\n" +
                "Address: " + mBtAdapter.getAddress());

        findViewById(R.id.create_bt_game_button).setOnClickListener(this);
        findViewById(R.id.join_bt_game_button).setOnClickListener(this);
        findViewById(R.id.start_bt_game_button).setOnClickListener(this);
    }

    private void ensureDiscoverable() {
        Log.d(TAG, "ensure discoverable");
        if (mBtAdapter.getScanMode() != BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE) {
            mIsServer = true;
            setBluetoothDiscoverable(120);
        }
    }

    private void setBluetoothDiscoverable(int seconds) {
        Intent discoverableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
        discoverableIntent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, seconds);
        startActivity(discoverableIntent);
    }

    private void showProgressBar(boolean show) {
        findViewById(android.R.id.progress).setVisibility(show ? View.VISIBLE : View.GONE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult " + resultCode);
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE:
                // When DeviceListActivity returns with a device to connect
                if (resultCode == Activity.RESULT_OK) {
                    // Get the device MAC address
                    String address = data.getExtras()
                            .getString(EXTRA_DEVICE_ADDRESS);
                    // Get the BluetoothDevice object
                    BluetoothDevice device = mBtAdapter.getRemoteDevice(address);
                    // Attempt to connect to the device
                    mBtChatService.connect(device);
                    Log.d(TAG, "Connect to device");
                }
                break;
            case REQUEST_ENABLE_BT:
                // When the request to enable Bluetooth returns
                if (resultCode == Activity.RESULT_OK) {
                    // Bluetooth is now enabled, so set up a chat session
                    setupChat();
                    Log.d(TAG, "Bluetooth enabled");
                } else {
                    // User did not enable Bluetooth or an error occurred
                    Log.d(TAG, "Bluetooth not enabled");
                    Toast.makeText(this, "Bluetooth not enabled.", Toast.LENGTH_SHORT).show();
//                    finish();
                }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // If BT is not on, request that it be enabled.
        // setupChat() will then be called during onActivityResult
        if (!mBtAdapter.isEnabled()) {
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
            // Otherwise, setup the chat session
        } else {
            if (mBtChatService == null) setupChat();
            Toast.makeText(this, "SetupChat", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupChat() {
        Log.d(TAG, "setupChat()");

        // Initialize the BluetoothChatService to perform bluetooth connections
        mBtChatService = new BluetoothChatService(this, new BluetoothHandler(this));

        // Initialize the buffer for outgoing messages
        mOutStringBuffer = new StringBuffer("");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mPairedDevicesTimer.cancel();
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        this.unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        mIsServer = false;
        Log.d(TAG, "doDiscovery()");

        // Indicate scanning in the title
        showProgressBar(true);

        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    // The BroadcastReceiver that listens for discovered devices and
    // changes the title when discovery is finished
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
                Log.i(TAG, "FOUND DEVICE BONDED " + (device.getBondState() == BluetoothDevice.BOND_BONDED));
                String deviceDetails = device.getName() + "\n" + device.getAddress();
                mFoundDevicesAdapter.remove(deviceDetails);
                mFoundDevicesAdapter.add(deviceDetails);

//                }
             // discovery is finished
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                showProgressBar(false);
                if (mFoundDevicesAdapter.getCount() == 0) {
                    String noDevices = "No devices found";
                    mFoundDevicesAdapter.add(noDevices);
                }
            }
            mFoundDevicesAdapter.notifyDataSetChanged();
        }
    };

    private void startChatService() {
        if (mBtChatService != null) {
            // Only if the state is STATE_NONE, do we know that we haven't started already
            if (mBtChatService.getState() == BluetoothChatService.STATE_NONE) {
                // Start the Bluetooth chat services
                mBtChatService.start();
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.create_bt_game_button:
                ensureDiscoverable();
                TextView title = (TextView) findViewById(R.id.found_devices_list_title);
                title.setText("Paired Devices");
                findPairedDevices();
                startChatService();
                break;
            case R.id.join_bt_game_button:
                doDiscovery();
                startChatService();
                break;
            case R.id.start_bt_game_button:
                mPairedDevicesTimer.cancel();
                if (findViewById(R.id.start_bt_game_button).isEnabled()) {

                } else {

                }
                break;
            default:
                break;
        }
    }

    private void findPairedDevices() {
        final Handler handler = new Handler();
        mPairedDevicesTimer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if (mIsServer && mBtAdapter.isEnabled()) {
                            try {
                                FetchPairedDevicesTask task = new FetchPairedDevicesTask();
                                task.execute();
                            } catch (Exception e) {
                                // TODO Auto-generated catch block
                            }
                        }
                    }
                });
            }
        };
        mPairedDevicesTimer.schedule(timerTask, 0, 2000); // time in ms
    }

    class FetchPairedDevicesTask extends AsyncTask<Void, Void, Set<BluetoothDevice>> {


        @Override
        protected Set<BluetoothDevice> doInBackground(Void... params) {
            Set<BluetoothDevice> deviceSet = new HashSet<BluetoothDevice>();
            if (mBtChatService != null) {
                deviceSet.addAll(mBtChatService.getConnectedDevices());
                return deviceSet;
            } else {
                return mBtAdapter.getBondedDevices();
            }

        }

        @Override
        protected void onPostExecute(Set<BluetoothDevice> devices) {
            mFoundDevicesAdapter.clear();
            for (BluetoothDevice device : devices) {
                mFoundDevicesAdapter.add(device.getName() + "\n" + device.getAddress());
            }
            mFoundDevicesAdapter.notifyDataSetChanged();

            boolean canStartGame = mFoundDevicesAdapter.getCount() >= 2;
            findViewById(R.id.start_bt_game_button).setEnabled(canStartGame);

        }
    }
}
