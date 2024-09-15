package com.example.universityattendanceapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Database database;
    private ArrayList<Class> classes;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ListView listView = findViewById(R.id.listview);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button new_class = findViewById(R.id.new_class);

        new_class.setOnClickListener(v-> {
            Intent intent = new Intent(MainActivity.this, ClassEditor.class);
            startActivity(intent);
        });
        database = new Database();
        classes = database.getAllClasses();
        listAdapter = new ListAdapter();
        listView.setAdapter(listAdapter);
    }

    @Override
     protected void onResume() {
        super.onResume();
        classes = database.getAllClasses();
        listAdapter.notifyDataSetChanged();
    }

    public class ListAdapter extends BaseAdapter {

        public ListAdapter() {}

        @Override
        public int getCount() {
            return classes.size();
        }

        @Override
        public Class getItem(int i) {
            return classes.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater inflater = getLayoutInflater();
            @SuppressLint({"InflateParams", "ViewHolder"})
            View v = inflater.inflate(R.layout.list_item, null);
            @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView text = v.findViewById(R.id.text);
            text.setText(classes.get(i).getClassName());

            text.setOnClickListener(v1-> {
                Intent intent = new Intent(MainActivity.this, ShowSessions.class);
                intent.putExtra("Class ID", classes.get(i).getID());
                intent.putExtra("Class Name", classes.get(i).getClassName());
                startActivity(intent);

            });
            text.setOnClickListener(v1-> {
                Intent intent = new Intent(MainActivity.this, ClassEditor.class);
                intent.putExtra("Class ID", classes.get(i).getID());
                intent.putExtra("Class Name", classes.get(i).getClassName());
                startActivity(intent);
            });
            return v;
        }
    }
}