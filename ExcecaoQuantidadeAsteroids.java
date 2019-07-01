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
public class ExcecaoQuantidadeAsteroids extends Exception 
{

    /**
     * Creates a new instance of <code>QuantidadeAsteroids</code> without detail
     * message.
     */
    public ExcecaoQuantidadeAsteroids() 
    {
    }

    /**
     * Constructs an instance of <code>QuantidadeAsteroids</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ExcecaoQuantidadeAsteroids(String msg, Throwable err) 
    {
        super(msg,err);
    }
}
