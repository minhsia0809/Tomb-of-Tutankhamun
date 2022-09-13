import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Graphics;

public class AgentDataPanel extends JPanel
{
    private Agent relativeAgent;
    private boolean showDetailedData;

    public AgentDataPanel()
    {
        this.setOpaque(false);
        this.relativeAgent = null;
        this.showDetailedData = false;
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        g.setFont(Resource.getFont(Font.PLAIN, 24));

        if (this.relativeAgent != null)
        {
            switch (this.relativeAgent.getState())
            {
                case Agent.LEFT:
                    g.drawImage(Resource.getImage("playerbackground_left"), 0, 0, this.getWidth(), this.getHeight(), this);
                    break;
                case Agent.DECIDED_TO_LEAVE:
                case Agent.DECIDED_TO_STAY:
                    g.drawImage(Resource.getImage("playerbackground"), 0, 0, this.getWidth(), this.getHeight(), this);
                    g.drawImage(Resource.getImage("decided"), 388, 14, (this.getHeight() - 28) * 9 / 11, this.getHeight() - 28, this);
                    break;
                case Agent.UNDECIDED:
                    g.drawImage(Resource.getImage("playerbackground"), 0, 0, this.getWidth(), this.getHeight(), this);
                    g.drawImage(Resource.getImage("undecided"), 388, 14, (this.getHeight() - 28) * 9 / 11, this.getHeight() - 28, this);
                    break;
                case Agent.STAY:
                    g.drawImage(Resource.getImage("playerbackground"), 0, 0, this.getWidth(), this.getHeight(), this);
                    break;
                case Agent.VICTORY:
                    g.drawImage(Resource.getImage("playerbackground"), 0, 0, this.getWidth(), this.getHeight(), this);
                    g.drawImage(Resource.getImage("victory"), 388, 14, (this.getHeight() - 28) * 9 / 11, this.getHeight() - 28, this);
                    break;
            }

            g.drawImage(Resource.getImage("player_" + this.relativeAgent.getType()), 16, 10, this.getHeight() - 20, this.getHeight() - 20, this);

            g.drawImage(Resource.getImage("gem"), 96, 15, (this.getHeight() - 30) * 8 / 11, this.getHeight() - 30, this);

            if (this.relativeAgent.isStateIn(Agent.LEFT) && !this.showDetailedData)
                g.drawString("...", 130, 38);
            else
                g.drawString(String.format("%02d", this.relativeAgent.getGems()), 130, 38);

            if (this.showDetailedData)
            {
                g.drawImage(Resource.getImage("tent"), 250, 15, (this.getHeight() - 30) * 22 / 11, this.getHeight() - 30, this);
                g.drawString(String.format("%02d", this.relativeAgent.totalValue()), 316, 38);
            }
        }
    }

    public void setRelativeAgent(Agent relativeAgent)
    {
        this.relativeAgent = relativeAgent;
    }

    public Agent getRelativeAgent()
    {
        return this.relativeAgent;
    }

    public void setShowDetailedData(boolean showDetailedData)
    {
        this.showDetailedData = showDetailedData;
    }
}
