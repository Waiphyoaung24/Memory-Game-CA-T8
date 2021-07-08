package com.example.memorygameca.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.memorygameca.R;
import com.example.memorygameca.data.models.fetchData;
import com.example.memorygameca.databinding.GridItemLayoutBinding;
import com.example.memorygameca.delegate.FetchActivityDelegate;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;



public class GridAdapter  extends RecyclerView.Adapter<GridAdapter.FetchDataViewHolder> {
    private Context context;
    private List<fetchData> mData;
    private fetchData fetchData;
    private GridItemLayoutBinding binding;
    private FetchActivityDelegate mDelegate;

    public RequestOptions options = new RequestOptions()
            .centerCrop()
            .placeholder(R.drawable.animation_progress)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .priority(Priority.HIGH)
            .dontAnimate()
            .dontTransform();

    public GridAdapter(Context context,FetchActivityDelegate mDelegate ) {
    this.context = context;
    this.mDelegate = mDelegate;
    mData = new ArrayList<>();



    }

    @NonNull
    @Override
    public FetchDataViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        return new FetchDataViewHolder(GridItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull GridAdapter.FetchDataViewHolder holder, int position) {

        holder.bind(mData.get(position));


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
    public void setNewData(List<fetchData> newData) {
        mData = newData;
        notifyDataSetChanged();
    }
    public void clearAllData(){
        mData = new ArrayList<>();
        notifyDataSetChanged();
    }


    public class FetchDataViewHolder extends  RecyclerView.ViewHolder{



        private GridItemLayoutBinding binding;
        private fetchData mData;

        public FetchDataViewHolder(GridItemLayoutBinding binding) {
            super(binding.getRoot());
           this.binding = binding;



        }
        public void bind(fetchData data){

            Glide.with(context)
                    .load(data.getImage())
                    .apply(options)
                    .into(binding.ivImages);


           itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   FrameLayout frameLayout = binding.flTick;
                   if (frameLayout.getVisibility() == View.VISIBLE) {
                       frameLayout.setVisibility(View.GONE);
                       binding.ivImages.setImageAlpha(255);

                   } else {
                       frameLayout.setVisibility(View.VISIBLE);
                       binding.ivImages.setBackgroundResource(0);
                       binding.ivImages.setImageAlpha(100);


                   }

                       mDelegate.onClickItem(data);


               }
           });

        }


        }
    }

