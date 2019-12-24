package com.example.lenovo.androidmyadmin.databaseActivities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.androidmyadmin.R;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {
    ArrayList<String> arrTblNames = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        final String dbName = getIntent().getStringExtra("data");
        TextView textView = (TextView)findViewById(R.id.textView);
        textView.setText("Database name : " + dbName);



        SQLiteDatabase db = getApplicationContext().openOrCreateDatabase(dbName, MODE_PRIVATE, null);
        ListView mlisteview = findViewById(R.id.listeTables);


        final ArrayList<String> tableNames = new ArrayList<String>();
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                arrTblNames.add( c.getString( c.getColumnIndex("name")) );
                c.moveToNext();
            }
        }

        for(int i = 0; i < arrTblNames.size(); i++)
        {
            if(arrTblNames.get(i).contains("android") == false && arrTblNames.get(i).contains("sqlite_sequence") == false)
            {
                tableNames.add(arrTblNames.get(i));
            }
        }
        adapter=new ArrayAdapter<String>(DetailActivity.this,android.R.layout.simple_list_item_1,tableNames);
        mlisteview.setAdapter(adapter);
        mlisteview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final CharSequence[] items ={"List","Delete"};
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(DetailActivity.this);

                final int tmp = position;
                alertDialog2.setTitle("Choose an action ");
                alertDialog2.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0)
                        {
                            Intent intent = new Intent(DetailActivity.this, DetailTableActivity.class);
                            intent.putExtra("dbName", dbName);
                            intent.putExtra("tableName", tableNames.get(tmp));
                            startActivity(intent);

                        }
                        if(which==1)
                        {
                            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(DetailActivity.this);
                            // Setting Dialog Title

                            alertDialog2.setTitle("Confirm Delete...");

// Setting Dialog Message
                            alertDialog2.setMessage("Are you sure you want delete this table?");

// Setting Icon to Dialog
                            alertDialog2.setIcon(R.drawable.suppuser);

// Setting Positive "Yes" Btn
                            alertDialog2.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            String data = tableNames.get(tmp);
                                            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase(dbName, MODE_PRIVATE, null);
                                            String sql ="DROP TABLE IF EXISTS "+data+";";
                                            try {
                                                mydatabase.execSQL(sql);

                                                Toast.makeText(DetailActivity.this,"Deleted successfully",Toast.LENGTH_LONG).show();
                                                finish();
                                                Intent intent = new Intent(DetailActivity.this, DetailActivity.class);
                                                intent.putExtra("data", dbName);
                                                startActivity(intent);
                                            }
                                            catch (Exception e)
                                            {
                                                Toast.makeText(DetailActivity.this,"Prooobleeeem"+e,Toast.LENGTH_LONG).show();
                                                System.err.println(e);
                                            }




                                        }
                                    });

// Setting Negative "NO" Btn
                            alertDialog2.setNegativeButton("NO",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            // Write your code here to execute after dialog
                                            Toast.makeText(getApplicationContext(),
                                                    "You clicked on NO", Toast.LENGTH_SHORT)
                                                    .show();
                                            dialog.cancel();
                                        }
                                    });

// Showing Alert Dialog
                            alertDialog2.show();


                        }

                    }
                });
                alertDialog2.show();
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
        MenuItem item2 = menu.findItem(R.id.main);
        MenuItem item3 = menu.findItem(R.id.log);
        item2.setVisible(false);
        item3.setVisible(false);

        int id = item.getItemId();
        if(id == R.id.app_bar_search)
        {
            SearchView searchView = (SearchView)item.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapter.getFilter().filter(newText);
                    return false;
                }
            });
        }
        else
        {
            Toast.makeText(this, "walou search a khawa", Toast.LENGTH_LONG).show();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.log) {
            return true;
        }
        else if (id == R.id.main)
        {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickerBtn(View view) {
        EditText textviewName = findViewById(R.id.editText2);
        EditText textviewNumber = findViewById(R.id.editText3);
        String numCol = textviewNumber.getText().toString();
        int tmp;
        if(textviewName.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter table name", Toast.LENGTH_LONG).show();
        }
        else if(textviewNumber.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter the number of columns", Toast.LENGTH_LONG).show();
        }
        else if (arrTblNames.contains(textviewName.getText().toString()))
        {

            Toast.makeText(this, "This table name already exists", Toast.LENGTH_LONG).show();
        }
        else if(!android.text.TextUtils.isDigitsOnly(numCol))
        {
            Toast.makeText(this, "Please select a number", Toast.LENGTH_LONG).show();
        }
        else if(android.text.TextUtils.isDigitsOnly(textviewName.getText().toString()))
        {
            Toast.makeText(this, "A table name cannot be entirely numeric", Toast.LENGTH_LONG).show();
        }
        else
        {
            tmp = Integer.parseInt(numCol);
            if(tmp <= 0)
            {
                Toast.makeText(this, "Please select a positive number", Toast.LENGTH_LONG).show();
            }
            else if(tmp > 100)
            {
                Toast.makeText(this, "A table cannot exceed 100 columns", Toast.LENGTH_LONG).show();
            }
            else
            {
                Intent intent = new Intent(DetailActivity.this, CreateTableActivity.class);

                intent.putExtra("db", getIntent().getStringExtra("data"));
                intent.putExtra("table",textviewName.getText().toString());
                intent.putExtra("numberCols",numCol);
                startActivity(intent);
            }
        }

    }
}
