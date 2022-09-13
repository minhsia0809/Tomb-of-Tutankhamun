import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;

public class PathPanel extends JPanel
{
    private ArrayList<Card> path;
    private int paintPosition = 0;
    private boolean gameOver;

    public PathPanel()
    {
        this.setOpaque(false);
        this.gameOver = false;

        MouseAdapter mouseAdapter = new MouseAdapter()
        {
            private int offset;

            @Override
            public void mousePressed(MouseEvent event)
            {
                if (event.getButton() == MouseEvent.BUTTON1)
                    this.offset = event.getX();
            }

            @Override
            public void mouseReleased(MouseEvent event)
            {
                if (event.isMetaDown())
                {
                    PathPanel.this.paintPosition = Integer.MIN_VALUE;
                    PathPanel.this.repaint();
                }
            }

            @Override
            public void mouseDragged(MouseEvent event)
            {
                if (event.getModifiersEx() == MouseEvent.BUTTON1_DOWN_MASK)
                {
                    int change = event.getX() - this.offset;
                    PathPanel.this.paintPosition += change;
                    this.offset = event.getX();
                    PathPanel.this.repaint();
                }
            }

            @Override
            public void mouseWheelMoved(MouseWheelEvent event)
            {
                PathPanel.this.paintPosition -= event.getWheelRotation() * 64;
                PathPanel.this.repaint();
            }
        };

        this.addMouseListener(mouseAdapter);
        this.addMouseMotionListener(mouseAdapter);
        this.addMouseWheelListener(mouseAdapter);
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        g.setColor(new Color(246, 221, 204, 127));

        int whole = (this.path.size() * 240) + 40;

        if (this.paintPosition > 0 || whole < this.getWidth())
            this.paintPosition = 0;
        else if (this.paintPosition < this.getWidth() - whole)
            this.paintPosition = this.getWidth() - whole;

        int roomWidth = (int) (this.getWidth() * this.getWidth() / (double) whole);
        int error = (int) (Math.abs(this.paintPosition) * this.getWidth() / (double) whole);
        g.fillRect(error, this.getHeight() - 20, roomWidth, 16);

        for (int i = 0; i < this.path.size(); i++)
        {
            Card room = this.path.get(i);
            int cardCode = Math.abs(room.hashCode() >> room.getNumber());
            Image roomImage = null;
            Image backgroundImage = null;
            Image itemImage = null;

            if (room instanceof Gemstone)
            {
                Gemstone roomOfGemstone = (Gemstone) room;

                roomImage = Resource.getImage("room_" + (cardCode / 13 % 5));
                backgroundImage = Resource.getImage("gemstone_" + (cardCode % 4));
                if (roomOfGemstone.getRemainValue() > 0)
                    itemImage = Resource.getImage("gemstone_item_" + (cardCode / 17 % 6));
                g.setColor(Resource.GEMSTONE_COLOR);
            }
            else if (room instanceof Artifact)
            {
                Artifact roomOfArtifact = (Artifact) room;

                roomImage = Resource.getImage("artifact_room_" + (cardCode % 2));
                if (roomOfArtifact.isInTomb())
                    itemImage = Resource.getImage("artifact_" + room.getNumber());
                g.setColor(Resource.ARTIFACT_COLOR);
            }
            else if (room instanceof Hazard)
            {
                roomImage = Resource.getImage("room_" + (cardCode % 4));
                backgroundImage = Resource.getImage("hazard_" + room.getNumber());
                g.setColor(Resource.HAZARD_COLOR);
            }

            g.drawImage(roomImage, this.paintPosition + 40 + (i * 240), 20, 200, 200, this);
            g.drawImage(backgroundImage, this.paintPosition + 40 + (i * 240), 20, 200, 200, this);
            g.drawImage(itemImage, this.paintPosition + 40 + (i * 240), 20, 200, 200, this);

            g.setFont(Resource.getFont(Font.PLAIN, 22));
            g.drawString(room.name(), this.paintPosition + 50 + (i * 240), 250);

            if (itemImage != null)
            {
                g.setFont(Resource.getFont(Font.PLAIN, 54));
                int value = room instanceof Gemstone ? ((Gemstone) room).getRemainValue() : ((Artifact) room).getValue();
                g.drawString(String.format("%2d", value), this.paintPosition + 50 + (i * 240) + 130, 64);
            }
        }

        if (this.gameOver)
        {
            g.setFont(Resource.getFont(Font.PLAIN, 96));
            g.setColor(Resource.ARTIFACT_COLOR);

            String text1 = "Game Over";
            Point center1 = Resource.centeredStringPoint(text1, g, this);
            g.drawString(text1, center1.x, center1.y);

            g.setFont(Resource.getFont(Font.PLAIN, 48));

            String text2 = "Thanks for Playing";
            Point center2 = Resource.centeredStringPoint(text2, g, this);
            g.drawString(text2, center2.x, center2.y + this.getHeight() / 4);
        }
    }

    public void setPath(ArrayList<Card> path)
    {
        this.path = path;
    }

    public void setPaintPosition(int paintPosition)
    {
        this.paintPosition = paintPosition;
    }

    public void setGameOver(boolean gameOver)
    {
        this.gameOver = gameOver;
    }
}
