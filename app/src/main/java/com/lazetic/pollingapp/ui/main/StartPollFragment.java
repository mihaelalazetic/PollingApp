package com.lazetic.pollingapp.ui.main;

import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lazetic.pollingapp.AdminActivity;
import com.lazetic.pollingapp.R;
import com.lazetic.pollingapp.UserActivity;
import com.lazetic.pollingapp.objects.MyAdapter;
import com.lazetic.pollingapp.objects.Task;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;


public class StartPollFragment extends Fragment {

    public StartPollFragment() {
        // Required empty public constructor
    }

    public static StartPollFragment newInstance() {
        return new StartPollFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_start_poll, container, false);

        List<Task> polls = ((AdminActivity) requireActivity()).getActivePolls();

        RecyclerView recyclerView = view.findViewById(R.id.activePolls);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new MyAdapter(polls, getContext(),task -> recyclerView.getContext())); //TODO DODADI DA MOZE DA SE OTVORI I DA SE VIDAT ODGOVORITE

        Spinner spinner = (Spinner) view.findViewById(R.id.listOfPolls);
        List<String> allPolls = ((AdminActivity) requireActivity()).getPolls();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, allPolls);

        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        Button startPoll = (Button) view.findViewById(R.id.startPollBtn);
        EditText timeView = (EditText) view.findViewById(R.id.time);

        startPoll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String spinnerText = spinner.getSelectedItem().toString();
                String stime = timeView.getText().toString();
                int time = Integer.parseInt(stime);

                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String myTime = sdf.format(new Date());

                SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
                Date d = null;
                try {
                    d = df.parse(myTime);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar cal = Calendar.getInstance();
                cal.setTime(Objects.requireNonNull(d));
                cal.add(Calendar.MINUTE, time);
                String newTime = df.format(cal.getTime());

                ((AdminActivity) requireActivity()).startPoll(spinnerText,myTime,newTime,stime);
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.admin_container,new StartPollFragment());
                fr.commit();

            }
        });
        return view;
    }

}