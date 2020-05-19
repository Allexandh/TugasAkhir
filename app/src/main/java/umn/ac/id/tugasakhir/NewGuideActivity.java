package umn.ac.id.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewGuideActivity extends AppCompatActivity {
    private EditText edNama,edEmail,edNomortelepon,edDeskripsi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_guide);

        edNama = findViewById(R.id.edtName);
        edEmail = findViewById(R.id.edtEmail);
        edNomortelepon = findViewById(R.id.edtNomorTelepon);
        edDeskripsi = findViewById(R.id.edtDeskripsi);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_guide_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.save_guide:
                saveGuide();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void saveGuide() {
        String nama = edNama.getText().toString();
        String email = edEmail.getText().toString()+"@gmail.com";
        String nomortelepon = edNomortelepon.getText().toString();
        String deskripsi = edDeskripsi.getText().toString();
        String destination = "oXirhFqycXM5Cb7zJ3nV";
        String imgurl = "https://firebasestorage.googleapis.com/v0/b/cultour-1a8d2.appspot.com/o/guidelist%2F1071625.jpg?alt=media&token=90ac5e58-4a4d-477f-b532-2c5230dceabf";
        Integer harga = 300000;

        CollectionReference guideRef = FirebaseFirestore.getInstance().collection("guidelist");
        guideRef.add(new GuideList(nama, email, nomortelepon, deskripsi, destination, imgurl, harga));
        Toast.makeText(this, "Guide Added",Toast.LENGTH_SHORT).show();
        finish();
    }

}
