package umn.ac.id.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.transition.FragmentTransitionSupport;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import javax.annotation.Nullable;

import umn.ac.id.tugasakhir.ui.destination.DestinationFragment;

public class DestinationActivity extends AppCompatActivity {
    String idDestination;
    String ids, btnOrang1,btnOrang2;

    TextView destNama, destDeskripsi, guideNama1, guideNama2, guideHarga1, guideHarga2;
    ImageView destImg,guideImg1, guideImg2;
    Button btnViewGuide;
    LinearLayout orang1,orang2;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination);

        destNama = findViewById(R.id.destNama);
        destDeskripsi = findViewById(R.id.destDeskripsi);
        guideNama1 = findViewById(R.id.guideNama1);
        guideNama2 = findViewById(R.id.guideNama2);
        guideHarga1 = findViewById(R.id.guideHarga1);
        guideHarga2 = findViewById(R.id.guideHarga2);

        destImg = findViewById(R.id.destImg);
        guideImg1 = findViewById(R.id.guideImg1);
        guideImg2 = findViewById(R.id.guideImg2);

        orang1 = findViewById(R.id.orang1);
        orang2 = findViewById(R.id.orang2);

        btnViewGuide = findViewById(R.id.btnViewGuide);

        Intent intent = getIntent();
        idDestination = intent.getStringExtra("id");

        btnViewGuide.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(DestinationActivity.this, "Satu "+btnOrang1, Toast.LENGTH_SHORT).show();
                Intent i = new Intent(DestinationActivity.this, GuidelistActivity.class);
                i.putExtra("id",idDestination);
                startActivity(i);
            }
        });



        //Log.d("TESTING","asd: "+idDestination);

        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        DocumentReference documentReference = fstore.collection("destination").document(idDestination);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                //Toast.makeText(MainActivity.this, documentSnapshot.getString("email"),Toast.LENGTH_SHORT).show();
                destNama.setText("Destination : "+ documentSnapshot.getString("nama"));
                destDeskripsi.setText(documentSnapshot.getString("deskripsi"));
                Picasso.with(DestinationActivity.this).load(documentSnapshot.getString("imgurl")).into(destImg);
                Log.d("TESTING","test"+ documentSnapshot.getString("nama"));

            }
        });

        db.collection("guidelist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Integer n =0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //String idd = document.get("id").toString();
                                String destination = document.get("destination").toString();
                                //Log.d("TESTING",document.get("harga").toString());
                                ids = document.getId();

                                if(idDestination.equals(destination) && n ==0){
                                    btnOrang1 = document.getId();
                                    guideNama1.setText(document.get("nama").toString());
                                    guideHarga1.setText(document.get("harga").toString()+"/hari");
                                    Picasso.with(DestinationActivity.this).load(document.get("imgurl").toString()).into(guideImg1);
                                    orang1.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            //Toast.makeText(DestinationActivity.this, "Satu "+btnOrang1, Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(DestinationActivity.this, GuideprofileActivity.class);
                                            i.putExtra("id",btnOrang1);
                                            startActivity(i);
                                        }
                                    });
                                    n++;
                                }else if(idDestination.equals(destination) && n == 1){
                                    btnOrang2 = document.getId();
                                    guideNama2.setText(document.get("nama").toString());
                                    guideHarga2.setText(document.get("harga").toString()+"/hari");
                                    Picasso.with(DestinationActivity.this).load(document.get("imgurl").toString()).into(guideImg2);

                                    orang2.setOnClickListener(new View.OnClickListener() {
                                        public void onClick(View v) {
                                            //Toast.makeText(DestinationActivity.this, "Duaa "+btnOrang2, Toast.LENGTH_SHORT).show();
                                            Intent i = new Intent(DestinationActivity.this, GuideprofileActivity.class);
                                            i.putExtra("id",btnOrang2);
                                            startActivity(i);
                                        }
                                    });
                                    n++;
                                }
                            }

                        } else {
                            //Log.d("TESTING", "Error getting documents: ", task.getException());
                        }
                    }
                });


    }
}
