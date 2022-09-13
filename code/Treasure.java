import java.util.ArrayList;

public abstract class Treasure extends Card
{
    protected final int value;
    
    public Treasure(int number, int value)
    {
        super(number);
        this.value = value;
    }

    public int getValue()
    {
        return this.value;
    }

    public abstract void share(ArrayList<Agent> receivers);
}
