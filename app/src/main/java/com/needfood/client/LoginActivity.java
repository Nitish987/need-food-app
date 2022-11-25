package com.needfood.client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private Button button;
    private TextView toSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() != null) {
            toHomeActivity();
        }

        email = findViewById(R.id.email);
        toSignup = findViewById(R.id.to_signup);
        password = findViewById(R.id.password);
        button = findViewById(R.id.login_btn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        button.setOnClickListener(view -> {
            String email = this.email.getText().toString();
            String password = this.password.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Fields are required.", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(email, password).addOnSuccessListener(authResult -> {
                    FirebaseMessaging.getInstance().getToken().addOnSuccessListener(s -> {
                        Map<String, Object> map = new HashMap<>();
                        map.put("msgToken", s);
                        FirebaseFirestore.getInstance().collection("user").document(FirebaseAuth.getInstance().getUid()).update(map).addOnSuccessListener(unused -> {
                            toHomeActivity();
                        }).addOnFailureListener(e -> {
                            Toast.makeText(this, "Unable to Login.", Toast.LENGTH_SHORT).show();
                            mAuth.signOut();
                        });
                    }).addOnFailureListener(e -> {
                        Toast.makeText(this, "Unable to Login.", Toast.LENGTH_SHORT).show();
                        mAuth.signOut();
                    });
                    toHomeActivity();
                }).addOnFailureListener(e -> {
                    Toast.makeText(this, "Unable to Login.", Toast.LENGTH_SHORT).show();
                });
            }
        });

        toSignup.setOnClickListener(view -> {
            Intent intent=new Intent(this,SignupActivity.class);
            startActivity(intent);
            finish();
        });
    }

    public void toHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}