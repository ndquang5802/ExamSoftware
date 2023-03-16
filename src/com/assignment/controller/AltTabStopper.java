package com.assignment.controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class AltTabStopper implements Runnable {
    private boolean working = true;
    private JFrame frame;

    public AltTabStopper(JFrame frame)
    {
        this.frame = frame;
    }

    public void stop()
    {
        working = false;
    }

    public static AltTabStopper create(JFrame frame)
    {
        AltTabStopper stopper = new AltTabStopper(frame);
        new Thread(stopper, "Alt-Tab Stopper").start();
        return stopper;
    }

    public void run()
    {
        try
        {
            Robot robot = new Robot();
            while (working)
            {
                robot.keyRelease(KeyEvent.VK_ALT);
                robot.keyRelease(KeyEvent.VK_TAB);
                frame.requestFocus();
                try {
                    Thread.sleep(10);
                } catch(Exception ignored) {

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}
