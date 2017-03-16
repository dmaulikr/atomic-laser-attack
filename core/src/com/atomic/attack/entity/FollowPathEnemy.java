package com.atomic.attack.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ai.steer.Steerable;
import com.badlogic.gdx.ai.steer.SteeringAcceleration;
import com.badlogic.gdx.ai.steer.behaviors.FollowPath;
import com.badlogic.gdx.ai.steer.utils.paths.LinePath;
import com.badlogic.gdx.ai.utils.Location;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.atomic.attack.SteeringUtils;

public abstract class FollowPathEnemy extends Enemy implements Steerable<Vector2> {

    protected boolean tagged;
    protected float angularVelocity = 2;
    protected float boundingRadius = 20;
    protected Vector2 linearVelocity;

    protected float maxLinearSpeed = 200;
    protected float maxLinearAcceleration = 400;
    protected float maxAngularSpeed = 5;
    protected float maxAngularAcceleration = 10;

    protected boolean openPath = true;

    LinePath<Vector2> linePath;
    FollowPath<Vector2, LinePath.LinePathParam> followPathSB;

    //SteeringBehavior<Vector2> steeringBehavior;
    private static final SteeringAcceleration<Vector2> steeringOutput = new SteeringAcceleration<Vector2>(new Vector2());

    public void createPath(Array<Vector2> wayPoints, float pathOffset, float predictionTime) {
        linePath = new LinePath<Vector2>(wayPoints, openPath);
        followPathSB = new FollowPath<Vector2, LinePath.LinePathParam>(this, linePath, pathOffset, predictionTime) //
                // Setters below are only useful to arrive at the end of an open path
                .setTimeToTarget(0.5f) //
                .setArrivalTolerance(0.001f) //
                .setDecelerationRadius(0);

    }

    public FollowPathEnemy(Vector2 pos, com.atomic.attack.entity.EntityManager entityManager, TextureRegion reg) {
        super(reg, pos, entityManager);
        this.linearVelocity = new Vector2();

        hasShadow = true;
        //createPath(wayPoints);


    }

    @Override
    public void update(float dt) {
        updateMain(dt);
        float speed = maxLinearSpeed;
        float acceleration = maxLinearAcceleration;
        maxLinearSpeed = speed * 60 * dt;
        maxLinearAcceleration = acceleration * 60 * dt;

        //Steering behaviour
        if (followPathSB != null) {
            followPathSB.calculateSteering(steeringOutput);
            applySteering(steeringOutput, Gdx.graphics.getDeltaTime());
        }
        maxLinearSpeed = speed;
        maxLinearAcceleration = acceleration;
    }

    public abstract void updateMain(float dt);

    private void applySteering (SteeringAcceleration<Vector2> steering, float time) {
        // Update position and linear velocity. Velocity is trimmed to maximum speed
        pos.mulAdd(linearVelocity, time);
        linearVelocity.mulAdd(steering.linear, time).limit(getMaxLinearSpeed());


    }

    @Override
    public boolean checkBottom(com.atomic.attack.camera.OrthoCamera camera) {
        return checkBottomAndSides(camera);
    }

    @Override
    public Vector2 getLinearVelocity() {
        return linearVelocity;
    }

    @Override
    public float getAngularVelocity() {
        return angularVelocity;
    }

    @Override
    public float getBoundingRadius() {
        return boundingRadius;
    }

    @Override
    public boolean isTagged() {
        return tagged;
    }

    @Override
    public void setTagged(boolean tagged) {
        this.tagged = tagged;
    }

    @Override
    public float getZeroLinearSpeedThreshold() {
        return 0;
    }

    @Override
    public void setZeroLinearSpeedThreshold(float value) {

    }

    @Override
    public float getMaxLinearSpeed() {
        return maxLinearSpeed;
    }

    @Override
    public void setMaxLinearSpeed(float maxLinearSpeed) {
        this.maxLinearSpeed = maxLinearSpeed;
    }

    @Override
    public float getMaxLinearAcceleration() {
        return maxLinearAcceleration;
    }

    @Override
    public void setMaxLinearAcceleration(float maxLinearAcceleration) {
        this.maxLinearAcceleration = maxLinearAcceleration;
    }

    @Override
    public float getMaxAngularSpeed() {
        return maxAngularSpeed;
    }

    @Override
    public void setMaxAngularSpeed(float maxAngularSpeed) {
        this.maxAngularSpeed = maxAngularSpeed;
    }

    @Override
    public float getMaxAngularAcceleration() {
        return maxAngularAcceleration;
    }

    @Override
    public void setMaxAngularAcceleration(float maxAngularAcceleration) {
        this.maxAngularAcceleration = maxAngularAcceleration;
    }

    @Override
    public Vector2 getPosition() {
        return pos;
    }

    @Override
    public float getOrientation() {
        return 0;
    }

    @Override
    public void setOrientation(float orientation) {

    }

    @Override
    public float vectorToAngle(Vector2 vector) {
        return SteeringUtils.vectorToAngle(vector);
    }

    @Override
    public Vector2 angleToVector(Vector2 outVector, float angle) {
        return SteeringUtils.angleToVector(outVector, angle);
    }

    @Override
    public Location<Vector2> newLocation() {
        return null;
    }
}
