package com.lazetic.pollingapp.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lazetic.pollingapp.R;
import com.lazetic.pollingapp.UserActivity;
import com.lazetic.pollingapp.objects.MyAdapter;
import com.lazetic.pollingapp.objects.RecyclerviewOnClickListener;
import com.lazetic.pollingapp.objects.Task;

import java.util.List;
import java.util.Objects;

public class UserTasksFragment extends Fragment implements RecyclerviewOnClickListener {


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

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new MyAdapter(polls, getContext(),this));
        return view;
    }

    @Override
    public void recyclerviewClick(Task task) {
        Fragment fragment = UserPollFragment.newInstance(task.getName());
        FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.user_container, fragment,"user_poll_fragment");
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}