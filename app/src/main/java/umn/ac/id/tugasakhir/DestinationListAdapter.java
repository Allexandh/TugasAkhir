package umn.ac.id.tugasakhir;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;


public class DestinationListAdapter extends FirestoreRecyclerAdapter<Destination, DestinationListAdapter.DestinationListHolder> {
    private OnItemClickListener listener;
//    /**
//     * Create a new RecyclerView adapter that listens to a Firestore Query.  See {@link
//     * FirestoreRecyclerOptions} for configuration options.
//     *
//     * @param options
//     */
    public DestinationListAdapter(@NonNull FirestoreRecyclerOptions<Destination> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull DestinationListHolder holder, int position, @NonNull Destination model) {
        holder.destination_nama.setText("• "+(model.getNama()));
    }

    @NonNull
    @Override
    public DestinationListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.destinationlist_item,parent,false);

        return new DestinationListHolder(v);
    }

    class DestinationListHolder extends RecyclerView.ViewHolder{
        TextView destination_nama;

        public DestinationListHolder(View itemView){
            super(itemView);
            destination_nama = itemView.findViewById(R.id.destination_nama);

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

    public void setOnItemClickListener(DestinationListAdapter.OnItemClickListener listener){
        this.listener = listener;
    }



}
