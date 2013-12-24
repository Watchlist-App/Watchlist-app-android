package com.watchlistapp.authorization;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.watchlistapp.R;


/**
 * Created by VEINHORN on 26/11/13.
 */
public class LogInActivity extends Activity {
    private EditText email;
    private EditText nickname;
    private EditText password;
    private Button loginButton;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);


        /*
        // Sets the action bar color as a drawable
        ActionBar actionBar = getSupportActionBar();
        String actionBarColor = getString(R.color.login_activity_actionbar_color);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(actionBarColor)));
        */

        email = (EditText)findViewById(R.id.activity_login_email_edit_text);
        password = (EditText)findViewById(R.id.activity_login_password_edit_text);
        loginButton = (Button)findViewById(R.id.activity_login_login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailOrName = email.getText().toString();
                String userPassword = password.getText().toString();

                login = new Login(LogInActivity.this, LogInActivity.this, emailOrName, userPassword);
                login.execute(); // run "log in" in background
            }
        });
    }
}
