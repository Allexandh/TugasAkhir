package umn.ac.id.tugasakhir.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import umn.ac.id.tugasakhir.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private int[] mImages = new int[] {
            R.drawable.bali, R.drawable.baliguide, R.drawable.surabaya
    };

    private String[] mImagesTitle = new String[] {
            "Bali", "Pantai Bali", "Gunung Bromo"
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        //
        /////////carousel
        CarouselView carouselView = root.findViewById(R.id.carousel);
        carouselView.setPageCount(mImages.length);
        carouselView.setImageListener(new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                imageView.setImageResource(mImages[position]);
            }
        });
        carouselView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(), mImagesTitle[position], Toast.LENGTH_SHORT).show();
            }
        });

//        View view1 = inflater.inflate(R.layout.fragment_home, container,    false);
//        TextView tv = (TextView) view1.findViewById(R.id.tvNamaUser);
//        tv.setText("HELLO GUYS");


        return root;
    }
}