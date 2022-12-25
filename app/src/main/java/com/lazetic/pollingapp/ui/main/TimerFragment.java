package com.lazetic.pollingapp.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lazetic.pollingapp.R;

import java.util.Timer;

public class TimerFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private Long time;

    public TimerFragment() {
        // Required empty public constructor
    }

    public static TimerFragment newInstance(Long time) {
        TimerFragment fragment = new TimerFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, time);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            time = getArguments().getLong(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }
}