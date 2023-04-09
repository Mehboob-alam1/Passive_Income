package com.mehboob.passiveincome.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.mehboob.passiveincome.R;

public class ViewPagerAdapter extends PagerAdapter {
    private Context ctx;


    public ViewPagerAdapter(Context context) {
        this.ctx = context;

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }



    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slider_layout, container, false);

        ImageView imageView;


        imageView = view.findViewById(R.id.img_slider);
        TextView heading = view.findViewById(R.id.txt_title);
        TextView desc = view.findViewById(R.id.textviewDescription);

        switch (position) {
            case 0:
                imageView.setImageResource(R.drawable.slider_img);
                heading.setText("Quick and Secure!");
                desc.setText("Deposit and withdraw Payments easily.\n" +
                        "We got you covered with industry\n" +
                        "standard security.");
                break;
            case 1:
                imageView.setImageResource(R.drawable.slider_img);
                heading.setText("Quick and Secure!");
                desc.setText("We provide good percentage with.\n" +
                        "your team ");
                break;
            case 2:
                imageView.setImageResource(R.drawable.slider_img);
                heading.setText("Quick and Secure!");
                desc.setText("Get bonus with every new member \n" +
                        "All the best!");
                break;

        }


        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {


        container.removeView((View) object);

    }
}