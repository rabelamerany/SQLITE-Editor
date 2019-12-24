package com.example.lenovo.androidmyadmin.databaseActivities;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.lenovo.androidmyadmin.R;

import java.util.ArrayList;

public class DetailTableActivity extends AppCompatActivity {
    int count = 0;
    int index = 0;
    String dbName;
    String tableName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_table);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        dbName = getIntent().getStringExtra("dbName");
        tableName = getIntent().getStringExtra("tableName");

        TextView textDb = (TextView)findViewById(R.id.databaseName);
        TextView textTable = (TextView)findViewById(R.id.tableName);

        textDb.setText("DB : " + dbName);
        textTable.setText("Table : " + tableName);

        SQLiteDatabase mydatabase;
        final ArrayList<String> colNames = new ArrayList<String>();

        mydatabase = getApplicationContext().openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        String query = "PRAGMA table_info(" + tableName + ");";
        Cursor c = mydatabase.rawQuery(query, null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                //System.err.println(c.getColumnIndex("name"));
                colNames.add( c.getString( c.getColumnIndex("name")) );
                c.moveToNext();
            }
        }
        c.close();

        TableLayout table;
        table = findViewById(R.id.table_layout);
        TableRow row = (TableRow) table.getChildAt(0);
        count = colNames.size();
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

        String query2 = "SELECT * FROM " + tableName + ";";
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
            for (int j = 0; j < count + 2; j++) {
                if(j == 0)
                {
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(18);
                    tv.setPadding(30, 5, 30, 5);
                    tv.setText("Delete");
                    tv.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            TableRow t = (TableRow) row2; //Your Layout
                            TextView firstTextView = (TextView) t.getChildAt(2);
                            String firstText = firstTextView.getText().toString();
                            String query = "DELETE FROM "+ tableName +" WHERE " + colNames.get(0) + " = " + firstText + ";";

                            SQLiteDatabase mydatabase;
                            mydatabase = getApplicationContext().openOrCreateDatabase(dbName, MODE_PRIVATE, null);
                            mydatabase.execSQL(query);
                            finish();
                            startActivity(getIntent());
                        }
                    });
                    row2.addView(tv);
                }
                else if(j == 1)
                {
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(18);
                    tv.setPadding(30, 5, 30, 5);
                    tv.setText("Update");
                    tv.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            TableRow t = (TableRow) row2; //Your Layout
                            ArrayList<String> rowList = new ArrayList<>();
                            for(int i = 2 ; i < row2.getChildCount() ; i++)
                            {
                                TextView firstTextView = (TextView) t.getChildAt(i);
                                rowList.add(firstTextView.getText().toString());
                            }
                            String tmp = rowList.get(0);
                            Intent intent = new Intent(DetailTableActivity.this, UpdateActivity.class);
                            intent.putExtra("db", dbName);
                            intent.putExtra("table",tableName);
                            intent.putExtra("numberCols",count);
                            intent.putExtra("tmp",tmp);
                            intent.putStringArrayListExtra("rowList", rowList);
                            startActivity(intent);
                        }
                    });
                    row2.addView(tv);
                }
                else
                {
                    TextView tv = new TextView(this);
                    tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));

                    tv.setGravity(Gravity.CENTER);
                    tv.setTextSize(18);
                    tv.setPadding(20, 5, 20, 5);
                    tv.setText(queryValues.get(k));

                    row2.addView(tv);
                    k++;
                }

            }
            table.addView(row2);
        }
    }

    public void onPresser(View view) {
        Intent intent = new Intent(DetailTableActivity.this, InsertActivity.class);
        intent.putExtra("db", dbName);
        intent.putExtra("table",tableName);
        intent.putExtra("numberCols",count);
        startActivity(intent);
    }
}
