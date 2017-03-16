package com.atomic.attack.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import com.atomic.attack.entity.Entity;
import com.atomic.attack.entity.EntityManager;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class LevelLoader {

    public static final String TAG = GameScreen.class.getName();

    public void loadLevel(TiledMap map, EntityManager entityManager) {
        Array<Entity> enemies = new Array<Entity>();

        MapLayers layers = map.getLayers();

        for (int i = 0; i < layers.getCount(); i++) {
            MapLayer layer = layers.get(i);

            for (MapObject object : layer.getObjects().getByType(RectangleMapObject.class)) {
                try {
                    Rectangle rect = ((RectangleMapObject) object).getRectangle();

                    Class<?> c = Class.forName("com.atomic.attack.entity.enemies." + layer.getName());
                    Constructor<?> cons = c.getConstructor(new Class[] { Vector2.class, EntityManager.class } );
                    //Constructor<?> cons = c.getConstructors()[0];
                    com.atomic.attack.entity.Enemy enemy = (com.atomic.attack.entity.Enemy)cons.newInstance(new Vector2((rect.getX() + rect.getWidth() / 2), (rect.getY() + rect.getHeight() / 2)), entityManager);
                    enemies.add(enemy);

                }catch (NullPointerException e) {
                    Gdx.app.debug(TAG, "Map Objects not loaded: " + layer.toString());
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }

        entityManager.addEntities(enemies);
    }

}
