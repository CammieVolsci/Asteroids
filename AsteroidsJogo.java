/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;

/**
 *  
 *  @author Camilla
 */
public class AsteroidsJogo extends JogoPrincipal
{
     /**
     *  
     *  
     */
    @Override
    public void create()
    {
        super.create();
        setTelaAtiva(new TelaMenu());
    }
	
}
