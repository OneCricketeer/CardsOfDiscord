package edu.rosehulman.csse.cardsofdiscord.util;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import edu.rosehulman.csse.cardsofdiscord.R;

public class BluetoothHandler extends Handler {

    private static final String TAG = BluetoothHandler.class.getSimpleName();
    private Context mContext;

    public BluetoothHandler(Context context) {
        this.mContext = context;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case BluetoothChatService.MESSAGE_STATE_CHANGE:
                Log.i(TAG, "MESSAGE_STATE_CHANGE: " + msg.arg1);
                switch (msg.arg1) {
                    case BluetoothChatService.STATE_CONNECTED:
                        // TODO: Handle now connected state
//                        mTitle.setText(R.string.title_connected_to);
//                        mTitle.append(mConnectedDeviceName);
//                        mConversationArrayAdapter.clear();
                        break;
                    case BluetoothChatService.STATE_CONNECTING:
                        // TODO: Handle connecting state
//                        mTitle.setText(R.string.title_connecting);
                        break;
                    case BluetoothChatService.STATE_LISTEN:
                    case BluetoothChatService.STATE_NONE:
                        // TODO: Handle not connected (yet) state
//                        mTitle.setText(R.string.title_not_connected);
                        break;
                }
                break;
            case BluetoothChatService.MESSAGE_WRITE:
                byte[] writeBuf = (byte[]) msg.obj;
                // construct a string from the buffer
                String writeMessage = new String(writeBuf);
                // TODO: Interpret written message (from self)
                Log.d(TAG, "MESSAGE_WRITE: " + writeMessage);
//                mConversationArrayAdapter.add("Me:  " + writeMessage);
                break;
            case BluetoothChatService.MESSAGE_READ:
                byte[] readBuf = (byte[]) msg.obj;
                // construct a string from the valid bytes in the buffer
                String readMessage = new String(readBuf, 0, msg.arg1);
                // TODO: Interpret read message (from connection)
                if (!readMessage.isEmpty()) {
                    Log.d(TAG, "MESSAGE_READ: " + readMessage);
//                    mConversationArrayAdapter.add(mConnectedDeviceName+":  " + readMessage);
                }
                break;
            case BluetoothChatService.MESSAGE_DEVICE_NAME:
                // save the connected device's name
                // TODO: Keep track of who we are connected to
                String mConnectedDeviceName = msg.getData().getString(BluetoothChatService.DEVICE_NAME);
                Toast.makeText(mContext, "Connected to "
                        + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
                break;
            case BluetoothChatService.MESSAGE_TOAST:
                //if (!msg.getData().getString(TOAST).contains("Unable to connect device")) {
                // TODO: Toast a received message?
                Toast.makeText(mContext, msg.getData().getString(BluetoothChatService.TOAST),
                        Toast.LENGTH_SHORT).show();
                //}
                break;
        }
    }
}
