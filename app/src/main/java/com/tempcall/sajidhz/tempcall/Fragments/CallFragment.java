package com.tempcall.sajidhz.tempcall.Fragments;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.tempcall.sajidhz.tempcall.CallStateListener;
import com.tempcall.sajidhz.tempcall.MainActivity;
import com.tempcall.sajidhz.tempcall.R;
import com.tempcall.sajidhz.tempcall.dialogs.SaveUserContactDialog;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

/**
 * Created by sajidh on 2/14/2017.
 */

public class CallFragment extends Fragment {

    AppCompatButton _callButton;
    AppCompatEditText _telephoneNumber;
    FloatSeekBar ft ;

    IntentFilter _intentFilter;
    CallStateListener _callstateListener;

    OutgoingCallReceiver _outgoingCallReceiver;

    TelephonyManager _tm;
    private static final int CALL_ACTIVITY_RESULT_CODE = 11000;


    public  CallFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _callstateListener = new CallStateListener();
        _outgoingCallReceiver  =  new OutgoingCallReceiver();

        _tm = (TelephonyManager) getActivity().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        _tm.listen(_callstateListener, PhoneStateListener.LISTEN_CALL_STATE);

        _telephoneNumber =  (AppCompatEditText)getActivity().findViewById(R.id.telephoneNumber);

        _intentFilter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
        getActivity().getApplicationContext().registerReceiver(_outgoingCallReceiver, _intentFilter);

        _callButton  =  (AppCompatButton)getActivity().findViewById(R.id.callButton);
//        _callButton.setOnClickListener(new View.OnClickListener() {
//
//            public void onClick(View arg0) {
//                //  makeCall();
//
//                showContactSaveDialog();
//            }
//        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView  = inflater.inflate(R.layout.call_fragment, container, false);

        LinearLayout root = (LinearLayout) rootView.findViewById(R.id.root);
        ft = new FloatSeekBar(getActivity().getApplicationContext());
        ft.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
                df.setMaximumFractionDigits(340);

                Toast.makeText(getActivity().getApplicationContext() , ft.getValue()+"" , Toast.LENGTH_SHORT).show();
            }
        });

        root.addView(ft);

        return rootView;
    }

    public void showContactSaveDialog(){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_edit_name");
        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }

        SaveUserContactDialog saveUserContactDialog = new SaveUserContactDialog();
        saveUserContactDialog.show(manager, "fragment_edit_name");
    }

    @Override
    public void onResume() {
        super.onResume();
        //handle call state listener here
        //tm.listen(callStateListener, PhoneStateListener.LISTEN_NONE);
        getActivity().getApplicationContext().registerReceiver(_outgoingCallReceiver, _intentFilter);
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        getApplicationContext().unregisterReceiver(_outgoingCallReceiver);
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getApplicationContext().unregisterReceiver(_outgoingCallReceiver);
        //handle call state listener here
    }



    private void makeCall() {
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:"+_telephoneNumber.getText().toString()));

        if (ActivityCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        startActivity(callIntent);
    }

    //move get application context to a method
    public class OutgoingCallReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(OutgoingCallReceiver.class.getSimpleName(), intent.toString());
            Toast.makeText(context, "Outgoing call catched!", Toast.LENGTH_LONG).show();
            //TODO: Handle outgoing call event here
        }
    }
}
