package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

public class GeneratePositionsCommandLinksFrame extends JFrame implements ActionListener {
    private final String[] Methods = {"Generate positions ID manually","Generate positions ID automatically"};
    private final String[] PositionsParams = {"No params","LOT Amount","Spread","Symbol","LOT And Symbol","Pip,LOT And Symbol","Spread And Symbol"};
    private JTextField domain,userID,brand,linksAmount, positionID, positionSum, lotAmount,spread,symbol,pip,pl;
    private JComboBox<String>generateMethod,positionParams;
    private JButton generate,clear;
    private JTextArea outputArea;
    private JScrollPane ScrollPane;
    private JPanel northPanel,southPanel;
    private JLabel result,label1,label2,label3,label4,label5,label6,label7,label8,label9,label10,label11,label12,label13;
    private static int frameCounter = 0;
    private Font font = new Font(Font.SANS_SERIF,Font.BOLD,13);
    private ArrayList<Long> Idlist = new ArrayList<>();
    private final String baseURLformat = "http://%s/position/?userid=%s&brand=%s&pl=%s&positionid=%s&position_sum=%s";
    private final String LotURL = "http://%s/position/?userid=%s&brand=%s&pl=%s&positionid=%s&position_sum=%s&LOTAmount=%s";
    private final String spreadURL = "http://%s/position/?userid=%s&brand=%s&pl=%s&positionid=%s&position_sum=%s&spread=%s";
    private final String symbolURL = "http://%s/position/?userid=%s&brand=%s&pl=%s&positionid=%s&position_sum=%s&symbol=%s";
    private final String LotSymbolURL = "http://%s/position/?userid=%s&brand=%s&pl=%s&positionid=%s&position_sum=%s&LOTAmount=%s&symbol=%s";
    private final String PipLotSymbolURL = "http://%s/position/?userid=%s&brand=%s&pl=%s&positionid=%s&position_sum=%s&LOTAmount=%s&pip=%s&symbol=%s";
    private final String SpreadSymbolURL = "http://%s/position/?userid=%s&brand=%s&pl=%s&positionid=%s&position_sum=%s&spread=%s&symbol=%s";
    public GeneratePositionsCommandLinksFrame(){
        domain = new JTextField();
        domain.setToolTipText("Domain");

        userID = new JTextField();
        userID.setToolTipText("User ID");
        brand = new JTextField();
        brand.setToolTipText("Brand");
        positionID = new JTextField();
        positionID.setToolTipText("Position ID");
        positionSum = new JTextField();
        positionSum.setToolTipText("Positions Sum");
        lotAmount = new JTextField();
        lotAmount.setToolTipText("Lot Amount");

        linksAmount = new JTextField();
        linksAmount.setToolTipText("Command links amount");

        spread = new JTextField();
        spread.setToolTipText("Spread");

        symbol = new JTextField();
        symbol.setToolTipText("Symbol");

        pip = new JTextField();
        pip.setToolTipText("Pip");

        pl = new JTextField();
        pl.setToolTipText("PL");

        positionParams = new JComboBox<>(PositionsParams);
        generateMethod = new JComboBox<>(Methods);
        positionParams = new JComboBox<>(PositionsParams);
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
        northPanel.setLayout(new GridLayout(7, 4,50,10));
        label1 = new JLabel("Generating Method:",SwingConstants.CENTER);
        label1.setFont(font);
        northPanel.add(label1);
        generateMethod.addActionListener(this);
        northPanel.add(generateMethod);


        label12 = new JLabel("Positions Params (Mandatory):",SwingConstants.CENTER);
        label12.setFont(font);
        northPanel.add(label12);
        positionParams.addActionListener(this);
        northPanel.add(positionParams);

        label2 = new JLabel("Domain (Mandatory):",SwingConstants.CENTER);
        label2.setFont(font);
        northPanel.add(label2);
        northPanel.add(domain);

        label3 = new JLabel("User ID (Mandatory):",SwingConstants.CENTER);
        label3.setFont(font);
        northPanel.add(label3);
        northPanel.add(userID);

        label4 = new JLabel("Brand (Mandatory):",SwingConstants.CENTER);
        label4.setFont(font);
        northPanel.add(label4);
        northPanel.add(brand);

        label13 = new JLabel("PL (Mandatory):",SwingConstants.CENTER);
        label13.setFont(font);
        northPanel.add(label13);
        northPanel.add(pl);

        label5 = new JLabel("Position ID (Mandatory):",SwingConstants.CENTER);
        label5.setFont(font);
        northPanel.add(label5);
        northPanel.add(positionID);

        label6 = new JLabel("Position Sum (Mandatory):",SwingConstants.CENTER);
        label6.setFont(font);
        northPanel.add(label6);
        northPanel.add(positionSum);

        label7 = new JLabel("Lot Amount (Optional):",SwingConstants.CENTER);
        label7.setFont(font);
        northPanel.add(label7);
        northPanel.add(lotAmount);

        label9 = new JLabel("Spread (Optional):",SwingConstants.CENTER);
        label9.setFont(font);
        northPanel.add(label9);
        northPanel.add(spread);

        label11 = new JLabel("Symbol (Optional):",SwingConstants.CENTER);
        label11.setFont(font);
        northPanel.add(label11);
        northPanel.add(symbol);

        label10 = new JLabel("Pip Amount (Optional)",SwingConstants.CENTER);
        label10.setFont(font);
        northPanel.add(label10);
        northPanel.add(pip);

        label8 = new JLabel("Links Amount (Mandatory):",SwingConstants.CENTER);
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

        setMinimumSize(new Dimension(1000, 700));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Generate Positions Command Links");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                setFrameCounter(0);
            }
        });
        setVisible(true);
        setWhichParams();
        frameCounter++;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateMethod){
            setIsManually();
        }
        if (e.getSource() == positionParams){
            setWhichParams();
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
        positionID.setText("");
        positionSum.setText("");
        lotAmount.setText("");
        spread.setText("");
        symbol.setText("");
        pip.setText("");
        linksAmount.setText("");
    }
    private void generateCommandLinks(){
        if (validateMandatoryInput()) {
            if (generateMethod.getSelectedItem().equals("Generate positions ID manually")) generateManuallyCommandLinks();
            else generateAutomaticallyCommandLinks();
        }
        else {
            JOptionPane.showMessageDialog(this,"Please fill all mandatory fields properly!");
        }
    }


    public static int getFrameCounter() {
        return frameCounter;
    }
    private void generateManuallyCommandLinks(){
        StringBuilder builder = new StringBuilder();
        switch (positionParams.getSelectedItem().toString())
        {
            case "No params":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(baseURLformat, domain.getText(),userID.getText(),brand.getText(),pl.getText(),positionID.getText(),positionSum.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
            break;
            case "LOT Amount":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(LotURL, domain.getText(),userID.getText(), brand.getText(),pl.getText(),positionID.getText(),positionSum.getText(), lotAmount.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
            break;
            case "Spread":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(spreadURL, domain.getText(),userID.getText(),brand.getText(),pl.getText(),positionID.getText(),positionSum.getText(),spread.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
            break;
            case "Symbol":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(symbolURL, domain.getText(),userID.getText(),brand.getText(),pl.getText(),positionID.getText(),positionSum.getText(),symbol.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
            break;
            case "LOT And Symbol":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(LotSymbolURL, domain.getText(),userID.getText(),brand.getText(),pl.getText(),positionID.getText(),positionSum.getText(),lotAmount.getText(),symbol.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
            break;
            case "Pip,LOT And Symbol":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(PipLotSymbolURL, domain.getText(),userID.getText(),brand.getText(),pl.getText(),positionID.getText(),positionSum.getText(),lotAmount.getText(),pip.getText(),symbol.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
            break;
            case "Spread And Symbol":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(SpreadSymbolURL, domain.getText(),userID.getText(),brand.getText(),pl.getText(),positionID.getText(),positionSum.getText(),spread.getText(),symbol.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
            break;

        }
    }
    private void generateAutomaticallyCommandLinks()
    {
        StringBuilder builder = new StringBuilder();
        switch (positionParams.getSelectedItem().toString())
        {
            case "No params":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(baseURLformat, domain.getText(),userID.getText(),brand.getText(),pl.getText(),generateRandomID(),positionSum.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
                break;
            case "LOT Amount":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(LotURL, domain.getText(),userID.getText(), brand.getText(),pl.getText(),generateRandomID(),positionSum.getText(), lotAmount.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
                break;
            case "Spread":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(spreadURL, domain.getText(),userID.getText(),brand.getText(),pl.getText(),generateRandomID(),positionSum.getText(),spread.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
                break;
            case "Symbol":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(symbolURL, domain.getText(),userID.getText(),brand.getText(),pl.getText(),generateRandomID(),positionSum.getText(),symbol.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
                break;
            case "LOT And Symbol":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(LotSymbolURL, domain.getText(),userID.getText(),brand.getText(),pl.getText(),generateRandomID(),positionSum.getText(),lotAmount.getText(),symbol.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
                break;
            case "Pip,LOT And Symbol":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(PipLotSymbolURL, domain.getText(),userID.getText(),brand.getText(),pl.getText(),generateRandomID(),positionSum.getText(),lotAmount.getText(),pip.getText(),symbol.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
                break;
            case "Spread And Symbol":
                for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                    builder.append(String.format(SpreadSymbolURL, domain.getText(),userID.getText(),brand.getText(),pl.getText(),generateRandomID(),positionSum.getText(),spread.getText(),symbol.getText())).append('\n');
                }
                outputArea.setText(builder.toString());
                break;

        }

    }
    
    //"No params","LOT Amount","Spread","Symbol","LOT And Symbol","Pip,LOT And Symbol","Spread And Symbol"
    private void setIsManually() {
        if (generateMethod.getSelectedItem().equals("Generate positions ID automatically")){
            positionID.setEnabled(false);
            positionID.setBackground(new Color(230,231,232));
        }
        else {
            positionID.setEnabled(true);
            positionID.setBackground(new Color(255, 255, 255));
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
    private boolean validateMandatoryInput(){
        switch (generateMethod.getSelectedItem().toString())
        {
            case "Generate position ID manually":
                if(domain.getText().isEmpty() || userID.getText().isEmpty() || brand.getText().isEmpty() || pl.getText().isEmpty() || positionID.getText().isEmpty() || positionSum.getText().isEmpty() || linksAmount.getText().isEmpty()) {
                    return false;
                }
                break;
            case "Generate position ID automatically":
                if(domain.getText().isEmpty() || userID.getText().isEmpty() || brand.getText().isEmpty() || pl.getText().isEmpty() || positionSum.getText().isEmpty() || linksAmount.getText().isEmpty()){
                    return false;
                }
                break;
        }
        return validateLinksAmount();
    }
    private void setWhichParams()
    {
        switch (positionParams.getSelectedItem().toString())
        {
            case "No params":
                lotAmount.setEnabled(false);
                lotAmount.setBackground(new Color(230,231,232));
                spread.setEnabled(false);
                spread.setBackground(new Color(230,231,232));
                symbol.setEnabled(false);
                symbol.setBackground(new Color(230,231,232));
                pip.setEnabled(false);
                pip.setBackground(new Color(230,231,232));
                break;
            case "LOT Amount":
                spread.setEnabled(false);
                spread.setBackground(new Color(230,231,232));
                symbol.setEnabled(false);
                symbol.setBackground(new Color(230,231,232));
                pip.setEnabled(false);
                pip.setBackground(new Color(230,231,232));
                lotAmount.setEnabled(true);
                lotAmount.setBackground(new Color(255, 255, 255));
                break;
            case "Spread":
                lotAmount.setEnabled(false);
                lotAmount.setBackground(new Color(230,231,232));
                symbol.setEnabled(false);
                symbol.setBackground(new Color(230,231,232));
                pip.setEnabled(false);
                pip.setBackground(new Color(230,231,232));
                spread.setEnabled(true);
                spread.setBackground(new Color(255, 255, 255));
                break;
            case "Symbol":
                lotAmount.setEnabled(false);
                lotAmount.setBackground(new Color(230,231,232));
                spread.setEnabled(false);
                spread.setBackground(new Color(230,231,232));
                pip.setEnabled(false);
                pip.setBackground(new Color(230,231,232));
                symbol.setEnabled(true);
                symbol.setBackground(new Color(255, 255, 255));
                break;
            case "LOT And Symbol":
                spread.setEnabled(false);
                spread.setBackground(new Color(230,231,232));
                pip.setEnabled(false);
                pip.setBackground(new Color(230,231,232));
                lotAmount.setEnabled(true);
                symbol.setEnabled(true);
                lotAmount.setBackground(new Color(255, 255, 255));
                symbol.setBackground(new Color(255, 255, 255));
                break;
            case "Pip,LOT And Symbol":
                spread.setEnabled(false);
                spread.setBackground(new Color(230,231,232));
                pip.setEnabled(true);
                pip.setBackground(new Color(255, 255, 255));
                lotAmount.setEnabled(true);
                lotAmount.setBackground(new Color(255, 255, 255));
                symbol.setEnabled(true);
                symbol.setBackground(new Color(255, 255, 255));
                break;
            case "Spread And Symbol":
                lotAmount.setEnabled(false);
                lotAmount.setBackground(new Color(230,231,232));
                pip.setEnabled(false);
                pip.setBackground(new Color(230,231,232));
                spread.setEnabled(true);
                spread.setBackground(new Color(255, 255, 255));
                symbol.setEnabled(true);
                symbol.setBackground(new Color(255, 255, 255));
                break;
        }
    }
    private static void setFrameCounter(int frameCounter) {
        GeneratePositionsCommandLinksFrame.frameCounter = frameCounter;
    }
}
