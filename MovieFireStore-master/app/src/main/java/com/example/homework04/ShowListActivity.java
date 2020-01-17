package com.example.homework04;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShowListActivity extends AppCompatActivity {
    ArrayList<Movie> movieList;
    String check;
    TextView header;
    Button finish;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_list);

        movieList =  getIntent().getExtras().getParcelableArrayList("list") ;
        Log.d("Demo movielist" , movieList+"");


        check=(String)getIntent().getExtras().getString("check");
        header=findViewById(R.id.textViewHead);

        if(check.equals("year")){
            header.setText(R.string.moviesYear);
            //Toast.makeText(ShowListActivity.this,movieList.toString(),Toast.LENGTH_SHORT).show();

            Collections.sort(movieList, new Comparator<Movie>() {
                        @Override
                        public int compare(Movie movie, Movie t1) {
                            return movie.getYear()-t1.getYear();
                        }
                    });
            callDisplay();
        }

        if(check.equals("rating")){
            header.setText(R.string.moviesRating);
            Collections.sort(movieList, new Comparator<Movie>() {
                @Override
                public int compare(Movie movie, Movie t1) {
                    return t1.getRating()-movie.getRating();
                }
            });
            callDisplay();

        }
        finish=findViewById(R.id.button_finish);
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goBackIntent=new Intent();
                setResult(RESULT_OK , goBackIntent);
                finish();
            }
        });
    }

    TextView name,description,genre,rating,year,imdb;


    public void viewDisplay(Movie movie){
        name=findViewById(R.id.Text_Name);
        description=findViewById(R.id.Text2_description);


        genre=findViewById(R.id.textViewgenreDisplay);
        rating=findViewById(R.id.textViewRatingDisplay);
        year=findViewById(R.id.textViewYearDisplay);
        imdb=findViewById(R.id.textViewimdbDisplay);

        name.setText(movie.getName());
        description.setText(movie.getDescription());
        genre.setText(movie.getGenre());
        rating.setText(movie.getRating()+"/5");
        year.setText(movie.getYear()+"");
        imdb.setText(movie.getImDb());



    }

    public void callDisplay(){
        ImageButton next=findViewById(R.id.imageNext);
        ImageButton prev=findViewById(R.id.imagePrev);
        ImageButton last=findViewById(R.id.imageLast);
        ImageButton first=findViewById(R.id.imageFirst);
        viewDisplay(movieList.get(0));
        //Toast.makeText(ShowListActivity.this, movieList.toString(), Toast.LENGTH_SHORT).show();
        i=0;


        last.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                last();
            }
        });

        first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                first();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next();
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prev();
            }
        });
    }

    public void last(){
        i=movieList.size()-1;
        viewDisplay(movieList.get(i));
    }

    public void first(){
        i=0;
        viewDisplay(movieList.get(i));
    }

    public void next(){
        i++;
        if(i==movieList.size()){
            i=movieList.size()-1;
            return;
        }

        viewDisplay(movieList.get(i));
    }

    public void prev(){
        i--;
        if(i==-1){
            i=0;
            return;
        }
        viewDisplay(movieList.get(i));
    }
}
