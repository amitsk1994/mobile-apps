package com.example.inclass11;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageViewAdapter   extends RecyclerView.Adapter<ImageViewAdapter.ViewHolder>{

    List<Image> mData ;
    OnAdapterClickListener onAdapterClickListener;

    public ImageViewAdapter(List<Image> mData , OnAdapterClickListener onAdapterClickListener) {
        this.mData = mData;
        this.onAdapterClickListener=onAdapterClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.image_item, parent, false);

        ViewHolder  viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Image image = mData.get(position);


        holder.image= image;
        Picasso.get().load(image.url).into(holder.imageView);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                onAdapterClickListener.onDeleteItemClicked( position);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView ;
        Image image ;

        public ViewHolder(@NonNull View itemView  ) {
            super(itemView);

            imageView =  itemView.findViewById(R.id.imageView);


        }
    }

}