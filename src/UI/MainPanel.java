package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainPanel extends JPanel implements ActionListener {
    private BufferedImage backgroundImage;
    private JMenuBar menuBar;
    private JMenu CommandLinks, SeparatorAndMarks,GenerateCommandLinks,GenerateCommandLinksFile;
    private JMenuItem AddAndRemoveSeparators, AddAndRemoveMarks,Registrations,Deposits,Positions,Commissions,MovePosition,MoveUserToNewAff,Disqualify,ManualQualification,MarkAsFraud;
    private JPanel northPanel;
    public MainPanel(){
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));
        try {
            backgroundImage = ImageIO.read(new File("src/assets/logo.jpg"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        CommandLinks = new JMenu("Command links");
        CommandLinks.setFont(new Font("Arial",Font.BOLD,15));
        CommandLinks.setIcon(new ImageIcon("src/assets/command.png"));
        GenerateCommandLinks = new JMenu("Generate command links");

        GenerateCommandLinks.addActionListener(this);
        GenerateCommandLinksFile = new JMenu("Generate command links from file");
        GenerateCommandLinksFile.addActionListener(this);
        CommandLinks.add(GenerateCommandLinks);
        CommandLinks.add(GenerateCommandLinksFile);

        AddAndRemoveSeparators = new JMenuItem("Add/Remove Separators");
        AddAndRemoveSeparators.addActionListener(this);
        AddAndRemoveMarks = new JMenuItem("Add/Remove Marks");
        AddAndRemoveMarks.addActionListener(this);
        Registrations = new JMenuItem("Registrations");
        Registrations.addActionListener(this);
        Deposits = new JMenuItem("Deposits");
        Deposits.addActionListener(this);
        Positions = new JMenuItem("Positions");
        Positions.addActionListener(this);

        SeparatorAndMarks = new JMenu("Separators and Marks");
        SeparatorAndMarks.setFont(new Font("Arial",Font.BOLD,15));
        SeparatorAndMarks.setIcon(new ImageIcon("src/assets/quotation.png"));
        SeparatorAndMarks.add(AddAndRemoveSeparators);
        SeparatorAndMarks.add(AddAndRemoveMarks);

        GenerateCommandLinks.add(Registrations);
        GenerateCommandLinks.add(Deposits);
        GenerateCommandLinks.add(Positions);


        menuBar = new JMenuBar();
        menuBar.add(CommandLinks);
        menuBar.add(SeparatorAndMarks);
        menuBar.setBorderPainted(true);

        northPanel = new JPanel();
        northPanel.setBackground(new Color(230,231,232));
        northPanel.setLayout(new BoxLayout(northPanel,BoxLayout.PAGE_AXIS));
        northPanel.add(menuBar);
        add(northPanel,BorderLayout.NORTH);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, ((getWidth() / 2) - (backgroundImage.getWidth() / 2)), ((getHeight() / 2) - (backgroundImage.getHeight() / 2)), backgroundImage.getWidth(), backgroundImage.getHeight(), this);
    }

    public BufferedImage getBackgroundImage() {
        return backgroundImage;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == AddAndRemoveSeparators && AddAndRemoveSeparatorFrame.getFrameCounter() == 0){
            AddAndRemoveSeparatorFrame addCommaSeparatorFrame = new AddAndRemoveSeparatorFrame();
        }
        if (e.getSource() == AddAndRemoveMarks && AddAndRemoveMarkFrame.getFrameCounter() == 0){
            AddAndRemoveMarkFrame addAndRemoveMarkFrame = new AddAndRemoveMarkFrame();
        }
        if (e.getSource() == Registrations && GenerateRegisterCommandLinksFrame.getFrameCounter() == 0){
            GenerateRegisterCommandLinksFrame frame = new GenerateRegisterCommandLinksFrame();
        }
        if (e.getSource() == Deposits && GenerateDepositsCommandLinksFrame.getFrameCounter() == 0){
            GenerateDepositsCommandLinksFrame frame = new GenerateDepositsCommandLinksFrame();
        }
        if (e.getSource() == Positions && GeneratePositionsCommandLinksFrame.getFrameCounter() == 0){
            GeneratePositionsCommandLinksFrame frame = new GeneratePositionsCommandLinksFrame();
        }
    }
}
