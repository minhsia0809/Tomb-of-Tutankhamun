import java.lang.reflect.Constructor;

public abstract class Card implements Cloneable
{
    private int number;

    public Card(int number)
    {
        this.number = number;
    }

    public abstract String name();

    public int getNumber()
    {
        return this.number;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Card)
        {
            Card c = (Card) obj;
            return this.getClass() == c.getClass() && this.number == c.number;
        }
        else
            return false;
    }
    
    @Override
    public final Card clone() throws CloneNotSupportedException
    {
        super.clone();

        try
        {
            Class<? extends Card> clazz = this.getClass();
            Constructor<? extends Card> constructor = clazz.getDeclaredConstructor(clazz);
            constructor.setAccessible(true);
            return constructor.newInstance(clazz.cast(this));
        }
        catch (Exception e)
        {
            throw new CloneNotSupportedException();
        }
    }
}
