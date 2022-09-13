import java.util.ArrayList;

public class Gemstone extends Treasure
{
    private int remainValue;
    
    public Gemstone(int number, int value)
    {
        super(number, value);
        this.remainValue = this.value;
    }
    
    public Gemstone(Gemstone g)
    {
        super(g.getNumber(), g.value);
        this.remainValue = g.remainValue;
    }

    public int getRemainValue()
    {
        return this.remainValue;
    }
    
    public void resetValue()
    {
        this.remainValue = this.value;
    }

    @Override
    public String name()
    {
        return "Gemstone";
    }

    @Override
    public void share(ArrayList<Agent> receivers)
    {
        if (!receivers.isEmpty())
        {
            int oneShare = this.remainValue / receivers.size();
            this.remainValue %= receivers.size();

            for (Agent receiver : receivers)
                receiver.addGems(oneShare);
        }
    }
    
    @Override
    public String toString()
    {
        return String.format("%s <Gemstone %d with value %d/%d>", this.name(), this.getNumber(), this.remainValue, this.value);
    }
}
