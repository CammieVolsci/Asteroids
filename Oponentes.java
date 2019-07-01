/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *
 * @author Camilla
 */
public class Oponentes extends AtorPrincipal
{
    private boolean colidiuOponente;

    /**
     * @param x
     * @param y
     * @param c
     */
    public Oponentes(float x, float y, Stage c) 
    {
        super(x,y,c);
        colidiuOponente = false;
    }
    
    public boolean getColidiuOponente()
    {
        return colidiuOponente;
    }
    
    /**
     *
     * 
     */
    public void colisao()
    {
        colidiuOponente = true;
        clearActions();
        addAction(Actions.fadeOut(1));
        addAction(Actions.after(Actions.removeActor()));
    }
}
