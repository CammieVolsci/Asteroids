/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *  Cria figura que vai ser a explosão quando a nave
 *  jogador é atingida por um asteróide ou outro inimigo
 *  @author Camilla
 */
public class Explosao extends AtorPrincipal
{
    /**
     *  
     * @param x
     * @param y
     * @param c
     */
    public Explosao(float x, float y, Stage c) 
    {
        super(x,y,c);
        
        String[] nomesArq = 
        {"1_0.png", "1_1.png", "1_2.png", "1_3.png", 
         "1_4.png", "1_5.png", "1_6.png", "1_7.png", 
         "1_8.png", "1_9.png", "1_10.png", "1_11.png",
         "1_12.png", "1_13.png","1_14.png", "1_15.png","1_16.png"};
        
        carregarArquivosAnimacao(nomesArq,0.1f,false); 
    }
    
    /**
     * Checa se a 
     * 
     */
    @Override
    public void act(float delta)
    {
        super.act(delta);
        
        if(checaAnimacaoTerminou())
            remove();
    }
    
}
