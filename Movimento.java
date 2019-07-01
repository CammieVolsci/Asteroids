/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *  Classe relativa a todos os métodos de movimento das personagens
 *  Tem uma instância na classe AtorPrincipal
 *  @author Camilla
 */
public class Movimento
{
    private float aceleracao;  
    private float desaceleracao;
    private float velMaxima;
    private Vector2 vetVelocidade;
    private Vector2 vetAceleracao;
    private Actor ator;
    private static Rectangle fronteirasTela;
    
    public Movimento(Actor ator)
    {
        this.ator = ator;
        aceleracao = 10;
        desaceleracao = 0;  
        velMaxima = 1000;
        vetAceleracao = new Vector2(0,0);
        vetVelocidade = new Vector2(0,0); 
    }
  
    /**
     *  Set velocidade da personagem
     *  Se length for 0, então assume que o ângulo do movimento é de 0 graus
     *  @param velocidade velocidade da personagem
     */
    public void setVelocidade(float velocidade)
    {      
        if(vetVelocidade.len()==0)
            vetVelocidade.set(velocidade,0);       
        else
            vetVelocidade.setLength(velocidade);
    }
    
    public float getVelocidade()
    {
        return vetVelocidade.len();
    }
    
    public void setAceleracao(float aceleracao)
    {
        this.aceleracao = aceleracao;
    }
    
    public float getAceleracao()
    {
        return aceleracao;
    }
    
    public void setVelMax(float velMaxima)
    {
        this.velMaxima = velMaxima;
    }
    
    public float getVelMaxima()
    {
        return velMaxima;
    }
    
    public void setDesaceleracao(float desaceleracao)
    {
        this.desaceleracao = desaceleracao;
    }
    
    public float getDesaceleracao()
    {
        return desaceleracao;
    }

    /**
     *  
     *    
     * @param angulo
     */
    public void acelerar(float angulo)
    {
        vetAceleracao.add
         (new Vector2(this.aceleracao, this.aceleracao).setAngle(angulo));
    }
   
    /**
     *  
     *  
     *  
     *  
     */
    public void desacelerar()
    {
        aceleracao = aceleracao - desaceleracao;
    }
 
    /**  
     *  
     * @param angulo
     */
    public void aceleracaoEmAngulo(float angulo)
    {
        vetAceleracao.add(new Vector2(aceleracao,0).setAngle(angulo));
    }
    
     /**
     *  
     *  
     *  
     *  
     * @param angulo
     */
    public void setAnguloMovimento(float angulo)
    {
        vetVelocidade.setAngle(angulo);
    }
    
     /**
     *  
     *  
     *  
     *  
     * @return 
     */
    public float getAnguloMovimento()
    {
        return vetVelocidade.angle();
    }
    
    /**
     *  
     *  
     *  
     *  
     * @param delta
     */
    public void aplicarFisica(float delta)
    {   
        vetVelocidade.add(vetAceleracao.x*delta,vetAceleracao.y*delta);
        float velocidade = getVelocidade();
        
        if(vetAceleracao.len()==0)
            velocidade -= getDesaceleracao()*delta;
        
        velocidade = MathUtils.clamp(velocidade,0,getVelMaxima());
        
        setVelocidade(velocidade);
        
        ator.moveBy(vetVelocidade.x*delta,vetVelocidade.y*delta);
        
        vetAceleracao.set(0,0);
    }
    
    /**
     *  
     *  
     *  
     *  
     * @param largura
     * @param altura
     */
    public static void setfronteirasTela(float largura, float altura)
    {
        fronteirasTela = new Rectangle(0,0,largura,altura);
    }
    
    /**
     *  
     *  
     *  
     *  
     * @param p
     */
    public static void setfronteirasTela(AtorPrincipal p)
    {
        setfronteirasTela(p.getWidth(),p.getHeight());
    }   
    
    /**
     *  Prende a personagem na tela
     *  Checa os 4 lados da tela 
     *  
     */
    public void presoNaTela()
    {
        if(ator.getX() < 0 - ator.getWidth())
            ator.setX(fronteirasTela.width);
        
        if(ator.getX() > fronteirasTela.width)
            ator.setX(0);
        
        if(ator.getY() < 0 - ator.getHeight())
            ator.setY(fronteirasTela.height);
        
        if(ator.getY() > fronteirasTela.height)
            ator.setY(0);
    }

}
