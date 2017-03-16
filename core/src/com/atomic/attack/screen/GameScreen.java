package com.atomic.attack.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.atomic.attack.camera.ScreenShake;


public class GameScreen extends Screen implements Pausable {

    private final String TAG = GameScreen.class.getSimpleName();
    protected float STARTING_POSITION_Y = 300f;
    protected float STARTING_POSITION_X = 300f;

    private com.atomic.attack.entity.EntityManager entityManager;
    private com.atomic.attack.camera.OrthoCamera statCamera;
    private com.atomic.attack.camera.OrthoCamera mapCamera;
    private com.atomic.attack.GameFont font;
    public float deltaTimeScale = 1f;

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    private ShapeRenderer shapeRenderer;
    private boolean pausedState = false;

    public ScreenShake screenShake;

    private com.atomic.attack.screen.menus.PauseMenu pauseMenu;

    private float endCoords;
    protected float cameraSpeed = 0.5f;

    private String mapPath;
    protected float cameraMovementModifier = 15f;

    private Music music;
    private float musicPosition;
    private String musicPath;
    public String level_Name;


    public GameScreen(String mapPath, float endCoords, String musicPath, String level_Name) {
        this.mapPath = mapPath;
        this.endCoords = endCoords;
        this.musicPath = musicPath;
        this.level_Name = level_Name;

        com.atomic.attack.MainGame.tracker.sendEvent(com.atomic.attack.Constants.PARAM_LEVEL_ID , level_Name, com.atomic.attack.Constants.EVENT_LEVEL_STARTED + "_" + level_Name);
    }


    @Override
    public void create() {
        //Assets.instance.assetManager = new AssetManager();
        com.atomic.attack.Assets.instance.loadGame(new AssetManager(), true);
        music = Gdx.audio.newMusic(Gdx.files.internal(musicPath));

        statCamera = new com.atomic.attack.camera.OrthoCamera();
        mapCamera = new com.atomic.attack.camera.OrthoCamera(cameraSpeed);
        mapCamera.setPosition(STARTING_POSITION_X, STARTING_POSITION_Y);
        statCamera.setPosition(com.atomic.attack.MainGame.WIDTH / 2, com.atomic.attack.MainGame.HEIGHT / 2);

        map = new TmxMapLoader().load(mapPath);
        renderer = new OrthogonalTiledMapRenderer(map, 1.0f);

        screenShake = new ScreenShake();

        entityManager = new com.atomic.attack.entity.EntityManager(mapCamera, statCamera, this, new Vector2(STARTING_POSITION_X, STARTING_POSITION_Y - com.atomic.attack.MainGame.HEIGHT / 3), screenShake);

        LevelLoader levelLoader = new LevelLoader();
        levelLoader.loadLevel(map, entityManager);

        font = new com.atomic.attack.GameFont(24);

        statCamera.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        mapCamera.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        shapeRenderer = new ShapeRenderer();

        /*
        for (int i = 0; i < 3000; i++) {
            entityManager.addEntity(new Heli(new Vector2(300, 500), entityManager));
        }
        */

        com.atomic.attack.MusicManager.instance.setMusic(music);
        com.atomic.attack.MusicManager.instance.startMusic();

    }

    @Override
    public void handleInput() {
        if (pausedState) {
            pauseMenu.handleInput();
        }
        else {
            if (Gdx.input.isTouched()) {
                Vector2 targetPos = mapCamera.unprojectCoordinates(Gdx.input.getX(), Gdx.input.getY());

                targetPos.sub(entityManager.player.getMiddleX(), entityManager.player.mouseOffset);
                Vector2 target = entityManager.findVectorToPlayerPos(targetPos);
                target.nor();

                target.scl(-1 * com.atomic.attack.UpgradeManager.instance.getCurrentThrusterSpeed() * dt * 60);   //Scaled by player's moving speed

                if (entityManager.player.getPos().dst(targetPos) > com.atomic.attack.UpgradeManager.instance.getCurrentThrusterSpeed() * dt * 60) {
                    entityManager.player.setPos(entityManager.player.getPos().add(target));
                }
                else {
                    entityManager.player.setPos(targetPos);
                }

                //deltaTimeScale = 0.5f;
            }
            //else deltaTimeScale = 1f;

            if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                pauseGame();
            }
        }
    }

    public void pauseGame() {
        if (!pausedState) {
            musicPosition = com.atomic.attack.MusicManager.instance.pauseMusic();
            pausedState = true;
            pauseMenu = new com.atomic.attack.screen.menus.PauseMenu(this);
        }
    }

    public void unPauseGame() {
        if (pausedState) {
            com.atomic.attack.MusicManager.instance.resumeMusic(musicPosition);
            pausedState = false;
            pauseMenu.dispose();
            Gdx.app.debug(TAG, pauseMenu.getClass().getSimpleName() + " disposed");
        }
    }

    public void exitScreen() {
        ScreenManager.transitionScreen(new com.atomic.attack.screen.menus.LevelSelectMenu());
    }

    @Override
    public void update(float deltaTime) {
        if (mapCamera.getPos().y >= endCoords) {
            entityManager.levelWon();
        }
        if (!pausedState) {
            this.dt = deltaTime * deltaTimeScale;

            statCamera.update();

            entityManager.update(dt);
            entityManager.updateCollisionPolygons();

            Vector2 playerPos = entityManager.player.getPos().cpy();
            mapCamera.updateY(dt);
            mapCamera.updateX(STARTING_POSITION_X, playerPos, cameraMovementModifier);

            screenShake.update(dt, mapCamera);

            mapCamera.update();
        }

    }


    @Override
    public void render(SpriteBatch sb) {

        renderer.setView(mapCamera);
        //renderer.getBatch().setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_COLOR);
        renderer.render();


        sb.setProjectionMatrix(mapCamera.combined);
        sb.begin();

        entityManager.renderEntityShadow(sb);
        entityManager.render(sb);       //Entity rendering

        sb.setProjectionMatrix(statCamera.combined);       //STATIC RENDERING AFTER THIS

        font.draw(sb, "Score: " + com.atomic.attack.MainGame.score, new Vector2(0, com.atomic.attack.MainGame.HEIGHT));
        entityManager.statRender(sb);

        sb.end();

        /*
        if (MainGame.debug == true) {
            shapeRenderer.setProjectionMatrix(mapCamera.combined);
            shapeRenderer.setAutoShapeType(true);
            shapeRenderer.begin();

            entityManager.renderCollisionPolygons(shapeRenderer);

            shapeRenderer.end();
        }
        */

        if (pausedState) {
            pauseMenu.render(sb);
        }


    }

    @Override
    public void resize(int width, int height) {
        statCamera.resize(width, height);
        mapCamera.resize(width, height);
    }

    @Override
    public void dispose() {
        entityManager.dispose();
        renderer.dispose();
        map.dispose();
        com.atomic.attack.MusicManager.instance.dispose();
        if (pausedState) {
            pauseMenu.dispose();
        }
        disposed = true;
    }

    @Override
    public void pause() {
        pauseGame();
    }

    @Override
    public void resume() {
        //Assets.instance.assetManager = new AssetManager();
        com.atomic.attack.Assets.instance.loadGame(new AssetManager(), true);
        pauseMenu = new com.atomic.attack.screen.menus.PauseMenu(this);
    }

}
