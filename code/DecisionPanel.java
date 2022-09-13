import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DecisionPanel extends JPanel implements ActionListener
{
    private final DecisionButton leaveButton;
    private final DecisionButton stayButton;
    private final DecisionButton restartButton;
    private final DecisionButton quitButton;
    private boolean decided = false;
    private boolean decision;
    private boolean restart = false;

    public DecisionPanel()
    {
        this.setLayout(new GridLayout(1, 2, 64, 16));
        this.setPreferredSize(this.getPreferredSize());
        this.setOpaque(false);

        this.leaveButton = new DecisionButton("LEAVE");
        this.leaveButton.addActionListener(this);

        this.stayButton = new DecisionButton("STAY");
        this.stayButton.addActionListener(this);

        this.restartButton = new DecisionButton("RESTART");
        this.restartButton.addActionListener(this);

        this.quitButton = new DecisionButton("QUIT");
        this.quitButton.addActionListener(this);
    }

    public void addButton()
    {
        this.add(this.leaveButton);
        this.add(this.stayButton);

        this.decided = false;
        this.revalidate();
        this.repaint();
    }

    public void removeButton()
    {
        this.remove(this.leaveButton);
        this.remove(this.stayButton);
        this.remove(this.restartButton);
        this.remove(this.quitButton);

        this.revalidate();
        this.repaint();
    }

    public void endGame()
    {
        this.add(this.quitButton);
        this.add(this.restartButton);

        this.revalidate();
        this.repaint();

        this.restart = false;
        while (!this.restart)
            Game.sleep(50);
        this.restart = false;
    }

    public boolean isDecided()
    {
        return this.decided;
    }

    public boolean getDecision()
    {
        return this.decision;
    }

    private class DecisionButton extends JButton
    {
        public DecisionButton(String text)
        {
            super(text);
            this.setFont(Resource.getFont(Font.PLAIN, 44));
            this.setOpaque(false);
            this.setContentAreaFilled(false);
            this.setBorderPainted(false);
        }

        @Override
        public void paintComponent(Graphics g)
        {
            super.paintComponent(g);

            g.drawImage(Resource.getImage("button"), 0, 0, this.getWidth(), this.getHeight(), this);

            String text = this.getText();
            Point center = Resource.centeredStringPoint(text, g, this);
            g.drawString(text, center.x, center.y);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event)
    {
        if (event.getSource() == this.quitButton)
            System.exit(0);
        else if (event.getSource() == this.restartButton)
            this.restart = true;
        else
        {
            this.decision = (event.getSource() == this.stayButton);
            this.decided = true;
        }
    }
}
