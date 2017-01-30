package com.it.rmmg.japinoy;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.Collections;
import java.util.List;

/**
 * Created by bern on 1/30/17.
 */
public class TranslationAdapter extends RecyclerView.Adapter<TranslationAdapter.ViewHolder>  {

    Activity activity;
    private Context context;
    private LayoutInflater inflater;
    List<TranslationModel> data= Collections.emptyList();




    public TranslationAdapter(Context context, List<TranslationModel> data){
        this.context=context;
//        inflater = LayoutInflater.from(context);
        this.data=data;

    }




    @Override
    public int getItemCount() {
        return data.size();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.translation_item_layout, parent, false);




        return new ViewHolderMedal(v);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        TranslationModel current = data.get(position);

        final ViewHolderMedal viewHolder = (ViewHolderMedal)holder;

        viewHolder.tvTranslate.setText(current.tag_word);
        viewHolder.tvTranslate.setTag(current.tag_audio);


//        Glide.with(context).load(current.medalHolder)
//                .thumbnail(0.1f)
//                .crossFade()
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(viewHolder.imgAudio);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View v) {
            super(v);
        }
    }

    class ViewHolderMedal extends ViewHolder{
        public ImageView imgAudio;
        public TextView tvTranslate;
        public ViewHolderMedal(View itemView) {
            super(itemView);

            imgAudio = (ImageView) itemView.findViewById(R.id.imgAudio);
            tvTranslate = (TextView) itemView.findViewById(R.id.tvTranslate);
        }



    }
}
