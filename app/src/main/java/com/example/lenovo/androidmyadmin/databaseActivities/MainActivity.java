package com.example.lenovo.androidmyadmin.databaseActivities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.lenovo.androidmyadmin.Export.ExportActivity;
import com.example.lenovo.androidmyadmin.Login.LoginActivity;
import com.example.lenovo.androidmyadmin.R;
import com.example.lenovo.androidmyadmin.Export.SqliteExporter;
import com.example.lenovo.androidmyadmin.sql.UserSQLiteHelper;

public class MainActivity extends AppCompatActivity {
    public static UserSQLiteHelper mSQLiteHelper;
    public SqliteExporter sqliteExporter;
    Button b ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.app_bar_search);

        item.setVisible(false);
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
            //auth.signOut();
            finish();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.main)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClick(View view) {
        Intent intent = new Intent(this, DatabaseActivity.class);

        //Toast.makeText(this, "it is me u're looking for", Toast.LENGTH_LONG).show();
        startActivity(intent);
    }

    public void onBiper(View view) {
        Intent intent = new Intent(this, SqlActivity.class);

        startActivity(intent);
    }

    public void cliquer(View view) {
        Intent intent = new Intent(this, ExportActivity.class);
        startActivity(intent);
    }
}
