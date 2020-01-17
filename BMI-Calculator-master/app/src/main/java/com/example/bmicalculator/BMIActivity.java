package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

import static android.widget.Toast.LENGTH_LONG;

public class BMIActivity extends AppCompatActivity {
    private String bmiCategory = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        final TextView output_TextView = findViewById(R.id.value);
        final EditText weight_editText = findViewById(R.id.editWeight);
        final EditText heightFeet_editText = findViewById(R.id.editFeet);
        final EditText heightInches_editText = findViewById(R.id.editInch);
        final TextView bmiCategory_textView =  findViewById(R.id.category);


        output_TextView.setVisibility(View.INVISIBLE);
        bmiCategory_textView.setVisibility(View.INVISIBLE);




        Button calculate_button = (Button) findViewById(R.id.btnCalculate);
        calculate_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //validations
                if(weight_editText.getText().toString().isEmpty() || heightFeet_editText.getText().toString().isEmpty()
                        || heightInches_editText.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(),  "Invalid Inputs!!!! ", LENGTH_LONG).show();

                    if(weight_editText.getText().toString().isEmpty())
                    {weight_editText.setError("Invalid Weight!!");}

                    if(heightFeet_editText.getText().toString().isEmpty())
                    {heightFeet_editText.setError("Invalid Height-Feet!!");}

                    if(heightInches_editText.getText().toString().isEmpty())
                    {heightInches_editText.setError("Invalid Height-Inches!!");}

                    output_TextView.setVisibility(View.INVISIBLE);
                    bmiCategory_textView.setVisibility(View.INVISIBLE);
                    return;
                }

                //calculate BMI
                float weight = Float.parseFloat(weight_editText.getText().toString());
                float heightFeet = Float.parseFloat(heightFeet_editText.getText().toString());
                float heightInches = Float.parseFloat(heightInches_editText.getText().toString());
                float height = (heightFeet*12)+heightInches;
                Double output = (double)weight*703/(height*height);



                //edge-cases for Nan / infinite
                if(output.isNaN() || output.isInfinite() || output==0){
                    Toast.makeText(getApplicationContext(),  "Invalid Inputs!!!! ", LENGTH_LONG).show();
                    output_TextView.setVisibility(View.INVISIBLE);
                    bmiCategory_textView.setVisibility(View.INVISIBLE);
                    return;
                }

                Toast.makeText(getApplicationContext(),  "BMI Calculated", LENGTH_LONG).show();

                //finding category
                if(output<=18.5) bmiCategory = "Underweight";
                else if(output > 18.5 && output <= 24.9) bmiCategory = "Normal weight";
                else if(output > 24.9 && output <= 29.9) bmiCategory = "Overweight";
                else if(output>=30) bmiCategory = "Obese";

                output =Double.parseDouble(new DecimalFormat("##.#").format(output));
                //String msg="YOur BMI: ";
                output_TextView.setVisibility(View.VISIBLE);
                output_TextView.setText("Your BMI: "+output);
                bmiCategory_textView.setVisibility(View.VISIBLE);
                bmiCategory_textView.setText("You are " + bmiCategory);

            }
        });

    }
}
