import java.util.ArrayList;

public class CompSammie extends Agent
{
    @Override
    public boolean decision(GameMetaData data)
    {
        int countHazard = 0;
        boolean treasure = false;

        Game.sleep((int)(1000 + Math.random() * 3000));

        ArrayList<Card> path = data.getPath();

        for (int i = 0 ; i < path.size() ; i++)
        {
            if (path.get(i) instanceof Hazard)
                countHazard++;
            if (path.get(i) instanceof Treasure)
                treasure = true;
        }

        if (countHazard > 3 || getGems() > 18)
            return false;
        else if (path.get(path.size() - 1) instanceof Treasure)
            return (Math.random() < 0.9);
        else if (treasure || getGems() > 14)
            return (Math.random() < 0.7);
        else if (data.getStayExplorersCount() < 4)
            return (Math.random() < 0.55);
        return (Math.random() < 0.35);

    }
}
