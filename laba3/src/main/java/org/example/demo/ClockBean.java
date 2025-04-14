package org.example.demo;


import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

@Named
@SessionScoped
public class ClockBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String currentTime;

    public ClockBean() {
        System.out.println("ClockBean initializedYOON");
        updateTime();

        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(11000);
                    updateTime();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void updateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss");
        currentTime = formatter.format(new Date());
    }

    public String getCurrentTime() {
        return currentTime;
    }
}
