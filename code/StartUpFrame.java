import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StartUpFrame extends JFrame implements ActionListener
{
    private final JComboBox<Integer> playerCountSelector;
    private final JCheckBox playableChecker;

    public StartUpFrame()
    {
        super(Resource.TITLE);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage(Resource.LOGO);

        Border compoundBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16), BorderFactory.createEtchedBorder());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 1));

        mainPanel.add(new JComponent()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                g.drawImage(Resource.getImage("theme"), 16, 16, this.getWidth() - 32, this.getHeight() - 32, this);
            }
        });

        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel settingPanel = new JPanel();
        settingPanel.setLayout(new FlowLayout());
        settingPanel.setBorder(compoundBorder);
        settingPanel.add(new JLabel("Player Count", SwingConstants.CENTER));
        this.playerCountSelector = new JComboBox<>(new Integer[]{3, 4, 5, 6, 7, 8});
        this.playerCountSelector.setSelectedItem(6);
        settingPanel.add(this.playerCountSelector);
        this.playableChecker = new JCheckBox("Playable", true);
        settingPanel.add(this.playableChecker);

        tabbedPane.addTab("Setting", settingPanel);

        JTextArea gameRuleArea = new JTextArea(12, 48);
        gameRuleArea.setText(Resource.loadGameRule());
        gameRuleArea.setLineWrap(true);
        gameRuleArea.setEditable(false);
        JScrollPane gameRulePane = new JScrollPane(gameRuleArea);
        gameRulePane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        gameRulePane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        gameRulePane.setBorder(compoundBorder);

        tabbedPane.addTab("Game Rule", gameRulePane);

        JPanel creditPanel = new JPanel();
        creditPanel.setLayout(new FlowLayout());
        creditPanel.setBorder(compoundBorder);
        creditPanel.add(new JLabel("<html>Background music:<br/>\"Egyptian Style, Dune Style\" by Spring Spring is licensed under CC BY 4.0</html>"));

        tabbedPane.addTab("Credit", creditPanel);

        mainPanel.add(tabbedPane);
        this.add(mainPanel, BorderLayout.CENTER);

        JButton b = new JButton("Start");
        b.addActionListener(this);
        this.add(b, BorderLayout.SOUTH);

        this.pack();
        this.setLocationRelativeTo(null);
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Game game = new Game((int) this.playerCountSelector.getSelectedItem(), this.playableChecker.isSelected());
        new Thread(game).start();
        this.dispose();
    }
}
