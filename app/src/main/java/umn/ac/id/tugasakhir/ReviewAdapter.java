package umn.ac.id.tugasakhir;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class ReviewAdapter extends FirestoreRecyclerAdapter<Review, ReviewAdapter.ReviewHolder> {

//    /**
//     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
//     * FirestoreRecyclerOptions} for configuration options.
//     *
//     * @param options
//     */
    public ReviewAdapter(@NonNull FirestoreRecyclerOptions<Review> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull ReviewHolder holder, int position, @NonNull Review model) {
        holder.tvRating.setText(String.valueOf(model.getRating()));
        holder.tvTitle.setText(model.getTitle());
        holder.tvDeskripsi.setText(model.getDeskripsi());

    }

    @NonNull
    @Override
    public ReviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewlist_item,parent,false);
        return new ReviewHolder(v);
    }

    class ReviewHolder extends RecyclerView.ViewHolder{
        TextView tvRating;
        TextView tvTitle;
        TextView tvDeskripsi;
        TextView tvAvgRating;


        public ReviewHolder(View itemView){
            super(itemView);
            tvRating=itemView.findViewById(R.id.tvRating);
            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvDeskripsi=itemView.findViewById(R.id.tvDeskripsi);
            tvAvgRating=itemView.findViewById(R.id.tvAvgRating);

        }
    }



}
