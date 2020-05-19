package umn.ac.id.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class GuidelistActivity extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference guidelistref = db.collection("guidelist");
    private GuideListAdapter adapter;

    String idDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guidelist);

        Intent intent = getIntent();
        idDestination = intent.getStringExtra("id");
        //Log.d("TESTING id","get id: "+id);



//        FloatingActionButton btnAdd = findViewById(R.id.fab);
//        btnAdd.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                startActivity(new Intent(GuidelistActivity.this, NewGuideActivity.class));
//            }
//        });

        setUpRecycleView();
    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_search, menu);
//
//        MenuItem searchItem = menu.findItem(R.id.action_search);
//        return true;
//    }

    private void setUpRecycleView(){
        Query query = guidelistref.whereEqualTo("destination",idDestination).orderBy("nama",Query.Direction.DESCENDING);
        //Query query = db.collection("guidelist");

        FirestoreRecyclerOptions<GuideList> options = new FirestoreRecyclerOptions.Builder<GuideList>()
                .setQuery(query, GuideList.class)
                .build();

        adapter = new GuideListAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        //Toast.makeText(GuidelistActivity.this, "Berhasil: "+recyclerView, Toast.LENGTH_SHORT).show();

        adapter.setOnItemClickListener(new GuideListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //Log.d("TESTING ACTIVITY","MASUK");

                GuideList guide = documentSnapshot.toObject(GuideList.class);
                String id = documentSnapshot.getId();
                String path = documentSnapshot.getReference().getPath();
                //Toast.makeText(GuidelistActivity.this, "Position: "+position+" & ID: "+id, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(GuidelistActivity.this, GuideprofileActivity.class);
                i.putExtra("id", id);
                startActivity(i);

            }
        });

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
