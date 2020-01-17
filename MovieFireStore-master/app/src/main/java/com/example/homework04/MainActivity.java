package com.example.homework04;
//Group 15
//Amit S Kalyanpur
//Kishan Singh
import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firestore.v1.FirestoreGrpc;

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
    ArrayList<Movie> movieList=new ArrayList<>(); ;
    int currentEdit = 0 ;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //movieList =
        addMovie_button = findViewById(R.id.button_addMovie);
        editMovie_button = findViewById(R.id.button2_edit);
        deleteMovie_button = findViewById(R.id.button3_deleteMovie);
        listByYear_button = findViewById(R.id.button4_listByYear);
        listByRating_button = findViewById(R.id.button5_listByRating);


        setTitle("My Favorite Movies");

        db = FirebaseFirestore.getInstance();

        updateList();

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
               // updateList();
                db.collection("movies").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
//                                        Movie movie=(Movie)document.toObject(Movie.class);
//                                        if(!movieList.contains(movie))
//                                                movieList.add(movie);
                                        Log.d("demo get all", document.toString() + " => " + document.getData());
                                        //Log.d("demo get movie", movie.toString() + " => " + document.getData());
                                    }
                                    alertForEdit();
                                } else {
                                    Log.d("demo get all", "Error getting documents: ", task.getException());
                                }
                            }
                        });


            }
        });

        deleteMovie_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //create list of movie names for alert dialog
                final String[] movies = new String[movieList.size()];
                int i= 0 ;
                for(Movie m : movieList)
                    movies[i++] = m.getName();

                AlertDialog.Builder build= new AlertDialog.Builder(MainActivity.this);
                build.setTitle("Pick a Movie");
                build.setItems(movies, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        db.collection("movies").document(movies[which])
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Log.d("demo delete", "DocumentSnapshot successfully deleted!");
                                        deleteInList(which);
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.w("demo delete", "Error deleting document", e);
                                    }
                                });

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
            updateList();
            Log.d("demo movie add list","");

        }
        else   if(requestCode == EDIT_REQ_CODE && resultCode == RESULT_OK){

            updateList();

        }

        else if(resultCode == RESULT_CANCELED){
            Log.d("START_ACTIVITY_RESULT" , "Cancelled");
        }
        else if(resultCode == VIEW_LIST_YEAR && resultCode == RESULT_OK){
            Log.d("START_ACTIVITY_RESULT" , "Back");
        }

    }

    private void alertForEdit(){
        updateList();
        Log.d("demo list",movieList+"");
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
                //Toast.makeText(getApplicationContext(), "Clicked " + movieList.get(which)+"" , Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this , EditMovieActivity.class);
                intent.putExtra(EDIT_MOVIE , movieList.get(which));
                deleteInList(which);
                currentEdit = which ;
                startActivityForResult(intent , EDIT_REQ_CODE);
            }
        });

        final AlertDialog alert= build.create();
        alert.show();
    }

    private void updateList(){
        //movieList.clear();
        db.collection("movies").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Movie movie=(Movie)document.toObject(Movie.class);
                                if(!movieList.contains(movie))
                                    movieList.add(movie);
                                Log.d("demo get all", document.toString() + " => " + document.getData());
                                Log.d("demo get movie", movie.toString() + " => " + document.getData());
                            }

                        } else {
                            Log.d("demo get all", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void deleteInList(int index){
        movieList.remove(index);
    }


}
