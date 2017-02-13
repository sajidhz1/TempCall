package com.tempcall.sajidhz.tempcall;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by sajidh on 1/31/2017.
 */

public class DialpadImageButton extends AppCompatButton {

    public DialpadImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public DialpadImageButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final boolean ret = super.onTouchEvent(event);
        if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            jumpDrawablesToCurrentState();
        }
        return ret;
    }
}
