package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class AddAndRemoveSeparatorFrame extends JFrame implements ActionListener {
    private static int frameCounter = 0;
    private JPanel east,west;

    private JLabel westLabel,eastLabel;
    private JTextArea westTextArea,eastTextArea;
    private JButton submit,clear;
    private JScrollPane WestScrollPane,EastScrollPane;
    private String separator;
    private JComboBox<String> Actions;
    private JComboBox<String> Separators;

    private final String[] actions = {"Add","Remove"};
    private final String[] separators = {",","|",";","-","/","_"};
    public AddAndRemoveSeparatorFrame(){

            setLayout(new BorderLayout());
            JPanel northFramePanel = new JPanel();
            northFramePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            submit = new JButton("Submit");
            submit.addActionListener(this);
            clear = new JButton("Clear");
            clear.addActionListener(this);

            Actions = new JComboBox<>(actions);
            Separators = new JComboBox<>(separators);
            northFramePanel.add(submit);
            northFramePanel.add(clear);
            northFramePanel.add(Actions);
            northFramePanel.add(Separators);

            JPanel centerFramePanel = new JPanel();
            centerFramePanel.setLayout(new GridLayout(1,2,30,0));

            west = new JPanel();
            west.setLayout(new BorderLayout());
            east = new JPanel();
            east.setLayout(new BorderLayout());

            westLabel = new JLabel("Input",SwingConstants.CENTER);
            westLabel.setFont(new Font("Arial",Font.BOLD,25));
            eastLabel = new JLabel("Result",SwingConstants.CENTER);
            eastLabel.setFont(new Font("Arial",Font.BOLD,25));

            westTextArea = new JTextArea();
            eastTextArea = new JTextArea();

            WestScrollPane = new JScrollPane(westTextArea);
            WestScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            WestScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
            EastScrollPane = new JScrollPane(eastTextArea);
            EastScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            EastScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

            west.add(westLabel,BorderLayout.NORTH);
            west.add(WestScrollPane,BorderLayout.CENTER);

            east.add(eastLabel,BorderLayout.NORTH);
            east.add(EastScrollPane,BorderLayout.CENTER);

            centerFramePanel.add(west);
            centerFramePanel.add(east);


            add(centerFramePanel,BorderLayout.CENTER);
            add(northFramePanel,BorderLayout.NORTH);

            setMinimumSize(new Dimension(800, 600));
            setDefaultCloseOperation(HIDE_ON_CLOSE);
            setLocationRelativeTo(null);
            setTitle("Add And Remove Separators");
            setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    setFrameCounter(0);
                }
            });
            setVisible(true);
            frameCounter++;

    }
    private void removeSeparator(String separator)
    {
        if(!westTextArea.getText().contains(separator)){
            JOptionPane.showMessageDialog(null,"This input doesn't contain this separator !");
        }
        else{
            StringBuilder builder = new StringBuilder();
            String enteredText = westTextArea.getText().trim();
            for(int i = 0;i < enteredText.length();i++)
            {
                if(!Character.toString(enteredText.charAt(i)).equals(separator))
                    builder.append(enteredText.charAt(i));
            }
            eastTextArea.setText(builder.toString());
        }
    }
    private void addSeparator(String separator){
        StringBuilder builder = new StringBuilder();
        String enteredText = westTextArea.getText().trim();
        for(int i = 0;i < enteredText.length();i++)
        {
            if(Character.isLetter(enteredText.charAt(i)) || Character.isDigit(enteredText.charAt(i)) || Character.toString(enteredText.charAt(i)).equals("-")){
                builder.append(enteredText.charAt(i));
            }else{
                builder.append(separator);
            }
        }
        eastTextArea.setText(builder.toString());
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit){
            switch (Actions.getSelectedItem().toString())
            {
                case "Add":
                    addSeparator(Separators.getSelectedItem().toString());
                    break;
                case "Remove":
                    removeSeparator(Separators.getSelectedItem().toString());
                    break;
                default:
                    break;
            }
        }
        if (e.getSource() == clear){
            westTextArea.setText("");
            eastTextArea.setText("");
        }
    }

    public static int getFrameCounter() {
        return frameCounter;
    }

    public static void setFrameCounter(int frameCounter) {
        AddAndRemoveSeparatorFrame.frameCounter = frameCounter;
    }
}
