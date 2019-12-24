package com.example.lenovo.androidmyadmin.ManageUsers;

import android.content.Intent;
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
import com.example.lenovo.androidmyadmin.model.User;
import com.example.lenovo.androidmyadmin.sql.DatabaseHelper;
import com.example.lenovo.androidmyadmin.sql.UserSQLiteHelper;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainAdminActivity extends AppCompatActivity {
    private DatabaseHelper databaseHelper ;
    private User user;

    Button mChooseBtn;
    EditText mEdtEmail;
    EditText mEdtPass;

    public static UserSQLiteHelper mSQLiteHelper;

    Spinner mySpinner;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        databaseHelper = new DatabaseHelper(MainAdminActivity.this);
        mChooseBtn=findViewById(R.id.button3);
        mEdtEmail=findViewById(R.id.edtEmail);
        mEdtPass=findViewById(R.id.edtPass);

        ArrayList<String> spinnerArray = new ArrayList<String>();
        spinnerArray.add("Admin");
        spinnerArray.add("User");
        mySpinner=(Spinner) findViewById(R.id.myspinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(MainAdminActivity.this,android.R.layout.simple_list_item_1,spinnerArray);
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);


        mChooseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isEmailValid(mEdtEmail.getText().toString())==false)
                {
                    Toast.makeText(MainAdminActivity.this, "Veuillez saisir un email valid", Toast.LENGTH_LONG).show();
                }

                else if (mEdtPass.getText().toString().isEmpty()) {
                    Toast.makeText(MainAdminActivity.this, "Veuillez saisir le mot de passe", Toast.LENGTH_LONG).show();




                } else if (mEdtPass.getText().toString().length() < 6) {
                    Toast.makeText(MainAdminActivity.this, "La longeur de mot de passe doit etre superieur a 6", Toast.LENGTH_LONG).show();
                } else {
                    try
                    {
                        if (!databaseHelper.checkUser(mEdtEmail.getText().toString().trim())) {
                            user = new User();
                            user.setEmail(mEdtEmail.getText().toString().trim());
                            user.setPassword(mEdtPass.getText().toString().trim());
                            user.setType(mySpinner.getSelectedItem().toString());

                            databaseHelper.addUser(user);
                            Toast.makeText(MainAdminActivity.this, "Added successfully", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(MainAdminActivity.this, ListUsers.class);
                            startActivity(intent);
                            Toast.makeText(MainAdminActivity.this, "Liste des utilisateurs", Toast.LENGTH_LONG).show();
                        }
                        else
                        {
                            Toast.makeText(MainAdminActivity.this,"Cet email exit deja",Toast.LENGTH_LONG).show();
                        }





                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }}
        });
        //creating DB



    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.log:
                Intent intent = new Intent(MainAdminActivity.this, LoginActivity.class);
                startActivity(intent);
                //Toast.makeText(this,"Gestion Base de données",Toast.LENGTH_LONG).show();

        }

        return super.onOptionsItemSelected(item);
    }
    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }


}

