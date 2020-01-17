package com.example.homework04;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button addMovie_button ;
    Button deleteMovie_button ;
    Button editMovie_button ;
    Button listByYear_button ;
    Button listByRating_button ;
    public static final int ADD_REQ_CODE = 100 ;
    public static final int EDIT_REQ_CODE = 101 ;
    public static final int VIEW_LIST_YEAR = 102 ;
    public static final int VIEW_LIST_RATING = 103 ;
    public static final String ADD_MOVIE = "ADD_MOVIE" ;
    public static final String EDIT_MOVIE = "EDIT_MOVIE" ;
    ArrayList<Movie> movieList ;
    int currentEdit = 0 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        movieList = new ArrayList<>();
        addMovie_button = findViewById(R.id.button_addMovie);
        editMovie_button = findViewById(R.id.button2_edit);
        deleteMovie_button = findViewById(R.id.button3_deleteMovie);
        listByYear_button = findViewById(R.id.button4_listByYear);
        listByRating_button = findViewById(R.id.button5_listByRating);


        setTitle("My Favorite Movies");

        addMovie_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this , AddMovieActivity.class);
                startActivityForResult(intent , ADD_REQ_CODE);
            }
        });

        editMovie_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create list of movie names for alert dialog
                String[] movies = new String[movieList.size()];
                int i= 0 ;
                for(Movie m : movieList)
                    movies[i++] = m.getName();

                AlertDialog.Builder build= new AlertDialog.Builder(MainActivity.this);
                build.setTitle("Pick a Movie");
                build.setItems(movies, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Clicked " + movieList.get(which)+"" , Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this , EditMovieActivity.class);
                        intent.putExtra(EDIT_MOVIE , movieList.get(which));
                        currentEdit = which ;
                        startActivityForResult(intent , EDIT_REQ_CODE);
                    }
                });

                final AlertDialog alert= build.create();
                alert.show();

            }
        });

        deleteMovie_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create list of movie names for alert dialog
                String[] movies = new String[movieList.size()];
                int i= 0 ;
                for(Movie m : movieList)
                    movies[i++] = m.getName();

                AlertDialog.Builder build= new AlertDialog.Builder(MainActivity.this);
                build.setTitle("Pick a Movie");
                build.setItems(movies, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        movieList.remove(movieList.get(which));
                    }
                });

                final AlertDialog alert= build.create();
                alert.show();

            }
        });

        listByYear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movieList.size()==0){
                    Toast.makeText(MainActivity.this, "No movies in database!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this , ShowListActivity.class);
                intent.putExtra("list", (ArrayList<Movie>)movieList);
                intent.putExtra("check", "year");
                startActivityForResult(intent , VIEW_LIST_YEAR);
            }
        });

        listByRating_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(movieList.size()==0){
                    Toast.makeText(MainActivity.this, "No movies in database!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(MainActivity.this , ShowListActivity.class);
                intent.putExtra("list", (ArrayList<Movie>)movieList);
                intent.putExtra("check", "rating");
                startActivityForResult(intent , VIEW_LIST_YEAR);
            }
        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ADD_REQ_CODE && resultCode == RESULT_OK){
            Movie addedMovie  = (Movie)data.getExtras().getSerializable(ADD_MOVIE);
            movieList.add(addedMovie);
        }
        else   if(requestCode == EDIT_REQ_CODE && resultCode == RESULT_OK){
            Movie editedMovie  = (Movie)data.getExtras().getSerializable(EDIT_MOVIE);
            if(!editedMovie.equals(movieList.get(currentEdit))){
                movieList.remove(movieList.get(currentEdit));
                movieList.add(editedMovie);
            }
        }

        else if(resultCode == RESULT_CANCELED){
            Log.d("START_ACTIVITY_RESULT" , "Cancelled");
        }
        else if(resultCode == VIEW_LIST_YEAR && resultCode == RESULT_OK){
            Log.d("START_ACTIVITY_RESULT" , "Back");
        }

    }


}
