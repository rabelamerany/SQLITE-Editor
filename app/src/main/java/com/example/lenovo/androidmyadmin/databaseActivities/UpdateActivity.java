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
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.androidmyadmin.R;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {
    String dbName;
    String tableName;
    SQLiteDatabase mydatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
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
        int count = extras.getInt("numberCols");

        ArrayList<String> rowList = getIntent().getExtras().getStringArrayList("rowList");
        //TextView textDb = (TextView)findViewById(R.id.databaseName);
        //TextView textTable = (TextView)findViewById(R.id.tableName);

        //textDb.setText("DB : " + dbName);
        //textTable.setText("Table : " + tableName);


        ArrayList<String> colNames = new ArrayList<String>();
        ArrayList<String> colTypes = new ArrayList<String>();

        mydatabase = getApplicationContext().openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        String query = "PRAGMA table_info(" + tableName + ");";
        Cursor c = mydatabase.rawQuery(query, null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                colNames.add( c.getString( c.getColumnIndex("name")) );
                colTypes.add( c.getString( c.getColumnIndex("type")) );
                c.moveToNext();
            }
        }
        TableLayout table;
        table = findViewById(R.id.table_layout);
        for (int i = 0; i < count; i++) {

            TableRow row = new TableRow(this);
            row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
            row.setMinimumWidth(209);
            // inner for loop
            for (int j = 0; j < 3; j++) {
                if(j == 0)
                {
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(18);
                    tv.setPadding(0, 5, 0, 5);
                    tv.setText(colNames.get(i));
                    row.addView(tv);
                }
                else if(j == 1)
                {
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(18);
                    tv.setPadding(0, 5, 0, 5);
                    tv.setText(colTypes.get(i));
                    row.addView(tv);
                }
                else
                {
                    EditText tv = new EditText(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
                    tv.setGravity(Gravity.CENTER);
                    tv.setWidth(200);
                    tv.setTextSize(18);
                    tv.setPadding(15, 15, 15, 15);
                    tv.setText(rowList.get(i));
                    row.addView(tv);
                }
            }
            table.addView(row);
        }
    }

    public void onBip(View view) {
        TableLayout table;
        table = findViewById(R.id.table_layout);

        ArrayList<String> colNames = new ArrayList<String>();
        ArrayList<String> colValues = new ArrayList<String>();
        StringBuilder names = new StringBuilder();
        StringBuilder values = new StringBuilder();

        for(int i = 1, j = table.getChildCount(); i < j; i++) {

            View view1 = table.getChildAt(i);
            if (view1 instanceof TableRow) {
                // then, you can remove the the row you want...
                // for instance...
                TableRow row = (TableRow) view1;

                TextView name = (TextView)row.getChildAt(0);
                EditText value = (EditText)row.getChildAt(2);

                colNames.add(name.getText().toString());
                colValues.add(value.getText().toString());
            }
        }
        for(int i = 1 ; i < colNames.size() ; i ++)
        {
            names = names.append(colNames.get(i)).append(" = '").append(colValues.get(i)).append("' ,");
        }

        String name = names.toString();
        name = names.toString().substring(0, names.toString().length() - 1);

        String query = "UPDATE " + tableName + " SET " + name + " WHERE " + colNames.get(0) + " = " + getIntent().getStringExtra("tmp") + ";";
        System.err.println("param is : " + query);
        try
        {
            mydatabase.execSQL(query);
            //System.err.println("param is : " + value);
            Intent intent = new Intent(UpdateActivity.this, DetailTableActivity.class);
            intent.putExtra("dbName", dbName);
            intent.putExtra("tableName",tableName);

            startActivity(intent);
        }
        catch(SQLiteException ex)
        {
            if(ex.getMessage().contains("SQLITE_MISMATCH"))
            {
                Toast.makeText(this, "Please enter column values", Toast.LENGTH_LONG).show();
            }
            else if(ex.getMessage().contains("SQLITE_CONSTRAINT_PRIMARYKEY"))
            {
                Toast.makeText(this, "The primary key constraint has been violated", Toast.LENGTH_LONG).show();
            }
        }

    }
}
