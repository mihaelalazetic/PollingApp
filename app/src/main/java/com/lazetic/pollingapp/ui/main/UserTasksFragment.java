package com.lazetic.pollingapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lazetic.pollingapp.R;
import com.lazetic.pollingapp.UserActivity;
import com.lazetic.pollingapp.objects.MyAdapter;
import com.lazetic.pollingapp.objects.Task;

import java.util.List;

public class UserTasksFragment extends Fragment {


    public UserTasksFragment() {
        // Required empty public constructor
    }

    public static UserTasksFragment newInstance() {return new UserTasksFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_tasks, container, false);
        List<Task> polls = ((UserActivity) requireActivity()).getPolls();

        RecyclerView recyclerView = view.findViewById(R.id.tasksRecycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(new MyAdapter(polls, getContext()));
        return view;
    }
}