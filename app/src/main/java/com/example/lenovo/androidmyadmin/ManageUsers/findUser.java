package com.example.lenovo.androidmyadmin.ManageUsers;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.lenovo.androidmyadmin.Login.LoginActivity;
import com.example.lenovo.androidmyadmin.R;
import com.example.lenovo.androidmyadmin.model.User;
import com.example.lenovo.androidmyadmin.sql.UserSQLiteHelper;

import java.util.ArrayList;

public class findUser extends AppCompatActivity {


    EditText txtId;
    ListView mListView;

    RecordListAdapter mAdapter=null;
    private RadioGroup radioGroup;
    private RadioButton radioButton;


    public static UserSQLiteHelper mSQLiteHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        mListView=(ListView) findViewById(R.id.listview_2);
        txtId =findViewById(R.id.Id);




        RadioGroup rg = (RadioGroup) findViewById(R.id.radioGroup1);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {



            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ArrayList<User> a = new ArrayList<User>();
                SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("database", MODE_PRIVATE, null);
                int count =0;
                mAdapter = new RecordListAdapter(findUser.this, R.layout.row, a);

                switch(checkedId) {
                    case R.id.radioId:

                        radioButton = findViewById(R.id.radioId);
                        //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                        Cursor c = mydatabase.rawQuery("SELECT * FROM user WHERE user_id ='" + txtId.getText().toString() + "';'", null);
                        if (c.moveToFirst()) {
                            while (!c.isAfterLast()) {
                                int id = c.getInt(0);
                                String email = c.getString(1);
                                String password = c.getString(2);
                                String type = c.getString(3);
                                User m = new User(id, email, password, type);
                                a.add(m);
                                count++;

                                c.moveToNext();
                            }

                        }
                        System.err.println("count is : " + a.size());

                        if (a.size() == 0) {
                            //if there is no record in table of db which means listview is emplty
                            Toast.makeText(findUser.this, "No record found ...", Toast.LENGTH_LONG).show();
                        } else {

                        }
                        mListView.setAdapter(mAdapter);
                        txtId.setText("");
                        radioButton.setChecked(false);
                        break;
                    case R.id.radioEmail:
                        mListView.setAdapter(null);
                        radioButton = findViewById(R.id.radioEmail);
                        //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();
                        c = mydatabase.rawQuery("SELECT * FROM user WHERE user_email='" + txtId.getText().toString() + "';'", null);
                        if (c.moveToFirst()) {
                            while (!c.isAfterLast()) {
                                int id = c.getInt(0);
                                String email = c.getString(1);
                                String password = c.getString(2);
                                String type = c.getString(3);
                                User m = new User(id, email, password, type);
                                a.add(m);
                                count++;

                                c.moveToNext();
                            }

                        }
                        System.err.println("count is : " + a.size());

                        if (a.size() == 0) {
                            //if there is no record in table of db which means listview is emplty
                            Toast.makeText(findUser.this, "No record found ...", Toast.LENGTH_LONG).show();
                        } else {

                        }
                        mListView.setAdapter(mAdapter);
                        txtId.setText("");
                        radioButton.setChecked(false);

                        break;
                    case R.id.radioType:
                        mListView.setAdapter(null);
                        radioButton = findViewById(R.id.radioType);
                        //Toast.makeText(getBaseContext(), radioButton.getText(), Toast.LENGTH_SHORT).show();

                        c = mydatabase.rawQuery("SELECT * FROM user WHERE user_type='" + txtId.getText().toString() + "';'", null);


                        if (c.moveToFirst()) {
                            while (!c.isAfterLast()) {
                                int id = c.getInt(0);
                                String email = c.getString(1);
                                String password = c.getString(2);
                                String type = c.getString(3);
                                User m = new User(id, email, password, type);
                                a.add(m);
                                count++;

                                c.moveToNext();
                            }

                        }
                        System.err.println("count is : " + a.size());

                        if (a.size() == 0) {
                            //if there is no record in table of db which means listview is emplty
                            Toast.makeText(findUser.this, "No record found ...", Toast.LENGTH_LONG).show();
                        } else {

                        }
                        mListView.setAdapter(mAdapter);
                        txtId.setText("");
                        radioButton.setChecked(false);
                        break;
                }
            }




    });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        //MenuItem item = menu.findItem(R.id.app_bar_search);

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
            //signOut();
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}


