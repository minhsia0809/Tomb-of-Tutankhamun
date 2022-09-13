import java.util.ArrayList;
import java.util.Collections;

public abstract class Agent
{
    private static final ArrayList<Integer> TYPES = new ArrayList<>();
    public static final int LEFT = 0;
    public static final int DECIDED_TO_LEAVE = 1;
    public static final int UNDECIDED = 2;
    public static final int DECIDED_TO_STAY = 3;
    public static final int STAY = 4;
    public static final int VICTORY = 5;

    private final int type;
    private int state;
    private int gems;
    private int gemsInsideTent;
    private final ArrayList<Artifact> possessionOfArtifacts = new ArrayList<>();

    static
    {
        for (int i = 0; i < 8; i++)
            TYPES.add(i);
        Collections.shuffle(TYPES);
    }

    public Agent()
    {
        this.type = TYPES.isEmpty() ? 0 : TYPES.remove(0);
        this.state = Agent.LEFT;
    }
    
    public int getType()
    {
        return this.type;
    }
    
    public void setState(int state)
    {
        if (state >= 0 && state <= 5)
            this.state = state;
        else
            throw new IllegalArgumentException();
    }

    public int getState()
    {
        return this.state;
    }

    public void setGems(int gems)
    {
        this.gems = gems;
    }

    public int getGems()
    {
        return this.gems;
    }

    public void setGemsInsideTent(int gemsInsideTent)
    {
        this.gemsInsideTent = gemsInsideTent;
    }

    public int getGemsInsideTent()
    {
        return this.gemsInsideTent;
    }

    public ArrayList<Artifact> getPossessionOfArtifacts()
    {
        return this.possessionOfArtifacts;
    }

    public void executeDecision()
    {
        if (this.state == Agent.DECIDED_TO_LEAVE)
            this.state = Agent.LEFT;
        else if (this.state == Agent.DECIDED_TO_STAY)
            this.state = Agent.STAY;
    }

    public boolean isStay()
    {
        return this.state == Agent.STAY;
    }

    public boolean isStateIn(int state)
    {
        return this.state == state;
    }

    public void addGems(int gems)
    {
        this.gems += gems;
    }

    public void storeGemsIntoTent()
    {
        this.gemsInsideTent += this.gems;
        this.gems = 0;
    }

    public void flee()
    {
        this.gems = 0;
        this.state = Agent.LEFT;
    }

    public int totalValue()
    {
        int valueOfArtifacts = 0;
        for (Artifact a : this.possessionOfArtifacts)
            valueOfArtifacts += a.getValue();
        return this.gemsInsideTent + valueOfArtifacts;
    }
    
    @Override
    public String toString()
    {
        return String.valueOf(this.type);
    }

    public final void act(Game game)
    {
        if (this.isStay())
        {
            this.state = Agent.UNDECIDED;
            game.getFrame().getAgentDataPanelOf(this).repaint();
            this.state = this.decision(new GameMetaData(game)) ? Agent.DECIDED_TO_STAY : Agent.DECIDED_TO_LEAVE;
            game.getFrame().getAgentDataPanelOf(this).repaint();
        }
    }
    
    public abstract boolean decision(GameMetaData data);
}
