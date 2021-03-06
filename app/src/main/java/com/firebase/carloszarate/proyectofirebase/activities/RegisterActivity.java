package com.firebase.carloszarate.proyectofirebase.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.carloszarate.proyectofirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText txtUser;
    private EditText txtPassword;
    private EditText txtEmail;
    private EditText txtName;
    private EditText txtLastName;
    private Button btnRegister, btnCancel;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        txtUser = (EditText) findViewById(R.id.editUsuario);
        txtPassword = (EditText) findViewById(R.id.editContraseña);
        txtEmail = (EditText) findViewById(R.id.editEmail);
        txtName = (EditText) findViewById(R.id.editNombre);
        txtLastName = (EditText) findViewById(R.id.editApellido);
        btnRegister = (Button) findViewById(R.id.buttonRegister);
        btnCancel = (Button) findViewById(R.id.btnCancel);

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String password = txtPassword.getText().toString().trim();
                final String email = txtEmail.getText().toString().trim();
                final String user = txtUser.getText().toString().trim();
                final String name = txtName.getText().toString().trim();
                final String lastName = txtLastName.getText().toString().trim();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(lastName)
                        || TextUtils.isEmpty(user) || TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(), "Por favor llene todos los campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if(!task.isSuccessful()){

                                    if (!isEmailValid(email) && !isPasswordValid(password)) {
                                        txtEmail.setError(getString(R.string.error_email_val));
                                        txtPassword.setError(getString(R.string.error_passowd_num));
                                    }else if(!isEmailValid(email)){
                                        txtEmail.setError(getString(R.string.errorLogin_Email));
                                    } else if (!isPasswordValid(password) ) {
                                        txtPassword.setError(getString(R.string.errorLogin_Password));
                                    }else {
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();
                                    }
                                }else{
                                    //Registrar usuario
                                    String user_id = firebaseAuth.getCurrentUser().getUid();
                                    DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);

                                    /*String user = txtUser.getText().toString();
                                    String name = txtName.getText().toString();
                                    String lastName = txtLastName.getText().toString();*/

                                    Map newPost = new HashMap();
                                    newPost.put("name",name);
                                    newPost.put("lastName",lastName);
                                    newPost.put("user", user);

                                    current_user_db.setValue(newPost);

                                    startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                    progressDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Registro:  " + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            }
                        });
            }
        });
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }
    private void showProcessDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Registro");
        progressDialog.setMessage("Registrando una nueva Cuenta...");
        progressDialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
