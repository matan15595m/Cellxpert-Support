package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class GenerateDepositsCommandLinksFrame extends JFrame implements ActionListener {
    private final String[] Methods = {"Generate transaction ID manually","Generate transaction ID automatically"};
    private JTextField domain,userID,brand,linksAmount,transactionID,transactionSum,actionType;
    private JComboBox<String>generateMethod;
    private JButton generate,clear;
    private JTextArea outputArea;
    private JScrollPane ScrollPane;
    private JPanel northPanel,southPanel;
    private JLabel result,label1,label2,label3,label4,label5,label6,label7,label8;
    private static int frameCounter = 0;
    private Font font = new Font(Font.SANS_SERIF,Font.BOLD,13);
    private ArrayList<Long> Idlist = new ArrayList<>();
    private final String baseURLformat = "http://%s/transaction/?brand=%s&userid=%s&transactionid=%s&transaction_sum=%s&actiontype=%s";
    public GenerateDepositsCommandLinksFrame(){
        domain = new JTextField();
        domain.setToolTipText("Domain");

        userID = new JTextField();
        userID.setToolTipText("User ID");
        brand = new JTextField();
        brand.setToolTipText("Brand");
        transactionID = new JTextField();
        transactionID.setToolTipText("Transaction ID");
        transactionSum = new JTextField();
        transactionSum.setToolTipText("Transaction Sum");
        actionType = new JTextField();
        actionType.setToolTipText("Action Type");

        linksAmount = new JTextField();
        linksAmount.setToolTipText("Command links amount");

        generateMethod = new JComboBox<>(Methods);
        generate = new JButton("Generate");
        generate.addActionListener(this);
        clear = new JButton("Clear All");
        clear.addActionListener(this);

        outputArea = new JTextArea();
        ScrollPane = new JScrollPane(outputArea);
        ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        result = new JLabel("Result:",SwingConstants.CENTER);
        result.setFont(new Font("Arial",Font.BOLD,25));

        setLayout(new BorderLayout());

        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(11, 2,50,0));
        label1 = new JLabel(" Generating Method:",SwingConstants.CENTER);
        label1.setFont(font);
        northPanel.add(label1);
        generateMethod.addActionListener(this);
        northPanel.add(generateMethod);

        label2 = new JLabel(" Domain (Mandatory):",SwingConstants.CENTER);
        label2.setFont(font);
        northPanel.add(label2);
        northPanel.add(domain);

        label3 = new JLabel(" User ID (Mandatory):",SwingConstants.CENTER);
        label3.setFont(font);
        northPanel.add(label3);
        northPanel.add(userID);

        label4 = new JLabel(" Brand (Mandatory):",SwingConstants.CENTER);
        label4.setFont(font);
        northPanel.add(label4);
        northPanel.add(brand);

        label5 = new JLabel(" Transaction ID (Mandatory):",SwingConstants.CENTER);
        label5.setFont(font);
        northPanel.add(label5);
        northPanel.add(transactionID);

        label6 = new JLabel(" Transaction Sum (Mandatory):",SwingConstants.CENTER);
        label6.setFont(font);
        northPanel.add(label6);
        northPanel.add(transactionSum);

        label7 = new JLabel(" Action Type (Mandatory):",SwingConstants.CENTER);
        label7.setFont(font);
        northPanel.add(label7);
        northPanel.add(actionType);

        label8 = new JLabel(" Links Amount (Mandatory):",SwingConstants.CENTER);
        label8.setFont(font);
        northPanel.add(label8);
        northPanel.add(linksAmount);

        generate.setFont(font);
        northPanel.add(generate);
        clear.setFont(font);
        northPanel.add(clear);

        southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(result,BorderLayout.NORTH);
        southPanel.add(ScrollPane,BorderLayout.CENTER);

        add(northPanel,BorderLayout.NORTH);
        add(southPanel);

        setMinimumSize(new Dimension(1000, 600));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Generate Deposits Command Links");
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateMethod){
            setIsManually();
        }
        if (e.getSource() == generate)
        {
            generateCommandLinks();
        }
        if (e.getSource() == clear){
            clearAll();
        }
    }
    private void clearAll(){
        outputArea.setText("");
        domain.setText("");
        userID.setText("");
        brand.setText("");
        transactionID.setText("");
        transactionSum.setText("");
        actionType.setText("");
        linksAmount.setText("");
    }
    private void generateCommandLinks(){
        if (validateInput())
        {
            switch (generateMethod.getSelectedItem().toString())
            {
                case "Generate transaction ID manually":
                    StringBuilder builder = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                        builder.append(String.format(baseURLformat, domain.getText(),brand.getText(),userID.getText(),transactionID.getText(),transactionSum.getText(),actionType.getText())).append('\n');
                    }
                    outputArea.setText(builder.toString());
                    break;
                case "Generate transaction ID automatically":
                    StringBuilder builder1 = new StringBuilder();
                    for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                        builder1.append(String.format(baseURLformat, domain.getText(),brand.getText(),userID.getText(),generateRandomID(),actionType.getText(),actionType.getText())).append('\n');
                    }
                    outputArea.setText(builder1.toString());
                    Idlist.clear();
                    break;
            }
        }
        else {
            JOptionPane.showMessageDialog(this,"Please fill all mandatory fields properly!");
        }
    }


    public static int getFrameCounter() {
        return frameCounter;
    }
    private void setIsManually() {
        if (generateMethod.getSelectedItem().equals("Generate transaction ID automatically")){
            transactionID.setEnabled(false);
            transactionID.setBackground(new Color(230,231,232));
        }
        else {
            transactionID.setEnabled(true);
            transactionID.setBackground(new Color(255, 255, 255));
        }
    }
    private long generateRandomID(){
        Random random = new Random();
        long number = random.nextLong();
        while (number < 0 || Idlist.contains(number)){
            number = random.nextLong();
        }
        Idlist.add(number);
        return number;
    }
    private boolean validateLinksAmount(){
        boolean isInt = true;
        int LinksAmount = 0;
        try {
            LinksAmount = Integer.parseInt(linksAmount.getText());
        }catch (NumberFormatException e){
            isInt = false;
        }
        return isInt && (LinksAmount > 0);
    }
    private boolean validateInput(){
        switch (generateMethod.getSelectedItem().toString())
        {
            case "Generate user and lead ID manually":
                if(domain.getText().isEmpty() || userID.getText().isEmpty() || brand.getText().isEmpty() || transactionID.getText().isEmpty() || transactionSum.getText().isEmpty() || actionType.getText().isEmpty() || linksAmount.getText().isEmpty()) {
                    return false;
                }
                break;
            case "Generate user and lead ID automatically":
                if(domain.getText().isEmpty() || userID.getText().isEmpty() || brand.getText().isEmpty() || transactionSum.getText().isEmpty() || actionType.getText().isEmpty() || linksAmount.getText().isEmpty()){
                    return false;
                }
                break;
        }
        return validateLinksAmount();
    }

    public static void setFrameCounter(int frameCounter) {
        GenerateDepositsCommandLinksFrame.frameCounter = frameCounter;
    }
}
