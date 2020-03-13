package com.example.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationPage extends AppCompatActivity {
    TextInputEditText remailId,rpassId,rrpassId;
    Button registerId;
    FirebaseAuth mAuth;
    TextInputLayout remailLayout,rpassLayout,rrpassLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);
        mAuth=FirebaseAuth.getInstance();
        remailId=findViewById(R.id.remail);
        rpassId=findViewById(R.id.rpassword);
        rrpassId=findViewById(R.id.rpassword1);
        registerId=findViewById(R.id.register);
        remailLayout=findViewById(R.id.remail_layout);
        rpassLayout=findViewById(R.id.rpass_layout);
        rrpassLayout=findViewById(R.id.rrpass_layout);
    }

    public void RegisterUser(View view) {
        String registeremail=remailId.getText().toString();
        String registerpass=rpassId.getText().toString();

        if(registeremail.isEmpty() && registerpass.isEmpty()){
            remailLayout.setError("Email Cannot Be Empty");
            rpassLayout.setError("Password Cannot be Empty");
            rrpassLayout.setError("Password Cannot be Empty");
            remailLayout.requestFocus();
        }
       else if(registeremail.isEmpty()){
            remailLayout.setError("Email Cannot Be Empty");
            remailLayout.requestFocus();
        }
        else if(registerpass.isEmpty()){
            rpassLayout.setError("Password Cannot be Empty");
            rpassLayout.requestFocus();
        }
        else if(!(registeremail.isEmpty() && registerpass.isEmpty())){
            mAuth.createUserWithEmailAndPassword(registeremail,registerpass).addOnCompleteListener(RegistrationPage.this,
                    new OnCompleteListener<com.google.firebase.auth.AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<com.google.firebase.auth.AuthResult> task) {
                            if(!(task.isSuccessful())){
                                Toast.makeText(RegistrationPage.this, "Unsuccesfull !"+task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(RegistrationPage.this, "Account Created!",
                                        Toast.LENGTH_SHORT).show();
                                Goback();

                            }
                        }
                    });
        }

        else{
            Toast.makeText(RegistrationPage.this, "Error Occured",
                    Toast.LENGTH_SHORT).show();

        }
    }
    public void Goback(){
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);

    }

    public void Launch(View view) {
        Intent i = new Intent(this,MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}

