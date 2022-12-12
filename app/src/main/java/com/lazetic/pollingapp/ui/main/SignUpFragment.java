package com.lazetic.pollingapp.ui.main;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.lazetic.pollingapp.MainActivity;
import com.lazetic.pollingapp.R;
import com.lazetic.pollingapp.R.id;
import com.lazetic.pollingapp.objects.User;

public class SignUpFragment extends Fragment {
    Button  logInButtonSignUpFrag,signUp;
    EditText name, email, pass, confPass;

    public SignUpFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static SignUpFragment newInstance(String param1, String param2) {
        SignUpFragment fragment = new SignUpFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        logInButtonSignUpFrag = view.findViewById(id.loginButton_signup);

        name = view.findViewById(id.signUpName);
        email = view.findViewById(id.signUpEmail);
        pass = view.findViewById(id.signUpPass);
        confPass = view.findViewById(id.confSignUpPass);

        signUp = view.findViewById(id.signUpButton);

        logInButtonSignUpFrag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = requireFragmentManager().beginTransaction();
                fr.replace(id.container, new MainFragment());
                fr.commit();
            }
        });

        signUp.setOnClickListener(view12 -> {
            if (emailValidator(email)){
                ((MainActivity) requireActivity()).signUpUser(new User(
                                name.getText().toString(),
                                email.getText().toString(),
                                pass.getText().toString()
                        ), confPass.getText().toString()
                );
            }
        });

        return view;
    }

    public boolean emailValidator(EditText etMail) {

// extract the entered data from the EditText
        String emailToText = etMail.getText().toString();

        if (!emailToText.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(emailToText).matches()) {
            Toast.makeText(getContext(), "Email Verified !", Toast.LENGTH_SHORT).show();
            return true;
        } else {
            Toast.makeText(getContext(), "Enter valid Email address !", Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}