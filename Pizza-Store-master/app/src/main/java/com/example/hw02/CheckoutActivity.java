package com.example.hw02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.List;

import static com.example.hw02.MainActivity.REQ_CODE;

public class CheckoutActivity extends AppCompatActivity {

    TextView basePrice_textView;
    TextView toppingsPrice_textView;
    TextView toppingsList_textView;
    TextView deliveryCost_textView;
    TextView totalCost_textView;
    Button btn_finish;

    public static final double   BASE_PRICE =  6.50;
    public static final double   DELIVERY_COST = 4.00;
    public static final double   PER_TOPPING_COST = 1.50;

    Pizza pizzaOrder;
    Boolean delivery;
    List<String> toppings;
    double toppingsPrice, deliveryCost, totalCost;
    String top;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        setTitle("Checkout");

        basePrice_textView = findViewById(R.id.textView4_basePrice);
        toppingsPrice_textView = findViewById(R.id.textView5_toppingsPrice);
        toppingsList_textView = findViewById(R.id.textView6_toppingsList);
        deliveryCost_textView = findViewById(R.id.textView_deliveryCost);
        totalCost_textView = findViewById(R.id.textView10_totalCost);
        btn_finish = findViewById(R.id.button_finish);



           pizzaOrder = (Pizza) getIntent().getExtras().getSerializable(MainActivity.PIZZA_ORDER);

        //take values from pizza order objects
        delivery = pizzaOrder.isChecked();
        toppings = pizzaOrder.getL();
        toppingsPrice = pizzaOrder.getL().size() * PER_TOPPING_COST;
        deliveryCost = delivery ? DELIVERY_COST : 0;
        totalCost = BASE_PRICE + toppingsPrice + deliveryCost;
          top = toppings.toString();
        top = top.substring(1, top.length() - 1);
        //set values to text Views
        basePrice_textView.setText(BASE_PRICE + "$");
        toppingsPrice_textView.setText(toppingsPrice + "$");
        toppingsList_textView.setText(top);
        deliveryCost_textView.setText(deliveryCost + "$");
        totalCost_textView.setText(totalCost + "$");


        //finish button

        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goBackIntent = new Intent();
                goBackIntent.putExtra(MainActivity.PIZZA_ORDER, pizzaOrder);
                setResult(RESULT_OK, goBackIntent);
                finish();


            }
        });

    }}
