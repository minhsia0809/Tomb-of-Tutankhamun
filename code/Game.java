import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;

public class Game implements Runnable
{
    private static final int ROUND = 5;
    private static final Random RANDOM = new Random(System.currentTimeMillis());

    private int currentRound;
    private final boolean hasPlayer;
    private final GameFrame frame;
    private final ArrayList<Agent> explorers = new ArrayList<>();
    private final ArrayList<Card> deck = new ArrayList<>();
    private final ArrayList<Card> path = new ArrayList<>();
    private final ArrayList<Artifact> allArtifacts = new ArrayList<>();
    private final ArrayList<Hazard> removedHazards = new ArrayList<>();

    public Game(int numberOfParticipants, boolean hasPlayer)
    {
        if (numberOfParticipants < 3 || numberOfParticipants > 8)
            throw new IllegalStateException("3 ~ 8");

        this.hasPlayer = hasPlayer;
        this.frame = new GameFrame();

        ArrayList<Class<? extends Agent>> computerClasses = new ArrayList<>();
        computerClasses.add(CompDefault.class);
        computerClasses.add(CompSammi.class);
        computerClasses.add(CompSammie.class);

        if (hasPlayer)
        {
            this.explorers.add(new Player(this.frame.getDecisionPanel()));
            numberOfParticipants--;
        }

        try
        {
            for (int i = 0; i < numberOfParticipants; i++)
            {
                Agent computer = computerClasses.get(RANDOM.nextInt(computerClasses.size())).getConstructor().newInstance();
                this.explorers.add(computer);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            System.exit(0);
        }

        this.frame.getPathPanel().setPath(this.path);
        for (int i = 0; i < this.explorers.size(); i++)
            this.frame.getAgentDataPanels()[i].setRelativeAgent(this.explorers.get(i));
    }

    @Override
    public void run()
    {
        this.frame.setVisible(true);

        while (true)
        {
            this.initializeGame();

            while (this.currentRound < Game.ROUND)
            {
                Game.sleep(1000);

                this.initializeRound();
                this.frame.getPathPanel().repaint();
                for (AgentDataPanel agentDataPanel : this.frame.getAgentDataPanels())
                    agentDataPanel.repaint();

                Game.sleep(1000);

                do
                {
                    this.path.add(this.deck.remove(0));
                    this.frame.getPathPanel().setPaintPosition(Integer.MIN_VALUE);
                    this.frame.getPathPanel().repaint();

                    Game.sleep(750);

                    Card currentRoom = this.path.get(this.path.size() - 1);

                    if (currentRoom instanceof Gemstone)
                    {
                        Gemstone roomOfGemstone = (Gemstone) currentRoom;
                        roomOfGemstone.share(this.getStayExplorers());
                    }
                    else if (currentRoom instanceof Hazard)
                    {
                        Hazard roomOfHazard = (Hazard) currentRoom;

                        for (int i = 0; i < this.path.size() - 1; i++)
                        {
                            if (roomOfHazard.equals(this.path.get(i)))
                            {
                                for (Agent stayExplorer : this.getStayExplorers())
                                    stayExplorer.flee();
                                this.removedHazards.add(roomOfHazard);
                                break;
                            }
                        }
                    }

                    this.frame.getPathPanel().repaint();
                    for (AgentDataPanel agentDataPanel : this.frame.getAgentDataPanels())
                        agentDataPanel.repaint();

                    ArrayList<Agent> leavingExplorers = new ArrayList<>();
                    HashMap<Agent, Thread> actionOrder = new HashMap<>();

                    for (Agent stayExplorer : this.getStayExplorers())
                    {
                        Thread action = new Thread(() -> stayExplorer.act(this));
                        action.start();
                        actionOrder.put(stayExplorer, action);
                    }

                    for (Map.Entry<Agent, Thread> actionSet : actionOrder.entrySet())
                    {
                        try
                        {
                            actionSet.getValue().join();
                        }
                        catch (InterruptedException e)
                        {
                            actionSet.getValue().interrupt();
                        }

                        if (actionSet.getKey().isStateIn(Agent.DECIDED_TO_LEAVE))
                            leavingExplorers.add(actionSet.getKey());
                    }

                    Game.sleep(500);

                    for (Agent explorer : this.explorers)
                    {
                        explorer.executeDecision();
                        this.frame.getAgentDataPanelOf(explorer).repaint();
                    }

                    if (!leavingExplorers.isEmpty())
                    {
                        for (Card room : this.path)
                            if (room instanceof Treasure)
                                ((Treasure) room).share(leavingExplorers);
                    }

                    this.frame.getPathPanel().repaint();

                    Game.sleep(1000);
                }
                while (this.isSomeoneStay());

                for (Agent explorer : this.explorers)
                {
                    explorer.storeGemsIntoTent();
                    this.frame.getAgentDataPanelOf(explorer).repaint();
                }

                this.currentRound++;
            }

            // Game over

            for (Agent explorer : this.findWinners())
                explorer.setState(Agent.VICTORY);

            for (Agent explorer : this.explorers)
            {
                this.frame.getAgentDataPanelOf(explorer).setShowDetailedData(true);
                this.frame.getAgentDataPanelOf(explorer).repaint();
            }

            this.path.clear();
            this.frame.getPathPanel().setGameOver(true);
            this.frame.getPathPanel().repaint();
            this.frame.getDecisionPanel().endGame();
        }
    }

    private void setUpCards()
    {
        this.deck.clear();
        this.path.clear();
        this.removedHazards.clear();

        this.deck.add(new Hazard(0));
        this.deck.add(new Hazard(0));
        this.deck.add(new Hazard(0));
        this.deck.add(new Hazard(1));
        this.deck.add(new Hazard(1));
        this.deck.add(new Hazard(1));
        this.deck.add(new Hazard(2));
        this.deck.add(new Hazard(2));
        this.deck.add(new Hazard(2));
        this.deck.add(new Hazard(3));
        this.deck.add(new Hazard(3));
        this.deck.add(new Hazard(3));
        this.deck.add(new Hazard(4));
        this.deck.add(new Hazard(4));
        this.deck.add(new Hazard(4));
        
        this.deck.add(new Gemstone(0, 1));
        this.deck.add(new Gemstone(1, 2));
        this.deck.add(new Gemstone(2, 3));
        this.deck.add(new Gemstone(3, 4));
        this.deck.add(new Gemstone(4, 5));
        this.deck.add(new Gemstone(4, 5));
        this.deck.add(new Gemstone(5, 7));
        this.deck.add(new Gemstone(5, 7));
        this.deck.add(new Gemstone(6, 9));
        this.deck.add(new Gemstone(7, 11));
        this.deck.add(new Gemstone(7, 11));
        this.deck.add(new Gemstone(8, 13));
        this.deck.add(new Gemstone(9, 14));
        this.deck.add(new Gemstone(10, 15));
        this.deck.add(new Gemstone(11, 17));
        
        this.allArtifacts.add(new Artifact(0, 5));
        this.allArtifacts.add(new Artifact(1, 7));
        this.allArtifacts.add(new Artifact(2, 8));
        this.allArtifacts.add(new Artifact(3, 10));
        this.allArtifacts.add(new Artifact(4, 12));
    }

    private void initializeGame()
    {
        this.currentRound = 0;
        this.setUpCards();

        for (Agent explorer : this.explorers)
        {
            explorer.setState(Agent.LEFT);
            explorer.setGems(0);
            explorer.setGemsInsideTent(0);
            explorer.getPossessionOfArtifacts().clear();

            this.frame.getAgentDataPanelOf(explorer).setShowDetailedData(explorer instanceof Controllable || !this.hasPlayer);
            this.frame.getAgentDataPanelOf(explorer).repaint();
        }

        this.frame.getDecisionPanel().removeButton();
        this.frame.getPathPanel().setGameOver(false);
    }

    private void initializeRound()
    {
        for (Agent explorer : this.explorers)
            explorer.setState(Agent.STAY);
        
        this.deck.addAll(this.path);
        this.path.clear();

        for (Card room : this.deck)
            if (room instanceof Gemstone)
                ((Gemstone) room).resetValue();

        this.deck.removeAll(new HashSet<>(this.allArtifacts));
        this.deck.removeAll(new HashSet<>(this.removedHazards));

        this.deck.add(this.allArtifacts.get(this.currentRound));
        Collections.shuffle(this.deck, RANDOM);

        this.frame.getLevelPanel().setCurrentRound(this.currentRound);
        this.frame.getLevelPanel().repaint();
        this.frame.getPathPanel().setPaintPosition(0);
    }
    
    public ArrayList<Agent> getStayExplorers()
    {
        ArrayList<Agent> stayExplorers = new ArrayList<>();
        
        for (Agent explorer : this.explorers)
            if (explorer.isStay())
                stayExplorers.add(explorer);
                
        return stayExplorers;
    }

    private boolean isSomeoneStay()
    {
        for (Agent explorer : this.explorers)
            if (explorer.isStay())
                return true;
            
        return false;
    }
       
    private Agent[] findWinners()
    {
        int max = 0;
        ArrayList<Agent> winners = new ArrayList<>();
        
        for (Agent explorer : this.explorers)
        {
            int totalValue = explorer.totalValue();

            if (max < totalValue)
            {
                max = totalValue;
                winners.clear();
            }

            if (totalValue == max)
                winners.add(explorer);
        }
        
        return winners.toArray(new Agent[0]);
    }

    public GameFrame getFrame()
    {
        return this.frame;
    }

    public ArrayList<Card> getPath()
    {
        return this.path;
    }

    public ArrayList<Hazard> getRemovedHazards()
     {
        return this.removedHazards;
    }

    public static void sleep(long millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException("Unexpected Interrupt", e);
        }
    }
}
