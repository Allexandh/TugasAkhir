package umn.ac.id.tugasakhir;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements IFireStoreLoadDone {
    View v;
    TextView tvNama,textOne,textTwo,textThree;
    ImageView imgOne, imgTwo,imgThree;
    public FirebaseFirestore db = FirebaseFirestore.getInstance();
    ArrayList<String> deskripsiDestination = new ArrayList<String>();
    ArrayList<String> imgUrlDestination = new ArrayList<String>();
    ArrayList<String> namaDestination = new ArrayList<String>();
    ArrayList<String> subgrupDestination = new ArrayList<String>();
    ArrayList<String> idDestination = new ArrayList<String>();
    CarouselView carouselView;

    /////
    ViewPager viewPager;
    DestinationAdapter destinationAdapter;

    IFireStoreLoadDone iFireStoreLoadDone;
    CollectionReference dest;

    MainActivity main;



    public String test="before";
    private FirebaseFirestore mFirebaseFirestore = FirebaseFirestore.getInstance();
    String userid;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_home, container,false);
        tvNama = v.findViewById(R.id.tvNamaUser);
        imgOne = v.findViewById(R.id.imgOne);
        imgTwo = v.findViewById(R.id.imgTwo);
        imgThree = v.findViewById(R.id.imgThree);

        textOne = v.findViewById(R.id.textOne);
        textTwo = v.findViewById(R.id.textTwo);
        textThree = v.findViewById(R.id.textThree);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
        if(user!=null){

            userid = fauth.getCurrentUser().getUid();
            DocumentReference documentReference = fstore.collection("users").document(userid);
            documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                    //Toast.makeText(MainActivity.this, documentSnapshot.getString("email"),Toast.LENGTH_SHORT).show();
                    tvNama.setText("Hello, "+documentSnapshot.getString("name"));
                }
            });

        }

        iFireStoreLoadDone = this;
        dest= FirebaseFirestore.getInstance().collection("destination");

        viewPager = v.findViewById(R.id.view_pager);
        getData();
//
//        carouselView = v.findViewById(R.id.carousel);
//        //carouselView.setPageCount(mImages.length);
//        carouselView.setPageCount(3);
//        carouselView.setImageListener(new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
//                //imageView.setImageResource(mImages[position]);
//                Picasso.with(getActivity()).load("https://firebasestorage.googleapis.com/v0/b/cultour-1a8d2.appspot.com/o/destination%2F71592fc887745eebcbabc29e79765b8d.jpg?alt=media&token=e3ce24b4-6e24-4666-a573-10fd14d9f15c").into(imageView);
//            }
//        });
//        carouselView.setImageClickListener(new ImageClickListener() {
//            @Override
//            public void onClick(int position) {
//                Toast.makeText(getActivity(), mImagesTitle[position], Toast.LENGTH_SHORT).show();
//            }
//        });
        //Log.d("TESTING","asdasd");

        db.collection("destination")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            Integer n=0;
                            //test = "setelah";
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                deskripsiDestination.add(document.get("deskripsi").toString());
                                imgUrlDestination.add(document.get("imgurl").toString());
                                namaDestination.add(document.get("nama").toString());
                                subgrupDestination.add(document.get("subgrup").toString());
                                idDestination.add(document.getId());
                                //Log.w("TESTING", "getting "+namaDestination.get(n));
                                n++;
                            }
                            Picasso.with(getActivity()).load(imgUrlDestination.get(0)).into(imgOne);
                            Picasso.with(getActivity()).load(imgUrlDestination.get(1)).into(imgTwo);
                            //Picasso.with(getActivity()).load(imgUrlDestination.get(2)).into(imgThree);



                            textOne.setText(namaDestination.get(0));
                            textTwo.setText(namaDestination.get(1));
                            //textThree.setText(namaDestination.get(2));

                            imgOne.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    //Toast.makeText(getActivity(), "Nicee", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getActivity(), DestinationActivity.class);
                                    i.putExtra("id",idDestination.get(0));
                                    startActivity(i);
                                }
                            });

                            imgTwo.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {
                                    //Toast.makeText(getActivity(), "Nicee", Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(getActivity(), DestinationActivity.class);
                                    i.putExtra("id",idDestination.get(1));
                                    startActivity(i);
                                }
                            });

                        } else {
                            Log.w("TESTING", "Error getting documents.", task.getException());
                        }
                    }
                });





        imgThree.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                Intent i = new Intent(getActivity(), MoveFragmentActivity.class);
//                startActivity(i);
                Fragment someFragment = new DestinationFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, someFragment ); // give your fragment container id in first parameter
                transaction.addToBackStack(null);  // if written, this transaction will be added to backstack
                transaction.commit();
                //Toast.makeText(getActivity(), "PINDAH>>>", Toast.LENGTH_SHORT).show();
            }
        });


        //Log.d("TESTING","test 2: "+test);




        return v;
    }







    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    public void getData(){
        dest.get()
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        iFireStoreLoadDone.onFireStoreLoadFailed(e.getMessage());
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>(){
                   @Override
                   public void onComplete(@NonNull Task<QuerySnapshot>task){
                       if (task.isSuccessful()) {
                           //Log.d("TESTING","MASUK");
                           List<Destination> destination = new ArrayList<>();
                           String id;
                           for (QueryDocumentSnapshot document : task.getResult()) {
                               Destination destinations = document.toObject(Destination.class);
                               id = document.getId();
                               destinations.setId(id);
                               //Log.d("TESTING",   " GET DATA => " + document.getData());

                               destination.add(destinations);
                           }
                           iFireStoreLoadDone.onFireStoreLoadSuccess(destination);
                       } else {
                           Log.w("TESTING", "Error getting documents.", task.getException());
                       }
                   }
                });
    }

    @Override
    public void onFireStoreLoadSuccess(List<Destination> destination) {
        //Log.d("TESTING","TERBUAT success");
        destinationAdapter = new DestinationAdapter(getActivity(),destination);
        viewPager.setAdapter(destinationAdapter);
    }

    @Override
    public void onFireStoreLoadFailed(String message) {
        Log.d("TESTING","U FAILED");
    }
}
