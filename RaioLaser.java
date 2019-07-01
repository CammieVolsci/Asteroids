/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.TimeUtils;

/**
 *
 * @author Camilla
 */
public class RaioLaser extends AtorPrincipal
{
    private long tempoAtivo;
    
    /**
     *  
     *  
     *  
     *  
     * @param x
     * @param y
     * @param image
     * @param c
     */
    public RaioLaser(float x, float y, String image, Stage c) 
    {
        super(x, y, c);
        
        carregarTextura(image);
        
        mover.setVelocidade(200);
        mover.setVelMax(400);
        mover.setDesaceleracao(0);
        colisao.setBordasPoligono(8);
        tempoAtivo = TimeUtils.nanoTime();
        
        addAction(Actions.delay(1));
        addAction(Actions.after(Actions.fadeOut(0.5f)));
        addAction(Actions.after(Actions.removeActor()));    
    }
   
    public long getTempoAtivo()
    {
        return tempoAtivo;
    }
    
    /**
     *  
     *  
     */
    @Override
    public void act(float delta)
    {
        super.act(delta);
        mover.aplicarFisica(delta);
        mover.presoNaTela();
    }

}
