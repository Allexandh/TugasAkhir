package umn.ac.id.tugasakhir.ui.destination;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageClickListener;
import com.synnapps.carouselview.ImageListener;

import umn.ac.id.tugasakhir.R;

public class DestinationFragment extends Fragment {

    private DestinationViewModel destinationViewModel;
    private int[] mImages = new int[] {
            R.drawable.bali, R.drawable.baliguide, R.drawable.surabaya
    };

    private String[] mImagesTitle = new String[] {
            "Bali", "Pantai Bali", "Gunung Bromo"
    };

    ListView listView;

    private int[] listId = new int[] {
            R.drawable.trump, R.drawable.trump, R.drawable.trump, R.drawable.trump, R.drawable.trump
    };

    private String[] listName = new String[] {
            "Travel agen 1", "Travel 2", "Agen 3", "Trump", "Tour"
    };

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

//        destinationViewModel =
//                ViewModelProviders.of(this).get(DestinationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_destination, container, false);
//        final TextView textView = root.findViewById(R.id.text_destination);
//        destinationViewModel.getText().observe(this, new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        ////////Ini buat test button di fragment
        //Button testBtn = (Button)root.findViewById(R.id.testBtn);
//        testBtn.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View v)
//            {
//                Toast.makeText(getActivity(),"Test Button di Fragment Berhasil!",Toast.LENGTH_SHORT).show();
//            }
//        });

//        listView = root.findViewById(R.id.listtour);
//        CustomAdaptor customAdaptor = new CustomAdaptor();
//        listView.setAdapter(customAdaptor);
//
//
//        CarouselView carouselView = root.findViewById(R.id.carousel);
//        carouselView.setPageCount(mImages.length);
//        carouselView.setImageListener(new ImageListener() {
//            @Override
//            public void setImageForPosition(int position, ImageView imageView) {
//                imageView.setImageResource(mImages[position]);
//            }
//        });
//        carouselView.setImageClickListener(new ImageClickListener() {
//            @Override
//            public void onClick(int position) {
//                Toast.makeText(getActivity(), mImagesTitle[position], Toast.LENGTH_SHORT).show();
//            }
//        });


        return root;
    }

    class CustomAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return listId.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            View viewlist = getLayoutInflater().inflate(R.layout.listlayout, null);


            ImageView mImageView = (ImageView) viewlist.findViewById(R.id.imageView);
            TextView mTextView = (TextView) viewlist.findViewById(R.id.textView);

            mImageView.setImageResource(listId[i]);
            mTextView.setText(listName[i]);



            return viewlist;
        }
    }


}