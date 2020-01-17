package com.example.homework04;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddMovieActivity extends AppCompatActivity {
    private Spinner genreSpinner;
    private static   String[] paths = {"Select" , "Action", "Animation", "Comedy", "Documentary", "Family", "Horror",
            " Crime and Others"};
    private String movieName , movieDescription , movieGenre , movieImdb ;
    private int  movieRating , movieYear ;
    private SeekBar movieRatingSeekBar ;
    private EditText movieNameEditText , movieDescriptionEditText , movieYearEditText , movieImdbEditText ;
    private Button addMovie_button ;
    private TextView rate;
    FirebaseFirestore db;
    Intent goBackIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie);
        setTitle("Add Movie");
        db = FirebaseFirestore.getInstance();







                addMovie_button = findViewById(R.id.button_finish);
        genreSpinner = findViewById(R.id.spinner_genreDropDown);
        movieNameEditText = findViewById(R.id.Text_Name);
        movieDescriptionEditText = findViewById(R.id.scrollView);
        movieYearEditText = findViewById(R.id.editText3_Year);
        movieImdbEditText = findViewById(R.id.editText4_ImdbLink);

        movieRatingSeekBar = findViewById(R.id.seekBar_Rating);
        movieRatingSeekBar.setMax(5);
        movieRatingSeekBar.setProgress(0);


        //DropDown logic
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddMovieActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);

        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext() , position+"",Toast.LENGTH_SHORT).show();
                movieGenre = paths[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //rating seekbar
        rate=findViewById(R.id.rate);

        movieRatingSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                movieRating = progress ;
                rate.setText(progress+"");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


        //addMovie button

        addMovie_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //validation

                if (movieNameEditText.getText().toString().isEmpty())
                {
                    Toast.makeText(getApplicationContext(), "Enter a movie name", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (movieDescriptionEditText.getText().toString().trim().length() == 0)
                {
                    Toast.makeText(getApplicationContext(), "Enter description for the movie", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (movieGenre.equals("Select"))
                {
                    Toast.makeText(getApplicationContext(), "You didn't select any genre", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (movieRatingSeekBar.getProgress() ==0)
                {
                     Toast.makeText(getApplicationContext(), "Select rating between 1-5 range", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (movieYearEditText.getText().toString().isEmpty()
                        || movieYearEditText.getText().toString().length() < 4
                        || Integer.parseInt(movieYearEditText.getText().toString()) < 1900
                        || Integer.parseInt(movieYearEditText.getText().toString()) > 2019
                )
                {
                    Toast.makeText(getApplicationContext(), "Enter a valid year within 1900 - 2019 range", Toast.LENGTH_SHORT).show();
                    return ;
                }

                if (movieImdbEditText.getText().toString().isEmpty())
                {

                    Toast.makeText(getApplicationContext(), "Enter a IMDB link", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (!((movieImdbEditText.getText().toString().toLowerCase().startsWith("www.imdb.com/"))  ))
                {
                    Toast.makeText(getApplicationContext(), "Enter a valid IMDB link", Toast.LENGTH_SHORT).show();
                    return ;
                }
                if (movieImdbEditText.getText().toString().length() > 0 && movieImdbEditText.getText().toString().contains(" "))
                {
                    Toast.makeText(getApplicationContext(), "Spaces not allowed in IMDB link", Toast.LENGTH_SHORT).show();
                    return ;
                }





                //take values from form
                movieName = movieNameEditText.getText().toString();
                movieDescription = movieDescriptionEditText.getText().toString();
                movieYear = Integer.parseInt(movieYearEditText.getText().toString());
                movieImdb = movieImdbEditText.getText().toString();
                movieRating = movieRatingSeekBar.getProgress();

                Movie movie=new Movie(movieName,movieDescription,movieGenre,movieRating,movieYear,movieImdb);
                Log.d("Demo add output" ,movieName);

                goBackIntent = new Intent();
                //goBackIntent.putExtra(MainActivity.ADD_MOVIE , new Movie(movieName,movieDescription,movieGenre,movieRating,movieYear,movieImdb));

                db.collection("movies").document(movieName).set(movie).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("demo add movie", "DocumentSnapshot successfully written!");
                        setResult(RESULT_OK , goBackIntent);
                        finish();
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("demo add movie", "Error writing document", e);
                            }
                        });

            }


        });
    }
}
