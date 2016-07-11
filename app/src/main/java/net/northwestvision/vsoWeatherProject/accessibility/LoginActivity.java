package net.northwestvision.vsoWeatherProject.accessibility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.northwestvision.vsoWeatherProject.MainActivity;
import net.northwestvision.vsoWeatherProject.R;
import net.northwestvision.vsoWeatherProject.utils.DatabaseHelper;

/**
 * Created by Acer on 06.07.2016.
 */
public class LoginActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);




        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onButtonClick(View v){

        if (v.getId() == R.id.Blogin){

            EditText a = (EditText) findViewById(R.id.TFusername);
            String str = a.getText().toString();
            EditText b = (EditText) findViewById(R.id.TFpassword);
            String pass = b.getText().toString();

            String password = helper.searchPass(str);
            if (pass.equals(password))
            {


                Intent i = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(i);

            }

            else {

                //pop up message

                Toast temp = Toast.makeText(LoginActivity.this, "Username and Password Don't Match", Toast.LENGTH_SHORT);
                temp.show();
            }



        }

        if (v.getId() == R.id.Bsignup){

            Intent i = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(i);

        }
    }

}
