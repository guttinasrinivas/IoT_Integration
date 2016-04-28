package com.manebula.vasu.iot_test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by gsjy on 28/4/16.
 */
public class RemoteIO implements Runnable {

    private Socket ioLaptop_;
    private static final int REMOTE_PORT = 5050;
    private static final String REMOTE_IP = "192.168.1.200";

    @Override
    public void run() {
        try {
            InetAddress serverAddr = InetAddress.getByName(REMOTE_IP);
            ioLaptop_ = new Socket(serverAddr, REMOTE_PORT);
        } catch (UnknownHostException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }
}
