package com.example.hw02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final String PIZZA_ORDER = "pizzaOrder";
    public static final int REQ_CODE = 100 ;



    boolean checked;
    int number_of_toppings=0;
    List<String> list = new ArrayList();
    int id, toppingCount = 0;
    Pizza pizza;
    CheckBox checkBox ;
    GridLayout grid;
    Button clear;
    ProgressBar prgBar ;
//    String[]  items;={"Bacon","Cheese","Garlic","Green Pepper","Mushroom","Olives","Onions","Red peppers"};
    String[] items ;


    HashMap<Integer, Integer> drawableHashMap = new HashMap<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //item values

        items = getResources().getStringArray(R.array.items);

        //input values for hashmap

        drawableHashMap.put(0,R.drawable.bacon);
        drawableHashMap.put(1,R.drawable.cheese);
        drawableHashMap.put(2,R.drawable.garlic);
        drawableHashMap.put(3,R.drawable.green_pepper);
        drawableHashMap.put(4,R.drawable.mashroom);
        drawableHashMap.put(5,R.drawable.olive);
        drawableHashMap.put(6,R.drawable.onion);
        drawableHashMap.put(7,R.drawable.red_pepper);



        Button addTopping=findViewById(R.id.buttonTopping);
        grid=findViewById(R.id.gridlayout);
        grid.removeAllViews();

        prgBar=findViewById(R.id.progressBar);
        prgBar.setMax(10);

        //TOPPINGS CODE
        addTopping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder toppingAlert=new AlertDialog.Builder(MainActivity.this);

                toppingAlert.setTitle("Choose a Topping").setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final   ImageButton img= new ImageButton(MainActivity.this);


                        GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams();

                        layoutParams.setGravity(Gravity.CENTER);

                        img.setMinimumWidth(175);
                        img.setMinimumHeight(175);
                        img.setScaleType(ImageView.ScaleType.FIT_XY);
                        img.setBackgroundColor(0);
                        id=i;

                        if(toppingCount==10){
                            Toast.makeText(MainActivity.this, "Cannot add more toppings", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        list.add(items[i]);



                        if(drawableHashMap.containsKey(i))
                        {
                            img.setImageResource(drawableHashMap.get(i));
                            img.setTag(items[i]);
                            toppingCount++;
                        }
//                        if(i==0){
////
//                            img.setImageResource(R.drawable.bacon);
//                            img.setTag("Bacon");
//                            toppingCount++;
//
//
//
//
//                        }
//                        else if(i==1){
////
//                            img.setImageResource(R.drawable.cheese);img.setTag("Cheese");
//                            toppingCount++;
//
//
//                        }
//                        else if(i==2){
//
//                            img.setImageResource(R.drawable.garlic);img.setTag("Garlic");
//                            toppingCount++;
//
//
//                        }
//                        else if(i==3){
//
//                            img.setImageResource(R.drawable.green_pepper);img.setTag("Green Pepper");
//                            toppingCount++;
//
//
//                        }
//                        else if(i==4){
//
//                            img.setImageResource(R.drawable.mashroom);img.setTag("Mushroom");
//                            toppingCount++;
//
//
//                        }
//                        else if(i==5){
//
//                            img.setImageResource(R.drawable.olive);img.setTag("Olives");
//                            toppingCount++;
//
//                        }
//                        else if(i==6){
//
//                            img.setImageResource(R.drawable.onion);img.setTag("Onions");
//                            toppingCount++;
//
//                        }
//                        else if(i==7){
//
//                            img.setImageResource(R.drawable.red_pepper);img.setTag("Red peppers");
//                            toppingCount++;
//
//                        }

                        grid.addView(img);
                        prgBar.setProgress(toppingCount);


                        img.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                toppingCount--;
                                prgBar.setProgress(toppingCount);
                                grid.removeView(img);
                                list.remove(view.getTag());
//                                Toast.makeText(getApplicationContext(), view.getTag() +"", Toast.LENGTH_LONG).show();

                            }
                        });

                    }
                }).show();

            }
        });


        checkBox=findViewById(R.id.checkBoxDelivery);


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true)checked=true;
                else checked=false;

            }
        });


        clear=findViewById(R.id.buttonClearPizza);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grid.removeAllViews();
                prgBar.setProgress(0);
                checkBox.setChecked(false);
                checked=false;
                list.clear();
                toppingCount=0;
            }
        });

        number_of_toppings=toppingCount; //to be passed to pizza object




        Button checkout_btn = findViewById(R.id.buttonCheckout);
        checkout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pizza=new Pizza(number_of_toppings, list,checked);

                Intent intent= new Intent(MainActivity.this , CheckoutActivity.class);
             //   Toast.makeText(getApplicationContext(), pizza+"", Toast.LENGTH_LONG).show();

                intent.putExtra(MainActivity.PIZZA_ORDER  , pizza);
                startActivityForResult(intent ,REQ_CODE );
            }


        });






    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //here

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {

                Pizza returned_PizzaOrder = (Pizza) data.getExtras().getSerializable(MainActivity.PIZZA_ORDER);
                if (pizza.equals(returned_PizzaOrder)) {
                    Toast.makeText(getApplicationContext(), "Successfully Returned!", Toast.LENGTH_LONG).show();

                    grid.removeAllViews();
                    prgBar.setProgress(0);
                    checkBox.setChecked(false);
                    checked=false;
                    checkBox.setChecked(false);
                    list.clear();
                    toppingCount=0;
                }
            } else if (resultCode == RESULT_CANCELED) {
              //  Toast.makeText(getApplicationContext(), "Value mismatch!!?", Toast.LENGTH_LONG).show();
            }

        }
    }
}
