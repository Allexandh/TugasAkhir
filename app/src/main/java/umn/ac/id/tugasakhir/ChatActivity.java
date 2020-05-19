package umn.ac.id.tugasakhir;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ChatActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference chatref = db.collection("chat");
    private ChatAdapter adapter;
    String userid, guideid;
    EditText messageEdt;
    //String message=null;
    Integer largeNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        FirebaseAuth fauth = FirebaseAuth.getInstance();
        userid = fauth.getCurrentUser().getUid();

        Intent intent = getIntent();
        guideid = intent.getStringExtra("guideid");

        messageEdt = findViewById(R.id.messageEdt);


        //Log.d("chattest","---"+userid+"---"+guideid);


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();

                db.collection("chat")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    largeNumber=1;
                                    Integer order=1;
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //Log.d("chattest","-----------");
                                        if(document.exists()){
                                            //Log.d("chattest","DATA: "+document.getData());
                                            if(document.get("guideid").toString().equals(guideid)
                                                    && document.get("userid").toString().equals(userid)){
                                                order=Integer.parseInt(document.get("order").toString());
                                                //Log.d("chattest","data masuk");
                                                if(order >largeNumber){
                                                    largeNumber = order;
                                                }
                                            }
                                        }else{
                                            Log.d("TESTING","MASUK doesn't exist");
                                        }
                                    }
                                    String message = null;
                                    message = messageEdt.getText().toString();
                                    if(!message.equals(null)){
                                        //Log.d("chattest","DATA TERINPUT: "+message);
                                        messageEdt.setText("");
                                        CollectionReference newchatref = FirebaseFirestore.getInstance().collection("chat");
                                        newchatref.add(new Chat(guideid,userid,message,largeNumber+1));
                                    }

                                } else {
                                    //Log.d("TESTING", "Error getting documents: ", task.getException());
                                }
                            }
                        });

            }

        });
        setUpRecycleView();
    }

    private void setUpRecycleView(){
        Query query = chatref.whereEqualTo("guideid",guideid).whereEqualTo("userid",userid).orderBy("order",Query.Direction.ASCENDING);
        //Query query = db.collection("guidelist");

        FirestoreRecyclerOptions<Chat> options = new FirestoreRecyclerOptions.Builder<Chat>()
                .setQuery(query, Chat.class)
                .build();
        //Log.d("chattest","DATA: "+options);

        adapter = new ChatAdapter(options);


        //Log.d("chattesting","masuk sini sih");

        RecyclerView recyclerView = findViewById(R.id.chat_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //Toast.makeText(GuidelistActivity.this, "Berhasil: "+recyclerView, Toast.LENGTH_SHORT).show();


    }


    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }


}
