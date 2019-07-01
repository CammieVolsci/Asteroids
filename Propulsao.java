/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Camilla
 */
public class Propulsao extends AtorPrincipal
{
    /**
     *  
     *  
     *  
     *  
     * @param x
     * @param y
     * @param c
     */
    public Propulsao(float x, float y, Stage c) 
    {
        super(x, y, c);
        carregarTextura("fire.png");
    }
    
}
