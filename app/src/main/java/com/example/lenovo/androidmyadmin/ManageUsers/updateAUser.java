package com.example.lenovo.androidmyadmin.ManageUsers;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.lenovo.androidmyadmin.Login.LoginActivity;
import com.example.lenovo.androidmyadmin.R;
import com.example.lenovo.androidmyadmin.sql.UserSQLiteHelper;

public class updateAUser extends AppCompatActivity {
    String name;
    String pass;
    EditText editText;

    Button mChooseBtn;


    EditText mEdtPass;

    public static UserSQLiteHelper mSQLiteHelper;

    Spinner mySpinner;

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_auser);
       Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });



        name= getIntent().getStringExtra("data");
        pass= getIntent().getStringExtra("pass");

        editText=findViewById(R.id.edtEmailupU);
        editText.setText(name);

        mChooseBtn=findViewById(R.id.button3upU);

        mEdtPass=findViewById(R.id.edtPassupU);
        mEdtPass.setText(pass);
        mySpinner=(Spinner) findViewById(R.id.myspinnerupU);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(updateAUser.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mEdtPass.getText().toString().isEmpty()) {
                    Toast.makeText(updateAUser.this, "Veuillez saisir le mot de passe", Toast.LENGTH_LONG).show();




                } else if (mEdtPass.getText().toString().length() < 6) {
                    Toast.makeText(updateAUser.this, "La longeur de mot de passe doit etre superieur a 6", Toast.LENGTH_LONG).show();
                } else {

                    try {

                        SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("database", MODE_PRIVATE, null);
                        String sql = "UPDATE user SET user_email= '" + editText.getText() + "',user_type='" + mySpinner.getSelectedItem().toString() + "',user_password='" + mEdtPass.getText() + "'WHERE user_email='" + name + "';'";
                        try {
                            mydatabase.execSQL(sql);
                            Toast.makeText(updateAUser.this, "Updated successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(updateAUser.this, ListUsers.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            Toast.makeText(updateAUser.this, "Probleeeem" + e, Toast.LENGTH_LONG).show();
                            //System.err.println(e);
                        }



                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
