package com.firebase.carloszarate.proyectofirebase.activities;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.carloszarate.proyectofirebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    TextView txtUser, txtName, txtLastName, txtEmail;
    ImageView imagePhoto;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtUser = (TextView) findViewById(R.id.textUser);
        txtName = (TextView) findViewById(R.id.textName);
        txtLastName = (TextView) findViewById(R.id.textLastName);
        txtEmail = (TextView) findViewById(R.id.textEmail);
        imagePhoto = (ImageView) findViewById(R.id.user_photo);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);

        authListener = new FirebaseAuth.AuthStateListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

            }
        };
    }

    public void setDataToView(FirebaseUser user){

        txtEmail.setText("User email: " + user.getEmail());
        txtName.setText("User name: " + user.getDisplayName());
        txtLastName.setText("User lastName: " + user.getDisplayName());
        txtUser.setText("User id: " + user.getUid());

    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
