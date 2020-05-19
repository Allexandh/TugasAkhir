package umn.ac.id.tugasakhir;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import java.util.List;

public class DestinationAdapter extends PagerAdapter {

    Context context;
    List<Destination> destinationList;
    LayoutInflater inflater;
    String id;

    public DestinationAdapter(Context context, List<Destination> destinationList){
        this.context = context;
        this.destinationList = destinationList;
        inflater = LayoutInflater.from(context);
        //Log.d("TESTING","TERBUAT adapter");
    }

    @Override
    public int getCount() {
        //Log.d("TESTING","TERBUAT count");
        return destinationList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        //Log.d("TESTING","TERBUAT viewfromobject");
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object){
        //Log.d("TESTING","TERBUAT destroy");
        ((ViewPager)container).removeView((View)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position){
        final View view = inflater.inflate(R.layout.view_pager_item,container,false);

        //Log.d("TESTING","TERBUAT instantiate");
        ImageView destImg = (ImageView)view.findViewById(R.id.imageItem);
        TextView destText = (TextView)view.findViewById(R.id.textItem);

//        String idd = destinationList.get(position).getId();
//        Log.d("TESTING","TERBUAT instantiate"+idd);

        destImg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Toast.makeText(context, destinationList.get(position).getNama(), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(context, DestinationActivity.class);
                i.putExtra("id",destinationList.get(position).getId());
                context.startActivity(i);
            }
        });


        Picasso.with(context).load(destinationList.get(position).getImgurl()).into(destImg);
        destText.setText(destinationList.get(position).getNama());

        container.addView(view);
        return view;
    }
}
