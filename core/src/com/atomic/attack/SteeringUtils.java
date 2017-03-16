package com.atomic.attack;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

public final class SteeringUtils {
    public static float vectorToAngle(Vector2 vector) {
        return (float) MathUtils.atan2(-vector.x, vector.y);
    }

    public static Vector2 angleToVector (Vector2 outVector, float angle) {
        outVector.x = -(float)MathUtils.sin(angle);
        outVector.y = (float)MathUtils.cos(angle);
        return outVector;
    }

    public static Array<Vector2> spinnerRightDive(Vector2 pos) {
        Array<Vector2> wayPoints = new Array<Vector2>(2);

        wayPoints.add(new Vector2(pos.x, pos.y));
        wayPoints.add(new Vector2(pos.x - 300f, pos.y - 20f));

        //wayPoints.add(new Vector2(pos.x, pos.y));
        //wayPoints.add(new Vector2(pos.x, pos.y - MainGame.HEIGHT / 3));
        //wayPoints.add(new Vector2(pos.x - MainGame.WIDTH, pos.y - MainGame.HEIGHT / 4));

        //System.out.println(mapCamera.unprojectCoordinates(pos.x, pos.y));

        return wayPoints;
    }

    public static Array<Vector2> createRandomPath (int numWaypoints, float minX, float minY, float maxX, float maxY) {
        Array<Vector2> wayPoints = new Array<Vector2>(numWaypoints);

        float midX = (maxX + minX) / 2f;
        float midY = (maxY + minY) / 2f;

        float smaller = Math.min(midX, midY);

        float spacing = MathUtils.PI2 / numWaypoints;

        for (int i = 0; i < numWaypoints; i++) {
            float radialDist = MathUtils.random(smaller * 0.2f, smaller);

            Vector2 temp = new Vector2(radialDist, 0.0f);

            rotateVectorAroundOrigin(temp, i * spacing);

            temp.x += midX;
            temp.y += midY;

            wayPoints.add(temp);
        }

        return wayPoints;
    }

    /** Rotates the specified vector angle rads around the origin */
    private static Vector2 rotateVectorAroundOrigin (Vector2 vector, float radians) {
        final Matrix3 tmpMatrix3 = new Matrix3();
        // Init and rotate the transformation matrix
        tmpMatrix3.idt().rotateRad(radians);

        // Now transform the object's vertices
        return vector.mul(tmpMatrix3);
    }
}
