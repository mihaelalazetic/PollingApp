package com.lazetic.pollingapp.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.lazetic.pollingapp.AdminActivity;
import com.lazetic.pollingapp.MainActivity;
import com.lazetic.pollingapp.R;
import com.lazetic.pollingapp.UserActivity;

import java.util.ArrayList;
import java.util.Objects;

public class MainFragment extends Fragment {

    private MainViewModel mViewModel;
    EditText emailText,passText;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        // TODO: Use the ViewModel
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Button signUpButtonMain = (Button) view.findViewById(R.id.signUp);

        signUpButtonMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.container,new SignUpFragment());
                fr.commit();
            }
        });
        emailText = (EditText) view.findViewById(R.id.signUpEmail);
        passText = (EditText) view.findViewById(R.id.signUpPass);
        Intent intent1 = getActivity().getIntent();
        emailText.setText(intent1.getStringExtra("email"));

        Button logInButtonMain = (Button) view.findViewById(R.id.loginButton);

        logInButtonMain.setOnClickListener(view1 -> {
            String user = ((MainActivity) requireActivity()).loginUser(emailText.getText().toString(),passText.getText().toString());
            if (Objects.equals(user, "admin")){
                ArrayList<String> pollNames = ((MainActivity) requireActivity()).getPolls();
                Intent intent = new Intent(getActivity(), AdminActivity.class);
                intent.putStringArrayListExtra("pollNames",pollNames);
                intent.putExtra("email",emailText.getText().toString());
                intent.putExtra("userName",user);
                startActivity(intent);
            }else if(Objects.equals(user, "user")) {
                Intent intent = new Intent(getActivity(), UserActivity.class);
                intent.putExtra("userName",user);
                startActivity(intent);
            }
        });
        return view;
    }

}