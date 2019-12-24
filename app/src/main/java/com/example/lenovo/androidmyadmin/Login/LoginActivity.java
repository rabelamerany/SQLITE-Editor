package com.example.lenovo.androidmyadmin.Login;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lenovo.androidmyadmin.ManageUsers.ListUsers;
import com.example.lenovo.androidmyadmin.R;
import com.example.lenovo.androidmyadmin.ManageUsers.UserSessionManager;
import com.example.lenovo.androidmyadmin.databaseActivities.MainActivity;
import com.example.lenovo.androidmyadmin.helpers.InputValidation;
import com.example.lenovo.androidmyadmin.sql.DatabaseHelper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private final AppCompatActivity activity = LoginActivity.this;

    private NestedScrollView nestedScrollView;



    private TextInputLayout textInputLayoutEmail;
    private TextInputLayout textInputLayoutPassword;

    private TextInputEditText textInputEditTextEmail,textInputEditTextName;
    private TextInputEditText textInputEditTextPassword;

    private AppCompatButton appCompatButtonLogin;

    private AppCompatTextView textViewLinkRegister;

    private InputValidation inputValidation;
    private DatabaseHelper databaseHelper;
    private TextView tvForgetPassword, resetpasswordtext;
    private Button btn_back,btn_logout;
    UserSessionManager userSessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();
        userSessionManager = new UserSessionManager(this);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        //btn_logout = (Button) findViewById(R.id.btn_logout);

        initViews();
        initListeners();
        initObjects();

    }

    /*
      btn.setOnClickListener(new View.OnClickListener() {
                        @Override public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
    */
    /**
     * This method is to initialize views
     */
    private void initViews() {
        nestedScrollView = (NestedScrollView) findViewById(R.id.nestedScrollView);

        textInputLayoutEmail = (TextInputLayout) findViewById(R.id.textInputLayoutEmail);
        textInputLayoutPassword = (TextInputLayout) findViewById(R.id.textInputLayoutPassword);

        textInputEditTextEmail = (TextInputEditText) findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword = (TextInputEditText) findViewById(R.id.textInputEditTextPassword);

        appCompatButtonLogin = (AppCompatButton) findViewById(R.id.appCompatButtonLogin);

        textViewLinkRegister = (AppCompatTextView) findViewById(R.id.textViewLinkRegister);

    }
    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonLogin.setOnClickListener(this);
        textViewLinkRegister.setOnClickListener(this);
    }

    /**
     * This method is to initialize objects to be used
     */
    private void initObjects() {
        databaseHelper = new DatabaseHelper(activity);
        inputValidation = new InputValidation(activity);
    }
    /**
     * This implemented method is to listen the click on view
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.appCompatButtonLogin:
                System.out.println("void on cliiiiiiiiiiiiiiiiiiiiiiiiiick");
                verifyFromSQLite();
                break;
            case R.id.textViewLinkRegister:
                // Navigate to RegisterActivity
                Intent intentRegister = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intentRegister);
                break;
            case R.id.btn_back:
                Intent intentRegister1 = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intentRegister1);
                break;
        }
    }
    /**
     * This method is to validate the input text fields and verify login credentials from SQLite
     */
    private void verifyFromSQLite() {
        if (!inputValidation.isInputEditTextFilled(textInputEditTextEmail, textInputLayoutEmail, getString(R.string.error_message_email))) {
            return;
        }
        if (!inputValidation.isInputEditTextFilled(textInputEditTextPassword, textInputLayoutPassword, getString(R.string.error_message_email))) {
            return;
        }
        if (databaseHelper.checkUser(textInputEditTextEmail.getText().toString().trim()
                , textInputEditTextPassword.getText().toString().trim())) {

            String message= textInputEditTextEmail.getText().toString();

            SQLiteDatabase mydatabase = getApplicationContext().openOrCreateDatabase("database", MODE_PRIVATE, null);
            String query = "SELECT user_type from user WHERE user_email = '" + message + "' AND user_password = '" + textInputEditTextPassword.getText().toString().trim() + "';";
            Cursor c = mydatabase.rawQuery(query, null);
            int leCount = 0;
            String test = "";
            if (c.moveToFirst()) {
                while ( !c.isAfterLast() ) {
                    //System.err.println(c.getColumnIndex("name"));
                    test =  c.getString( 0) ;
                    c.moveToNext();
                }
            }
            c.close();
            System.err.println("Type is : " + test);
            if(test.equals("Admin"))
            {
                System.out.println("admin is cheked");
                String email = textInputEditTextEmail.getText().toString().trim();
                userSessionManager.createUserLoginSession(email);
                //Intent accountsIntent = new Intent(this, ListUsers.class);
                Intent accountsIntent = new Intent(this, ListUsers.class);
                //accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
                emptyInputEditText();
                startActivity(accountsIntent);
            }
            else if (test.equals("User")){
                System.out.println("user is cheked");
                String email = textInputEditTextEmail.getText().toString().trim();
                userSessionManager.createUserLoginSession(email);
                Intent intent = new Intent(this, MainActivity.class);
                //accountsIntent.putExtra("EMAIL", textInputEditTextEmail.getText().toString().trim());
                emptyInputEditText();
                startActivity(intent);
            }
        }
        else {
            Toast.makeText(this, "This user doesn't exist.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This method is to empty all input edit text
     */
    private void emptyInputEditText() {
        textInputEditTextEmail.setText(null);
        textInputEditTextPassword.setText(null);
    }
}
