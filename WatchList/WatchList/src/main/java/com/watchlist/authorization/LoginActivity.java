package com.watchlist.authorization;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.watchlist.R;


/**
 * Created by VEINHORN on 26/11/13.
 */
public class LoginActivity extends ActionBarActivity {
    private EditText email;
    private EditText nickname;
    private EditText password;
    private Button loginButton;
    private Login login;

    @Override
    protected void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_login);

        email = (EditText)findViewById(R.id.activity_login_email_edit_text);
        nickname = (EditText)findViewById(R.id.activity_login_nickname_edit_text);
        password = (EditText)findViewById(R.id.activity_login_password_edit_text);
        loginButton = (Button)findViewById(R.id.activity_login_login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // User that typed his data
                User user = new User();
                // Put information from text fields to User object
                user.setName(nickname.getText().toString());
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());

                login = new Login(user, LoginActivity.this, LoginActivity.this);
                login.execute(); // run "log in" in background
            }
        });
    }
}
