package com.example.dtbdemo.firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.dtbdemo.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class UserRepository {

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore;

    public void register(String email, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> {
                    String uid = authResult.getUser().getUid();
                    // Sau đó lưu thông tin người dùng vào Firestore
                    User user = new User(uid, email);
                    firestore.getInstance().collection("users").document(uid).set(user);
                });

    }

    private void login(String email, String pass) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            Log.d("DEBUG", "Login sucessful");
                        }
                        else {
                            Log.d("DEBUG", "login fail");
                        }
                    }
                });
    }
}
