package com.example.karyc.vkontaktikum.ui;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.karyc.vkontaktikum.R;

public class CustomDataBindings {

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String url) {
        Glide.with(view)
                .load(url)
                .apply(RequestOptions.circleCropTransform()
                        .placeholder(R.color.colorDivider)
                        .fallback(R.drawable.ic_baseline_face_24px))
                .into(view);
    }

    @BindingAdapter("online")
    public static void setBackground(View view, int online) {
        if (online == 1) {
            view.setBackgroundResource(R.drawable.status_background_online);
        } else view.setBackgroundResource(R.drawable.status_background_offline);
    }
}
