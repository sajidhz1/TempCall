package com.tempcall.sajidhz.tempcall.dialogs;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.tempcall.sajidhz.tempcall.R;

/**
 * Created by sajidh on 2/7/2017.
 */

public class SaveUserContactDialog extends AppCompatDialogFragment implements AppCompatTextView.OnEditorActionListener{

    AppCompatEditText _mName;
    AppCompatEditText _mNumber;

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_to_contacts, container);
        _mName = (AppCompatEditText) view.findViewById(R.id.editText);

        // set this instance as callback for editor action
        _mName.setOnEditorActionListener(this);
        _mName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        getDialog().setTitle("Save Temporary Contact");

        return view;
    }
}
