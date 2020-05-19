package umn.ac.id.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.Nullable;

public class DatePickerActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    TextView textAwalTanggal,textAkhirTanggal;
    Button btnAwalTanggal, btnAkhirTanggal, btnSubmit;
    String flag = null;
    String userid;

    String awal, akhir;
    Integer awalTanggal, awalBulan, awalTahun;
    Integer akhirTanggal, akhirBulan, akhirTahun;

    String book = "1";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_picker);

        textAwalTanggal = findViewById(R.id.textAwalTanggal);
        textAkhirTanggal = findViewById(R.id.textAkhirTanggal);

        btnAwalTanggal = findViewById(R.id.btnAwalTanggal);
        btnAkhirTanggal = findViewById(R.id.btnAkhirTanggal);

        btnSubmit = findViewById(R.id.btnSubmit);

        Intent intent = getIntent();
        userid = intent.getStringExtra("id");

        btnAwalTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "0";
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        btnAkhirTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = "1";
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        //dbAwalBulan
        //awalDataSubmit
        //dbAwalTahun



        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Toast.makeText(DatePickerActivity.this, "INI kah benar: "+userid, Toast.LENGTH_SHORT).show();
                db.collection("availabledates")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    Integer dbAwalTanggal,dbAwalBulan,dbAwalTahun;
                                    Integer dbAkhirTanggal,dbAkhirBulan,dbAkhirTahun;
                                    List<String> arr = new ArrayList<String>();
                                    book="1";
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //Log.d("datapicker",userid+" = "+document.get("guideid"));
                                        if(userid.equals(document.get("guideid"))){
                                            Log.d("datapicker","//////////////////");
                                            dbAwalTanggal = Integer.parseInt(document.get("awaltanggal").toString());
                                            dbAwalBulan = Integer.parseInt(document.get("awalbulan").toString());
                                            dbAwalTahun = Integer.parseInt(document.get("awaltahun").toString());

                                            dbAkhirTanggal = Integer.parseInt(document.get("akhirtanggal").toString());
                                            dbAkhirBulan = Integer.parseInt(document.get("akhirbulan").toString());
                                            dbAkhirTahun = Integer.parseInt(document.get("akhirtahun").toString());

                                            String curr;
                                            Log.d("datapicker","-------------");

                                            for(Integer n=0;dbAwalTanggal<=dbAkhirTanggal && dbAwalBulan <= dbAkhirBulan ||
                                                    dbAwalTanggal>dbAkhirTanggal && dbAwalBulan < dbAkhirBulan && book.equals("1");dbAwalTanggal++){
                                                if(dbAwalTanggal>31){
                                                    dbAwalTanggal=1;
                                                    dbAwalBulan++;
                                                    //Log.d("datapicker","ganti bulan");
                                                }

                                                if(dbAwalBulan>12){
                                                    dbAwalBulan=1;
                                                    dbAwalTahun=1;
                                                }
                                                curr = dbAwalTanggal+"/"+dbAwalBulan+"/"+dbAwalTahun;
                                                //Log.d("datapicker","----- "+curr);
                                                Log.d("datapicker","CHECK : "+curr+" = "+awal);
                                                Log.d("datapicker","CHECK : "+curr+" = "+akhir);
                                                if(curr.equals(awal) || curr.equals(akhir)){
                                                    book = "0";

                                                }

                                            }


                                        }
                                    }
                                    if(book.equals("1")){
                                        Toast.makeText(DatePickerActivity.this, "Guide berhasil di book",Toast.LENGTH_SHORT).show();
                                        CollectionReference guideRef = FirebaseFirestore.getInstance().collection("availabledates");
                                        guideRef.add(new Availabledates(userid,awalTanggal,awalBulan,awalTahun,akhirTanggal,akhirBulan,akhirTahun));
                                        finish();
                                    }else{
                                        Toast.makeText(DatePickerActivity.this, "Guide tidak dapat di book",Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Log.w("TESTING", "Error getting documents.", task.getException());
                                }
                            }
                        });

            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);



        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        //Toast.makeText(DatePickerActivity.this, "DATA: "+awalTanggal, Toast.LENGTH_SHORT).show();
        if(flag.equals("0")){
            awalTanggal= dayOfMonth;
            awalBulan = month+1;
            awalTahun= year;
            awal = awalTanggal+"/"+awalBulan+"/"+awalTahun;
            textAwalTanggal.setText(currentDateString);
        }else if(flag.equals("1")){
            akhirTanggal = dayOfMonth;
            akhirBulan = month+1;
            akhirTahun = year;
            akhir = akhirTanggal+"/"+akhirBulan+"/"+akhirTahun;
            textAkhirTanggal.setText(currentDateString);
        }
    }
}
