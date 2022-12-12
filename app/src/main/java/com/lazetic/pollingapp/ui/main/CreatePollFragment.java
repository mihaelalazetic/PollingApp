package com.lazetic.pollingapp.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.lazetic.pollingapp.AdminActivity;
import com.lazetic.pollingapp.R;

public class CreatePollFragment extends Fragment {
    EditText name,q1,a1,q2,a2,q3,a3;
    Button create;

    public CreatePollFragment() {
        // Required empty public constructor
    }

    public static CreatePollFragment newInstance() {return new CreatePollFragment();}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_poll, container, false);
        name = view.findViewById(R.id.newPollName);
        q1 = view.findViewById(R.id.q1);
        a1 = view.findViewById(R.id.a1);
        q2 = view.findViewById(R.id.q2);
        a2 = view.findViewById(R.id.a2);
        q3 = view.findViewById(R.id.q3);
        a3 = view.findViewById(R.id.a3);

        create = view.findViewById(R.id.createNewPollBtn);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AdminActivity) requireActivity()).createPoll(name.getText().toString(),
                                                                q1.getText().toString(),
                                                                a1.getText().toString(),
                                                                q2.getText().toString(),
                                                                a2.getText().toString(),
                                                                q3.getText().toString(),
                                                                a2.getText().toString());
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.admin_container,new StartPollFragment());
                fr.commit();
            }
        });

        return  view;
    }
}