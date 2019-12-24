package com.example.lenovo.androidmyadmin.databaseActivities;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
import android.widget.Toast;

import com.example.lenovo.androidmyadmin.Login.LoginActivity;
import com.example.lenovo.androidmyadmin.R;

import java.util.ArrayList;
import java.util.List;


public class DatabaseActivity extends AppCompatActivity {

    ListView mlisteview;
    String[] dbList;
    List<String> matieres = new ArrayList<String>();
    ArrayAdapter<String> adapter ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();

        //email = (TextView) findViewById(R.id.email);
        //email.setText(intent.getStringExtra("message"));
        mlisteview = findViewById(R.id.ListMatieres);

        dbList = this.databaseList();

        for(int i = 0; i < dbList.length; i++)
        {
            if(dbList[i].contains("journal") == false && dbList[i].contains("google") == false && dbList[i].contains("RECORD") == false && dbList[i].contains("database") == false)
            {
                matieres.add(dbList[i]);
            }
        }

         adapter = new ArrayAdapter<String>(DatabaseActivity.this,android.R.layout.simple_list_item_1,matieres);
         //adapter = new ArrayAdapter<String>(this,);
        mlisteview.setAdapter(adapter);
        mlisteview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(this,"data",Toast.LENGTH_LONG).show();
                Intent intent3 = new Intent(DatabaseActivity.this, DetailActivity.class);
                intent3.putExtra("data", matieres.get(position));
                startActivity(intent3);
            }
        });
    }
    public void signOut() {
        //auth.signOut();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);
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
            signOut();
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.main)
        {
            finish();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClicker(View view) {
        Intent intent = new Intent(this, DatabaseActivity.class);
        EditText messageView;
        messageView = (findViewById(R.id.editText));
        String messageText = messageView.getText().toString();
        if(messageText.isEmpty())
        {
            Toast.makeText(this, "Please enter database name", Toast.LENGTH_LONG).show();
        }
        else if(matieres.contains(messageText))
        {
            Toast.makeText(this, "This database name already exists", Toast.LENGTH_LONG).show();
        }
        else
        {
            SQLiteDatabase mydatabase;
            mydatabase = getApplicationContext().openOrCreateDatabase(messageText, MODE_PRIVATE,null);

            DatabaseActivity.this.finish();
            Intent intent2 =new Intent(this, DatabaseActivity.class);
            startActivity(intent2);
        }



        /*String[] dbList = this.databaseList();

        for(int i = 0; i < dbList.length; i++)
        {
            if(dbList[i].contains("journal") == false)
            {
                matieres.add(dbList[i]);
            }
        }

        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(DatabaseActivity.this,android.R.layout.simple_list_item_1,matieres);
        mlisteview.setAdapter(adapter);*/
    }
}
