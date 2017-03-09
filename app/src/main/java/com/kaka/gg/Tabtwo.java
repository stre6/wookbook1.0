package com.kaka.gg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by stre6 on 2016-10-20.
 */

public class Tabtwo extends Fragment {
    public Tabtwo() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab, null);
        return inflater.inflate(R.layout.tab2, container, false);
    }
}
