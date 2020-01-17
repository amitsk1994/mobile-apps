package com.example.inclass_03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class DisplayActivity extends AppCompatActivity {
    ImageView img;
    final static int REQ_CODE=100;
    final static String VALUE_KEY="value";
    final static String TO_EDIT="User";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        final User user = (User) getIntent().getExtras().getSerializable(MainActivity.USER_KEY);
        TextView name=findViewById(R.id.textName);
        TextView gender=findViewById(R.id.textSex);


        if(user.gender.equals("Male")){
            img=findViewById(R.id.profileImage);
            img.setImageResource(R.drawable.male);
        }
        else{
            img=findViewById(R.id.profileImage);
            img.setImageResource(R.drawable.female);
        }
        name.setText(user.firstName+" "+user.lastName);
        gender.setText(user.gender);


        Button edit = findViewById(R.id.buttonEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                User user2 = (User) getIntent().getExtras().getSerializable(EditActivity.USER_KEY);
                User user3;
                if(user2==null){
                    user3=user;
                }
                else{
                    user3=user2;
                }
                Intent goToMyProfileIntent = new Intent(DisplayActivity.this,EditActivity.class);
                goToMyProfileIntent.putExtra(TO_EDIT, user3);
//                goToMyProfileIntent.putExtra(MainActivity.VALUE_KEY , "");
//                setResult(RESULT_OK , goToMyProfileIntent);
                startActivityForResult(goToMyProfileIntent,REQ_CODE);
                //finish();

            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
                if(requestCode==REQ_CODE){
            if(resultCode==RESULT_OK){
                String value= data.getExtras().getString(VALUE_KEY);
            }
        }
    }
}
