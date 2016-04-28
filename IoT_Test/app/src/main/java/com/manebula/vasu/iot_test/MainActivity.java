package com.manebula.vasu.iot_test;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Chronometer;
import android.view.MotionEvent;
import android.support.v4.view.GestureDetectorCompat;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.RunnableFuture;

public class MainActivity extends AppCompatActivity {

    private TextView oDisplayLabel_;
    private GestureDetectorCompat gdc;
    private final Handler handler_ = new Handler();
    private final Timer timer_ = new Timer();
    private Runnable rDispReset_;

    private Socket ioLaptop_;
    private static final int REMOTE_PORT = 5050;
    private static final String REMOTE_IP = "192.168.1.200";
    PrintWriter outLaptop_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        oDisplayLabel_ = (TextView) findViewById(R.id.oDisplayLabel);
        ioLaptop_ = new Socket();

        rDispReset_ = new Runnable() {
            @Override
            public void run() {
                oDisplayLabel_.setText("");
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        Button bGo = (Button) findViewById(R.id.bGo);
        bGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* Open a client connection, first. */
                try {
                    if (ioLaptop_.isConnected() == false) {
                        InetAddress serverAddr = InetAddress.getByName(REMOTE_IP);
                        ioLaptop_ = new Socket(serverAddr, REMOTE_PORT);
                        outLaptop_ = new PrintWriter(new BufferedWriter(
                                new OutputStreamWriter(ioLaptop_.getOutputStream())),
                                true);
                    }
                    String str = "Hello, world, over network...";
                    outLaptop_.println(str);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                /* Setup Timer for resetting the display */
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        handler_.post(rDispReset_);
                    }
                };
                timer_.schedule(task, 1000);
                oDisplayLabel_.setText(R.string.btn_clicked_notice);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
