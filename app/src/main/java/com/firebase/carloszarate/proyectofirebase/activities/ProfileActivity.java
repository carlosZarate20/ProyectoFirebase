package com.firebase.carloszarate.proyectofirebase.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.carloszarate.proyectofirebase.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    TextView txtUser, txtName, txtLastName, txtEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtUser = (TextView) findViewById(R.id.textUser);
        txtName = (TextView) findViewById(R.id.textName);
        txtLastName = (TextView) findViewById(R.id.textLastName);
        txtEmail = (TextView) findViewById(R.id.textEmail);

        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        setDataToView(user);
    }

    public void setDataToView(FirebaseUser user){

        txtEmail.setText(user.getEmail());
    }
}
