package com.mehboob.crypto.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;

import com.mehboob.crypto.R;
import com.mehboob.crypto.ui.models.Slider;
import com.smarteist.autoimageslider.SliderViewAdapter;

import java.util.ArrayList;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderViewHolder> {

    private ArrayList<Slider> list;
    private Context context;


    public SliderAdapter(ArrayList<Slider> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public SliderViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slider_layout, parent,false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SliderViewHolder viewHolder, int position) {

        Slider slider=     list.get(position);


        Glide.with(context)
                .load(slider.getImageUrl())
                .into(viewHolder.imageView);

        viewHolder.itemView.setOnClickListener(v -> {


            // Toast.makeText(context, ""+slider.getImageUrl(), Toast.LENGTH_SHORT).show();
            CustomTabsIntent.Builder customIntent = new CustomTabsIntent.Builder();

            // below line is setting toolbar color
            // for our custom chrome tab.
            customIntent.setToolbarColor(ContextCompat.getColor(context, R.color.colorPrimary));

            // we are calling below method after
            // setting our toolbar color.

openCustomTab(context.getApplicationContext(),customIntent.build(),Uri.parse(slider.getImageLink()));

//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(slider.getImageLink()));
//            context.startActivity(i);
        });

    }


    public static void openCustomTab(Context activity, CustomTabsIntent customTabsIntent, Uri uri) {
        // package name is the default package
        // for our custom chrome tab
        String packageName = "com.android.chrome";
        if (packageName != null) {

            // we are checking if the package name is not null
            // if package name is not null then we are calling
            // that custom chrome tab with intent by passing its
            // package name.
            customTabsIntent.intent.setPackage(packageName);
            customTabsIntent.intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // in that custom tab intent we are passing
            // our url which we have to browse.
            customTabsIntent.launchUrl(activity, uri);
        } else {
            // if the custom tabs fails to load then we are simply
            // redirecting our user to users device default browser.
            activity.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        }
    }
    @Override
    public int getCount() {
        return list.size();
    }

    public class SliderViewHolder extends ViewHolder {
        ImageView imageView;

        public SliderViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_auto_image_slider);
        }
    }
}