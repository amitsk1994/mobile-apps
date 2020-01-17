package com.example.threadhandler;
//Group 15
//Amit Suresh Kalyanpur
//Kishan Singh
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    Handler hand;
    int cnt;
    SeekBar seekbar;
    ProgressBar pgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        seekbar=findViewById(R.id.seekBar);
        seekbar.setMax(10);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                    TextView times=findViewById(R.id.editText);
                    times.setText(i+" Times");
                    cnt=i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pgr=findViewById(R.id.progressBar);
        pgr.setVisibility(View.INVISIBLE);
        hand=new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {

                switch (msg.what)
                {

                    case(DoWork.ends):
                        Log.d("str","Work Ended");
                        pgr.setVisibility(View.INVISIBLE);
                        TextView editmin=findViewById(R.id.editText3);
                        editmin.setText(msg.getData().getDouble("minimum")+"");
                        TextView editmax=findViewById(R.id.editText4);
                        editmax.setText(msg.getData().getDouble("maximum")+"");
                        TextView editavg=findViewById(R.id.editText5);
                        editavg.setText(msg.getData().getDouble("average")+"");
                        break;

                }


                return false;

            }

        });

        final ExecutorService es= Executors.newFixedThreadPool(2);
        Button btn=findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if(seekbar.getProgress()==0){
                    Toast.makeText(getApplicationContext() , "Select complexity greater than 0!" , Toast.LENGTH_LONG).show();
                    return ;
                }
                es.execute(new DoWork());
                pgr.setVisibility(View.VISIBLE);

            }
        });
    }

    public class DoWork implements Runnable{

        double avg=0;
        double minimum=0;
        double maximum=0;


        static  final String Progress_key="end";
        static final int ends=2;
        @Override
        public void run() {
            Message startMSG= new Message();

            hand.sendMessage(startMSG);

            List<Double> l=HeavyWork.getArrayNumbers(cnt);
            Collections.sort(l);
            for(Double d:l){
                avg+=d;
            }
            avg=avg/l.size();
            minimum=l.get(0);
            maximum=l.get(l.size()-1);

            Message endMSG= new Message();
            Bundle bd=new Bundle();
            bd.putDouble("minimum", minimum);
            bd.putDouble("maximum", maximum);
            bd.putDouble("average", avg);
            endMSG.setData(bd);
            endMSG.what=ends;
            hand.sendMessage(endMSG);
        }
    }

}
