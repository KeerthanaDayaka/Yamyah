package com.example.loginfirebase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class MainActivity extends AppCompatActivity {
    TextInputEditText lemailId,lpassId;
    Button LoginId;
    FirebaseAuth mAuth;
    TextInputLayout lemailLayout,lpassLayout;
    FirebaseAuth.AuthStateListener mAuthStateListener,fbAuthStateListener;
    SignInButton signInButton;
    LoginButton fbLogin;
    CallbackManager mcallbackManager;
    GoogleSignInClient mGoogleSignInClient;
    String TAG="MainActivity";
    private static final String TAG1="FaceBook Authenictaion";
    private int rc=1,frc=1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        lemailId=findViewById(R.id.lemail);
        lpassId=findViewById(R.id.lpassword);

        LoginId=findViewById(R.id.login);
        lemailLayout=findViewById(R.id.lemail_layout);
        lpassLayout=findViewById(R.id.lpass_layout);
        signInButton=findViewById(R.id.sign_in);
        fbLogin=findViewById(R.id.fb_login);
        fbLogin.setReadPermissions(Arrays.asList("email","public_profile"));

        GoogleSignInOptions gso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).
                requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        mAuthStateListener=new FirebaseAuth.AuthStateListener() {

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mAuth.getCurrentUser();
                if(mFirebaseUser!=null){
                    Toast.makeText(MainActivity.this, "You are Logged In", Toast.LENGTH_SHORT).show();
                    Intent i= new Intent(MainActivity.this,HomeActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
    /*
        //FaceBook
        FacebookSdk.sdkInitialize(getApplicationContext());
        mcallbackManager = CallbackManager.Factory.create();
        fbLogin.registerCallback(mcallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG1,"Succes"+loginResult);
                handleFacebookToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Log.d(TAG1,"Cancel");

            }
            @Override
            public void onError(FacebookException error) {
                Log.d(TAG1,"Error "+error);

            }
        });
        fbAuthStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser fbUser=mAuth.getCurrentUser();
                if(fbUser!=null){
                Toast.makeText(MainActivity.this, "You are Logged with FB", Toast.LENGTH_SHORT).show();
                Intent i= new Intent(MainActivity.this,HomeActivity.class);
                startActivity(i);
                }
                else{
                    Toast.makeText(MainActivity.this, "Try Login,Again", Toast.LENGTH_SHORT).show();
                }


            }
        };*/


    }

    /*
    AccessTokenTracker tokenTracker=new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if (currentAccessToken== null){
                mAuth.signOut();
            }
        }
    };


    private void handleFacebookToken(AccessToken token){
        Log.d(TAG1,"Handle FaceBook Token" +token);
        AuthCredential fbcredential= FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(fbcredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Log.d(TAG1,"Sign in Succesfull");
                    Toast.makeText(MainActivity.this, "Login Succesfull"+task.getException(), Toast.LENGTH_SHORT).show();
                    FirebaseUser user=mAuth.getCurrentUser();

                }
                else{
                    Log.d(TAG1,"Sign Failed "+task.getException());
                    Toast.makeText(MainActivity.this, "Login Failed"+task.getException(), Toast.LENGTH_SHORT).show();

                }
            }
        });
    }*/

    public void Register(View view) {
        Intent intent= new Intent(this,RegistrationPage.class);
        startActivity(intent);
    }

    public void LoginCheck(View view) {
        String lemail=lemailId.getText().toString();
        String lpass=lpassId.getText().toString();

        if(lemail.isEmpty() && lpass.isEmpty()){
            lemailLayout.setError("Email Cannot Be Empty");
            lpassLayout.setError("Password Cannot be Empty");
            lemailLayout.requestFocus();
        }
        else if(lemail.isEmpty()){
            lemailLayout.setError("Email Cannot Be Empty");
            lemailLayout.requestFocus();
        }
        else if(lpass.isEmpty()){
            lpassLayout.setError("Password Cannot be Empty");
            lpassLayout.requestFocus();
        }
        else if(!(lemail.isEmpty() && lpass.isEmpty())){
            mAuth.signInWithEmailAndPassword(lemail,lpass).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "User not Registered! Please Register",
                                Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Intent intoHome=new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intoHome);
                    }
                }
            });
        }
        else{
            Toast.makeText(MainActivity.this, "Error Occured",
                    Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onStart(){
        super.onStart();
        //mAuth.addAuthStateListener(fbAuthStateListener);
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthStateListener!=null){
            mAuth.removeAuthStateListener(mAuthStateListener);
        }
        /*
        if (fbAuthStateListener!=null){
            mAuth.removeAuthStateListener(fbAuthStateListener);
        }*/
    }

    private void signIn() {
        Intent signIntent= mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signIntent,rc);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //mcallbackManager.onActivityResult(requestCode,resultCode,data);
        if(requestCode==rc){
            Task<GoogleSignInAccount> task=GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        if(requestCode==frc) {
            Intent intoHome = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intoHome);
        }
        else{
            Toast.makeText(this, "Hello deepak", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> Completedtask) {
        try {
            GoogleSignInAccount acc=Completedtask.getResult(ApiException.class);
            Toast.makeText(this, "Sign in Succesfull", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);

        }
        catch (Exception e){
            Toast.makeText(this, "Sign in Failed", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(null);

        }
    }

    private void FirebaseGoogleAuth(GoogleSignInAccount acct) {
        AuthCredential authCredential= GoogleAuthProvider.getCredential(acct.getIdToken(),null);
        mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(MainActivity.this, "Successfull", Toast.LENGTH_SHORT).show();
                    FirebaseUser user=mAuth.getCurrentUser();
                    updateUI(user);
                }
                else{
                    Toast.makeText(MainActivity.this, "UnSuccessfull", Toast.LENGTH_SHORT).show();
                    updateUI(null);
                }
            }

        });
    }

    private void updateUI(FirebaseUser user) {
        GoogleSignInAccount account=GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if(account!=null){
            String PersonName=account.getDisplayName();
            String PersonGivenName=account.getGivenName();
            String PersonFamilyName=account.getFamilyName();
            String PersonEmail=account.getEmail();
            String PersonId=account.getId();
            Uri PersonPhoto=account.getPhotoUrl();
            Toast.makeText(this, PersonName, Toast.LENGTH_SHORT).show();
        }
    }


}
