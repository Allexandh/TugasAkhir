package umn.ac.id.tugasakhir;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class RatingBarDialog extends AppCompatDialogFragment {
    RatingBar ratingBarDialog;
    String rate, reviewText, title;
    EditText reviewDialog;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
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
                        rate = String.valueOf(ratingBarDialog.getRating());
                        reviewText = reviewDialog.getText().toString();

                        Float rateFloat= Float.parseFloat(rate);
                        Integer rating = Math.round(rateFloat);


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


                        Toast.makeText(getActivity(), "ratings: "+reviewText+rating, Toast.LENGTH_SHORT).show();

                        CollectionReference reviewref = FirebaseFirestore.getInstance().collection("review");
                        reviewref.add(new Review(reviewText, "asdas", title, rating));
                    }
                });
        return builder.create();
    }




}
