/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

/**
 *  Classe para todos os métodos relativos a testar colisão
 *  entre duas personagens,
 *  existe uma instância dessa classe na classe AtorPrincipal
 *  @author Camilla
 */
public class Colisao 
{
    private Polygon colisao;
    private Actor ator;
    
    /**
     *   
     * @param ator
     */
    public Colisao(Actor ator)
    {
        this.ator = ator;
        colisao = new Polygon();
    }
 
    /**
     *
     * 
     */
    public void setBordasRetangulo()
    {
        float altura = ator.getWidth();
        float largura = ator.getHeight();      
        float[] vertices = {0,0,largura,0,largura,altura,0,altura};
        
        colisao = new Polygon(vertices);
    }
    
    /**
     *
     * 
     *  @param nLados
     */
    public void setBordasPoligono(int nLados)
    {
        float altura = ator.getWidth();
        float largura = ator.getHeight();
        float[] vertices = new float[2*nLados];
        
        for (int i=0;i<nLados;i++)
        {
            float angulo = i*6.28f/nLados;
            
            vertices[2*i] = largura/2 * MathUtils.cos(angulo) + largura/2;
            vertices[2*i+1] = altura/2 * MathUtils.sin(angulo) + altura/2;
        }
        colisao = new Polygon(vertices);
    }
    
    /**
     *
     * 
     *  @return 
     */
    public Polygon getBordasPoligono()
    {
        colisao.setPosition(ator.getX(),ator.getY());
        colisao.setOrigin(ator.getOriginX(),ator.getOriginY());
        colisao.setRotation (ator.getRotation());
        colisao.setScale(ator.getScaleX(),ator.getScaleY());
        
        return colisao;
    }
    
    /**
     *  Teste se duas personagens se sobrepõe
     *  usando poligonos
     *  @param other
     *  @return 
     */
    public boolean sobrepor(AtorPrincipal other)
    {
        Polygon poli1 = this.getBordasPoligono();
        Polygon poli2 = other.colisao.getBordasPoligono();
        
        if(!poli1.getBoundingRectangle().overlaps(poli2.getBoundingRectangle()))
            return false;
        
        return Intersector.overlapConvexPolygons(poli1, poli2);
    }

}
