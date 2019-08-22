package com.example.loginsignup.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.loginsignup.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import static com.twitter.sdk.android.core.Twitter.initialize;

public class SocialLogin extends AppCompatActivity {

    //FOR GOOGLE BUTOON
    int RC_SIGN_IN = 0;
    SignInButton signInButton;
    GoogleSignInClient mGoogleSignInClient;

    //FOR FACEBOOK BUTTON
    LoginButton fbLoginButton;
    CallbackManager callbackManager;
    int FB_SIGN_IN = 999;

    //for Twitter Login
    TwitterLoginButton twitterLoginButton;
    int TW_SIGN_IN = 555;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(this);
        setContentView(R.layout.activity_social_login);

        signInButton = findViewById(R.id.sign_in_button);
        fbLoginButton = findViewById(R.id.fbSigIn);
        twitterLoginButton = findViewById(R.id.twitterlogin);

        // Creating CallbackManager
        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        // Facebook button Initialize
        fbLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                startActivity(new Intent(SocialLogin.this, MainActivity.class));
                finish();
            }

            @Override
            public void onCancel() {
                Toast.makeText(SocialLogin.this, "Login using Facebook Failed", Toast.LENGTH_LONG).show();

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SocialLogin.this, "Login using different media ", Toast.LENGTH_LONG).show();

            }
        });

        // For Twitter
        initialize(this);
        TwitterAuthConfig twitterAuthConfig= new TwitterAuthConfig("CONSUMER_KEY", "CONSUMER_SECRET");
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(twitterAuthConfig)
                .debug(true)
                .build();
        initialize(config);

        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {

                TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;


                login(session);
            }

            @Override
            public void failure(TwitterException exception) {
                Toast.makeText(SocialLogin.this, "Authentication failed! => " + exception.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    private void login(TwitterSession session) {

        Intent intent = new Intent(SocialLogin.this, MainActivity.class);
        startActivity(intent);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else if (requestCode == FB_SIGN_IN){
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }
        else {
            twitterLoginButton.onActivityResult(requestCode, resultCode, data);
        }
        }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount googleSignInAccount  = completedTask.getResult(ApiException.class);
            startActivity(new Intent(SocialLogin.this, MainActivity.class));
            finish();
        } catch (ApiException e) {

            Toast.makeText(SocialLogin.this, "LOGIN using Google Failed", Toast.LENGTH_LONG).show();
        }
    }

}
