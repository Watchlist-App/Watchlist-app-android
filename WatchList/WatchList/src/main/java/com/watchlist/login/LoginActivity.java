package com.watchlist.login;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.watchlist.R;

public class LoginActivity extends ActionBarActivity {

    private EditText emailEditText;
    private EditText nicknameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditText = (EditText)findViewById(R.id.activity_login_email_edit_text);
        nicknameEditText = (EditText)findViewById(R.id.activity_login_nickname_edit_text);
        passwordEditText = (EditText)findViewById(R.id.activity_login_email_edit_text);

        loginButton = (Button)findViewById(R.id.activity_login_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User(emailEditText.getText().toString(), nicknameEditText.getText().toString(), passwordEditText.getText().toString());
                LoginConnector loginConnector = new LoginConnector();
                String result = loginConnector.checkIsLogin(user.getEmail(), user.getNickname(), user.getPassword());
                Toast toast = Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG);
                toast.show();
            }
        });

        // Sets the action bar color as a drawable
        ActionBar actionBar = getSupportActionBar();
        String actionBarColor = getString(R.color.actionbar_color);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor(actionBarColor)));
    }
}
