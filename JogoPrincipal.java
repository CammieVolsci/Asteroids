/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;

/**
 *  
 *  @author Camilla
 */
public abstract class JogoPrincipal extends Game
{
    public static LabelStyle labelStyle;
    private static JogoPrincipal jogo;
    
    /**
    *  
    *  
    */
    public JogoPrincipal()
    {
        jogo = this;
    }
    
    /**
    *  
    *  
     * @param t
    */
    public static void setTelaAtiva(TelaPrincipal t)
    {
        jogo.setScreen(t);
    }
    
    /**
    *  
    *  
    */
    @Override
    public void create()
    {
        InputMultiplexer IM = new InputMultiplexer();
        Gdx.input.setInputProcessor(IM);
        labelStyle = new LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("kenvector_future_bitmap.fnt"));
    }

}
