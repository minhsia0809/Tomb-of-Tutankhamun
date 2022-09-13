import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

public final class Resource
{
    public static final String TITLE = "Tomb of Tutankhamun";
    public static final Image LOGO;
    public static final Color GEMSTONE_COLOR = new Color(171, 235, 198);
    public static final Color ARTIFACT_COLOR = new Color(249, 231, 159);
    public static final Color HAZARD_COLOR = new Color(245, 183, 177);

//    private static final String SRC_PATH = "C:\\Users\\Leo-PC\\Desktop\\these_weed\\resource\\";
    private static final String SRC_PATH = "." + File.separator + "resource" + File.separator;
    private static final Font FONT;
    private static final HashMap<String, Image> IMAGES = new HashMap<>();

    private Resource()
    {
        throw new UnsupportedOperationException();
    }

    static
    {
        LOGO = new ImageIcon(SRC_PATH + "logo.png").getImage();

        Font tempFont = null;

        try
        {
            Font f = Font.createFont(Font.TRUETYPE_FONT, new File(SRC_PATH + "font" + File.separator + "dpcomic.regular.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(f);

            tempFont = new Font("DPComic", Font.PLAIN, 1);
        }
        catch (FontFormatException | IOException e)
        {
            e.printStackTrace();
            System.exit(0);
        }
        finally
        {
            FONT = tempFont;
        }

        File[] listImageFiles = Objects.requireNonNull(new File(SRC_PATH + "image").listFiles());
        for (File file : listImageFiles)
        {
            String fileName = file.toString();
            if (fileName.endsWith(".png"))
                IMAGES.put(fileName.substring(fileName.lastIndexOf(File.separator) + 1, fileName.lastIndexOf('.')), new ImageIcon(fileName).getImage());
        }
    }

    public static String loadGameRule()
    {
        StringBuilder gameRule = new StringBuilder();
        String line;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(Resource.SRC_PATH + "game_rule.txt"), StandardCharsets.UTF_8)))
        {
            while ((line = reader.readLine()) != null)
                gameRule.append(line + '\n');
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(0);
        }

        return gameRule.toString().trim();
    }

    public static Font getFont(int style, float size)
    {
        return FONT.deriveFont(style, size);
    }

    public static Image getImage(String imageName)
    {
        return IMAGES.get(imageName);
    }

    public static Point centeredStringPoint(String text, Graphics g, Component c)
    {
        FontMetrics metrics = g.getFontMetrics(g.getFont());
        int centeredTextX = (c.getWidth() - metrics.stringWidth(text)) / 2;
        int centeredTextY = ((c.getHeight() - metrics.getHeight()) / 2) + metrics.getAscent();

        return new Point(centeredTextX, centeredTextY);
    }
}
