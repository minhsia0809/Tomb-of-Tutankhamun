import java.util.ArrayList;

public class CompSammi extends Agent
{
    @Override
    public boolean decision(GameMetaData data)
    {
        Game.sleep((int)(1000 + Math.random() * 3000));

        ArrayList<Card> path = data.getPath();
        
        if (path.size() <= 3)
            return true;
        else if (path.get(path.size() - 1) instanceof Treasure)
            return (Math.random() < 0.85);
        else
            return (Math.random() < 0.55);
    }
}
