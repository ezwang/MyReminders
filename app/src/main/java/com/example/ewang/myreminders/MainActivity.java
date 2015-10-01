package com.example.ewang.myreminders;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ArrayList<String> arr;
    ArrayAdapter<String> adapter;
    SharedPreferences prefs;

    @Override
    public void onClick(View v) {
        try {
            EditText txtAdd = (EditText)this.findViewById(R.id.txtAdd);
            String reminder = txtAdd.getText().toString();
            txtAdd.getText().clear();
            arr.add(reminder);
            adapter.notifyDataSetChanged();
            prefs.edit().putString(Objects.toString(System.currentTimeMillis()), reminder).commit();
        }
        catch (Exception ex) {
            Toast.makeText(this, "Error making new reminder!", Toast.LENGTH_SHORT).show();
            Log.e("MyReminders", "Error occured when adding reminder", ex);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("Reminders", Context.MODE_PRIVATE);

        Map<String, ?> items = prefs.getAll();

        arr = new ArrayList<String>();

        for (Map.Entry<String, ?> a : items.entrySet()) {
            arr.add(a.getValue().toString());
        }

        ListView listView = (ListView)this.findViewById(R.id.listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);

        Button btnAdd = (Button)this.findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_delete) {
            prefs.edit().clear().commit();
            arr.clear();
            adapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
