package UI;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainPanel panel  = new MainPanel();
    public MainFrame (){
        add(panel);
        setSize(panel.getBackgroundImage().getWidth()+100,panel.getBackgroundImage().getHeight()+100);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Cellxpert Support");
        setMinimumSize(new Dimension(panel.getBackgroundImage().getWidth()+100,panel.getBackgroundImage().getHeight()+100));
        setVisible(true);
    }
}
