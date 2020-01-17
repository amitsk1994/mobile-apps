package com.example.inclass_03;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ImageView img;
    RadioButton rb;
    RadioButton radioButton_male;
    RadioButton radioButton_female;

    final static String USER_KEY="user";
    String str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RadioGroup rg=findViewById(R.id.radioGp);
        img=findViewById(R.id.imageView);
        final EditText fnameText=findViewById(R.id.editFirstName);
        final EditText lnameText=findViewById(R.id.editLastName);


        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                  rb=findViewById(i);
                  str=rb.getText().toString();
                if(str.equals("Male")){

                    img.setImageResource(R.drawable.male);
                }
                else{

                    img.setImageResource(R.drawable.female);
                }

                rb.setError(null);

            }
        });

        Button btn=findViewById(R.id.btnSave);
            radioButton_male=findViewById(R.id.radioMale);
             radioButton_female=findViewById(R.id.radioFemale);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstName = fnameText.getText().toString();
                String lastName = lnameText.getText().toString();


                //validations

                if(firstName==null || firstName.equals("") ||firstName.isEmpty()
                        ||lastName==null || lastName.equals("") ||lastName.isEmpty()
                        || (!radioButton_male.isChecked()  && !radioButton_female.isChecked())


                )
                {
                    if(firstName==null || firstName.equals("") ||firstName.isEmpty() )
                    {
                        fnameText.setError("Enter a valid firstName");

                    }

                    if(lastName==null || lastName.equals("") ||lastName.isEmpty() )
                    {
                        lnameText.setError("Enter a valid lastName");

                    }

                    if(!radioButton_male.isChecked()  && !radioButton_female.isChecked())
                    { radioButton_male.setError("check one gender");
                        Toast.makeText(getApplicationContext() , "Choose a gender!" , Toast.LENGTH_LONG).show();

                    }
                    return;
                }

                User user=new User(fnameText.getText().toString(),lnameText.getText().toString(),str);

                Intent i=new Intent(MainActivity.this, DisplayActivity.class);
                i.putExtra(USER_KEY, user);
                startActivity(i);
                finish();

            }
        });
    }

}
