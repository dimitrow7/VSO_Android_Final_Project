package net.northwestvision.vsoWeatherProject.accessibility;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import net.northwestvision.vsoWeatherProject.R;
import net.northwestvision.vsoWeatherProject.datastructure.Contact;
import net.northwestvision.vsoWeatherProject.utils.DatabaseHelper;

/**
 * Created by Acer on 06.07.2016.
 */
public class SignUpActivity extends AppCompatActivity {

    DatabaseHelper helper = new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Help TEST", Snackbar.LENGTH_LONG)
                        .setAction("Help", null).show();
            }
        });
    }

    public void onButtonClick(View v) {

        if (v.getId() == R.id.BSlogin) {




            Intent i = new Intent(SignUpActivity.this, LoginActivity.class);

            startActivity(i);



        }
    }


    public void onSignUpClick(View v){

        if (v.getId() == R.id.Bsignupbutton){

            EditText name = (EditText) findViewById(R.id.TFname);
            EditText email = (EditText) findViewById(R.id.TFemail);
            EditText uname = (EditText) findViewById(R.id.TFuname);
            EditText pass1 = (EditText) findViewById(R.id.TFpass1);
            EditText pass2 = (EditText) findViewById(R.id.TFpass2);


            String namestr = name.getText().toString();
            String emailstr = email.getText().toString();
            String unamestr = uname.getText().toString();
            String pass1str = pass1.getText().toString();
            String pass2str = pass2.getText().toString();


            if (!pass1str.equals(pass2str)){

                //pop up message

                Toast pass = Toast.makeText(SignUpActivity.this, "Passwords Don't match", Toast.LENGTH_SHORT);
                pass.show();

            }
            else
            {

                //insert the details in the database

                Contact c = new Contact();
                c.setName(namestr);
                c.setEmail(emailstr);
                c.setUname(unamestr);
                c.setPass(pass1str);

                helper.insertContact(c);


                Toast.makeText(SignUpActivity.this, "Sign Up Successful Click Login", Toast.LENGTH_LONG).show();
            }

        }

    }

}
