package com.lazetic.pollingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.lazetic.pollingapp.objects.Poll;
import com.lazetic.pollingapp.objects.Task;
import com.lazetic.pollingapp.ui.main.UserTasksFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        db = openOrCreateDatabase("poll_system", MODE_PRIVATE, null);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.user_container, new UserTasksFragment());
        fragmentTransaction.commit();

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Добре дојдовте " + userName + "!");


    }

    public List<Task> getPolls() {

        List<Task> polls = new ArrayList<Task>();
        assert db != null;
        Cursor cursor = db.rawQuery("SELECT * from poll_logs WHERE active = 1 ", null);
        cursor.moveToFirst();
        int name_col = cursor.getColumnIndex("poll_name");
        int start_col = cursor.getColumnIndex("start_time");
        int end_col = cursor.getColumnIndex("end_time");
        if (cursor.getCount() <= 0) {
            polls.add(new Task("No active polls to show", "", ""));
        } else {
            do {
                polls.add(new Task(cursor.getString(name_col), cursor.getString(start_col), cursor.getString(end_col)));
                System.out.println("poll names: " + cursor.getString(name_col));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return polls;
    }

    public Poll getPollDetails(String name) {
        Cursor cursor = db.rawQuery("SELECT * from polls WHERE poll_name = '" + name + "'; ", null);
        cursor.moveToFirst();
        int q1 = cursor.getColumnIndex("q1"); int q2 = cursor.getColumnIndex("q2"); int q3 = cursor.getColumnIndex("q3");
        int a1 = cursor.getColumnIndex("a1"); int a2 = cursor.getColumnIndex("a2"); int a3 = cursor.getColumnIndex("a3");
        Poll poll;
        do {
            poll = new Poll(name,cursor.getString(q1), cursor.getString(q2), cursor.getString(q3),
                    cursor.getString(a1), cursor.getString(a2), cursor.getString(a3));
        } while (cursor.moveToNext());
        cursor.close();
        return poll;
    }
}