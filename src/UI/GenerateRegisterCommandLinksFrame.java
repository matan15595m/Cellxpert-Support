package UI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class GenerateRegisterCommandLinksFrame extends JFrame implements ActionListener {
    private final String[] Countries = {"AF", "AL", "DZ", "AD", "AO", "AG", "AR", "AM", "AU", "AT", "AZ", "BS", "BH", "BD", "BB", "BY", "BE", "BZ", "BJ", "BT", "BO", "BA", "BW", "BR", "BN", "BG", "BF", "BI", "CV", "KH", "CM", "CA", "CF", "TD", "CL", "CN", "CO", "KM", "CG", "CR", "HR", "CU", "CY", "CZ", "DK", "DJ", "DM", "DO", "EC", "EG", "SV", "GQ", "ER", "EE", "SZ", "ET", "FJ", "FI", "FR", "GA", "GM", "GE", "DE", "GH", "GR", "GD", "GT", "GN", "GW", "GY", "HT", "HN", "HU", "IS", "IN", "ID", "IR", "IQ", "IE", "IL", "IT", "CI", "JM", "JP", "JO", "KZ", "KE", "KI", "KP", "KR", "XK", "KW", "KG", "LA", "LV", "LB", "LS", "LR", "LY", "LI", "LT", "LU", "MG", "MW", "MY", "MV", "ML", "MT", "MH", "MR", "MU", "MX", "FM", "MD", "MC", "MN", "ME", "MA", "MZ", "MM", "NA", "NR", "NP", "NL", "NZ", "NI", "NE", "NG", "MK", "NO", "OM", "PK", "PW", "PA", "PG", "PY", "PE", "PH", "PL", "PT", "QA", "RO", "RU", "RW", "KN", "LC", "VC", "WS", "SM", "ST", "SA", "SN", "RS", "SC", "SL", "SG", "SK", "SI", "SB", "SO", "ZA", "SS", "ES", "LK", "SD", "SR", "SE", "CH", "SY", "TW", "TJ", "TZ", "TH", "TL", "TG", "TO", "TT", "TN", "TR", "TM", "TV", "UG", "UA", "AE", "GB", "US", "UY", "UZ", "VU", "VA", "VE", "VN", "YE", "ZM", "ZW"};
    private final String[] Methods = {"Generate user and lead ID manually","Generate user and lead ID automatically"};
    private JTextField domain,userID,brand,affiliateID,status,linksAmount,utmCampaign,leadID;
    private JComboBox<String> countries,generateMethod;
    private JButton generate,clear;
    private JTextArea outputArea;
    private JScrollPane ScrollPane;
    private JPanel northPanel,southPanel;
    private JLabel result,logo;
    private boolean isManually = true;
    private static int frameCounter = 0;
    private ArrayList<Long> Idlist = new ArrayList<>();
    private final String baseURLformat = "https://%s/user/?userid=%s&leadid=%s&brand=%s&isocountry=%s&bta=%s&Status=%s";
    private final String baseURLwithUTM = "https://%s/user/?userid=%s&leadid=%s&brand=%s&bta=%s&utm_campaign=%s&isocountry=%s";
    public GenerateRegisterCommandLinksFrame(){
        domain = new JTextField();
        domain.setToolTipText("Domain");

        userID = new JTextField();
        userID.setToolTipText("User ID");
        leadID = new JTextField();
        leadID.setToolTipText("Lead ID");
        brand = new JTextField();
        brand.setToolTipText("Brand");
        affiliateID = new JTextField();
        affiliateID.setToolTipText("Affiliate ID");
        status = new JTextField();
        status.setToolTipText("Status");
        linksAmount = new JTextField();
        linksAmount.setToolTipText("Command links amount");
        utmCampaign = new JTextField();
        utmCampaign.setToolTipText("Utm_Campaign");


        countries = new JComboBox<>(Countries);
        generateMethod = new JComboBox<>(Methods);

        generate = new JButton("Generate");
        generate.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        generate.addActionListener(this);
        clear = new JButton("Clear All");
        clear.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        clear.addActionListener(this);

        outputArea = new JTextArea();
        ScrollPane = new JScrollPane(outputArea);
        ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        result = new JLabel("Result:",SwingConstants.CENTER);
        result.setFont(new Font("Arial",Font.BOLD,25));

        setLayout(new GridLayout(2,1));

        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(11, 2, 100, 0));

        JLabel label1 = new JLabel("Generating Method:",SwingConstants.CENTER);
        label1.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        northPanel.add(label1);
        generateMethod.addActionListener(this);
        northPanel.add(generateMethod);
        JLabel label2 = new JLabel(" Domain (Mandatory):",SwingConstants.CENTER);
        label2.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        northPanel.add(label2);
        northPanel.add(domain);
        JLabel label3 = new JLabel("User ID (Mandatory):",SwingConstants.CENTER);
        label3.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        northPanel.add(label3);
        northPanel.add(userID);
        JLabel label4 = new JLabel("Lead ID (Mandatory):",SwingConstants.CENTER);
        label4.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        northPanel.add(label4);
        northPanel.add(leadID);
        JLabel label5 = new JLabel("Brand (Mandatory):",SwingConstants.CENTER);
        label5.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        northPanel.add(label5);
        northPanel.add(brand);


        JLabel label6 = new JLabel("Affiliate ID (Mandatory):",SwingConstants.CENTER);
        label6.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        northPanel.add(label6);
        northPanel.add(affiliateID);

        JLabel label7 = new JLabel("Status (Mandatory):",SwingConstants.CENTER);
        label7.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        northPanel.add(label7);
        northPanel.add(status);

        JLabel label8 = new JLabel("UTM Campaign (Optional):",SwingConstants.CENTER);
        label8.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        northPanel.add(label8);
        northPanel.add(utmCampaign);

        JLabel label9 = new JLabel("Country:",SwingConstants.CENTER);
        label9.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        northPanel.add(label9);
        northPanel.add(countries);

        JLabel label10 = new JLabel("Links Amount (Mandatory):",SwingConstants.CENTER);
        label10.setFont(new Font(Font.SANS_SERIF,Font.BOLD,13));
        northPanel.add(label10);
        northPanel.add(linksAmount);


        northPanel.add(generate);
        northPanel.add(clear);


        southPanel = new JPanel();
        southPanel.setLayout(new BorderLayout());
        southPanel.add(result,BorderLayout.NORTH);
        southPanel.add(ScrollPane,BorderLayout.CENTER);

        add(northPanel);
        add(southPanel);

        setMinimumSize(new Dimension(1000, 600));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Generate Registrations Command Links");
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
        leadID.setText("");
        brand.setText("");
        affiliateID.setText("");
        status.setText("");
        utmCampaign.setText("");
    }
    private void generateCommandLinks(){
        if (validateInput())
        {
            switch (generateMethod.getSelectedItem().toString())
            {
                case "Generate user and lead ID manually":
                    if (!utmCampaign.getText().isEmpty())
                    {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                            builder.append(String.format(baseURLwithUTM, domain.getText(), userID.getText(), leadID.getText(), brand.getText(), affiliateID.getText(), utmCampaign.getText(), countries.getSelectedItem())).append('\n');
                        }
                        outputArea.setText(builder.toString());
                    } else
                    {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                            builder.append(String.format(baseURLformat, domain.getText(), userID.getText(), leadID.getText(), brand.getText(), countries.getSelectedItem(), affiliateID.getText(), status.getText())).append('\n');
                        }
                        outputArea.setText(builder.toString());
                    }
                    break;
                case "Generate user and lead ID automatically":
                    if (!utmCampaign.getText().isEmpty())
                    {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++)
                        {
                            builder.append(String.format(baseURLwithUTM, domain.getText(), generateRandomID(), generateRandomID(), brand.getText(), affiliateID.getText(), utmCampaign.getText(), countries.getSelectedItem())).append('\n');
                        }
                        outputArea.setText(builder.toString());
                        Idlist.clear();
                    } else {
                        StringBuilder builder = new StringBuilder();
                        for (int i = 0; i < Integer.parseInt(linksAmount.getText()); i++) {
                            builder.append(String.format(baseURLformat, domain.getText(), generateRandomID(), generateRandomID(), brand.getText(), countries.getSelectedItem(), affiliateID.getText(), status.getText())).append('\n');
                        }
                        outputArea.setText(builder.toString());
                        Idlist.clear();
                    }
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
        if (generateMethod.getSelectedItem().equals("Generate user and lead ID automatically")){
            userID.setEnabled(false);
            userID.setBackground(new Color(230,231,232));
            leadID.setEnabled(false);
            leadID.setBackground(new Color(230,231,232));
        }
        else {
            userID.setEnabled(true);
            userID.setBackground(new Color(255, 255, 255));
            leadID.setEnabled(true);
            leadID.setBackground(new Color(255, 255, 255));
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
                if(domain.getText().isEmpty() || userID.getText().isEmpty() || leadID.getText().isEmpty() || brand.getText().isEmpty() || affiliateID.getText().isEmpty() || status.getText().isEmpty() || linksAmount.getText().isEmpty()) {
                    return false;
                }
                break;
            case "Generate user and lead ID automatically":
                if(domain.getText().isEmpty() || brand.getText().isEmpty() || affiliateID.getText().isEmpty() || status.getText().isEmpty() || linksAmount.getText().isEmpty()){
                    return false;
                }
                break;
        }
        return validateLinksAmount();
    }

    public static void setFrameCounter(int frameCounter) {
        GenerateRegisterCommandLinksFrame.frameCounter = frameCounter;
    }
}
