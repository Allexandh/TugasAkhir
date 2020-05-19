package umn.ac.id.tugasakhir;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.squareup.picasso.Picasso;

import javax.annotation.Nullable;


public class ChatAdapter extends FirestoreRecyclerAdapter<Chat, ChatAdapter.ChatHolder> {
    Context context;
    Integer n=0;
    String name;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ChatAdapter(@NonNull FirestoreRecyclerOptions<Chat> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatHolder holder, int position, @NonNull Chat model) {
        n++;
        //Log.d("chattest","on bind view holder"+n);

//        FirebaseFirestore fstore = FirebaseFirestore.getInstance();
//        DocumentReference documentReference = fstore.collection("users").document(model.getUserid());
//        documentReference.addSnapshotListener(View, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                //Toast.makeText(MainActivity.this, documentSnapshot.getString("email"),Toast.LENGTH_SHORT).show();
//                name=documentSnapshot.getString("name");
//            }
//        });

        //holder.tvItemNama.setText(name);
        holder.tvItemMessage.setText((model.getMessage()));
    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.d("chattest","on create view holder");
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        return new ChatHolder(v);
    }



    class ChatHolder extends RecyclerView.ViewHolder{
        TextView tvItemNama;
        TextView tvItemMessage;


        public ChatHolder(View itemView){
            super(itemView);
            //Log.d("chattest","chat holder");
            //tvItemNama=itemView.findViewById(R.id.tvItemNama);
            tvItemMessage=itemView.findViewById(R.id.tvItemMessage);
        }
    }



}
