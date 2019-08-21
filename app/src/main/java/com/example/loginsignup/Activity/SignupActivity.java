package com.example.loginsignup.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    private ProgressDialog pDialog;
    private EditText textemail;
    private EditText textpass;
    private Button signupbtn;
    private TextView loginlink;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        textemail = findViewById(R.id.input_email);
        textpass = findViewById(R.id.input_password);
        signupbtn = findViewById(R.id.signbtn);
        loginlink = findViewById(R.id.link_signup);

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        loginlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        saveToServerDB();
    }

    public void onSignupSuccess() {
        signupbtn.setEnabled(true);
        setResult(RESULT_OK, null);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        signupbtn.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;


        String email = textemail.getText().toString();
        String password = textpass.getText().toString();


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            textemail.setError("enter a valid email address");
            valid = false;
        } else {
            textemail.setError(null);
        }


        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            textpass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            textpass.setError(null);
        }

        return valid;
    }
    private void saveToServerDB() {
        pDialog = new ProgressDialog(SignupActivity.this);
        pDialog.setIndeterminate(true);
        pDialog.setMessage("Creating Account...");
        pDialog.setCancelable(false);
        showpDialog();


        String email = textemail.getText().toString();
        String password = textpass.getText().toString();

        APIService service = ApiClient.getClient().create(APIService.class);

        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        service.userSignUp(user).enqueue(new Callback<User>() {
            public void onResponse(Call call, Response response) {
                hidepDialog();

                onSignupSuccess();

                Log.d("onResponse", "" + response.toString());


                if(response.code() == 200) {
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));

                }else {
                    Toast.makeText(SignupActivity.this, "Signup Successfull" + response.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call call, Throwable t) {
                hidepDialog();
                Log.d("onFailure", t.toString());
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

}


