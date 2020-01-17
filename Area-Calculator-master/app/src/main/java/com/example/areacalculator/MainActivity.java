//Kishan Singh 801060377
//Amit Suresh Kalyanpur 801006048


package com.example.areacalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private double area;
    private TextView shape_textView;
    private String shapeType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView triangleImage=findViewById(R.id.imageTriangle);
        ImageView squareImage=findViewById(R.id.imageSquare);
        ImageView circleImage=findViewById(R.id.imageCircle);
        final EditText length1_editText=findViewById(R.id.inLength1);
        final EditText length2_editText=findViewById(R.id.inLength2);
        final TextView length2=findViewById(R.id.length2);
        final EditText answer_editText=findViewById(R.id.answer);
        shape_textView = findViewById(R.id.shape);
        shape_textView.setText("Select a shape!");

        //Triangle Image click
        triangleImage.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                length2_editText.setVisibility(TextView.VISIBLE);
                length2.setVisibility(TextView.VISIBLE);
                shapeType="triangle";
                shape_textView.setText(shapeType);




            }
        });

        //Square Image click

        squareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                length2_editText.setVisibility(TextView.INVISIBLE);
                length2.setVisibility(TextView.INVISIBLE);

                shapeType="square";
                shape_textView.setText(shapeType);

            }
        });

        //Circle Image click

        circleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                length2_editText.setVisibility(TextView.INVISIBLE);
                length2.setVisibility(TextView.INVISIBLE);

                shapeType="circle";
                shape_textView.setText(shapeType);
            }
        });




        //Calculate button

        Button calculate=findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(length1_editText.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please check your input.", Toast.LENGTH_SHORT).show();
                }

                else if(shapeType.equals("triangle")){
                    if(length2_editText.getText().toString().isEmpty()){
                        Toast.makeText(getApplicationContext(), "Please check your input", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int s1 = Integer.parseInt(length1_editText.getText().toString());
                        int s2 = Integer.parseInt(length2_editText.getText().toString());

                        area = s1 * s2 * 0.5;
                    }
                }

                else if(shapeType.equals("square")     ){
                    int s1=Integer.parseInt(length1_editText.getText().toString());

                    area=s1*s1;
                }

                else if(shapeType.equals("circle")     ){
                    int s1=Integer.parseInt(length1_editText.getText().toString());

                    area=s1*s1*3.1416;
                }
                else {
                    Toast.makeText(getApplicationContext(), "Select a shape!!!", Toast.LENGTH_SHORT).show();
                }

                answer_editText.setText(area+"");
            }
        });



        // Clear button
        Button clear_button=findViewById(R.id.clear);

        clear_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                length1_editText.setText("");
                length2_editText.setText("");
                length2_editText.setVisibility(TextView.VISIBLE);
                length2.setVisibility(TextView.VISIBLE);
                shape_textView.setText("Select a shape");
                answer_editText.setText("");
            }
        });




    }
}