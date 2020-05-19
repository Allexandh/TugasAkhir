package umn.ac.id.tugasakhir;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class JawaFragment extends Fragment {
    View view;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference destinationref = db.collection("destination");
    private DestinationListAdapter adapter;

    public JawaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_jawa,container,false);
        setUpRecycleView();
        return view;
    }
    private void setUpRecycleView(){
        Query query = destinationref.whereEqualTo("subgrup","Jawa").orderBy("nama",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<Destination> options = new FirestoreRecyclerOptions.Builder<Destination>()
                .setQuery(query, Destination.class)
                .build();

        adapter = new DestinationListAdapter(options);

        RecyclerView recyclerView = view.findViewById(R.id.destination_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new DestinationListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot, int position) {
                //Log.d("TESTING ACTIVITY","MASUK");

                //GuideList guide = documentSnapshot.toObject(GuideList.class);
                String id = documentSnapshot.getId();
                //String path = documentSnapshot.getReference().getPath();
                //Toast.makeText(GuidelistActivity.this, "Position: "+position+" & ID: "+id, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), DestinationActivity.class);
                i.putExtra("id", id);
                //Toast.makeText(getActivity(), "This id: "+id, Toast.LENGTH_SHORT).show();
                startActivity(i);
            }
        });
    }
    @Override
    public void onStart(){
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


}
