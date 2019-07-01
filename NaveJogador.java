/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Camilla
 */
public class NaveJogador extends AtorPrincipal
{    
    private int angulo;
    private int healthPoints;
    private int pontuacao;
    private boolean playing;
    private Sound propulsaoJogador;
    private Propulsao foguetePropulsao;
    
    /**
     *  
     * @param x
     * @param y
     * @param c
     */
    public NaveJogador(float x, float y, Stage c) 
    {
        super(x,y,c);
        
        carregarTextura("playership.png");
        angulo = 90;
        healthPoints = 3;
        pontuacao = 0;
        
        propulsaoJogador = Gdx.audio.newSound(Gdx.files.internal("Spaceship_engine.mp3"));
        colisao.setBordasPoligono(8);
        mover.setAceleracao(100);
        mover.setVelMax(500);
        mover.setDesaceleracao(300);
        mover.aceleracaoEmAngulo(90);       
        
       foguetePropulsao = new Propulsao(0,0,c);
       addActor(foguetePropulsao);
       foguetePropulsao.setPosition(-foguetePropulsao.getWidth(),
               getHeight()/2 - foguetePropulsao.getHeight()/2);
    }
    
    public void pararAnimacao()
    {
        foguetePropulsao.setVisible(false);
    }
    
    public int getHP()
    {
        return healthPoints;
    }    
    
    public int getPontuacao()
    {
        return pontuacao;
    }
    /**
     *  
     *  
     *  
     *  
     */
    public void desaceleraJogador()
    {
        float ac = mover.getAceleracao();
        
        for(int i=0; i<2*ac; i++)
            mover.desacelerar();
    }
    
    public void setPlaying(boolean playing)
    {
        this.playing = playing;
    }
    
    public void pararSons()
    {
        this.propulsaoJogador.stop();
    }
    /**
     *  
     *  
     *  
     *  
     */
    public void diminuiHP()
    {
        healthPoints = healthPoints - 1;
    }  
    
    /**
     *  
     *  
     *  
     *  
     * @param pontuacao
     */
    public void aumentaPontuacao(int pontuacao)
    {
        this.pontuacao = this.pontuacao + pontuacao;
    }

    /**
     *  
     *  
     *  
     *  
     * @return 
     */
    public RaioLaser atiraLasers()
    {
        if(getStage()==null)
            return null;       
        
        RaioLaser laser = new RaioLaser(0,0,"laser.png",this.getStage());
        laser.centralizarNoAtorPrincipal(this);
        laser.setRotation(this.getRotation());
        laser.mover.setAnguloMovimento(this.getRotation());
        
        return laser;
    }
    
    /**
     *  
     *  
     */
    @Override
    public void act(float delta)
    {
        super.act(delta);
        
        if(getHP() > 0)
        {
            if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
                setRotation(angulo++);

            if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
                setRotation(angulo--);

            if(Gdx.input.isKeyPressed(Input.Keys.UP))
            {
                 mover.acelerar(angulo); 
                 if(playing == false)
                 {
                     propulsaoJogador.loop(0.25f);
                     playing = true;
                 }
                 foguetePropulsao.setVisible(true);
            }    
            else
                foguetePropulsao.setVisible(false);

            mover.aplicarFisica(delta);
            mover.presoNaTela();   
        }
    }
    
}
