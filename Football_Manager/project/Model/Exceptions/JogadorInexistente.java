package Model.Exceptions;

public class JogadorInexistente extends Exception
{
    public JogadorInexistente()
    {
        super();
    }

    public JogadorInexistente(String msg)
    {
        super(msg);
    }
}
