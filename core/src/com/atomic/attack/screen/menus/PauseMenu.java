package com.atomic.attack.screen.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.StretchViewport;

public class PauseMenu extends com.atomic.attack.screen.Screen {

    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;
    private Table table;
    private TextButton buttonResume;
    private TextButton buttonExit;
    private Label heading;

    private com.atomic.attack.screen.Pausable gameScreen;

    public PauseMenu(com.atomic.attack.screen.Pausable gameScreen) {
        this.gameScreen = gameScreen;
        create();
    }

    @Override
    public void create() {
        if (com.atomic.attack.Assets.instance.mainMenu == null || com.atomic.attack.Assets.instance.mainMenu.mainMenu == null) {
            com.atomic.attack.Assets.instance.loadMenu(new AssetManager());
        }
        atlas = com.atomic.attack.Assets.instance.mainMenu.mainMenu;

        stage = new Stage(new StretchViewport(com.atomic.attack.MainGame.WIDTH, com.atomic.attack.MainGame.HEIGHT));
        skin = new Skin(atlas);

        Image background = new Image(atlas.findRegion("pauseMenuBackground"));
        //background.scaleBy(0.6f);
        background.setBounds(0 + com.atomic.attack.MainGame.WIDTH / 4, com.atomic.attack.MainGame.HEIGHT / 4,
                com.atomic.attack.MainGame.WIDTH - com.atomic.attack.MainGame.WIDTH / 2f, com.atomic.attack.MainGame.HEIGHT - com.atomic.attack.MainGame.HEIGHT / 2f);


        table = new Table(skin);
        //table.setBounds(0, 0, MainGame.WIDTH, MainGame.HEIGHT);
        table.setBounds(0 + com.atomic.attack.MainGame.WIDTH / 4, com.atomic.attack.MainGame.HEIGHT / 4,
                com.atomic.attack.MainGame.WIDTH - com.atomic.attack.MainGame.WIDTH / 2f, com.atomic.attack.MainGame.HEIGHT - com.atomic.attack.MainGame.HEIGHT / 2f);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonSmall");

        com.atomic.attack.GameFont font = new com.atomic.attack.GameFont(16);
        textButtonStyle.font = font.font;

        buttonResume = new TextButton("RESUME", textButtonStyle);
        buttonExit = new TextButton("MAIN MENU", textButtonStyle);

        com.atomic.attack.GameFont headingFont = new com.atomic.attack.GameFont(18);
        Label.LabelStyle headingStyle = new Label.LabelStyle(headingFont.font, Color.WHITE);
        heading = new Label("PAUSE", headingStyle);
        heading.setFontScale(2);

        stage.addActor(background);
        //table.add(background);
        table.add(heading).colspan(2).center();
        table.row().pad(20f);
        table.add(buttonResume);
        table.add(buttonExit);

        //table.debug();

        stage.addActor(table);

        buttonResume.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.unPauseGame();
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                gameScreen.exitScreen();
            }
        });

        Gdx.input.setInputProcessor(stage);

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            gameScreen.unPauseGame();
        }
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {

        stage.act(Gdx.graphics.getRawDeltaTime());

        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        stage.dispose();
        //skin.dispose();
        //atlas.dispose();
        disposed = true;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {
        create();
    }
}
