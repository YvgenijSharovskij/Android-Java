package com.dilutioncalculator1.admin.dilutioncalculator1;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class MainActivity extends SingleFragmentActivity {

    public static final String EXTRA_QNA_ID ="MainActivity";

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }
}

