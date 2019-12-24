package com.example.lenovo.androidmyadmin.ManageUsers;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.lenovo.androidmyadmin.Login.LoginActivity;
import com.example.lenovo.androidmyadmin.R;
import com.example.lenovo.androidmyadmin.databaseActivities.MainActivity;
import com.example.lenovo.androidmyadmin.model.User;
import com.example.lenovo.androidmyadmin.sql.UserSQLiteHelper;

import java.util.ArrayList;

public class ListUsers extends AppCompatActivity {
    ListView mListView;
    RecordListAdapter mAdapter=null;
    Button b;
    Button find;
    public static UserSQLiteHelper mSQLiteHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_users);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSQLiteHelper=new UserSQLiteHelper(this,"database",null,1);
        //creating table in db
        mSQLiteHelper.queryData("CREATE TABLE IF NOT EXISTS user(user_id INTEGER PRIMARY KEY AUTOINCREMENT,user_email VARCHAR,user_password VARCHAR,user_type VARCHAR)");
        mListView=(ListView) findViewById(R.id.listview_);

        ArrayList<User>a = new ArrayList<User>();
        SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("database", MODE_PRIVATE, null);
        Cursor c = mydatabase.rawQuery("SELECT * FROM user", null);

        int count = 0;

        if (c.moveToFirst())
        {
            while(!c.isAfterLast())
            {
                int id = c.getInt(0);
                String email=c.getString(1);
                String password=c.getString(2);
                String type=c.getString(3);
                User m = new User(id, email,password, type);
                /*imageList.add(image);*/
                a.add(m);
                count++;

                c.moveToNext();
            }
        }
        System.err.println("count is : " + a.size());
        if(a.size()==0)
        {
            //if there is no record in table of db which means listview is emplty
            Toast.makeText(this,"No record found ...",Toast.LENGTH_LONG).show();
        }
        else
        {
        }



        mAdapter = new RecordListAdapter(this,R.layout.row,a);

        mListView.setAdapter(mAdapter);
        //mAdapter.notifyDataSetChanged();
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> adpter, View view, final int position, long id) {
                Log.i("HelloListView", "You clicked Item: " + id + " at position:" + position);

                final User data = (User) adpter.getItemAtPosition(position);


                final CharSequence[] items ={"update","Delete"};
                AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(ListUsers.this);


                alertDialog2.setTitle("Choose an action ");
                alertDialog2.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0)
                        {
                            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(ListUsers.this);
                            // Setting Dialog Title

                            alertDialog2.setTitle("Confirm Update...");

// Setting Dialog Message
                            alertDialog2.setMessage("Are you sure you want update this user?");

// Setting Icon to Dialog
                            alertDialog2.setIcon(R.drawable.edituser);

// Setting Positive "Yes" Btn
                            alertDialog2.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(ListUsers.this, updateAUser
                                                    .class);
                                            intent.putExtra("data", data.getEmail());
                                            intent.putExtra("pass", data.getPassword());


                                            startActivity(intent);



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
                        if(which==1)
                        {
                            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(ListUsers.this);
                            // Setting Dialog Title

                            alertDialog2.setTitle("Confirm Delete...");

// Setting Dialog Message
                            alertDialog2.setMessage("Are you sure you want delete this user?");

// Setting Icon to Dialog
                            alertDialog2.setIcon(R.drawable.suppuser);

// Setting Positive "Yes" Btn
                            alertDialog2.setPositiveButton("YES",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            User data = (User) adpter.getItemAtPosition(position);
                                            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("database", MODE_PRIVATE, null);
                                            String sql ="DELETE FROM user WHERE user_id= "+data.getId()+";";
                                            try {
                                                mydatabase.execSQL(sql);
                                            }
                                            catch (Exception e)
                                            {
                                                Toast.makeText(ListUsers.this,"Prooobleeeem"+e,Toast.LENGTH_LONG).show();
                                                System.err.println(e);
                                            }

                                            Toast.makeText(ListUsers.this,"Deleted successfully",Toast.LENGTH_LONG).show();
                                            Intent intent = new Intent(ListUsers.this, ListUsers.class);
                                            startActivity(intent);



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
        b=findViewById(R.id.buttonAdd);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListUsers.this, MainAdminActivity.class);
                startActivity(intent);
                Toast.makeText(ListUsers.this,"Creer un utilisateur",Toast.LENGTH_LONG).show();

            }

        });
        find=findViewById(R.id.buttonFind);
        find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListUsers.this, findUser.class);
                startActivity(intent);
                Toast.makeText(ListUsers.this,"Find a User",Toast.LENGTH_LONG).show();

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


