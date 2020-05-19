package umn.ac.id.tugasakhir;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Toast;

public class MoveFragmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_fragment);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fg = new DestinationFragment();
        fragmentTransaction.replace(R.id.fragment_container, fg);
        fragmentTransaction.commit();


        Toast.makeText(MoveFragmentActivity.this, "Nicee", Toast.LENGTH_SHORT).show();
    }
}
