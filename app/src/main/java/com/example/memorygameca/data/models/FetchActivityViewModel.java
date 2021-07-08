package com.example.memorygameca.data.models;

import android.content.Context;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class FetchActivityViewModel extends ViewModel {

    private List<fetchData> list = new ArrayList<fetchData>();

    public void setImage(fetchData data){

            list.add(data);
        }


    public List<fetchData> getImageData(){
        return list;
    }
    public void remove(fetchData data){
        list.remove(data);
    }
    public void clearAllData(){
        list.clear();
    }
}
