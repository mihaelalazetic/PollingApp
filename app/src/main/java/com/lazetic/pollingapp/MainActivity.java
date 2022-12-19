package com.lazetic.pollingapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.lazetic.pollingapp.objects.User;
import com.lazetic.pollingapp.ui.main.MainFragment;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow();
        }
        if (!checkDataBase()) {
            createDb();
        } else {
            db = openOrCreateDatabase("poll_system", MODE_PRIVATE, null);

        }
    }


    public void createDb() {

        db = openOrCreateDatabase("poll_system", MODE_PRIVATE, null);
        //TODO add location to db
        db.execSQL("CREATE TABLE IF NOT EXISTS user_details(id INTEGER primary key autoincrement ,name VARCHAR, email VARCHAR,password VARCHAR(1000),location_name VARCHAR);");
        db.execSQL("INSERT INTO user_details(name,email,password) VALUES('Administrator','admin','admin')");
        db.execSQL("INSERT INTO user_details(name,email,password,location_name) VALUES('Mihaela','miha@yahoo.com','123','Poll Place')");

        db.execSQL("CREATE TABLE IF NOT EXISTS locations(id INTEGER primary key autoincrement , lat VARCHAR,long VARCHAR,location_name VARCHAR);");

        db.execSQL("CREATE TABLE IF NOT EXISTS polls(id INTEGER primary key autoincrement , poll_name VARCHAR , q1 VARCHAR,a1 VARCHAR, q2 VARCHAR,a2 VARCHAR, q3 VARCHAR,a3 VARCHAR);");
        db.execSQL("INSERT INTO polls(poll_name,q1,a1,q2,a2,q3,a3) VALUES('Default Poll'," +
                "'What’s the best pizza topping?','Pepperoni;Pineapple;Just cheese;Mushrooms'," +
                "'What’s the cutest animal?','Kittens;Puppies;Rabbits;'," +
                "'What was the best series?','Breaking Bad;The Sopranos;Mr. Robot')");
        db.execSQL("INSERT INTO polls(poll_name,q1,a1,q2,a2,q3,a3) VALUES('Poll1'," +
                "'q1','Pepperoni;Pineapple;Just cheese;Mushrooms'," +
                "'q2','Kittens;Puppies;Rabbits;'," +
                "'q3','Breaking Bad;The Sopranos;Mr. Robot')");
        db.execSQL("CREATE TABLE IF NOT EXISTS poll_logs(poll_name VARCHAR, active VARCHAR CHECK (active IN ('0', '1')), start_time VARCHAR, end_time VARCHAR,time VARCHAR);");
//        db.execSQL("INSERT INTO poll_logs(poll_name,active,start_time,end_time,time) VALUES('Default Poll','1','19:00','20:00','60');");
//        db.execSQL("INSERT INTO poll_logs(poll_name,active,start_time,end_time,time) VALUES('Nekoj Dr Poll','1','10:00','11:00','60');");
//        db.execSQL("INSERT INTO poll_logs(poll_name,active,start_time,end_time,time) VALUES('Ime','1','17:00','19:00','120');");
        db.execSQL("CREATE TABLE IF NOT EXISTS user_logs(id INTEGER primary key autoincrement, user_name VARCHAR,poll_name VARCHAR, a1 VARCHAR,a2 VARCHAR, a3 VARCHAR, start_time VARCHAR, end_time VARCHAR);");
    }

    public String loginUser(String email, String password) {
        String name = null;
        Cursor cursor = db.rawQuery("SELECT * from user_details WHERE email LIKE '%" + email + "%' AND password LIKE '" + password + "'", null);
        if (cursor.moveToFirst()) {
            Toast.makeText(this, "Успешно се најавивте!", Toast.LENGTH_LONG).show();
            if (Objects.equals(email, "admin")) {
                return "admin";
            }
            int n = cursor.getColumnIndex("name");
            name = cursor.getString(n);
            cursor.close();

        } else {
            Toast.makeText(this, "Погрешен email или лозинка! Пробајте повторно!", Toast.LENGTH_LONG).show();
            return "";
        }

        return name;

    }

    public void signUpUser(User user, String confPass) {
        Cursor cursor = db.rawQuery("SELECT * from user_details WHERE email LIKE '%" + user.getEmail() + "%'", null);
        if (cursor.moveToFirst()) {
            Toast.makeText(this, "Веќе постои корисник со имејл " + user.getEmail(), Toast.LENGTH_LONG).show();
        } else if (!Objects.equals(user.getPassword(), confPass)) {
            Toast.makeText(this, "Лозинките не се совпаѓаат!", Toast.LENGTH_LONG).show();
        } else {
            db.execSQL("INSERT INTO user_details(name,email,password) VALUES(" + user + ");");
            Toast.makeText(this, "Успешно се регистриравте! \n Продолжете кон Најава!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("email", user.getEmail());
            startActivity(intent);
        }
        cursor.close();
    }

    public ArrayList<String> getPolls() {
        ArrayList<String> pollNames = new ArrayList<>();
        int i = 0;
        Cursor cursor = db.rawQuery("SELECT poll_name from polls", null);
        cursor.moveToFirst();
        int id = cursor.getColumnIndex("poll_name");
        do {
            pollNames.add(cursor.getString(id));
            System.out.println("pollNames: " + pollNames.get(i));
            i++;
        } while (cursor.moveToNext());
        cursor.close();
        return pollNames;
    }

    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;
        try {
            checkDB = SQLiteDatabase.openDatabase("/data/data/com.lazetic.pollingapp/databases/poll_system", null,
                    SQLiteDatabase.OPEN_READONLY);
            checkDB.close();
            System.out.println("postoi");
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            System.out.println("ne postoi");
        }
        return checkDB != null;
    }
}