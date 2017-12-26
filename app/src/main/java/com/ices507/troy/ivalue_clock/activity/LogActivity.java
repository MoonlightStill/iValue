package com.ices507.troy.ivalue_clock.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ices507.troy.ivalue_clock.R;

/**
 * Created by troy on 17-12-19.
 *
 * @Description:
 * @Modified By:
 */

public class LogActivity extends AppCompatActivity {
    private final String TAG = "LoginActivity";
    private AutoCompleteTextView tvUsername;
    private EditText etPassword;
    private ProgressBar pbLogin;
    private Button btnLogin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvUsername = (AutoCompleteTextView) findViewById(R.id.tv_username);
        etPassword = (EditText) findViewById(R.id.et_password);
        pbLogin = (ProgressBar) findViewById(R.id.login_progress);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
    }

    private void attemptLogin() {
        StringBuffer error = new StringBuffer("");
        String username = tvUsername.getText().toString();
        String password = etPassword.getText().toString();
        boolean noError = true;
        if (username.length() == 0) {
            error.append(getString(R.string.error_no_username));
            error.append("\n");
            noError = false;
        }
        if (password.length() == 0) {
            error.append(getString(R.string.error_no_password));
            noError = false;
        }

        if (noError) {
            new UserLoginTask(username, password).execute();
        } else {
            Log.e(TAG, error.toString());
            Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }

    }


    public class UserLoginTask extends AsyncTask<Void, Integer, Integer> {
        private final String username;
        private final String password;
        private final static int WRONG_PASSWORD = 0x500;
        private final static int NONEXIST_USERNAME = 0x501;
        private final static int NO_INTERNET = 0x502;

        UserLoginTask(String username, String password) {
            this.username = username;
            this.password = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {

            publishProgress();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(Integer s) {
            switch (s) {
                case WRONG_PASSWORD:
                    break;
                case NO_INTERNET:
                    break;
                case NONEXIST_USERNAME:
                    break;
            }
        }
    }
}
