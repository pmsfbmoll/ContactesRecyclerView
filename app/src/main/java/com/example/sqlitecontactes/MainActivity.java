package com.example.sqlitecontactes;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbhelp;
    protected int count;
    private MyDB md;
    private SQLiteDatabase db;
    private RecyclerView mRecyclerView;
    private WordListAdapter mAdapter;
    private boolean edited;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    createContact();
                } catch (Exception e) {
                    Log.d("patata", "La entrada ja existeix");
                }
            }
        });

        this.dbhelp = new MyDatabaseHelper(this);
        this.db = dbhelp.getWritableDatabase();
        this.md = new MyDB(this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        try {
            md.createRecords("1", "Agustí", "601593683");
            md.createRecords("2", "Andreu", "601485472");
            md.createRecords("3", "Bernat", "610492048");
            md.createRecords("4", "Carles", "666666666");
        } catch (Exception e) {
            Log.d("patata", "La entrada ja existeix");
        }
        count=md.getCount();

        mAdapter = new WordListAdapter(this, md);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    public void createContact(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add contact");
        LayoutInflater factory = LayoutInflater.from(this);
        final View textEntryView = factory.inflate(R.layout.popup, null);
        final EditText input1 = (EditText) textEntryView.findViewById(R.id.editText);
        final EditText input2 = (EditText) textEntryView.findViewById(R.id.editText2);
        builder.setView(textEntryView);
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String contact = input1.getText().toString();
                String num = input2.getText().toString();
                if(!contact.equals("") && !num.equals("")) {
                    md.createRecords(String.valueOf(++count), contact, num);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
