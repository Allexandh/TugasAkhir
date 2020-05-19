package umn.ac.id.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import javax.annotation.Nullable;

public class GuideprofileActivity extends AppCompatActivity {
    TextView tvNama, tvDeskripsi,tvAvgRating;
    String userid;
    Integer avgRating=0;
    Integer n =0;
    ImageView fotoguide;
    Button btnDatePicker, btnMessage;
    RatingBar ratingBar;
    LinearLayout ratingView;

    RatingBar ratingBarDialog;
    String rate, reviewText, title;
    EditText reviewDialog;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference reviewref = db.collection("review");
    private ReviewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guideprofile);

        tvNama = findViewById(R.id.namaGuide);
        tvDeskripsi = findViewById(R.id.deskripsiGuide);
        tvAvgRating = findViewById(R.id.tvAvgRating);

        fotoguide = findViewById(R.id.fotoguide);

        btnDatePicker = findViewById(R.id.btnDatePicker);
        btnMessage = findViewById(R.id.btnMessage);

        ratingView = findViewById(R.id.ratingView);

        Intent intent = getIntent();
        userid = intent.getStringExtra("id");
        //Toast.makeText(GuideprofileActivity.this, "ID: "+userid, Toast.LENGTH_SHORT).show();



        ratingView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

//                RatingBarDialog dialog = new RatingBarDialog();
//                dialog.show(getSupportFragmentManager(),"Rating Bar");
                //Toast.makeText(GuideprofileActivity.this, "ratings", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(GuideprofileActivity.this);
                LayoutInflater inflater = GuideprofileActivity.this.getLayoutInflater();
                View view = inflater.inflate(R.layout.ratingbar_dialog,null);
                ratingBarDialog = view.findViewById(R.id.ratingBarDialog);
                reviewDialog = view.findViewById(R.id.reviewDialog);

                builder.setView(view)
                        .setTitle("Rating")
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                rate = null;
                                reviewText = null;
                                rate = String.valueOf(ratingBarDialog.getRating());
                                reviewText = reviewDialog.getText().toString();


                                Float rateFloat= Float.parseFloat(rate);
                                Integer rating = Math.round(rateFloat);

                                if(rating == 0){
                                    Toast.makeText(GuideprofileActivity.this, "Rating Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
                                }else{
                                    if(rating == 1){
                                        title = "Bad";
                                    }else if(rating == 2){
                                        title = "Could be better";
                                    }else if(rating == 3){
                                        title = "Nice";
                                    }else if(rating == 4){
                                        title = "Awesome";
                                    }else if(rating == 5){
                                        title = "Awesome";
                                    }

                                    CollectionReference reviewref = FirebaseFirestore.getInstance().collection("review");
                                    reviewref.add(new Review(reviewText, userid, title, rating));
                                }

                            }
                        });

                builder.show();


            }
        });

        btnDatePicker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(GuideprofileActivity.this, "Masuk date picker", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(GuideprofileActivity.this, DatePickerActivity.class);
                i.putExtra("id", userid);
                startActivity(i);
            }
        });

        btnMessage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(GuideprofileActivity.this, "Masuk message", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(GuideprofileActivity.this, ChatActivity.class);
                i.putExtra("guideid", userid);
                startActivity(i);
            }
        });






        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fstore.collection("guidelist").document(userid);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                //Toast.makeText(MainActivity.this, documentSnapshot.getString("email"),Toast.LENGTH_SHORT).show();
                tvNama.setText(documentSnapshot.getString("nama"));
                tvDeskripsi.setText(documentSnapshot.getString("deskripsi"));
                Picasso.with(GuideprofileActivity.this).load(documentSnapshot.getString("imgurl")).into(fotoguide);
            }
        });

        //DocumentReference reviewReference = fstore.collection("review").document(userid);
        db.collection("review")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            String idd=null;
                            String iddd = userid;
                            String flag = null;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d("TESTING","MASUK document");
                                if(document.exists()){

                                    //Log.d("TESTING","MASUK exist");
                                    idd = document.get("id").toString();
                                    if(idd.equals(iddd)){
                                        avgRating += Integer.parseInt(document.get("rating").toString());
                                        n++;
                                        flag="1";
                                    }else{
                                    }
                                }else{

                                    Log.d("TESTING","MASUK doesn't exist");
                                }

                            }
                            if(flag!=null){
                                Float avg = (float)avgRating/(float)n;
                                avgRating = avgRating/n;
                                tvAvgRating.setText(String.format("%.2f", avg)+" out of 5");
                            }
                        } else {
                            //Log.d("TESTING", "Error getting documents: ", task.getException());
                        }
                    }
                });

//        DocumentReference reviewReference = fstore.collection("review").document(userid);
//        reviewReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                //Toast.makeText(MainActivity.this, documentSnapshot.getString("email"),Toast.LENGTH_SHORT).show();
//                tvNama.setText(documentSnapshot.getString("nama"));
//                tvDeskripsi.setText(documentSnapshot.getString("deskripsi"));
//            }
//        });






        setUpRecycleView(userid);
    }

    private void setUpRecycleView(String userid){
        Query query = reviewref.whereEqualTo("id",userid).orderBy("rating",Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<Review> options = new FirestoreRecyclerOptions.Builder<Review>()
                .setQuery(query, Review.class)
                .build();

        adapter = new ReviewAdapter(options);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

//    public void reviewrating(View view){
//        Toast.makeText(GuideprofileActivity.this, "BERIKAN KAMI RATING", Toast.LENGTH_SHORT).show();
//        Log.d("TESTING","BERIKAN KAMI RATE");
//    }

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
