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
import com.google.firebase.firestore.FirebaseFirestore;

public class EditMovieActivity extends AppCompatActivity {

    private Spinner genreSpinner;
    private static   String[] paths = {"Select","Action", "Animation", "Comedy", "Documentary", "Family", "Horror",
            " Crime and Others"};
    private String movieName , movieDescription , movieGenre , movieImdb ;
    private int  movieRating , movieYear ;
    private int genrePosition ;
    private SeekBar movieRatingSeekBar ;
    private EditText movieNameEditText , movieDescriptionEditText , movieYearEditText , movieImdbEditText ;
    private Button saveChanges_button ;
    private TextView rate;
    Movie movie;
    FirebaseFirestore db;
    Intent goBackIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_movie);

        setTitle("Edit Movie");

        movie =(Movie) getIntent().getExtras().getParcelable(MainActivity.EDIT_MOVIE);

        //finding the genre position

        for(int i = 0 ; i < paths.length ; i++)
        {
            if(paths[i].equals(movie.getGenre())) genrePosition = i ;
            //Toast.makeText(getApplicationContext() , "selected"+genrePosition+"",Toast.LENGTH_SHORT).show();

        }


        saveChanges_button = findViewById(R.id.button_finish);
        genreSpinner = findViewById(R.id.spinner_genreDropDown);
        movieNameEditText = findViewById(R.id.Text_Name);
        movieDescriptionEditText = findViewById(R.id.scrollView);
        movieYearEditText = findViewById(R.id.editText3_Year);
        movieImdbEditText = findViewById(R.id.editText4_ImdbLink);

        movieRatingSeekBar = findViewById(R.id.seekBar_Rating);
        movieRatingSeekBar.setMax(5);

        //setting default values from movies object

        movieNameEditText.setText(movie.getName());
        movieDescriptionEditText.setText(movie.getDescription());
        movieYearEditText.setText(movie.getYear()+"") ;
        movieImdbEditText.setText(movie.getImDb());
        movieRatingSeekBar.setProgress(movie.getRating());

        db = FirebaseFirestore.getInstance();



        //DropDown logic
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditMovieActivity.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(adapter);
        genreSpinner.setPrompt("Select");
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


        genreSpinner.setSelection(genrePosition);

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


        //Save changes button

        //addMovie button

        saveChanges_button.setOnClickListener(new View.OnClickListener() {
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
                    Toast.makeText(getApplicationContext(), "Select rating between 1-5 range", Toast.LENGTH_SHORT).show();                    return ;
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

                Log.d("Demo" , "Successsss");
                Log.d("Demo" , movieRatingSeekBar.getProgress()+"");
                Log.d("Demo" , movieRating+"");



                //take values from form
//                movieName = movieNameEditText.getText().toString();
//                movieDescription = movieDescriptionEditText.getText().toString();
//                movieYear = Integer.parseInt(movieYearEditText.getText().toString());
//                movieImdb = movieImdbEditText.getText().toString();
//                movieRating = movieRatingSeekBar.getProgress();
                String key=movie.getName();
                db.collection("movies").document(key)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("demo delete", "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w("demo delete", "Error deleting document", e);
                            }
                        });
                movie.setName(movieNameEditText.getText().toString());
                movie.setDescription(movieDescriptionEditText.getText().toString());
                movie.setYear(Integer.parseInt(movieYearEditText.getText().toString()));
                movie.setImDb(movieImdbEditText.getText().toString());
                movie.setRating(movieRatingSeekBar.getProgress());


//Log.d("Demo edit output" ,new Movie(movieName,movieDescription,movieGenre,movieRating,movieYear,movieImdb)+"");
                goBackIntent = new Intent();
                db.collection("movies").document(movie.getName()).set(movie).addOnSuccessListener(new OnSuccessListener<Void>() {
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
                //goBackIntent.putExtra(MainActivity.EDIT_MOVIE , new Movie(movieName,movieDescription,movieGenre,movieRating,movieYear,movieImdb));




    }
}
