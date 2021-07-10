package com.example.memorygameca.activites;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.GridLayout;

import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.example.memorygameca.R;

import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MemoryButton extends AppCompatButton  {

    protected int row;
    protected int column;
    protected int frontDrawableId;
    protected boolean isFlipped = false;
    protected boolean isMatched = false;

    protected Drawable front;
    protected Drawable back;

    Map<Integer,String> imagesMap;




    public MemoryButton(Context context, int r, int c, int frontImageDrawableId, Map<Integer,String> imagesMap)  {
        super(context);
        row = r;
        column = c;
        frontDrawableId = frontImageDrawableId;
        this.imagesMap = imagesMap;

//        front = context.getDrawable(frontImageDrawableId);
        back = context.getDrawable(R.drawable.code);
        Bitmap bitmap = null;
        try {
            bitmap = Glide.with(context).asBitmap().load(imagesMap.get(frontImageDrawableId)).submit().get();

        }catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        front = new BitmapDrawable(this.getResources(),bitmap);

        setBackground(back);

        GridLayout.LayoutParams tempParams = new GridLayout.LayoutParams(GridLayout.spec(r),GridLayout.spec(c));
        tempParams.width = (int) getResources().getDisplayMetrics().density * 155;
        tempParams.height = (int) getResources().getDisplayMetrics().density * 200;
        setLayoutParams(tempParams);
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public int getFrontDrawableId() {
        return frontDrawableId;
    }

    public void flip(){
        if(isMatched)
            return;
        if(isFlipped){
            setBackground(back);
            isFlipped = false;
        }else{
            setBackground(front);
            isFlipped= true;
        }
    }
}
