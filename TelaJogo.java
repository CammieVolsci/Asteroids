/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.TimeUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;

/**
 *  Função que cria a tela do jogo na qual ocorrem todas as ações
 *  @author Camilla
 */
public class TelaJogo extends TelaPrincipal
{
    private AtorPrincipal player;
    private AtorPrincipal ufo;
    
    private long tempoInvencivel; 
    private long tempoTiroOponente;
    private long tempoAlienAparecer;   
    private int lasersAtirados;
    private int lasersAlienAtirados;
    private int maxAlienTiros;
    private int maxAlien;
    private int totalAlien;
    private int totalAsteroid;
    
    private boolean colidiuLaser;
    private boolean colidiuNave;   
    private boolean colidiuLaserOponente;
    private boolean colidiuNaveOponente;
    private boolean derrota;
    private boolean alienVivo;
    private boolean playerInvencivel;
    
    private Random randX;
    private Random randY;
    private Random randM;
    
    private List<AtorPrincipal> asteroid;
    private List<AtorPrincipal> laserFogoJogador;
    private List<AtorPrincipal> laserFogoAlien;
    private List<Vidas> playerHP;
    private List<Integer> asteroidsRemovidosColisao;
    private List<Integer> asteroidsRemovidosLaser;
    private List<Character> asteroidTamanhosColisao;
    private List<Character> asteroidTamanhosLaser;
    private List<Integer> lasersAtivos;
    private List<Integer> lasersAtivosAlien;
    private List<Integer> lasersJogadorRemovidos;
    private List<Integer> lasersAlienRemovidos;     
    
    private Label pontuacaoLabel;
    private Label gameOverLabel;
    
    private float volumeAudio;
    private float volumeMusica;
    private Sound laserJogador;
    private Sound laserAlien;
    private Sound explosaoPersonagens;
    private Music backgroundMusic;
    
    private void inicializaVariaveis()
    {       
        colidiuNave = false;
        colidiuNaveOponente = false;
        colidiuLaser = false;
        colidiuLaserOponente = false;
        playerInvencivel = false;   
        derrota = false;
        alienVivo = false;
        
        tempoAlienAparecer = TimeUtils.nanoTime(); // se iniciar no 0, alien nunca aparece
        
        lasersAtirados = 0;
        lasersAlienAtirados = 0;
        tempoInvencivel = 0;
        tempoTiroOponente = 0;
        maxAlienTiros = 3;
        maxAlien = 1;
        totalAlien = 0;
        totalAsteroid = 16;
        
        randX = new Random();
        randY = new Random();
        randM = new Random();
        
        playerHP                         = new ArrayList<Vidas>();
        laserFogoJogador                 = new ArrayList<AtorPrincipal>();
        laserFogoAlien                   = new ArrayList<AtorPrincipal>();
        asteroid                         = new ArrayList<AtorPrincipal>();
        asteroidsRemovidosColisao        = new ArrayList<Integer>();
        asteroidsRemovidosLaser          = new ArrayList<Integer>();
        asteroidTamanhosColisao          = new ArrayList<Character>();
        asteroidTamanhosLaser            = new ArrayList<Character>();
        lasersAtivos                     = new ArrayList<Integer>();
        lasersJogadorRemovidos           = new ArrayList<Integer>();
        lasersAtivosAlien                = new ArrayList<Integer>();
        lasersAlienRemovidos             = new ArrayList<Integer>();
        
        pontuacaoLabel = new Label("Score: ", JogoPrincipal.labelStyle);
        gameOverLabel = new Label("Aperte 'R' para reiniciar o jogo", JogoPrincipal.labelStyle);
    }
  
    /**
     *  Inicializa todas as personagens do jogo, cenário, nave do jogador e asteroides
     *  Cria lista de asteroides com movimentação aleatória
     *  #OBG_TBR_COLECOES
     *  #OBG_TBR_POLIMORFISMO
     */
    @Override
    public void inicializar() 
    {      
        int x, y;
        AtorPrincipal espaco = new AtorPrincipal(0,0,cenarioJogo);
        espaco.carregarTextura("vialactea.png");
        espaco.setSize(900,700);
        Movimento.setfronteirasTela(espaco); 
        
        inicializaVariaveis();

        for(int i=0;i<4;i++) 
        {
            x = randX.nextInt(900) +1;
            y = randY.nextInt(700) +1;
            
            while((x<525 && x>325) || (y<425 && y>225))
            {
                x = randX.nextInt(900) +1;
                y = randY.nextInt(700) +1;
            }
            asteroid.add(new Asteroide(x,y,"AsteroidG.png",cenarioJogo));
        }
    
        asteroid.get(0).mover.setAceleracao(50);
        asteroid.get(1).mover.setAceleracao(50);
        asteroid.get(2).mover.setAceleracao(50);
        asteroid.get(3).mover.setAceleracao(50);
        
        playerHP.add(new Vidas(10,660,cenarioJogo)); //10 + 37 = 47 + 5 = 52 
        playerHP.add(new Vidas(52,660,cenarioJogo)); //52 + 37 = 89 + 5 = 94
        playerHP.add(new Vidas(94,660,cenarioJogo));
        
        pontuacaoLabel.setPosition(20,630);
        pontuacaoLabel.setFontScale(0.5f);
        cenarioJogo.addActor(pontuacaoLabel);
        
        player = new NaveJogador(425,325,cenarioJogo);
        player.rotateBy(90);  
        
        carregaSonsEmusica();
    }

    /**
     *  Basicamente o loop do jogo, atualiza sempre os objetos
     *  Checa colisões, HP e cria novos asteróides
     *  @param delta medida de tempo usada pelo LibGDX
     */
    @Override
    public void atualizar(float delta) 
    {
        if(!derrota)
        {
            if (totalAlien < maxAlien)
               gerarAliens();      

            testaColisaoAsteroids(player);
            
            if(TimeUtils.timeSinceNanos(tempoTiroOponente)>1e9 && alienVivo )
                naveAlienAtiraLasers();  
            
            if(alienVivo)
                checaLaserAlien();

            if(TimeUtils.timeSinceNanos(tempoInvencivel)>2e9)
                playerInvencivel = false;  
        }
        
        if(Gdx.input.isKeyPressed(Input.Keys.R))
        {
            backgroundMusic.stop();
            reiniciaJogo();   
        }
        
        if(totalAsteroid == 0)
        {
            if(!asteroid.isEmpty())
                asteroid.clear();
            
            gerarMaisAsteroids();
        }    
       
       NaveJogador jogador = (NaveJogador) player;
       pontuacaoLabel.setText("Score: " + jogador.getPontuacao());
    }
    
    /**
     *  Criar laser toda hora que jogador aperta tecla de espaço
     *  @param tecla
     *  @return
     */
    @Override
    public boolean keyUp(int tecla)
    {
        
        NaveJogador jogador = (NaveJogador) player;
        
        if(jogador.getHP() > 0)
        {
            if(tecla==Keys.SPACE)
            {
                laserFogoJogador.add(jogador.atiraLasers());
                lasersAtivos.add(lasersAtirados++);
                laserJogador.play(volumeAudio);
            }

            if(tecla==Keys.UP)
            {
                jogador.setPlaying(false);
                jogador.pararSons();
                jogador.desaceleraJogador();  
            }            
        }
            return false;
    }
    
    private void carregaSonsEmusica()
    {
        laserJogador = Gdx.audio.newSound(Gdx.files.internal("Laser_player.mp3"));
        laserAlien = Gdx.audio.newSound(Gdx.files.internal("Laser_ufo.mp3"));
        explosaoPersonagens = Gdx.audio.newSound(Gdx.files.internal("Explosion.wav"));
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Fly.mp3"));
        
        volumeMusica = 0.04f;
        volumeAudio = 0.25f;
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(volumeMusica);
        backgroundMusic.play();
    }

    private void reiniciaJogo()
    {
        AsteroidsJogo.setTelaAtiva(new TelaMenu());
    }
    
    private void aumentaPontuacao(Asteroide asteroide)
    {
        NaveJogador jogador = (NaveJogador) player;     
        
        try
        {
            switch(asteroide.getTamanho()) 
            {
                case 'G':
                    jogador.aumentaPontuacao(20);
                    break;
                case 'M':
                    jogador.aumentaPontuacao(50);
                    break;
                case 'P':
                    jogador.aumentaPontuacao(100);
                    break;
                default:
                    break;
            }
            
            if(jogador.getPontuacao()<0)
                throw new ExcecaoPontuacaoJogador();
        }
        
        catch(ExcecaoPontuacaoJogador e)
        {
            System.out.printf("\nExceção: %s.\n", e);
            System.out.printf("Pontuacao do jogador negattiva.\n");
        }
        
    }
    
    /**
     *  Checa a quantidade do HP do jogador
     *  Se for 0 dá game over e termina o jogo
     */     
    private void checaHP()
    {
        NaveJogador jogador = (NaveJogador) player;
        colidiuNave = false;
        
        if(jogador.getHP()>0)
        {
            int hp = jogador.getHP()-1;
            
            jogador.diminuiHP();
            playerHP.get(hp).removerAtor();
            playerHP.remove(hp);
            explosaoPersonagens.play(volumeAudio);
            
            Explosao explodeNave = new Explosao(0,0,cenarioJogo);        
            explodeNave.centralizarNoAtorPrincipal(player);

            Action centralizar = Actions.moveTo(425,325);
            Action demoraAparecer = Actions.delay(30);     
            jogador.addAction(centralizar);
            jogador.mover.setVelocidade(0);
            jogador.addAction(demoraAparecer);   
            
            playerInvencivel = true;
            tempoInvencivel = TimeUtils.nanoTime();
        }
        
        if(!derrota && jogador.getHP()<= 0)
        {
            derrota = true;
            jogador.pararSons();
            jogador.pararAnimacao();
            
            AtorPrincipal mensagemGameOver = new AtorPrincipal(200,350,cenarioFimJogo);
            mensagemGameOver.carregarTextura("game-over.png");
            Explosao explodeNave = new Explosao(0,0,cenarioFimJogo);
            gameOverLabel.setPosition(65,260);
            gameOverLabel.setFontScale(0.9f);
            cenarioFimJogo.addActor(gameOverLabel);
            
            explodeNave.centralizarNoAtorPrincipal(player);       
            jogador.removerAtor();
            jogador = null;
            laserFogoJogador.clear();
            
            mensagemGameOver.setOpacidade(0);
            mensagemGameOver.addAction(Actions.delay(1));
            mensagemGameOver.addAction(Actions.after(Actions.fadeIn(1)));     
        }           
    }
    
    private void gerarAliens()
    {
        Random rand = new Random();
        
        int x = rand.nextInt(900) +1;
        int y = rand.nextInt(700) +1;
        int ufoAparece = rand.nextInt(500) +1;
        
        if(ufoAparece<10 && !alienVivo && TimeUtils.timeSinceNanos(tempoAlienAparecer) > 20e9) 
        {
            
            ufo = new NaveAlien(x,y,cenarioJogo);
            alienVivo = true;
            totalAlien++;
        } 
    }
    
    /**
     *  Gera mais asteróides uma vez que todos os asteróides (tamanho G),
     *  foram destruídos da tela.
     *  Sempre ficam 4 no máximo.
     *  #OBG_TBR_COLECOES
     */
    private void gerarMaisAsteroids()
    {
        int x,y;
        
        asteroid.clear();
        totalAsteroid = 16;
        
        try
        {
            for(int i=0;i<4;i++) 
            {
                x = randX.nextInt(900) +1;
                y = randY.nextInt(700) +1;

                while((x<player.getX()+100 && x>player.getX()-100) || 
                        (y<player.getY()+100 && y>player.getY()-100))
                {
                    x = randX.nextInt(900) +1;
                    y = randY.nextInt(700) +1;
                }
                asteroid.add(new Asteroide(x,y,"AsteroidG.png",cenarioJogo));
            } 
            
            if(asteroid.size()>16)
                throw new ExcecaoQuantidadeAsteroids();
        }
        
        catch(ExcecaoQuantidadeAsteroids e)
        {
            System.out.printf("\nExceção: %s.\n", e);
            System.out.printf("Tem asteróides demais no cenário.\n");
        }
               
    }
    
    /**
     *  Gera dois asteróides de tamanho menor sempre que um asteróide
     *  grande ou médio for destruído
     *  Aumenta a velocidade dos asteróides menores
     *  #OBG_TBR_COLECOES
     */
    private void gerarBebesAsteroids(float x, float y, char tamanho)
    {
        try
        {
            for(int i=0;i<2;i++)
            {
                if(tamanho=='G')
                {
                    Asteroide a1 = new Asteroide(x,y,"AsteroidM.png",cenarioJogo);
                    a1.mover.setVelMax(200);
                    asteroid.add(a1);
                }            
                else if(tamanho=='M')
                {
                    Asteroide a1 = new Asteroide(x,y,"AsteroidP.png",cenarioJogo);
                    a1.mover.setVelMax(300);
                    asteroid.add(a1);
                }
            } 
            
            if(asteroid.size()>16)
                throw new ExcecaoQuantidadeAsteroids();
        }
        
        catch(ExcecaoQuantidadeAsteroids e)
        {
            System.out.printf("\nExceção: %s.\n", e);
            System.out.printf("Tem asteróides demais no cenário.\n");
        }     
        
    } 
    
    private void testaColisaoAsteroids(AtorPrincipal nave)
    {
        int posicaoAsteroid=0;
        float x=0, y=0;
        
        for(AtorPrincipal rocha : asteroid)
        {
            Asteroide asteroide = (Asteroide) rocha;
            NaveAlien alien = (NaveAlien) ufo;
            
            if(asteroide.getInvencivel())
                asteroide.perdeuInvecibilidade();
            
            if(asteroide.colisao.sobrepor(nave) && !asteroide.getColidiuOponente() 
                && (asteroide.getTamanho() == 'G' || asteroide.getTamanho() == 'M')
                && !playerInvencivel && !asteroide.getInvencivel() && asteroide.getVivo())
            {
                x = asteroide.getX();
                y = asteroide.getY(); 
                asteroide.colisao(); 

                if(!colidiuNave) 
                {
                    asteroide.setVivo(false);
                    asteroidTamanhosColisao.add(asteroide.getTamanho());
                    asteroidsRemovidosColisao.add(posicaoAsteroid);                            
                }    

                colidiuNave = true; 

            }                      
            else if(asteroide.colisao.sobrepor(nave) && !asteroide.getColidiuOponente() 
                && asteroide.getTamanho() == 'P' && !playerInvencivel
                && !asteroide.getInvencivel()  && asteroide.getVivo())
            {
                asteroide.colisao(); 

                if(!colidiuNave) 
                {
                    asteroide.setVivo(false);
                    asteroidTamanhosColisao.add(asteroide.getTamanho());
                    asteroidsRemovidosColisao.add(posicaoAsteroid);    
                } 

                colidiuNave = true; 


                aumentaPontuacao(asteroide);
            }
            
            if(!laserFogoJogador.isEmpty())
            {   
                int posicaoAsteroidNoLaser =0;
                
                for(AtorPrincipal laser : laserFogoJogador)
                {
                    if(laser.colisao.sobrepor(asteroide) 
                       && !asteroide.getInvencivel() && asteroide.getVivo())
                    {
                        x = asteroide.getX();
                        y = asteroide.getY();  
                        
                        asteroide.colisao(); 
                        explosaoPersonagens.play(volumeAudio);
                        
                        if(nave instanceof NaveJogador)
                            aumentaPontuacao(asteroide);  
                                 
                        if(!colidiuLaser) 
                        {
                            asteroide.setVivo(false);
                            asteroidTamanhosLaser.add(asteroide.getTamanho());
                            asteroidsRemovidosLaser.add(posicaoAsteroidNoLaser);                                                      
                        }
                        
                        colidiuLaser = true;                       
                    }
                    else if(alienVivo && laser.colisao.sobrepor(alien))
                    {
                        removeLaserAlien();
                        alien.colisao();
                        colidiuLaser = true;
                        alienVivo = false;
                        tempoAlienAparecer = TimeUtils.nanoTime();
                        totalAlien--;
                        
                        if (totalAlien < 0)
                            totalAlien = 0;
                    }
                    
                    posicaoAsteroidNoLaser++;
                }             
            }   
            
            posicaoAsteroid++;
        }
        
        removerAsteroideColisao(x,y);

        if(colidiuNaveOponente && alienVivo)   
        {
            alienVivo = false;
            colidiuNaveOponente = false;
            explosaoPersonagens.play(volumeAudio);
            removeLaserAlien();
            ufo.removerAtor();
            totalAlien--;
            
            if(totalAlien<0)
                totalAlien=0;
        }
        
        removeLasers();  
    }
    
    private void checaLaserAlien()
    {
        if(!laserFogoAlien.isEmpty())
        {   
            NaveJogador jogador = (NaveJogador) player;
            
            for(AtorPrincipal laser : laserFogoAlien)
                if(laser.colisao.sobrepor(jogador)  && !playerInvencivel)
                    colidiuLaserOponente = true;             
        }
        
        removeLaserAlien();
        
        if(colidiuLaserOponente)
        {
            checaHP(); 
            colidiuLaserOponente = false;
        }
    }
    
    private void removerAsteroideColisao(float x, float y)
    {
        if(colidiuNave || colidiuLaser)
        {   
            if(colidiuNave)
                checaHP();      
             
            for(int i=0;i<asteroidsRemovidosColisao.size();i++)
            {   
                if(asteroidTamanhosColisao.get(i) != 'P')
                    gerarBebesAsteroids(x,y,asteroidTamanhosColisao.get(i));  
                else
                    totalAsteroid--;
                
                asteroid.remove(asteroidsRemovidosColisao.get(i));
            }
            
            for(int i=0;i<asteroidsRemovidosLaser.size();i++)
            {
                if(asteroidTamanhosLaser.get(i) != 'P')
                    gerarBebesAsteroids(x,y,asteroidTamanhosLaser.get(i)); 
                else
                    totalAsteroid--;
                
                asteroid.remove(asteroidsRemovidosLaser.get(i));
            }
            
            asteroidsRemovidosColisao.clear();
            asteroidsRemovidosLaser.clear();
            asteroidTamanhosColisao.clear();
            asteroidTamanhosLaser.clear();
            
            colidiuLaser = false;
            colidiuNave  = false;
        }
    }
    
    private void removeLasers()
    {
        if(laserFogoJogador.size() == lasersAtivos.size()) // Algo deu errado para ser falso
        {
            for(int i=0;i<lasersAtivos.size();i++)
            {
                RaioLaser laser = (RaioLaser) laserFogoJogador.get(i);
                
                if(TimeUtils.timeSinceNanos(laser.getTempoAtivo()) > 2e9) 
                {
                    lasersJogadorRemovidos.add(i);
                    lasersAtirados--;
                    
                    if(lasersAtirados<0)
                        lasersAtirados = 0;
                }
            }
            
            for(int i=0;i<lasersJogadorRemovidos.size();i++)
            {
                RaioLaser laser = (RaioLaser) laserFogoJogador.get(i);
                laserFogoJogador.remove(laser);
                laser.remove();
                lasersAtivos.remove(i);
            }
            
            lasersJogadorRemovidos.clear();                   
        }
        else // limpa tudo para não travar
        {
            removeLaserFogo();
            lasersAtirados = 0;
            lasersAtivos.clear();
            lasersJogadorRemovidos.clear();
        }
    }
            
    private void removeLaserFogo()
    {
        for(int i=0;i<laserFogoJogador.size();i++)
        {
            RaioLaser laser = (RaioLaser) laserFogoJogador.get(i);
            laser.remove();
        }
        
        laserFogoJogador.clear();
    }
    
    private void removeLaserAlien()
    {
        if(laserFogoAlien.size() == lasersAtivosAlien.size())
        {
            
            for(int i=0;i<lasersAtivosAlien.size();i++)
            {
                RaioLaser laser = (RaioLaser) laserFogoAlien.get(i);
                
                if(TimeUtils.timeSinceNanos(laser.getTempoAtivo()) > 2e9) 
                {
                    lasersAlienRemovidos.add(i);
                    lasersAlienAtirados--;
                    
                   if(lasersAlienAtirados<0)
                        lasersAlienAtirados = 0;
                }
            }
            
            for(int i=0;i<lasersAlienRemovidos.size();i++)
            {
                RaioLaser laser = (RaioLaser) laserFogoAlien.get(i);
                laserFogoAlien.remove(laser);
                laser.remove();
                lasersAtivosAlien.remove(i);
            }
            
            lasersAlienRemovidos.clear();                   
        }
        else // limpa tudo para não travar
        {
            removeLaserFogoAlien();
            lasersAlienAtirados = 0;
            lasersAtivosAlien.clear();
            lasersAlienRemovidos.clear();
        }
        
    }
    
    private void naveAlienAtiraLasers()
    {
        if(lasersAlienAtirados <= maxAlienTiros)
        {
            tempoTiroOponente = 0;
            NaveAlien alien = (NaveAlien) ufo;
            laserFogoAlien.add(alien.atiraLasers());
            laserAlien.play(volumeAudio);
            lasersAtivosAlien.add(lasersAlienAtirados++);
            tempoTiroOponente = TimeUtils.nanoTime();
        }
    }

    private void removeLaserFogoAlien()
    {
        for(int i=0;i<laserFogoAlien.size();i++)
        {
            RaioLaser laser = (RaioLaser) laserFogoAlien.get(i);
            laser.remove();
        }
        
        laserFogoAlien.clear();
    }

}