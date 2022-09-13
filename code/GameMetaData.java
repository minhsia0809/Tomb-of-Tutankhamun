import java.util.ArrayList;

public class GameMetaData
{
    private final int stayExplorersCount;
    private final ArrayList<Card> path;
    private final ArrayList<Hazard> removedHazards;

    public GameMetaData(Game game){
        this.stayExplorersCount = game.getStayExplorers().size();
        this.path = new ArrayList<>();
        this.removedHazards = new ArrayList<>();

        for (Card c : game.getPath())
        {
            try
            {
                this.path.add(c.clone());
            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
        }

        for (Hazard h : game.getRemovedHazards())
        {
            try
            {
                this.removedHazards.add((Hazard) h.clone());
            }
            catch (CloneNotSupportedException e)
            {
                e.printStackTrace();
            }
        }
    }

    public int getStayExplorersCount()
    {
        return this.stayExplorersCount;
    }

    public ArrayList<Card> getPath()
    {
        return this.path;
    }

    public ArrayList<Hazard> getRemovedHazards()
    {
        return this.removedHazards;
    }
}
