/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * explicação
 * @author Camilla
 */
public abstract class TelaPrincipal implements Screen, InputProcessor
{
    protected Stage cenarioJogo;
    protected Stage cenarioFimJogo;
    
    /**
     *
     */
    public TelaPrincipal()
    {
        cenarioJogo = new Stage();
        cenarioFimJogo = new Stage();
        inicializar();
    }
    
    //métodos abstratos/
    public abstract void inicializar();
    public abstract void atualizar(float dt);
    
    /**
     *
     *  @param delta
     */
    @Override
    public void render(float delta)
    {
        cenarioJogo.act(delta);
        cenarioFimJogo.act(delta);
        atualizar(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        cenarioJogo.draw();
        cenarioFimJogo.draw();
    }

    //métodos da interface Screen
    @Override
    public void resize(int width, int height) { }
    @Override
    public void pause() { }
    @Override
    public void resume() { }
    @Override
    public void dispose() { }
    
    /**
     *  
     *  
     *  
     *  
     */
    @Override
    public void show() 
    { 
        InputMultiplexer IM = (InputMultiplexer)Gdx.input.getInputProcessor();
        IM.addProcessor(this);
        IM.addProcessor(cenarioJogo);
        IM.addProcessor(cenarioFimJogo);
    }
    
    @Override
    public void hide() 
    { 
        InputMultiplexer IM = (InputMultiplexer)Gdx.input.getInputProcessor();
        IM.removeProcessor(this);
        IM.removeProcessor(cenarioJogo);
        IM.removeProcessor(cenarioFimJogo);
    }
    
    //métodos da interface InputProcessor
    @Override
    public boolean keyDown(int i) 
    {
        return false;
    }

    @Override
    public boolean keyUp(int i) 
    {
        return false;
    }

    @Override
    public boolean keyTyped(char c) 
    {
        return false;
    }

    @Override
    public boolean touchDown(int i, int i1, int i2, int i3) 
    {
        return false;
    }

    @Override
    public boolean touchUp(int i, int i1, int i2, int i3) 
    {
        return false;
    }

    @Override
    public boolean touchDragged(int i, int i1, int i2) 
    {
        return false;
    }

    @Override
    public boolean mouseMoved(int i, int i1) 
    {
        return false;
    }

    @Override
    public boolean scrolled(int i) 
    {
        return false;
    }
}
