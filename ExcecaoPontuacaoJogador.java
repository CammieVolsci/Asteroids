/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package programasjava.trabalho;

/**
 *
 * @author Camilla
 */
public class ExcecaoPontuacaoJogador extends Exception 
{

    /**
     * Creates a new instance of <code>PontuacaoJogador</code> without detail
     * message.
     */
    public ExcecaoPontuacaoJogador() 
    {
    }

    /**
     * Constructs an instance of <code>PontuacaoJogador</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcecaoPontuacaoJogador(String msg, Throwable err) 
    {
        super(msg,err);
    }
}
