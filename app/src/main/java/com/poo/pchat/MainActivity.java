package com.poo.pchat;

import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.*;
import android.view.inputmethod.EditorInfo;
import android.widget.*;
import com.poo.pchat.entities.Message;
import com.poo.pchat.tcp.ClientAsyncTask;
import com.poo.pchat.tcp.ConnectionError;
import com.poo.pchat.tcp.OnConnectionEventOccurs;
import com.poo.pchat.view.CustomDialog;
import com.poo.pchat.view.UserListTab;

import java.text.SimpleDateFormat;

/**
 * This class represents the main activity.
 */
public class MainActivity extends ActionBarActivity implements View.OnClickListener, OnConnectionEventOccurs {

    private DrawerLayout mDrawerLayout;

    private LinearLayout mDisplay;

    private UserListTab mUserListTab;
    private ImageButton mUserListBt;

    private EditText mInput;
    private ImageButton mSend;

    private ClientAsyncTask mClientAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDisplay = (LinearLayout) findViewById(R.id.main_chat_display);

        mUserListTab = (UserListTab) findViewById(R.id.main_user_list);

        mUserListBt = (ImageButton) findViewById(R.id.main_user_list_bt);
        mUserListBt.setOnClickListener(this);

        mInput = (EditText) findViewById(R.id.main_input);
        mInput.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent event) {

                if (keyCode == EditorInfo.IME_ACTION_SEARCH ||
                        keyCode == EditorInfo.IME_ACTION_DONE ||
                        event.getAction() == KeyEvent.ACTION_DOWN &&
                                event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {

                    if(mClientAsyncTask == null || !mClientAsyncTask.isConnected()){
                        Toast.makeText(MainActivity.this, getString(R.string.main_not_connected), Toast.LENGTH_LONG).show();
                        return true;
                    }

                    mClientAsyncTask.sendForAll(mInput.getText().toString());
                    mInput.setText("");
                    return true;

                }
                return false;
            }
        });

        mSend = (ImageButton) findViewById(R.id.main_send_bt);
        mSend.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.action_audio:
                if(mClientAsyncTask == null || !mClientAsyncTask.isConnected()){
                    Toast.makeText(MainActivity.this, getString(R.string.main_not_connected), Toast.LENGTH_LONG).show();
                    return true;
                }

                return true;
            case R.id.action_image:
                if(mClientAsyncTask == null || !mClientAsyncTask.isConnected()){
                    Toast.makeText(MainActivity.this, getString(R.string.main_not_connected), Toast.LENGTH_LONG).show();
                    return true;
                }

                return true;
            case R.id.action_connect_disconnect:
                if(mClientAsyncTask == null || !mClientAsyncTask.isConnected())
                    connect(item);
                else
                    disconnect(item);
                return true;
            case R.id.action_change_name:
                if(mClientAsyncTask == null || !mClientAsyncTask.isConnected()){
                    Toast.makeText(MainActivity.this, getString(R.string.main_not_connected), Toast.LENGTH_LONG).show();
                    return true;
                }

                final CustomDialog customDialog = new CustomDialog(this);
                customDialog.setMessage(getString(R.string.main_put_new_name));
                customDialog.setOkOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(mClientAsyncTask != null && mClientAsyncTask.isConnected())
                            mClientAsyncTask.changeName(customDialog.readInput());
                        else
                            Toast.makeText(MainActivity.this, getString(R.string.main_not_connected), Toast.LENGTH_LONG).show();
                        customDialog.dismiss();
                    }
                });
                customDialog.show();
                return true;
            case R.id.action_about:
                Dialog d = new Dialog(this);
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.about_dialog);
                TextView version = (TextView)d.findViewById(R.id.about_version);
                try {
                    PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                    version.setText(getString(R.string.app_name) + "\nv" + pInfo.versionName);
                }catch(Exception e){
                    version.setText(getString(R.string.app_name));
                }
                d.show();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Starts a new connection.
     */
    public void connect(final MenuItem item){
        final CustomDialog dialog = new CustomDialog(this);
        dialog.setMessage(getString(R.string.main_put_server_ip));

        if(mClientAsyncTask != null)
            dialog.setInputText(mClientAsyncTask.getServerAddress());
        else
            dialog.setInputText("");

        dialog.setOkOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String[] ipPort = dialog.readInput().split(":");
                    mClientAsyncTask = new ClientAsyncTask(MainActivity.this);

                    mClientAsyncTask.connect(ipPort[0], Integer.parseInt(ipPort[1]));
                    dialog.dismiss();

                    if(item != null){
                        item.setTitle(getString(R.string.action_disconnect));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, getString(R.string.main_connection_failure), Toast.LENGTH_LONG).show();
                }
            }
        });
        dialog.show();
    }

    /**
     * Closes the current connection.
     */
    public void disconnect(MenuItem item){
        try {
            mClientAsyncTask.disconnect();
        }catch(Exception e){
            Toast.makeText(MainActivity.this, getString(R.string.main_disconnection_failure), Toast.LENGTH_LONG).show();
        }

        if(item != null){
            item.setTitle(getString(R.string.action_connect));
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.main_send_bt:
                if(mClientAsyncTask == null || !mClientAsyncTask.isConnected()){
                    Toast.makeText(MainActivity.this, getString(R.string.main_not_connected), Toast.LENGTH_LONG).show();
                    break;
                }

                mClientAsyncTask.sendForAll(mInput.getText().toString());
                mInput.setText("");
                break;

            case R.id.main_user_list_bt:
                mDrawerLayout.openDrawer(mUserListTab);
                break;
        }
    }

    @Override
    public void onDisconnect(final ConnectionError e) {
        // Called by ClientAsyncTask.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(e != null){
                    Toast.makeText(MainActivity.this, getString(R.string.main_disconnection_failure) +
                            " " + getString(R.string.main_error_code) + " " + e.getCode() +".", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,
                            getString(R.string.main_successfully_disconnected), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onConnect(final ConnectionError e) {
        // Called by ClientAsyncTask.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(e != null){
                    Toast.makeText(MainActivity.this, getString(R.string.main_connection_failure) +
                            " " + getString(R.string.main_error_code) + " " + e.getCode() +".", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(MainActivity.this,
                            getString(R.string.main_successfully_connected), Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onMessageReceived(final Message msg){
        // Called by ClientAsyncTask.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout msgLayout = null;
                switch(msg.getType()){
                    case Message.MESSAGE_FOR_ALL:
                        msgLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.public_text_message_received, mDisplay, false);
                        ((TextView) msgLayout.findViewById(R.id.public_text_sender)).setText(msg.getFromUser() +
                                "[" + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(msg.getDate()) + "] :");
                        ((TextView) msgLayout.findViewById(R.id.public_text_message)).setText(msg.getMessage());
                        break;

                    case Message.PRIVATE_MESSAGE:
                        msgLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.private_text_message_received, mDisplay, false);
                        ((TextView) msgLayout.findViewById(R.id.public_text_sender)).setText(msg.getFromUser() +
                                "[" + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(msg.getDate()) + "] :");
                        ((TextView) msgLayout.findViewById(R.id.public_text_message)).setText(msg.getMessage());
                        break;
                    default:
                        return;
                }
                mDisplay.addView(msgLayout);
            }
        });
    }

    @Override
    public void onMessageSent(final Message msg){
        // Called by ClientAsyncTask.
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                LinearLayout msgLayout = null;
                switch(msg.getType()){
                    case Message.MESSAGE_FOR_ALL:
                        msgLayout = (LinearLayout) getLayoutInflater().inflate(R.layout.public_text_message_sent, mDisplay, false);
                        ((TextView) msgLayout.findViewById(R.id.public_text_sender)).setText(msg.getFromUser() +
                                "[" + new SimpleDateFormat("dd/MM/yyyy HH:mm").format(msg.getDate()) + "] :");
                        ((TextView) msgLayout.findViewById(R.id.public_text_message)).setText(msg.getMessage());
                        break;
                    default:
                        return;
                }
                mDisplay.addView(msgLayout);
            }
        });
    }
}
