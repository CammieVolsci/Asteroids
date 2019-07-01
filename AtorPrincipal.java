/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

/**
 *  Classe personagem extende a classe ator da biblioteca
 *  Todos as personagens do jogo (nave, jogador e asteróide) extendem dela
 *  @author Camilla
 */
public class AtorPrincipal extends Group
{
    private Animation<TextureRegion> animacao;
    private float tempoPassado;
    private boolean animacaoPausada;
    protected Movimento mover;
    protected Colisao colisao;  
    
     /**
     *  
     *  
     *   @param x
     *   @param y
     *   @param c
     */
    public AtorPrincipal(float x, float y, Stage c)
    {
        super();
        
        setPosition(x,y);
        c.addActor(this);  
        
        colisao = new Colisao(this);
        mover = new Movimento(this);
        this.animacao = null;
        tempoPassado = 0;     
        animacaoPausada = false;
    }
    
    /**
     *
     */
    public void removerAtor()
    {
        addAction(Actions.fadeOut(1));
        addAction(Actions.after(Actions.removeActor()));
    }
    
    /**
     *
     * @param delta
     */
    @Override
    public void act(float delta)
    {
        super.act(delta);
        if(!animacaoPausada)
            tempoPassado += delta;
    }

    // ~~ // Métodos relativos a desenhar a imagem // ~~ //

    /**
     *
     * @param batch
     * @param parentAlpha
     */
    @Override
    public void draw(Batch batch, float parentAlpha)
    {
        Color c = getColor();
        batch.setColor(c.r,c.g,c.b,c.a);
        
        if(animacao!=null && isVisible())
            batch.draw(animacao.getKeyFrame(tempoPassado),getX(), getY(), 
                    getOriginX(), getOriginY(), getWidth(), getHeight(), 
                    getScaleX(), getScaleY(), getRotation());
        
        super.draw(batch, parentAlpha);
        
    }
    
    /**
     *
     * 
     *  @param nomeArq
     *  @return 
     */
    public Animation<TextureRegion> carregarTextura(String nomeArq)
    {
        String[] nomeArqs = new String[1];
        nomeArqs[0] = nomeArq;
        
        return carregarArquivosAnimacao(nomeArqs,1,true);

    }
  
    /**
     *
     * 
     * @param opacidade
     */
    public void setOpacidade(float opacidade)
    {
        this.getColor().a = opacidade;
    }
    
    // ~~ // Métodos relativos a animação // ~~ //
    
    /**
     *
     * 
     *  @param animacao
     */
    public void setAnimacao(Animation<TextureRegion> animacao)
    {
        this.animacao = animacao;
        TextureRegion textura = this.animacao.getKeyFrame(0);
        float largura = textura.getRegionWidth();
        float altura = textura.getRegionHeight();
        
        setSize(largura,altura);
        setOrigin(largura/2,altura/2);
        
        if(colisao.getBordasPoligono()==null)
            colisao.setBordasRetangulo();
    }
    
    /**
     *
     * 
     * @param nomeArq
     * @param duracaoFrames
     * @param loop
     * @return 
     */
    public Animation<TextureRegion> carregarArquivosAnimacao
        (String[] nomeArq, float duracaoFrames, boolean loop)
    {
        int tamanhoArq = nomeArq.length;
        Array<TextureRegion> vetTexturas = new Array<TextureRegion>();
        
        for(int i=0;i<tamanhoArq;i++)
        {
            String fileName = nomeArq[i];
            Texture textura = new Texture(Gdx.files.internal(fileName));
            textura.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            vetTexturas.add(new TextureRegion(textura));
        }
        
        Animation<TextureRegion> animacaoT = 
                new Animation<TextureRegion>(duracaoFrames,vetTexturas);
        
        if(loop)
            animacaoT.setPlayMode(Animation.PlayMode.LOOP);      
        else
            animacaoT.setPlayMode(Animation.PlayMode.NORMAL);
        
        if(animacao==null)
            setAnimacao(animacaoT);
        
        return animacaoT;
    }
 
    /**
     *
     * 
     *  @return 
     */
    public boolean checaAnimacaoTerminou()
    {
        return animacao.isAnimationFinished(tempoPassado);
    }
    
    /**
     *
     * 
     *  @param pausar
     */
    public void setPausarAnimacao(boolean pausar)
    {
        animacaoPausada = pausar;
    }   
    
    /**
     *  Centraliza um ator na tela
     *  @param x coordenada x do ator
     *  @param y coordenada y do ator
     */
    private void centralizar(float x, float y)
    {
        setPosition(x-getWidth()/2,y-getHeight()/2);
    }
    
    /**
     *  Centraliza um ator em outro ator
     * @param outro o outro ator para centralizar em cima
     */
    public void centralizarNoAtorPrincipal(AtorPrincipal outro)
    {
        centralizar(outro.getX() + outro.getWidth()/2, 
                outro.getY() + outro.getHeight()/2);
    }
    
}
