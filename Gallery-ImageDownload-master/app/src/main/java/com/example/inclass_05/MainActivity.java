package com.example.inclass_05;
//Group 15
//Amit S Kalyanpur
//Kishan Singh
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String keyword;
    ImageView imageView;
    List<String>urls=new ArrayList<>();
    ProgressBar progressBar;
    ImageButton next;
    ImageButton back;
    int size;
    int i=0;
    AlertDialog.Builder build;
    List<String> list ;
    AlertDialog alert;
    TextView name_textView;
    Button btn_go;
    String search_word = "";
    Boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btn_go = findViewById(R.id.button_GO);
        name_textView = findViewById(R.id.name_TextView);

        next=findViewById(R.id.rightButton);
        back=findViewById(R.id.leftButton);

        next.setEnabled(false);

        back.setEnabled(false);

        if(isConnected()){
            Toast.makeText(getApplicationContext() , "connected" , Toast.LENGTH_SHORT).show();
            btn_go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag=true;
                    next.setEnabled(true);

                    back.setEnabled(true);

                    new DoGetWork().execute("http://dev.theappsdr.com/apis/photos/keywords.php");

                }
            });
        }
        else{
            Toast.makeText(getApplicationContext() , " not connected" , Toast.LENGTH_SHORT).show();
        }



        imageView=findViewById(R.id.imageView);
        imageView.setVisibility(View.INVISIBLE);

        //Button btn = findViewById(R.id.button);
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(ProgressBar.INVISIBLE);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(flag==false){
//                   // Toast.makeText(MainActivity.this, "Invalid operation", Toast.LENGTH_SHORT).show();
//                    return;
//                }


                if(i<0) i = 0 ;
                else if(i>=size-1) i = 0 ;
                else i++ ;
                //Toast.makeText(getApplicationContext(), i+"", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(ProgressBar.VISIBLE);
                new GetImageData((ImageView) findViewById(R.id.imageView)).execute(urls.get(i));
                progressBar.setVisibility(ProgressBar.INVISIBLE);

            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(flag==false){
//                    Toast.makeText(MainActivity.this, "Invalid operation", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                if(i<=0) i = size-1 ;

                else if(i>size-1) i = 0 ;
                else i-- ;

                //Toast.makeText(getApplicationContext(), i+"", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(ProgressBar.VISIBLE);

                new GetImageData((ImageView) findViewById(R.id.imageView)).execute(urls.get(i));
                progressBar.setVisibility(ProgressBar.INVISIBLE);

            }
        });



    }


    private boolean isConnected() {
        ConnectivityManager connect = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connect.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            return false;
        }
        return true;

    }

    private class DoGetWork extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {

            Log.d("output" , s);

            final AlertDialog.Builder build= new AlertDialog.Builder(MainActivity.this);
            final String[] arr = s.split(";") ;
            //Arrays.copyOf(arr, arr.length-1);
            build.setTitle("Choose")
                    .setCancelable(false)
                    .setItems(arr,  new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //show image
                            name_textView.setText(arr[which]);
                            search_word = arr[which];
                            Log.d("clicked on" , arr[which]);
                            urls.clear();


                             new GetDetails().execute("http://dev.theappsdr.com/apis/photos/index.php?keyword="+search_word);

                            progressBar.setVisibility(ProgressBar.VISIBLE);
                            imageView.setVisibility(View.VISIBLE);




                        }
                    });

            final AlertDialog alert= build.create();
            alert.show();




        }

        @Override
        protected String doInBackground(String... strings) {

            HttpURLConnection connection = null;

            String result = null;
            Log.d("url",  strings[0] );
            try {

                URL url = new URL( strings[0] );
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {

                    result = IOUtils.toString(connection.getInputStream(), "UTF-8");

                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

            finally {
                //Close open connections and reader
                if (connection != null) {
                    connection.disconnect();
                }

            }

            return result;






        }

    }

    private class GetDetails extends AsyncTask<String, Void, String> {

        @Override
        protected void onPostExecute(String s) {
            Log.d("demo2",s);
            s=s.trim();
            if(s.trim().length()==0 || s.equals(null)||s.isEmpty()){
                progressBar.setVisibility(View.INVISIBLE);
                Log.d("random", "end of random");
               // Toast.makeText(getApplicationContext(), "No images to display. Please select another category", Toast.LENGTH_LONG);

            }
            String[]arr=s.split("\\s+");
            for(int i=0;i<arr.length;i++){
                urls.add(arr[i].trim());
            }
            if(s.trim().length()==0 || s.equals(null)||s.isEmpty()){
              urls.clear();
                Toast.makeText(getApplicationContext(), "No images to display. Please select another category", Toast.LENGTH_LONG).show();
                next.setEnabled(false);

                back.setEnabled(false);
                return;
            }



            //Toast.makeText(MainActivity.this, urls+"", Toast.LENGTH_SHORT);
            Log.d("urls" , urls.size()+"");

            size=urls.size();
            if(urls.get(0).equals("random")){
                progressBar.setVisibility(View.INVISIBLE);
                //Toast.makeText(getApplicationContext(), "No images to display. Please select another category", Toast.LENGTH_SHORT).show();
                return;

            }
            if(size!=0){
                new GetImageData((ImageView) findViewById(R.id.imageView)).execute(urls.get(0));
                progressBar.setVisibility(ProgressBar.INVISIBLE);

            }
//            else{
//                Toast.makeText(MainActivity.this, "Please select something with a image url", Toast.LENGTH_SHORT);
//            }

        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder str=new StringBuilder();
            HttpURLConnection connection=null;
            InputStream inputStream=null;
            BufferedReader buff=null;
            //l=new ArrayList();

            try{
                URL url=new URL(strings[0]);
                Log.d("demo3",url.toString());
                connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    str.append(IOUtils.toString(connection.getInputStream(),"UTF8"));
                }

            }
            catch(Exception e){
                e.printStackTrace();
            }

            finally {
                if(connection!=null){
                    connection.disconnect();
                }
                if(inputStream!=null){
                    try {
                        buff.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return str.toString();



        }

    }

    private class GetImageData extends AsyncTask<String, Void, Void> {
        ImageView img;
        Bitmap image;
        public GetImageData(ImageView img1) {
            img=img1;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if(image!=null && img!=null){
                img.setImageBitmap(image);

            }

        }

        @Override
        protected Void doInBackground(String... strings) {
//            for(int i=0;i<1000;i++){
//                for(int j=0;j<100000;j++){
//
//                }
//            }
            HttpURLConnection connection=null;

            image=null;
            try{
                URL url=new URL(strings[0]);
                connection= (HttpURLConnection) url.openConnection();
                connection.connect();
                if(connection.getResponseCode()==HttpURLConnection.HTTP_OK){
                    image= BitmapFactory.decodeStream(connection.getInputStream());
                }
            }
            catch(MalformedURLException e){
                e.printStackTrace();
            }
            catch(Exception e){
                e.printStackTrace();
            }

            finally {
                if(connection!=null){
                    connection.disconnect();
                }

            }
            //return image;
            return null;
        }
    }


}
