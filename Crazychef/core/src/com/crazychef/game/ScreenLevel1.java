package com.crazychef.game;

import com.controller.Controller;
import com.models.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Input.*;


public class ScreenLevel1 implements Screen{
    final Crazychef game;
    private Controller controller;
    private Texture chefFront;
    private Texture background;
    private Texture tableImageH;
    private Texture tableImageV;
    private OrthographicCamera camera;
    private Chef chef;
    private Kitchen cozinha;

    public ScreenLevel1(final Crazychef game, Controller controller){
        this.game = game;
        this.controller = controller;
        chefFront = new Texture(Gdx.files.internal("Chef/chefFront.png"));//textura do chef
        background = new Texture(Gdx.files.internal("Kitchen/chao_fases.png"));//textura da fase
        tableImageH = new Texture(Gdx.files.internal("Kitchen/tableH.png"));
        tableImageV = new Texture(Gdx.files.internal("Kitchen/tableV.png"));

        camera = new OrthographicCamera();//camera
        camera.setToOrtho(false, 1280, 720);
        cozinha = new Kitchen(10, 17);

        chef = new Chef(640,320);//pos na matriz [4][8]
        cozinha.setObjects(chef, Math.round(chef.y/80), Math.round(chef.x/80));
        controller.setChef(chef);
        controller.setKitchen(cozinha);
    }

    @Override
    public void render(float delta){
        camera.update();

        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin(); //inicia a renderização
        game.batch.draw(background, 0,0, 1280, 720);//imagem do fundo
        game.batch.draw(chefFront, chef.x, chef.y,chef.width,chef.height);

        //cria o mapa da sala
        for(int j=1; j<15;j++){
            Table tableV = new Table(80*j,480);
            cozinha.setObjects(tableV, 6, j);
            game.batch.draw(tableImageV,tableV.x, tableV.y, tableV.width,tableV.height);
        }

        game.batch.draw(chefFront, chef.x, chef.y,chef.width,chef.height);
        game.batch.end();

        if (Gdx.input.isKeyJustPressed(Keys.LEFT))
            controller.left();
        if (Gdx.input.isKeyJustPressed(Keys.RIGHT))
            controller.right();
        if (Gdx.input.isKeyJustPressed(Keys.UP))
            controller.up();
        if (Gdx.input.isKeyJustPressed(Keys.DOWN))
            controller.down();

        //nao deixa o chef sair das bordas da tela
        if (chef.x < 0)
            chef.x = 0;
        if (chef.x > 1280 - 80)
            chef.x = 1280 - 80;
        if (chef.y > 720 - 80)
            chef.y = 720 - 80;
        if (chef.y < 0)
            chef.y = 0;

    }


    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        chefFront.dispose();
        background.dispose();
    }

    @Override
    public void show(){
    }
}
