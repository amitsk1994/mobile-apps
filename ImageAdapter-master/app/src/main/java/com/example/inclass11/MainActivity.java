package com.example.inclass11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnAdapterClickListener {
    Button pic;
    public static List<Image> imageArrayList=new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager layoutManager;
    public static   String storageRef;
    FirebaseFirestore db;
    ProgressBar progressBar;



    final int REQUEST_IMAGE_CAPTURE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = FirebaseFirestore.getInstance();
        progressBar=findViewById(R.id.progressBar);
        getList();
        pic=findViewById(R.id.btn_TakePic);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isConnected()){
                    dispatchTakePictureIntent();
                }
                else{
                    Toast.makeText(MainActivity.this, "Check internet Connectivity", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI
                        && networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
            return false;
        }
        return true;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Camera Callback........
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            uploadImage(imageBitmap);

        }
    }

    private void uploadImage(Bitmap photoBitmap){
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        Date date = new Date();
        storageRef = "images/camera"+date.toString()+".jpg";
        final StorageReference imageRepo = storageReference.child(storageRef);

//        Converting the Bitmap into a bytearrayOutputstream....
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photoBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imageRepo.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("demo", "onFailure: "+e.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Log.d("Demo", "onSuccess: "+"Image Uploaded!!!");
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                int progress= (int) ((100*taskSnapshot.getBytesTransferred())/taskSnapshot.getTotalByteCount());
                progressBar.setProgress(progress);
            }
        });

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                return null;
                if (!task.isSuccessful()){
                    throw task.getException();
                }

                return imageRepo.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()){
                    Log.d("demo image uppload", "Image Download URL"+ task.getResult());
                    String imageURL = task.getResult().toString();
                    Log.d("demo image url", imageURL+"");
                    Log.d("demo image url", storageRef+"");
                    Image image=new Image(imageURL , storageRef);
                    imageArrayList.add(image);
                    image.setImage_id(Math.random());
                    db.collection("images").document("image"+image.getImage_id()).set(image).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            mAdapter.notifyDataSetChanged();
                            progressBar.setProgress(0);
                        }
                    });



                }
            }
        });





    }



    @Override
    public void onDeleteItemClicked(final int pos) {


        final int position = pos;
        Log.d("Demo print delete", imageArrayList.get(position)+"");

        FirebaseStorage storage = FirebaseStorage.getInstance();

        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();


        StorageReference desertRef = storageRef.child(imageArrayList.get(position).imageReference);



       // String docName=imageArrayList.get(position).url.substring(8);

        desertRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("Demo print delete", " removing from interface    ");
                imageArrayList.remove(position);
                mAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();

            }
        });

        db.collection("images").document("image"+imageArrayList.get(position).getImage_id())
                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mAdapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
            }
        });




    }

    public void getList(){

        db.collection("images").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Image image=(Image)document.toObject(Image.class);
                                if(!imageArrayList.contains(image))
                                    imageArrayList.add(image);
                                Log.d("demo get all", document.toString() + " => " + document.getData());
                                Log.d("demo get image", image.toString() + " => " + document.getData());
                            }
                            recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

                            Log.d("demo list","list arr"+imageArrayList);
                            recyclerView.setHasFixedSize(true);

                            layoutManager = new LinearLayoutManager(MainActivity.this);
                            recyclerView.setLayoutManager(layoutManager);

// specify an adapter (see also next example)
                            mAdapter = new ImageViewAdapter(imageArrayList, MainActivity.this);
                            recyclerView.setAdapter(mAdapter);
                            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

                        } else {
                            Log.d("demo get all", "Error getting documents: ", task.getException());
                        }
                    }
                });




    }
}
