import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

public class GameFrame extends JFrame
{
    private final LevelPanel levelPanel;
    private final PathPanel pathPanel;
    private final AgentDataPanel[] agentDataPanels = new AgentDataPanel[8];
    private final DecisionPanel decisionPanel;

    public GameFrame()
    {
        super(Resource.TITLE);
        this.setSize(1280, 720);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Resource.LOGO);

        this.setContentPane(new JComponent()
        {
            @Override
            public void paintComponent(Graphics g)
            {
                g.drawImage(Resource.getImage("background"), 0, 0, this.getWidth(), this.getHeight(), this);
            }
        });

        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;

        this.levelPanel = new LevelPanel();
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.25;
        c.weighty = 0.35;
        c.insets = new Insets(16, 16, 16, 0);
        this.add(levelPanel, c);

        JPanel informationPanel = new JPanel();
        informationPanel.setOpaque(false);
        informationPanel.setLayout(new GridLayout(4, 2, 16, 4));
        c.gridx = 1;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.75;
        c.weighty = 0.35;
        c.insets = new Insets(8, 16, 8, 16);
        for (int i = 0; i < 8; i++)
        {
            this.agentDataPanels[i] = new AgentDataPanel();
            informationPanel.add(this.agentDataPanels[i]);
        }
        this.add(informationPanel, c);

        this.pathPanel = new PathPanel();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 0.5;
        c.insets = new Insets(0, 0, 0, 0);
        this.add(pathPanel, c);

        this.decisionPanel = new DecisionPanel();
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.weightx = 1;
        c.weighty = 0.15;
        c.insets = new Insets(16, 64, 16, 64);
        this.add(decisionPanel, c);
    }

    public LevelPanel getLevelPanel()
    {
        return this.levelPanel;
    }

    public PathPanel getPathPanel()
    {
        return this.pathPanel;
    }

    public AgentDataPanel[] getAgentDataPanels()
    {
        return this.agentDataPanels;
    }

    public AgentDataPanel getAgentDataPanelOf(Agent relativeAgent)
    {
        for (AgentDataPanel panel : this.agentDataPanels)
            if (panel.getRelativeAgent() == relativeAgent)
                return panel;

        return null;
    }

    public DecisionPanel getDecisionPanel()
    {
        return this.decisionPanel;
    }
}