package com.firebase.carloszarate.proyectofirebase.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.carloszarate.proyectofirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    private EditText textEmail;
    private EditText textPassword;
    private Button btnLogin, btnRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        textEmail = (EditText) findViewById(R.id.editCorreo);
        textPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.botonLogin);
        btnRegister = (Button) findViewById(R.id.botonRegistrar);

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Inicio de Session Correcto", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            }
        };

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = textEmail.getText().toString();
                final String password = textPassword.getText().toString();

                //Validacion de los campos
                if (TextUtils.isEmpty(password) || TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter email address and password!", Toast.LENGTH_SHORT).show();
                    return;

                }
                /*if (!TextUtils.isEmpty(password) && !isPasswordValid(password) ) {
                    textPassword.setError(getString(R.string.errorLogin_Password));
                    /*focusView = textPassword;
                    cancel = true;
                }
                if(!isEmailValid(email)){
                    textEmail.setError(getString(R.string.errorLogin_Email));
                }*/

                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(!task.isSuccessful()){
                            //Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                            if(!isEmailValid(email) && !isPasswordValid(password)){
                                textEmail.setError(getString(R.string.errorLogin_Email));
                                textPassword.setError(getString(R.string.errorLogin_Password));
                            } else if(!isEmailValid(email)){
                                textEmail.setError(getString(R.string.errorLogin_Email));
                            } else if (!isPasswordValid(password) ) {
                                textPassword.setError(getString(R.string.errorLogin_Password));
                            } else {
                                Toast.makeText(LoginActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                            }
                        }/*else {
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }*/
                    }
                });
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
