/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.TimeUtils;

/**
 *  Inimigo asteróide, extende e superclasse Inimigos
 *  @author Camilla
 */
public class Asteroide extends Oponentes
{  
    private char tamanho;
    private float random;
    private long tempoInvencivel;
    private Boolean vivo;
    private Boolean invencivel;
    
    /**
     *  Construtor cria um asteróide,
     *  coloca sua velocidade e coloca ele para girar
     *  @param x
     *  @param y
     *  @param image
     *  @param c
     */
    public Asteroide(float x, float y, String image, Stage c) 
    {
        super(x,y,c);    
        
        vivo = true;
        invencivel = true;
        tempoInvencivel = TimeUtils.nanoTime();
        
        carregarTextura(image);     
        this.tamanho = image.charAt(8); 
        random = MathUtils.random(30);
        
        Action girar = Actions.rotateBy(30 + random,1);
        this.addAction(Actions.forever(girar));
    
        colisao.setBordasPoligono(8);
        mover.setVelocidade(100);
        mover.setVelMax(150);
        mover.setDesaceleracao(0);
        mover.setAnguloMovimento(MathUtils.random(360));

        //pensar num bom valor para velocidade
    }

    public char getTamanho()
    {
        return tamanho;
    }
    
    public void setVivo(Boolean vivo)
    {
        this.vivo = vivo;
    }
    
    public Boolean getVivo()
    {
        return vivo;
    }
    
    public void setInvencivel(Boolean invencivel)
    {
        this.invencivel = invencivel;
    }
    
    public Boolean getInvencivel()
    {
        return invencivel;
    }
    
    public long getTempoInvencivel()
    {
       return tempoInvencivel;
    }
    
    public void perdeuInvecibilidade()
    {
        if(TimeUtils.timeSinceNanos(tempoInvencivel)>1e9)
            setInvencivel(false);      
    }

    /**
     *  Estou sobreescrevendo um método da biblioteca, act
     *  Esse método faz o asteróide mover
     *  @param delta variável de tempo que a biblioteca usa
     */
    @Override
    public void act(float delta)
    {
        super.act(delta);  
        mover.aplicarFisica(delta);
        mover.presoNaTela();
    }
    
}
