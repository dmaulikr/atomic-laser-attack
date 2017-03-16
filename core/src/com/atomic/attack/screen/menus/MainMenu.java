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
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenu extends com.atomic.attack.screen.Screen {
    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;
    private Table table;
    private TextButton buttonPlay;
    private TextButton buttonCredits;
    private TextButton buttonExit;

    private Label heading;

    @Override
    public void create() {
        if (com.atomic.attack.Assets.instance.mainMenu == null || com.atomic.attack.Assets.instance.mainMenu.mainMenu == null) {
            com.atomic.attack.Assets.instance.loadMenu(new AssetManager());
        }
        atlas = com.atomic.attack.Assets.instance.mainMenu.mainMenu;

        stage = new Stage(new FitViewport(com.atomic.attack.MainGame.WIDTH, com.atomic.attack.MainGame.HEIGHT));
        Gdx.input.setInputProcessor(stage);
        skin = new Skin(atlas);

        Image background = new Image(atlas.findRegion("MainMenuBackground"));
        //background.scaleBy(0.6f);
        background.setBounds(0, 0, com.atomic.attack.MainGame.WIDTH, com.atomic.attack.MainGame.HEIGHT);

        table = new Table(skin);
        table.setBounds(0, 0, com.atomic.attack.MainGame.WIDTH, com.atomic.attack.MainGame.HEIGHT);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonSmall");

        com.atomic.attack.GameFont font = new com.atomic.attack.GameFont(18);
        textButtonStyle.font = font.font;

        buttonPlay = new TextButton("PLAY", textButtonStyle);
        buttonCredits = new TextButton("CREDITS", textButtonStyle);
        buttonExit = new TextButton("EXIT", textButtonStyle);


        com.atomic.attack.GameFont hFont = new com.atomic.attack.GameFont(48);

        Label.LabelStyle headingStyle = new Label.LabelStyle(hFont.font, Color.WHITE);
        heading = new Label(com.atomic.attack.Constants.TITLE, headingStyle);
        //heading.setFontScale(1);

        //heading.setPosition(100, 100);

        stage.addActor(background);
        table.add(heading);
        table.row();
        table.add(buttonPlay).pad(10f);
        table.row();
        table.add(buttonCredits).pad(10f);
        table.row();
        table.add(buttonExit).pad(10f);

        //table.debug();

        stage.addActor(table);

        buttonPlay.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                com.atomic.attack.screen.ScreenManager.transitionScreen(new LevelSelectMenu());
            }
        });

        buttonCredits.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                com.atomic.attack.screen.ScreenManager.transitionScreen(new CreditsMenu());
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (!com.atomic.attack.MusicManager.instance.isPlaying()) {
            com.atomic.attack.MusicManager.instance.setMusic(Gdx.audio.newMusic(Gdx.files.internal(com.atomic.attack.Constants.MENU_MUSIC)));
            com.atomic.attack.MusicManager.instance.startMusic();
        }
    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
        }
    }

    @Override
    public void update(float dt) {
    }

    @Override
    public void render(SpriteBatch sb) {
        stage.act(Gdx.graphics.getDeltaTime());
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
        com.atomic.attack.MusicManager.instance.pauseMusic();
    }

    @Override
    public void resume() {
        create();
    }

}
