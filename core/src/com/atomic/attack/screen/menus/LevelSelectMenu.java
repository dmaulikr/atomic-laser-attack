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
import com.atomic.attack.MusicManager;
import com.atomic.attack.screen.gameScreens.Level1;


public class LevelSelectMenu extends com.atomic.attack.screen.Screen {

    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;
    private Table table;
    private TextButton buttonLevel1;
    private TextButton buttonLevel2;
    private TextButton buttonLevel3;
    private TextButton buttonExit;
    private TextButton buttonEasy, buttonMedium, buttonHard;

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

        TextButton.TextButtonStyle levelButtonStyle = new TextButton.TextButtonStyle();
        levelButtonStyle.up = skin.getDrawable("levelButton");

        com.atomic.attack.GameFont font = new com.atomic.attack.GameFont(16);
        textButtonStyle.font = font.font;
        levelButtonStyle.font = font.font;

        buttonLevel1 = new TextButton("LEVEL 1", levelButtonStyle);
        buttonLevel2 = new TextButton("LEVEL 2", levelButtonStyle);
        buttonLevel3 = new TextButton("LEVEL 3", levelButtonStyle);
        buttonExit = new TextButton("MAIN MENU", textButtonStyle);
        buttonEasy = new TextButton("EASY", textButtonStyle);
        buttonMedium = new TextButton("MEDIUM", textButtonStyle);
        buttonHard = new TextButton("HARD", textButtonStyle);

        com.atomic.attack.GameFont headingFont = new com.atomic.attack.GameFont(32);
        Label.LabelStyle headingStyle = new Label.LabelStyle(headingFont.font, Color.WHITE);
        heading = new Label(com.atomic.attack.Constants.LEVEL_SELECT, headingStyle);
        heading.setFontScale(1);

        stage.addActor(background);
        table.add(heading).colspan(3).center();
        table.row().pad(30f);
        table.add(buttonLevel1);
        table.add(buttonLevel2);
        table.add(buttonLevel3);
        table.row();
        table.add(buttonEasy).right();
        table.add(buttonMedium);
        table.add(buttonHard).left();
        table.row();
        table.add(buttonExit).pad(20f).colspan(3).center();

        //table.debug();

        stage.addActor(table);

        buttonLevel1.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                com.atomic.attack.screen.ScreenManager.transitionScreen(new Level1());
            }
        });

        buttonLevel2.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                com.atomic.attack.screen.ScreenManager.transitionScreen(new com.atomic.attack.screen.gameScreens.Level2());
            }
        });

        buttonLevel3.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                com.atomic.attack.screen.ScreenManager.transitionScreen(new com.atomic.attack.screen.gameScreens.Level3());
            }
        });

        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                com.atomic.attack.screen.ScreenManager.transitionScreen(new MainMenu());
            }
        });

        buttonEasy.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                com.atomic.attack.UpgradeManager.instance.setDifficulty(0);
            }
        });

        buttonMedium.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                com.atomic.attack.UpgradeManager.instance.setDifficulty(1);
            }
        });

        buttonHard.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                com.atomic.attack.UpgradeManager.instance.setDifficulty(2);
            }
        });

        resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        if (!MusicManager.instance.isPlaying()) {
            MusicManager.instance.setMusic(Gdx.audio.newMusic(Gdx.files.internal(com.atomic.attack.Constants.MENU_MUSIC)));
            MusicManager.instance.startMusic();
        }

    }

    @Override
    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.BACK) || Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            com.atomic.attack.screen.ScreenManager.transitionScreen(new MainMenu());
        }
    }

    @Override
    public void update(float dt) {
        setLevelButtonColor(com.atomic.attack.UpgradeManager.instance.getDifficulty());

    }

    private void setLevelButtonColor(int lvl) {
        Color color = new Color(1f, 1f, 1f, 1f);
        Color normColor = new Color(1f, 1f, 1, 0.3f);
        if (lvl == 0) {
            buttonEasy.setColor(color);
        }
        else {
            buttonEasy.setColor(normColor);
        }
        if (lvl == 1) {
            buttonMedium.setColor(color);
        }
        else {
            buttonMedium.setColor(normColor);
        }
        if (lvl == 2) {
            buttonHard.setColor(color);
        }
        else {
            buttonHard.setColor(normColor);
        }
    }

    @Override
    public void render(SpriteBatch sb) {

        stage.getBatch().setColor(sb.getColor());
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
        MusicManager.instance.pauseMusic();
    }

    @Override
    public void resume() {
        create();
    }
}
