package com.atomic.attack.camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import java.util.Random;

/**
 * @author brokenbeach
 * @since  10-04-2016
 *
 * A class for creating a screenshake effect in libGDX games.
 *
 * Call 'shake(...)' to start a screen shake, and make sure that 'update(...)' is called once per frame after the
 * camera's position is set, but before 'camera.update()' is called.
 *
 */
public class ScreenShake {

    private Random random;
    private float elapsed;
    private float duration;
    private float power;

    public ScreenShake(){

        random = new Random();
        elapsed = 0;
        duration = 0;
        power = 0;
    }

    /**
     * Start the screen shaking with a given power and duration
     *
     * @param power How powerful the shaking should be
     * @param duration Time in seconds the screen should shake
     */
    public void shake(float power, float duration) {
        this.power = power;
        this.elapsed = 0;
        this.duration = duration * 1000;
    }

    /**
     * @param delta Time in seconds since last update
     * @param camera The game's camera
     */
    public void update(float delta, OrthographicCamera camera){

        /*
         * If the screen is shaking, but hasn't finished
         */
        if(elapsed < duration) {
            /*
             * Calculate the amount of shake based on how long it has been shaking already
             */
            elapsed += delta * 1000;
            float currentPower = power * ((duration - elapsed) / duration);
            float x = (random.nextFloat() - 0.5f) * 2 * currentPower;
            float y = (random.nextFloat() - 0.5f) * 2 * currentPower;
            camera.translate(-x, -y);
        }
    }

}