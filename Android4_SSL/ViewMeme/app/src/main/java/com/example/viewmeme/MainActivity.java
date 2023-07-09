package com.example.viewmeme;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button viewImg;
    private Button nextBtn;
    private Button backBtn;
    private Button closeBtn;
    ImageView imageView;
    private int currentIndex = 0;
    private List<Uri> imageUris = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MysqlDb database = new MysqlDb();
        nextBtn = findViewById(R.id.next);
        backBtn = findViewById(R.id.back);
        closeBtn = findViewById(R.id.close);
        imageView = findViewById(R.id.image);
        viewImg = findViewById(R.id.viewBtn);
        viewImg.setOnClickListener(v -> {

            imageView.setVisibility(View.VISIBLE);
            backBtn.setVisibility(View.VISIBLE);
            nextBtn.setVisibility(View.VISIBLE);
            closeBtn.setVisibility(View.VISIBLE);

            try {
                database.getImageUris(MainActivity.this,new ImageUrisCallback() {
                    @Override
                    public void onSuccess(List<Uri> imageUris) {
                        if (imageUris != null && !imageUris.isEmpty()) {
                            MainActivity.this.imageUris = imageUris;
                            displayImage(currentIndex);
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        Toast.makeText(MainActivity.this,"ERROR with list",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex++;
                if (currentIndex >= imageUris.size()) {
                    currentIndex = 0; // Quay lại hình ảnh đầu tiên nếu đã đến cuối danh sách
                }
                displayImage(currentIndex);
            }
        });
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentIndex--;
                if (currentIndex < 0) {
                    currentIndex = imageUris.size() - 1; // Quay lại hình ảnh cuối cùng nếu đã đến đầu danh sách
                }
                displayImage(currentIndex);
            }
        });

        closeBtn.setOnClickListener(v -> {
            nextBtn.setVisibility(View.INVISIBLE);
            backBtn.setVisibility(View.INVISIBLE);
            closeBtn.setVisibility(View.INVISIBLE);
            imageView.setVisibility(View.INVISIBLE);
        });

    }
    private void displayImage(int index){
        Uri imageUri = imageUris.get(index);
        Picasso.get().load(imageUri).into(imageView);
    }
}