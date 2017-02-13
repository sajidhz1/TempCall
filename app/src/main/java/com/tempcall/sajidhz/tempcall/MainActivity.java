package com.tempcall.sajidhz.tempcall;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.tempcall.sajidhz.tempcall.dialogs.SaveUserContactDialog;

public class MainActivity extends AppCompatActivity {

    AppCompatButton _callButton;
    AppCompatEditText _telephoneNumber;

    IntentFilter _intentFilter;
    CallStateListener _callstateListener;

    OutgoingCallReceiver _outgoingCallReceiver;

    TelephonyManager _tm;
    private static final int CALL_ACTIVITY_RESULT_CODE = 11000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _callstateListener = new CallStateListener();
        _outgoingCallReceiver  =  new OutgoingCallReceiver();

        _tm = (TelephonyManager) getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        _tm.listen(_callstateListener, PhoneStateListener.LISTEN_CALL_STATE);

        _telephoneNumber =  (AppCompatEditText)findViewById(R.id.telephoneNumber);

        _intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        getApplicationContext().registerReceiver(_outgoingCallReceiver, _intentFilter);

        _callButton  =  (AppCompatButton)findViewById(R.id.callButton);
        _callButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View arg0) {
              //  makeCall();

                showContactSaveDialog();
            }
        });
    }


    public void showContactSaveDialog(){
        FragmentManager manager = getSupportFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_edit_name");
        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }

        SaveUserContactDialog saveUserContactDialog = new SaveUserContactDialog();
        saveUserContactDialog.show(manager, "fragment_edit_name");
    }

    @Override
    protected void onResume() {
        super.onResume();
        //handle call state listener here
        //tm.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
        getApplicationContext().registerReceiver(_outgoingCallReceiver, _intentFilter);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        getApplicationContext().unregisterReceiver(_outgoingCallReceiver);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getApplicationContext().unregisterReceiver(_outgoingCallReceiver);
        //handle call state listener here
    }



    private void makeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+_telephoneNumber.getText().toString()));

        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    public class OutgoingCallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(OutgoingCallReceiver.class.getSimpleName(), intent.toString());
            Toast.makeText(context, "Outgoing call catched!", Toast.LENGTH_LONG).show();
            //TODO: Handle outgoing call event here
        }
    }
}
