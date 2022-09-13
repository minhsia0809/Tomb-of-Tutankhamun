import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

public class LevelPanel extends JPanel
{
    private int currentRound = 0;

    public LevelPanel()
    {
        this.setOpaque(false);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setFont(Resource.getFont(Font.PLAIN, 36));
        g.setColor(new Color(45, 28, 9, 128));

        g.drawImage(Resource.getImage("map"), 0, 0, this.getWidth(), this.getHeight(), this);
        g.drawImage(Resource.getImage("area_" + this.currentRound), 0, 0, this.getWidth(), this.getHeight(), this);

        String text = "Area " + (this.currentRound + 1);
        Point center = Resource.centeredStringPoint(text, g, this);
        g.drawString(text, center.x, center.y);
    }

    public void setCurrentRound(int currentRound)
    {
        this.currentRound = currentRound;
    }
}
