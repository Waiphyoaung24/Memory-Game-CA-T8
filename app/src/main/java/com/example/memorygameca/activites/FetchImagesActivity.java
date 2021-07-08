package com.example.memorygameca.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.memorygameca.R;
import com.example.memorygameca.adapters.GridAdapter;
import com.example.memorygameca.data.models.FetchActivityViewModel;
import com.example.memorygameca.data.models.fetchData;
import com.example.memorygameca.databinding.ActivityFetchImagesBinding;
import com.example.memorygameca.delegate.FetchActivityDelegate;
import com.example.memorygameca.utils.Utils;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;


public class FetchImagesActivity extends AppCompatActivity implements FetchActivityDelegate {


    MaterialButton btnFetch;
    ProgressBar progressBar;
    EditText etUrl;
    RecyclerView rvImages;
    MaterialButton btnSend;
    LinearLayout llprogress;
    private FetchActivityViewModel model;
    private GridAdapter gridAdapter;
    private List<fetchData> data;
    boolean flag ;
    ProgressBar pgHor;
    TextView tvDownload;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fetch_images);


        initComponents();
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               gridAdapter.clearAllData();
                fetchData();
            }
        });


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (model.getImageData().size() == 6) {

                    Toast.makeText(FetchImagesActivity.this, "Your data is successfully saved", Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(FetchImagesActivity.this, "Please choose 6 images", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public List<fetchData> retrieveData(String url) {

        List<fetchData> data = new ArrayList<fetchData>();
        Utils utils = new Utils();

        for (int i = 0; i < utils.listImages.length; i++) {

            String img = "https://cdn." + url + "/img-thumbs/280h/" + utils.listImages[i] + ".jpg";
            fetchData imgfromUrl = new fetchData(img, i);
            data.add(imgfromUrl);
        }

        return data;
    }


    public void initComponents() {


        model = new ViewModelProvider(this).get(FetchActivityViewModel.class);
        btnFetch = findViewById(R.id.btnFetch);
        etUrl = findViewById(R.id.et_url);
        rvImages = findViewById(R.id.rv_images);
        gridAdapter = new GridAdapter(getApplicationContext(), this);
        btnSend = findViewById(R.id.btn_send);
        llprogress = findViewById(R.id.ll_progress);
        pgHor = findViewById(R.id.pb_hor);
        tvDownload = findViewById(R.id.tv_pb);


    }

    public void fetchData() {

        data = new ArrayList<>();
        data = retrieveData(etUrl.getText().toString().substring(8, 20));
        gridAdapter.clearAllData();
        gridAdapter.setNewData(data);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        rvImages.setLayoutManager(gridLayoutManager);
        rvImages.setAdapter(gridAdapter);
        llprogress.setVisibility(View.VISIBLE);
        pgHor.setProgress(100);
        tvDownload.setText("Downloading 20 of 20 images");



    }

    @Override
    public void onClickItem(fetchData data) {
        flag = true;
        for(int i = 0 ; i < model.getImageData().size();i++){
            if(data.getId() == model.getImageData().get(i).getId()){
                model.remove(data);
                flag = false;
            }
        }
        if(flag == true) {
                model.setImage(data);
                btnSend.setVisibility(View.VISIBLE);
                llprogress.setVisibility(View.GONE);

            }
        }
    }





