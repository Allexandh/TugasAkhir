package umn.ac.id.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import umn.ac.id.tugasakhir.Database.UserDao;
import umn.ac.id.tugasakhir.Database.UserDatabase;
import umn.ac.id.tugasakhir.Models.User;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText edtName;
    private EditText edtLastName;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtNomorTelepon;

    String name;
    String nomortelepon;
    String email;

    private Button btCancel;
    private Button btRegister;

    private UserDao userDao;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
//        progressDialog = new ProgressDialog(this);
//        progressDialog.setCancelable(false);
//        progressDialog.setMessage("Registering...");
//        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//        progressDialog.setProgress(0);


        edtName = findViewById(R.id.name);
        //edtLastName = findViewById(R.id.lastname);
        edtNomorTelepon = findViewById(R.id.nomortelepon);
        edtEmail = findViewById(R.id.email);
        edtPassword = findViewById(R.id.password);

        btCancel = findViewById(R.id.cancel);
        btRegister = findViewById(R.id.register);

//        userDao = Room.databaseBuilder(this, UserDatabase.class, "mi-database.db")
//                .allowMainThreadQueries()
//                .build()
//                .getUserDao();

        btCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });




        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                name = edtName.getText().toString();
                nomortelepon = edtNomorTelepon.getText().toString();

                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(/*this,*/ new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {

                                    FirebaseAuth fauth = FirebaseAuth.getInstance();
                                    FirebaseFirestore fstore = FirebaseFirestore.getInstance();
                                    String userid = fauth.getCurrentUser().getUid();
                                    DocumentReference documentReference = fstore.collection("users").document(userid);
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("name", name);
                                    user.put("nomortelepon", nomortelepon);
                                    user.put("email", email);

                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(RegisterActivity.this, "Authentication Success",
                                                    Toast.LENGTH_SHORT).show();

                                        }
                                    });


                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(RegisterActivity.this, "Authentication failed",
                                            Toast.LENGTH_SHORT).show();
                                    //updateUI(null);
                                }

                                // ...
                            }
                        });

//                if (!isEmpty()) {
//
//                    progressDialog.show();
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            //Toast.makeText(RegisterActivity.this, "Tests", Toast.LENGTH_SHORT).show();
//                            User user = new User(edtName.getText().toString(), edtLastName.getText().toString(),
//                                    edtEmail.getText().toString(), edtPassword.getText().toString());
//                            userDao.insert(user);
//                            progressDialog.dismiss();
//                            startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
//                        }
//                    }, 1000);
//
//                } else {
//                    Toast.makeText(RegisterActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
//                }
            }
        });
    }

    private boolean isEmpty() {
        if (TextUtils.isEmpty(edtEmail.getText().toString()) ||
                TextUtils.isEmpty(edtPassword.getText().toString()) ||
                TextUtils.isEmpty(edtName.getText().toString())
        ) {
            return true;
        } else {
            return false;
        }
    }
}
