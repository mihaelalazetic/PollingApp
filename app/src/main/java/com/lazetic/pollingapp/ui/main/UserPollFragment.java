package com.lazetic.pollingapp.ui.main;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lazetic.pollingapp.R;
import com.lazetic.pollingapp.UserActivity;
import com.lazetic.pollingapp.objects.Poll;
import com.lazetic.pollingapp.objects.User;


public class UserPollFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private String name;
    RadioGroup radioGroup1,radioGroup2,radioGroup3;
    TextView q1, q2, q3;
    Toolbar username;
    Button finishPoll;

    public UserPollFragment() {
        // Required empty public constructor
    }

    public static UserPollFragment newInstance(String name) {
        UserPollFragment fragment = new UserPollFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, name);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_poll, container, false);
        TextView titleTV = view.findViewById(R.id.titleTV);
        titleTV.setText(name);

        setupQuestions(view);

        return view;
    }

    public void setupQuestions(View view) {

        q1 = view.findViewById(R.id.q1);
        q2 = view.findViewById(R.id.q2);
        q3 = view.findViewById(R.id.q3);

        Poll poll = ((UserActivity) requireActivity()).getPollDetails(name);
        String q11 = poll.q1;
        String q22 = poll.q2;
        String q33 = poll.q3;
        q1.setText(q11);
        q2.setText(q22);
        q3.setText(q33);

        String a1 = poll.a1;
        String a2 = poll.a2;
        String a3 = poll.a3;

        String[] ans1 = a1.split(";");
        String[] ans2 = a2.split(";");
        String[] ans3 = a3.split(";");

        radioGroup1 = view.findViewById(R.id.radio1);
        radioGroup2 = view.findViewById(R.id.radio2);
        radioGroup3 = view.findViewById(R.id.radio3);

        for_loop(ans1, radioGroup1);
        for_loop(ans2, radioGroup2);
        for_loop(ans3, radioGroup3);

        radioClick(radioGroup1);
        radioClick(radioGroup2);
        radioClick(radioGroup3);

        finishPoll = view.findViewById(R.id.finishPoll);
        finishPoll.setOnClickListener(view1 -> {

            FragmentTransaction fr = requireFragmentManager().beginTransaction();
            fr.replace(R.id.user_container, new UserTasksFragment());
            fr.commit();
        });
    }

    public void for_loop(String[] ans, RadioGroup radioGroup) {
        for (String s : ans) {
            RadioButton rdbtn = new RadioButton(getContext());
            rdbtn.setId(View.generateViewId());
            rdbtn.setText(s);
            rdbtn.setOnClickListener(View::computeScroll);
            rdbtn.setTextColor(Color.BLACK);
            rdbtn.setButtonTintList(ColorStateList.valueOf(Color.BLACK));
            rdbtn.setPadding(0,10,0,50);
            radioGroup.addView(rdbtn);
        }
    }

    public void radioClick(RadioGroup radioGroup1){
        radioGroup1.setOnCheckedChangeListener((group, checkedId) -> {
            RadioButton radioButton = group.findViewById(checkedId);

            // on below line we are setting text
            // for our status text view.
            Toast.makeText(getContext(),""+radioButton.getText(), Toast.LENGTH_SHORT).show();
            for(int i = 0; i < radioGroup1.getChildCount(); i++){
                ((RadioButton)radioGroup1.getChildAt(i)).setEnabled(false);
            }
            username = requireActivity().findViewById(R.id.toolbar);
            ((UserActivity) requireActivity()).updateUserLogA1(username.getTitle().toString(),name,radioButton.getText().toString());
        });
    }
}