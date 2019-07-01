/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 *
 * @author Camilla
 */
public class NaveAlien extends Oponentes
{
    /**
     *  
     *  @param x
     *  @param y
     *  @param c
     */
    public NaveAlien(float x, float y, Stage c) 
    {
        super(x,y,c);    
        carregarTextura("alienship.png");
        
        colisao.setBordasPoligono(8);
        mover.setVelocidade(100);
        mover.setVelMax(200);
        mover.setDesaceleracao(0);
        mover.setAnguloMovimento(50);
        mover.setAnguloMovimento(MathUtils.random(180));

        //pensar num bom valor para velocidade
    }
    
    /**
     *  
     *  
     *  @param delta
     *  
     */
    @Override
    public void act(float delta)
    {
        super.act(delta);  
        mover.aplicarFisica(delta);
        mover.presoNaTela();
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
        
        RaioLaser laser = new RaioLaser(0,0,"laserenemy.png",this.getStage());
        laser.centralizarNoAtorPrincipal(this);
        laser.setRotation(this.getRotation());
        laser.mover.setAnguloMovimento(this.getRotation());
        
        return laser;
    }
    
}
