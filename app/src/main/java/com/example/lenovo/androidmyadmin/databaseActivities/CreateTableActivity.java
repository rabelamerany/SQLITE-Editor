package com.example.lenovo.androidmyadmin.databaseActivities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.androidmyadmin.R;

import java.util.ArrayList;

public class CreateTableActivity extends AppCompatActivity {
    String dbName = "";
    String tableName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        Bundle extras = getIntent().getExtras();
        tableName = extras.getString("table");
        dbName = extras.getString("db");
        int numberCols = Integer.parseInt(extras.getString("numberCols"));

        TextView textView = (TextView)findViewById(R.id.textView2);
        textView.setText(tableName);
        TableLayout table;
        table = findViewById(R.id.table_layout);
        for (int i = 0; i < numberCols; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            row.setMinimumWidth(209);
            // inner for loop
            for (int j = 0; j < 5; j++) {
                if(j == 0)
                {
                    EditText tv = new EditText(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(18);
                    tv.setPadding(0, 5, 0, 5);


                    row.addView(tv);
                }
                else if(j == 1)
                {
                    ArrayList<String> spinnerArray = new ArrayList<String>();
                    spinnerArray.add("VARCHAR");
                    spinnerArray.add("INTEGER");
                    spinnerArray.add("FLOAT");
                    spinnerArray.add("BOOLEAN");
                    spinnerArray.add("DATETIME");

                    Spinner spinner = new Spinner(this);
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
                    spinner.setAdapter(spinnerArrayAdapter);
                    row.addView(spinner);
                }
                else
                {
                    CheckBox cb = new CheckBox(getApplicationContext());

                    row.addView(cb);
                }
            }
            table.addView(row);
        }
    }

    public void onPress(View view) {
        TableLayout table;
        table = findViewById(R.id.table_layout);

        ArrayList<String> colNames = new ArrayList<String>();
        ArrayList<String> colTypes = new ArrayList<String>();
        ArrayList<String> colPrimary = new ArrayList<String>();
        ArrayList<String> colAuto = new ArrayList<String>();
        ArrayList<String> colNull = new ArrayList<String>();
        String theQuery = "";
        int nameCount = 0;
        int countPrimary = 0;
        for(int i = 1, j = table.getChildCount(); i < j; i++) {

            View view1 = table.getChildAt(i);
            if (view1 instanceof TableRow) {
                // then, you can remove the the row you want...
                // for instance...
                TableRow row = (TableRow) view1;

                TextView view2 = (TextView)row.getChildAt(0);
                Spinner spin = (Spinner)row.getChildAt(1);
                CheckBox primary = (CheckBox)row.getChildAt(2);
                CheckBox auto = (CheckBox)row.getChildAt(3);
                CheckBox notNull = (CheckBox)row.getChildAt(4);

                String priTmp = "";
                String autoTmp = "";
                String nullTmp = "";
                if(primary.isChecked())
                {
                    countPrimary++;
                    priTmp = "PRIMARY KEY";
                }
                if(auto.isChecked())
                {
                    autoTmp = "AUTOINCREMENT";
                }
                if(notNull.isChecked())
                {
                    nullTmp = "NOT NULL";
                }
                if(view2.getText().toString().equals(""))
                {
                    nameCount++;
                }
                colNames.add(view2.getText().toString());
                colTypes.add(spin.getSelectedItem().toString());
                colPrimary.add(priTmp);
                colNull.add(nullTmp);
                colAuto.add(autoTmp);
            }
        }
        System.err.println("count is : " + nameCount);
        if(nameCount == 0)
        {
            if(countPrimary <= 1)
            {
                for(int i = 0 ; i < colNames.size() ; i ++)
                {
                    theQuery = theQuery + colNames.get(i) + " " + colTypes.get(i) + " " + colPrimary.get(i) + " " + colAuto.get(i) + " " + colNull.get(i) + ",";

                }
                theQuery = theQuery.substring(0, theQuery.length() - 1);

                Bundle extras = getIntent().getExtras();
                String tablee = extras.getString("table");
                SQLiteDatabase mydatabase;
                mydatabase = getApplicationContext().openOrCreateDatabase(dbName,MODE_PRIVATE,null);
                String query = "CREATE TABLE " + tablee + "(" + theQuery +")";
                System.err.println("the major query is : " + query);
                try
                {
                    mydatabase.execSQL(query);

                    Intent intent = new Intent(this, DetailActivity.class);
                    intent.putExtra("data", dbName);
                    startActivity(intent);
                }
                catch(SQLiteException ex)
                {
                    if(ex.getMessage().contains("AUTOINCREMENT"))
                    {
                        Toast.makeText(this, "VARCHAR cannot be AUTOINCREMENT", Toast.LENGTH_LONG).show();
                    }
                }
            }
            else
            {
                Toast.makeText(this, "Please select one PRIMARY KEY", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            Toast.makeText(this, "Please enter all column names", Toast.LENGTH_LONG).show();
        }

    }
}
