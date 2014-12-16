/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.alandk.smartflash.activity;

import android.app.Activity;
import android.os.Bundle;

/**
 *
 * @author phucdk
 */
public class ColorPickerActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        // ToDo add your GUI initialization code here
        setContentView(R.layout.act_picker);
    }

}
