package com.example.loginsignup.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginsignup.API.APIService;
import com.example.loginsignup.API.ApiClient;
import com.example.loginsignup.Model.User;
import com.example.loginsignup.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private ProgressDialog pDialog;
    private EditText editTextEmail;
    private EditText editTextPass;
    private Button loginBtn;
    private Button socialLogin;
    private TextView signuplink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.input_email);
        editTextPass = findViewById(R.id.input_password);
        loginBtn = findViewById(R.id.loginbtn);
        signuplink = findViewById(R.id.link_signup);
        socialLogin = findViewById(R.id.social_login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        signuplink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
               // finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        socialLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SocialLogin.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                //finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

            }
        });

    }

    private void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }
        loginBtn.setEnabled(false);
        loginByServer();
    }

    private void loginByServer() {

        pDialog = new ProgressDialog(LoginActivity.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);

        showpDialog();

        String email = editTextEmail.getText().toString();
        String password = editTextPass.getText().toString();


        APIService service = ApiClient.getClient().create(APIService.class);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        service.userLogIn(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                hidepDialog();

                if (response.code() == 200) {
                    onLoginSuccess();
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong password : ErrorCode : 200" + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                hidepDialog();
            }
        });
    }


    private void showpDialog() {

        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
            }
        }
    }

    public void onLoginSuccess() {
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }

    public void onLoginFailed() {

        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        loginBtn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = editTextEmail.getText().toString();
        String password = editTextPass.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("enter a valid email address");
            requestFocus(editTextEmail);
            valid = false;
        } else {
            editTextEmail.setError(null);
        }

        if (password.isEmpty()) {
            editTextPass.setError("Password is empty");
            requestFocus(editTextPass);
            valid = false;
        } else {
            editTextPass.setError(null);
        }

        return valid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

}
    
