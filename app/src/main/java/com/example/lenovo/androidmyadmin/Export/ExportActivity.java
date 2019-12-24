package com.example.lenovo.androidmyadmin.Export;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lenovo.androidmyadmin.R;
import com.example.lenovo.androidmyadmin.sql.UserSQLiteHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExportActivity extends AppCompatActivity {
    public static UserSQLiteHelper mSQLiteHelper;
    public SqliteExporter sqliteExporter;
    Spinner spinner;
    String[] dbList;
    List<String> matieres = new ArrayList<String>();
    Button b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbList = this.databaseList();

        for(int i = 0; i < dbList.length; i++)
        {
            if(dbList[i].contains("journal") == false && dbList[i].contains("google") == false && dbList[i].contains("RECORD") == false && dbList[i].contains("database") == false)
            {
                matieres.add(dbList[i]);
            }
        }
        spinner = (Spinner)findViewById(R.id.spinnerDb);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, matieres);
        spinner.setAdapter(spinnerArrayAdapter);

    }


    public void cliiiquer(View view) {
        String db = spinner.getSelectedItem().toString();
        SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase(db, MODE_PRIVATE, null);
        sqliteExporter = new SqliteExporter();
        try {
            sqliteExporter.export(mydatabase);
            Toast.makeText(ExportActivity.this, "La base de données "+mydatabase.toString()+"est exporté avec succées", Toast.LENGTH_LONG).show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
