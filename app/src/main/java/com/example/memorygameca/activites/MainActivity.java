package com.example.memorygameca.activites;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.memorygameca.R;
import com.example.memorygameca.activites.Image;
import com.example.memorygameca.activites.ImageAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    // 输入的网址
    private EditText etUrl;
    // 下载按钮
    private Button btnFetch;
    // 图片布局
    private GridView gvImg;
    // 进度条
    private ProgressBar pbHor;
    // 进度文本
    private TextView tvPb;

    private Handler mHandler;
    // 存放图片
    private List<Image> imgList;

    private ArrayList<String> prepareImages;

    // 数据适配器
    private ImageAdapter adapter;

    // 记录选中的数量
    private int selNum = 0;
    // 选中图片后的跳转按钮
    private Button btnTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        btnTo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



            }
        });

        // 初始化图片属性，默认为空
        imgList = new ArrayList<>();
        // 默认20张空白图
        for (int i = 0; i < 20; i++) {
            imgList.add(new Image());
        }
        // 设置数据适配器
        adapter = new ImageAdapter(this, imgList, pbHor, tvPb);
        gvImg.setAdapter(adapter);

        mHandler = new Handler() {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                adapter.notifyDataSetChanged();
            }
        };

        // 设置点击图片的事件
        gvImg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // 如果当前是已选中，就取消，如果是没选中，就选择
                if (imgList.get(position).isSel()) {
                    imgList.get(position).setSel(false);
                    selNum--;
                    if (selNum < 6) {
                        // 如果选择图片小于6个，设置不可见
                        btnTo.setVisibility(View.GONE);
                    }
                } else {
                    if (selNum >= 6) {
                        Toast.makeText(MainActivity.this, "You can only choose up to 6", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    imgList.get(position).setSel(true);
                    selNum++;
                    if (selNum >= 6) {
                        // 如果选择图片大于6个，设置可见
                        btnTo.setVisibility(View.VISIBLE);
                        btnTo.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                prepareImages();
                                Intent intent = new Intent(MainActivity.this,GameActivity.class);
                                intent.putStringArrayListExtra("image",prepareImages);
                                startActivity(intent);
                            }
                        });
                    }
                }
                // 刷新界面
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void initData() {

        // 添加点击事件
        btnFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // 获取输入的图片地址链接
                String url = etUrl.getText().toString();
                if ("".equals(url.trim())) {
                    Toast.makeText(MainActivity.this, "URL is invalid", Toast.LENGTH_SHORT).show();
                    return;
                }

                // 每次点击Fetch按钮后，图片需要还原
                for (int i = 0; i < 20; i++) {
                    imgList.get(i).setImgSrc(null);
                }
                adapter.notifyDataSetChanged();

                // 还原进度条
                pbHor.setProgress(0);
                // 设置文本
                tvPb.setText("Downloading 0 of 20 images");

                // 开启子线程，访问网页
                new Thread(() -> {
                    try {
                        int index = 0;

                        // 获取网页源码
                        Document document = Jsoup.connect(url).timeout(10000).get();
                        // 获取所有图片标签
                        Elements elements = document.select("img");
                        for (Element element : elements) {
                            // 需要判断图片标签是否是正确的图片
                            String imgSrc = element.attr("src");

                            // 判断后缀
                            if (imgSrc.contains(".jpg") || imgSrc.contains(".png")) {
                                // 只需要前20张
                                if (index >= 20) {
                                    break;
                                }
                                imgList.get(index).setImgSrc(imgSrc);
                                index++;
                            }
                        }

                        // 通知图片
                        Message msg = new Message();
                        msg.obj = imgList;
                        mHandler.sendMessage(msg);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

            }
        });
    }

    /**
     * 初始化页面控件
     */
    private void initView() {

        etUrl = findViewById(R.id.et_url);
        btnFetch = findViewById(R.id.btn_fetch);
        gvImg = findViewById(R.id.gv_img);
        pbHor = findViewById(R.id.pb_hor);
        tvPb = findViewById(R.id.tv_pb);
        btnTo = findViewById(R.id.btn_to);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void prepareImages(){
        prepareImages = new ArrayList<>();
        List<Image> images = imgList.stream().filter(Image::isSel).collect(Collectors.toList());
         for(Image i : images) {
             prepareImages.add(i.getImgSrc());
         }
    }
}