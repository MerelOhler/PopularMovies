package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    private String url = "bla";
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView imageView = findViewById(R.id.imageView);
        textView = findViewById(R.id.textview);
        Picasso.get().setIndicatorsEnabled(true);
        Picasso.get().load("https://www.tate.org.uk/art/images/work/T/T05/T05010_10.jpg").into(imageView);

        MainViewModel model = ViewModelProviders.of(this).get(MainViewModel.class);
        LiveData<String> movieDBResult = model.getJsonReturn(url);
        movieDBResult.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                textView.setText(s);
            }
        });


    }
}
