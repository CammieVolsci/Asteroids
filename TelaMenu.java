/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.ui.Label;

/**
 *  Cria tela do menu de inicio do jogo
 *  @author Camilla
 */
public class TelaMenu extends TelaPrincipal
{
    private Label novoJogoLabel;
    
    /**
     *  
     *  
     *  
     *  
     */
    public TelaMenu()
    {
        super();
    }
    
    /**
     *
     */
    @Override
    public void inicializar() 
    {
        AtorPrincipal espaco = new AtorPrincipal(0,0,cenarioJogo);
        espaco.carregarTextura("vialactea.png");
        espaco.setSize(900,700);
        AtorPrincipal titulo = new AtorPrincipal(200,350,cenarioJogo);
        titulo.carregarTextura("logo.png");
        
        novoJogoLabel = new Label("Pressione 'E' para entrar no jogo", JogoPrincipal.labelStyle);
        novoJogoLabel.setPosition(55,260);
        novoJogoLabel.setFontScale(0.9f);
        cenarioJogo.addActor(novoJogoLabel);
    }

    /**
     *
     *  @param delta
     */
    @Override
    public void atualizar(float delta) 
    {
        if(Gdx.input.isKeyPressed(Input.Keys.E))
            JogoPrincipal.setTelaAtiva(new TelaJogo());
    }
  
}
