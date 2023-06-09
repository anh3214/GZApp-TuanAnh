package com.example.gztruyen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.example.gztruyen.adapters.ApiAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private Context context2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        context2 = this;
        //createNewUser("test1@gmail.com","123456");
        login("test1@gmail.com","123456");
        //postData();
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    private void login(String email, String password){
        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.getIdToken(true).addOnCompleteListener(idTokenTask -> {
                                if (idTokenTask.isSuccessful()) {
                                    mAuth.getCurrentUser().getIdToken(true)
                                            .addOnSuccessListener(result -> {
                                                String token = result.getToken();
                                                SharedPreferences mPrefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
                                                SharedPreferences.Editor prefsEditor = mPrefs.edit();
                                                prefsEditor.putString("token", token);
                                                prefsEditor.apply();
                                                Intent intent = new Intent(context2,Home.class);
                                                context2.startActivity(intent);
                                            })
                                            .addOnFailureListener(e -> {
                                                // Lấy token thất bại
                                            });
                                } else {
                                    //Toast.makeText(LoginActivity.this, "Failed to get ID token", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            //Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void createNewUser(String newUser, String newPassword){
        mAuth.createUserWithEmailAndPassword(newUser,newPassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("Debug", "Create done");
                        }else{
                            Log.d("Debug","Create false");
                        }
                    }
                });
    }
    private void resetPass(String email){
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("Debug", "Reset done");
                        }else{
                            Log.d("Debug","Reset false");
                        }
                    }
                });
    }
    private void signOut(){
        mAuth.signOut();
    }
    private void postData(){
        Map<String, Object> user = new HashMap<>();
        user.put("first", "Alan");
        user.put("middle", "Mathison");
        user.put("last", "Turing");
        user.put("born", 1912);

        // Add a new document with a generated ID
        db.collection("users")
                .add(user)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("Debug", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("Debug", "Error adding document", e);
                    }
                });
    }
    private void getData(){
        db.collection("users").document()
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Log.d("Debug", "DocumentSnapshot data: " + document.getData());
                            } else {
                                Log.d("Debug", "No such document");
                            }
                        } else {
                            Log.d("Debug", "get failed with ", task.getException());
                        }
                    }
                });
    }

}