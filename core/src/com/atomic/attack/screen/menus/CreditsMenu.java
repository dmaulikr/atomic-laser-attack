package com.atomic.attack.screen.menus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class CreditsMenu extends com.atomic.attack.screen.Screen {

    private TextureAtlas atlas;
    private Stage stage;
    private Skin skin;
    private Table scrollTable;
    private TextButton buttonBack;
    private ScrollPane scroller;
    private FileHandle file;

    private Label creditsText;

    @Override
    public void create() {
        if (com.atomic.attack.Assets.instance.mainMenu == null || com.atomic.attack.Assets.instance.mainMenu.mainMenu == null) {
            com.atomic.attack.Assets.instance.loadMenu(new AssetManager());
        }
        atlas = com.atomic.attack.Assets.instance.mainMenu.mainMenu;

        stage = new Stage(new FitViewport(com.atomic.attack.MainGame.WIDTH, com.atomic.attack.MainGame.HEIGHT));
        skin = new Skin(atlas);

        Image background = new Image(atlas.findRegion("MainMenuBackground"));
        background.setBounds(0, 0, com.atomic.attack.MainGame.WIDTH, com.atomic.attack.MainGame.HEIGHT);

        scrollTable = new Table(skin);
        scrollTable.setBounds(0, 0, com.atomic.attack.MainGame.WIDTH, com.atomic.attack.MainGame.HEIGHT);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("buttonSmall");

        com.atomic.attack.GameFont font = new com.atomic.attack.GameFont(16);
        textButtonStyle.font = font.font;

        buttonBack = new TextButton("MAIN MENU", textButtonStyle);

        com.atomic.attack.GameFont creditsFont = new com.atomic.attack.GameFont(16);
        Label.LabelStyle creditsStyle = new Label.LabelStyle(creditsFont.font, Color.WHITE);

        file = Gdx.files.internal(com.atomic.attack.Constants.CREDITS_FILE_PATH);
        String credits = file.readString();

        creditsText = new Label(credits, creditsStyle);
        //creditsText.setFontScale(1);

        stage.addActor(background);
        scrollTable.add(creditsText).colspan(3).center();
        scrollTable.row().pad(40f);
        scrollTable.add(buttonBack).pad(5f).colspan(3).center();
        scrollTable.row();

        scroller = new ScrollPane(scrollTable);
        scroller.setFillParent(true);
        scroller.setBounds(0, 0, com.atomic.attack.MainGame.WIDTH, com.atomic.attack.MainGame.HEIGHT);

        stage.addActor(scroller);

        buttonBack.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                com.atomic.attack.screen.ScreenManager.transitionScreen(new MainMenu());
            }
        });

        disposed = false;
        Gdx.input.setInputProcessor(stage);

        if (!com.atomic.attack.MusicManager.instance.isPlaying()) {
            com.atomic.attack.MusicManager.instance.setMusic(Gdx.audio.newMusic(Gdx.files.internal(com.atomic.attack.Constants.MENU_MUSIC)));
            com.atomic.attack.MusicManager.instance.startMusic();
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
        if (!Gdx.input.isTouched()) {
            scroller.setScrollPercentY(scroller.getScrollPercentY() + (dt / 20));
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
        //scrollTable.remove();
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
        com.atomic.attack.screen.ScreenManager.setScreen(new CreditsMenu(), true);
        //create();
    }
}
