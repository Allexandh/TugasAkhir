package umn.ac.id.tugasakhir;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;

public class DestinationFragment extends Fragment {
    View v;
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;
    ImageView anotherImageView;
    private FragmentActivity myContext;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_destination, container,false);

        //FragmentManager fragManager = myContext.getSupportFragmentManager();

        tabLayout = v.findViewById(R.id.destination_tab_layout);
        viewPager = v.findViewById(R.id.destination_view_pager);
        ViewPageAdapter adapter = new ViewPageAdapter(getChildFragmentManager());
        adapter.AddFragment(new JawaFragment(),"Jawa");
        adapter.AddFragment(new BaliFragment(),"Bali");
        adapter.AddFragment(new SumatraFragment(),"Sumatra");
        adapter.AddFragment(new KalimantanFragment(),"Kalimantan");
        adapter.AddFragment(new NusaTenggaraFragment(),"NusaTenggara");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        anotherImageView = v.findViewById(R.id.anotherImageView);

        anotherImageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Toast.makeText(getActivity(), "Nicee", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getActivity(), DestinationActivity.class);
                i.putExtra("id","c9Po86pCHcONhacxySzZ");
                startActivity(i);
            }
        });





//        Intent i = new Intent(getActivity(), Test.class);
//        startActivity(i);


        return v;

    }
}
