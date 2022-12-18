package com.lazetic.pollingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import com.lazetic.pollingapp.objects.Task;
import com.lazetic.pollingapp.ui.main.CreatePollFragment;
import com.lazetic.pollingapp.ui.main.StartPollFragment;
import com.lazetic.pollingapp.ui.main.UserPollFragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AdminActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        db = openOrCreateDatabase("poll_system", MODE_PRIVATE, null);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.admin_container, new StartPollFragment());
        fragmentTransaction.commit();

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Добре дојдовте " + userName + "!");

        Button create = findViewById(R.id.create_poll);
        Button start = findViewById(R.id.start_poll);

        create.setOnClickListener(view -> {

            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container, CreatePollFragment.newInstance()).commitNow();
        });

        start.setOnClickListener(view -> {
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_container, StartPollFragment.newInstance()).commitNow();
        });
    }

    public List<Task> getActivePolls() {

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

    public List<String> getPolls() {
        List<String> activePolls = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT poll_name FROM polls", null);
        cursor.moveToFirst();
        int name_col = cursor.getColumnIndex("poll_name");
        do {
            activePolls.add(cursor.getString(name_col));
            System.out.println("poll names: " + cursor.getString(name_col));
        } while (cursor.moveToNext());
        cursor.close();
        return activePolls;
    }

    public void startPoll(String name, String start, String end, String time) {
        Cursor cursor = db.rawQuery("SELECT poll_name FROM poll_logs WHERE poll_name LIKE '%" + name + "%' AND active = 1;", null);
        if (cursor.moveToFirst()) {
            Toast.makeText(this, "Poll is already started!", Toast.LENGTH_LONG).show();
        } else {
            db.execSQL("INSERT INTO poll_logs(poll_name,active,start_time,end_time,time) VALUES( '" + name + "','1','" + start + "','" + end + "','" + time + "') ;");
            //TODO BROADCAST PORAKA DO SITE DEKA E POCNAT POLL

            Long timee = Integer.parseInt(time) * 60000L;
            new CountDownTimer(timee, 1000) {

                public void onTick(long millisUntilFinished) {
                    Toast.makeText(AdminActivity.this, "Sekundi: "+millisUntilFinished / 1000, Toast.LENGTH_SHORT).show();
//                    mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                }

                public void onFinish() {
                    db.execSQL("UPDATE poll_logs SET active = 0 WHERE poll_name = '" + name + "';");

                }
            }.start();

        }

    }

    public void createPoll(String name, String q1, String a1, String q2, String a2, String q3, String a3) {
        Cursor cursor = db.rawQuery("SELECT poll_name FROM polls WHERE poll_name LIKE '%" + name + "%';", null);
        if (cursor.getCount() > 0) {
//            Toast toast = Toast.makeText(this,"Poll with name " + name + "already exists!" , Toast.LENGTH_LONG);
//            toast.getView().setBackgroundColor(Color.parseColor("#F6AE2D"));
//            toast.show();

            Toast.makeText(this, "Poll with name " + name + "already exists!", Toast.LENGTH_LONG).show();
        } else {
            db.execSQL("INSERT INTO polls(poll_name,q1,a1,q2,a2,q3,a3) VALUES( '" + name + "','" + q1 + "','" + formatA(a1) + "','" + q2 + "','" + formatA(a2) + "','" + q3 + "','" + formatA(a3) + "') ;");
            Toast.makeText(this, "Poll " + name + " created!", Toast.LENGTH_LONG).show();
        }
    }

    public  String formatA(String a){
        return a.replaceAll("\n", ";");
    }
}