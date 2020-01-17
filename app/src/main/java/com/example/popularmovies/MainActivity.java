package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView);
        Picasso.get().setIndicatorsEnabled(true);
        Picasso.get().load("https://www.tate.org.uk/art/images/work/T/T05/T05010_10.jpg").into(imageView);


    }
}
