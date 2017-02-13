package com.tempcall.sajidhz.tempcall;


import android.app.Application;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by sajidh on 2/5/2017.
 */

public class CallStateListener extends PhoneStateListener {
    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                // called when someone is ringing to this phone

                Toast.makeText(TempCallApplication.getContext(),
                        "Incoming: "+incomingNumber,
                        Toast.LENGTH_LONG).show();
                break;
        }
    }
}
