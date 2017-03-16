package com.atomic.attack;

import com.badlogic.gdx.utils.Timer;

import java.util.ArrayList;
import java.util.List;

public class RawFPSCounter {

    private double calculatedFPS;
    private long time;
    private double FPS;
    private long averageFPS = 0;
    private List<Double> fpsArray;

    public RawFPSCounter() {
        fpsArray = new ArrayList<Double>();

        Timer.schedule(new Timer.Task() {
            @Override
            public void run() {
                for (double fps : fpsArray) {
                    averageFPS += fps;
                }
                if (!fpsArray.isEmpty()) averageFPS = averageFPS / fpsArray.size();
                fpsArray.clear();
                System.out.println("Raw FPS: " + averageFPS);
            }
        }, 1, 1f);
    }


    public void startRawFPS() {
        time = System.nanoTime();

        //return "FPS: " + calculatedFPS;
    }

    public void endRawFps() {
        calculatedFPS = (System.nanoTime() - time);
        FPS = (1 / calculatedFPS) * 1000000000;

        fpsArray.add(FPS);

    }
}
