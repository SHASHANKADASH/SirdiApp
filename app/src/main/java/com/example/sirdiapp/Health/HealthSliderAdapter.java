package com.example.sirdiapp.Health;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.sirdiapp.R;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class HealthSliderAdapter extends
        SliderViewAdapter<HealthSliderAdapter.SliderAdapterVH> {

    private final Context context;
    private int mCount;

    public HealthSliderAdapter(Context context) {
        this.context = context;
    }

    public void setCount(int count) {
        this.mCount = count;
    }

    @Override
    public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_slider_layout_item, null);
        return new SliderAdapterVH(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "This is item in position " + position, Toast.LENGTH_SHORT).show();
            }
        });

        switch (position) {
            case 0:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.doctor_interaction)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 1:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.clinics)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            case 2:
                Glide.with(viewHolder.itemView)
                        .load(R.drawable.blood_donation)
                        .fitCenter()
                        .into(viewHolder.imageViewBackground);
                break;
            default:
                viewHolder.textViewDescription.setText("This is default");
                viewHolder.textViewDescription.setTextColor(Color.BLACK);
                break;

        }

    }

    @Override
    public int getCount() {
        //slider view count could be dynamic size
        return mCount;
    }

    //setting the layout for image slider
    class SliderAdapterVH extends SliderViewAdapter.ViewHolder {

        final View itemView;
        final ImageView imageViewBackground;
        final TextView textViewDescription;

        public SliderAdapterVH(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.slider_image);
            textViewDescription = itemView.findViewById(R.id.slider_text);
            this.itemView = itemView;
        }
    }
}