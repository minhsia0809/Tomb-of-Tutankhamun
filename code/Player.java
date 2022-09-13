public class Player extends Agent implements Controllable
{
    private final DecisionPanel decisionPanel;

    public Player(DecisionPanel decisionPanel)
    {
        this.decisionPanel = decisionPanel;
    }
    
    @Override
    public boolean decision(GameMetaData data)
    {
        this.decisionPanel.addButton();

        while (!this.decisionPanel.isDecided())
            Game.sleep(50);

        this.decisionPanel.removeButton();
        return this.decisionPanel.getDecision();
    }
}
