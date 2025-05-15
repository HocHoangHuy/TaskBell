package com.example.dtbdemo;

import android.os.Bundle;

import com.example.dtbdemo.firestore.ReminderRepository;
import com.example.dtbdemo.firestore.TaskRepository;
import com.example.dtbdemo.model.Listt;
import com.example.dtbdemo.room.ListDAO;
import com.example.dtbdemo.room.ReminderDAO;
import com.example.dtbdemo.room.TaskDAO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.example.dtbdemo.model.Task;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.dtbdemo.databinding.ActivityMainBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import android.view.Menu;
import android.view.MenuItem;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    private FirebaseFirestore firestore;

    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);

       firestore = FirebaseFirestore.getInstance();


    }

    public void syncListFromFirestoreToRoom(Listt listtFromFirestore,
                                            ListDAO listtDao,
                                            TaskDAO taskDao,
                                            ReminderDAO reminderDao,
                                            TaskRepository taskRepository,
                                            ReminderRepository reminderRepository) {

        // 1. Lưu List vào Room
        Executors.newSingleThreadExecutor().execute(() -> {
            listtDao.insertList(listtFromFirestore);
        });

        // 2. Lấy tất cả Task từ Firestore
        taskRepository.getTasksByListId(String.valueOf(listtFromFirestore.getId()), tasks -> {
            if (tasks == null) return;

            // Lưu tất cả Task vào Room
            Executors.newSingleThreadExecutor().execute(() -> {
                taskDao.insertAll(tasks);
            });

            // 3. Với mỗi Task, lấy Reminder từ Firestore
            for (Task task : tasks) {
                reminderRepository.getReminderByTaskId(String.valueOf(task.getId()), reminders -> {
                    if (reminders != null && !reminders.isEmpty()) {
                        Executors.newSingleThreadExecutor().execute(() -> {
                            reminderDao.insertAll(reminders);
                        });
                    }
                });
            }
        });
    }


//    private FirebaseAuth mAuth;
//    private FirebaseDatabase database;
//    private DatabaseReference myRef;
//    private FirebaseFirestore firestore;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        mAuth = FirebaseAuth.getInstance();
//        database = FirebaseDatabase.getInstance();
//        myRef = database.getReference("message");
//        firestore = FirebaseFirestore.getInstance();
//
//        //login("test@gmail.com", "123456");
//        //createNewUser("newuser@gmail.com", "123456");
//       //postDataToRealTimeDB("hello");
//       //readDataFromRealTimeDB();
//        postDataToFirestore();
//        listenToFirestoreChanges();
//        //addPostData(new Post("Tuan Tran", "Android with Firebase"));
//        //addPostData(new Post("Test post", "I dont know"));
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//    }
//
//    private void login(String email, String pass) {
//        mAuth.signInWithEmailAndPassword(email, pass)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//
//                        if (task.isSuccessful()) {
//                            Log.d("DEBUG", "Login sucessful");
//                        }
//                        else {
//                            Log.d("DEBUG", "login fail");
//                        }
//                    }
//                });
//    }
//
//    private void createNewUser(String newUserEmail, String newUserPass)  {
//        mAuth.createUserWithEmailAndPassword(newUserEmail, newUserPass)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("DEBUG", "create new user sucessful");
//                        }
//                        else {
//                            Log.d("DEBUG", "create new user fail");
//                        }
//                    }
//                });
//    }
//
//    private void postDataToRealTimeDB(String data) {
//        myRef.setValue(data)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("DEBUG", "post data " + data + "sucessful");
//                        }
//                        else {
//                            Log.d("DEBUG", "post data fail");
//                        }
//                    }
//                });
//    }
//
//    private void readDataFromRealTimeDB() {
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String value = snapshot.getValue(String.class);
//                Log.d("DEBUG", "Value is: " + value);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                Log.w("DEBUG", "Failed to read value.", error.toException());
//            }
//        });
//    }
//
//    private void postDataToFirestore() {
//// Create a new user with a first and last name
//        Map<String, Object> user = new HashMap<>();
//        user.put("first", "Ada1");
//        user.put("last", "Lovelace");
//        user.put("born", 1815);
//
//// Add a new document with a generated ID
//        firestore.collection("users")
//                .add(user)
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Log.d("DEBUG", "DocumentSnapshot added with ID: " + documentReference.getId());
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Log.w("DEBUG", "Error adding document", e);
//                    }
//                });
//    }
//
//    private void listenToFirestoreChanges() {
//        firestore.collection("users")
//                .addSnapshotListener(new EventListener<QuerySnapshot>() {
//                    @Override
//                    public void onEvent(@Nullable QuerySnapshot value,
//                                        @Nullable FirebaseFirestoreException error) {
//                        if (error != null) {
//                            Log.w("DEBUG", "Listen failed.", error);
//                            return;
//                        }
//
//                        for (DocumentSnapshot doc : value) {
//                            if (doc.exists()) {
//                                Log.d("DEBUG", "Current data: " + doc.getData());
//                            }
//                        }
//                    }
//                });
//    }
//
//
//    public void addPostData(Post data) {
//        DatabaseReference myRefRoot = database.getReference();
//        myRefRoot.child("posts").setValue(data)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                        if (task.isSuccessful()) {
//                            Log.d("DEBUG", "post data sucessful");
//                        }
//                        else {
//                            Log.d("DEBUG", "post data fail");
//                        }
//                    }
//                });
//    }
}