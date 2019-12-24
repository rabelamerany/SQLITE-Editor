package com.example.lenovo.androidmyadmin.databaseActivities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.androidmyadmin.R;
import com.example.lenovo.androidmyadmin.databaseActivities.MainActivity;

import java.util.ArrayList;

public class SqlActivity extends AppCompatActivity {
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String[] dbList = this.databaseList();
        ArrayList<String> dbs = new ArrayList<String>();

        System.err.println("length is : " + dbList.length);
        for(int i = 0; i < dbList.length; i++)
        {
            if(dbList[i].contains("journal") == false)
            {
                dbs.add(dbList[i]);
            }
        }
        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, dbs);
        spinner.setAdapter(spinnerArrayAdapter);


        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("UPDATE ROW");
        spinnerArray.add("DELETE ROW");
        spinnerArray.add("INSERT ROW");
        spinnerArray.add("SELECT");

        Spinner spinner2 = (Spinner)findViewById(R.id.spinner3);
        ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, spinnerArray);
        spinner2.setAdapter(spinnerArrayAdapter2);
    }

    public void onBipBtn(View view) {

        Spinner spinner = (Spinner)findViewById(R.id.spinner);
        Spinner spinnerType = (Spinner)findViewById(R.id.spinner3);
        EditText edit = (EditText)findViewById(R.id.edit);
        String dbName = spinner.getSelectedItem().toString();
        String query = edit.getText().toString();
        String type = spinnerType.getSelectedItem().toString();
        if(query.isEmpty())
        {
            Toast.makeText(this, "Please enter a query", Toast.LENGTH_LONG).show();
        }
        else
        {
            try
            {
                if(type.equals("UPDATE ROW"))
                {
                    SQLiteDatabase mydatabase;
                    mydatabase = openOrCreateDatabase(dbName, MODE_PRIVATE, null);

                    mydatabase.execSQL(query);
                    //mydatabase.execSQL("update test1 set name = 'ijlal' where id = 1");

                    Intent intent = new Intent(this, MainActivity.class);

                    startActivity(intent);
                }
                if(type.equals("UPDATE ROW") || type.equals("DELETE ROW") || type.equals("INSERT ROW"))
                {
                    SQLiteDatabase mydatabase;
                    mydatabase = openOrCreateDatabase(dbName, MODE_PRIVATE, null);

                    mydatabase.execSQL(query);
                    //mydatabase.execSQL("update test1 set name = 'ijlal' where id = 1");

                    Intent intent = new Intent(this, MainActivity.class);

                    startActivity(intent);
                }
                else if (type.equals("SELECT"))
                {
                    String result = query.substring(query.indexOf("from") + 4, query.indexOf(";"));
                    Toast.makeText(this, result, Toast.LENGTH_LONG).show();
                    if(query.contains("*"))
                    {
                        SQLiteDatabase mydatabase;
                        final ArrayList<String> colNames = new ArrayList<String>();

                        mydatabase = getApplicationContext().openOrCreateDatabase(dbName, MODE_PRIVATE, null);
                        String querytmp = "PRAGMA table_info(" + result + ");";
                        Cursor c = mydatabase.rawQuery(querytmp, null);

                        if (c.moveToFirst()) {
                            while ( !c.isAfterLast() ) {
                                //System.err.println(c.getColumnIndex("name"));
                                colNames.add( c.getString( c.getColumnIndex("name")) );
                                c.moveToNext();
                            }
                        }
                        //c.close();

                        TableLayout table;
                        table = findViewById(R.id.table_layout);
                        table.removeAllViews();

                        final TableRow row = new TableRow(this);
                        row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                        row.setMinimumWidth(209);

                        count = colNames.size();
                        System.err.println("count is : " + count);

                        for (int j = 0; j < colNames.size() ; j++) {
                            TextView tv = new TextView(this);
                            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                            tv.setGravity(Gravity.CENTER);
                            //tv.setWidth(40);
                            tv.setTextSize(18);
                            tv.setPadding(30, 15, 30, 15);
                            tv.setText(colNames.get(j));
                            row.addView(tv);
                        }
                        table.addView(row);

                        String query2 = "SELECT * FROM " + result + ";";
                        Cursor c2 = mydatabase.rawQuery(query2, null);
                        int numberRows = 0;
                        int num = 0;
                        ArrayList<String> queryValues = new ArrayList<String>();
                        if (c2.moveToFirst()) {
                            while ( !c2.isAfterLast() ) {
                                for(int i = 0 ; i < colNames.size() ; i++)
                                {
                                    queryValues.add( c2.getString(i) );
                                    num++;
                                }
                                numberRows++;
                                c2.moveToNext();
                            }
                        }
                        System.err.println(" result is : " + num);
                        System.err.println(" queryresult is : " + queryValues.size());
                        c2.close();
                        for (int i = 0, k = 0; i < numberRows ; i++) {

                            final TableRow row2 = new TableRow(this);
                            row2.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                            row2.setMinimumWidth(209);
                            // inner for loop
                            for (int j = 0; j < count ; j++) {
                                TextView tv = new TextView(this);
                                tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                                tv.setGravity(Gravity.CENTER);
                                tv.setTextSize(18);
                                tv.setPadding(20, 5, 20, 5);
                                tv.setText(queryValues.get(k));

                                row2.addView(tv);
                                k++;
                            }
                            table.addView(row2);
                        }
                    }
                }

            }
            catch(SQLiteException ex)
            {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

    }
}
