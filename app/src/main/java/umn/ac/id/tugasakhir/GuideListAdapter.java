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
import com.google.firebase.firestore.DocumentSnapshot;
import com.squareup.picasso.Picasso;


public class GuideListAdapter extends FirestoreRecyclerAdapter<GuideList, GuideListAdapter.GuideListHolder> {
    private OnItemClickListener listener;
    Context context;
    /**
     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
     * FirestoreRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public GuideListAdapter(@NonNull FirestoreRecyclerOptions<GuideList> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull GuideListHolder holder, int position, @NonNull GuideList model) {
        holder.tvNama.setText(model.getNama());
        holder.tvNomorTelepon.setText(model.getNomortelepon());
        Picasso.with(context).load(model.getImgurl()).into(holder.guideListImage);
        //Log.d("TESTING","data: "+model.getNama());
    }

    @NonNull
    @Override
    public GuideListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.guidelist_item,parent,false);
        return new GuideListHolder(v);
    }

    class GuideListHolder extends RecyclerView.ViewHolder{
        TextView tvNama;
        TextView tvNomorTelepon;
        ImageView guideListImage;


        public GuideListHolder(View itemView){
            super(itemView);
            tvNama=itemView.findViewById(R.id.tvItemNama);
            tvNomorTelepon=itemView.findViewById(R.id.tvItemNomorTelepon);
            guideListImage = itemView.findViewById(R.id.guideListImage);
            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){

                    int position = getAdapterPosition();
                    if(position!= RecyclerView.NO_POSITION && listener!= null){
                        listener.onItemClick(getSnapshots().getSnapshot(position),position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListener{
        void onItemClick(DocumentSnapshot documentSnapshot, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

}
