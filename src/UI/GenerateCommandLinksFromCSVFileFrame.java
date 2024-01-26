package UI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;

public class GenerateCommandLinksFromCSVFileFrame extends JFrame implements ActionListener {
    private static int frameCounter = 0;
    private final String[] Actions = {"Add tag to affiliates","Marks users as fraud","Add manual adjustment to affiliates"};
    private final String addTagURL = "https://%s/api/admin/?api_username=%s&api_password=%s&command=UpdateAffiliateDetails&affiliateid=%s&Tags=%s";
    private final String markAsFraudURL = "https://%s/admin/?affId=%s&command=markFraudulentUser&userid=%s";
    private final String addManualAdjURL = "https://%s/admin/?command=addcommission&bta=%s&pDate=%s&pAmount=%s&pCommissionType=%s&pUserId=0";
    private final JComboBox actions = new JComboBox(Actions);
    private final JTextField URI = new JTextField();
    private final JTextField username = new JTextField();
    private final JTextField password = new JTextField();
    private final JTextField domain = new JTextField();
    private final ImageIcon uploadIcon = new ImageIcon("src/assets/upload.png");
    private final JButton uploadFile = new JButton(uploadIcon);
    private final JButton generate = new JButton("Generate");
    private final JButton clearAll = new JButton("Clear All");
    private final JTextArea outPutArea = new JTextArea();
    private final JPanel northPanel = new JPanel();
    private final JPanel southPanel = new JPanel();
    private final JPanel box = new JPanel();
    private final JLabel result = new JLabel("Result:",SwingConstants.CENTER);
    private final JLabel action = new JLabel("Choose Action:",SwingConstants.CENTER);
    private final JLabel Username = new JLabel("Admin User Name:",SwingConstants.CENTER);
    private final JLabel Password = new JLabel("Admin Password:",SwingConstants.CENTER);
    private final JLabel Domain = new JLabel("Domain:",SwingConstants.CENTER);
    private final JLabel filePath = new JLabel("File Path:",SwingConstants.CENTER);
    private final JScrollPane ScrollPane = new JScrollPane(outPutArea);
    private File file;

    private Font font = new Font(Font.SANS_SERIF,Font.BOLD,13);
    public GenerateCommandLinksFromCSVFileFrame(){
        setLayout(new BorderLayout());
        northPanel.setLayout(new GridLayout(6, 2,20,0));
        action.setFont(font);
        northPanel.add(action);
        northPanel.add(actions);

        Domain.setFont(font);
        northPanel.add(Domain);
        northPanel.add(domain);

        Username.setFont(font);
        northPanel.add(Username);
        northPanel.add(username);

        Password.setFont(font);
        northPanel.add(Password);
        northPanel.add(password);

        filePath.setFont(font);
        northPanel.add(filePath);
        box.setLayout(new BoxLayout(box,BoxLayout.LINE_AXIS));
        box.add(URI);
        box.add(uploadFile);
        northPanel.add(box);

        generate.setFont(font);
        generate.addActionListener(this);
        clearAll.setFont(font);
        clearAll.addActionListener(this);
        northPanel.add(generate);
        northPanel.add(clearAll);

        ScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        ScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        result.setFont(new Font("Arial",Font.BOLD,25));
        southPanel.setLayout(new BorderLayout());
        southPanel.add(result,BorderLayout.NORTH);
        southPanel.add(ScrollPane,BorderLayout.CENTER);
        actions.addActionListener(this);
        uploadFile.addActionListener(this);
        add(northPanel,BorderLayout.NORTH);
        add(southPanel);
        setMinimumSize(new Dimension(1000, 600));
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setLocationRelativeTo(null);
        setTitle("Generate Command Links From .XLSX File");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                resetFrameCounter();
            }
        });
        setVisible(true);
        frameCounter++;
    }

    public static int getFrameCounter() {
        return frameCounter;
    }

    private static void resetFrameCounter() {
        GenerateCommandLinksFromCSVFileFrame.frameCounter = 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == actions){
            setWhichParams();
        }
        if (e.getSource() == generate)
        {
            if (validateMandatoryInput()) {
                switch (actions.getSelectedItem().toString())
                {
                    case "Add tag to affiliates":
                        try {
                            generateTags();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Marks users as fraud":
                        try {
                            markAsFraud();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                    case "Add manual adjustment to affiliates":
                        try {
                            generateManualCommissions();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        break;
                }
            }
            else JOptionPane.showMessageDialog(null,"Please fill all mandatory fields !");
        }
        if (e.getSource() == clearAll){
            clearAll();
        }
        if (e.getSource() == uploadFile){
            showFileChooser();
        }
    }
    private void showFileChooser(){
        FileDialog fileDialog = new FileDialog(this,"Please choose file",FileDialog.LOAD);
        fileDialog.setVisible(true);
        if (fileDialog.getFile() == null){
            JOptionPane.showMessageDialog(null,"No file chosen !");
        }
        else {
            file = new File(fileDialog.getDirectory(),fileDialog.getFile());
            URI.setText(file.getAbsolutePath());
        }
    }
    private void clearAll(){
        outPutArea.setText("");
        domain.setText("");
        username.setText("");
        password.setText("");
        URI.setText("");
    }
    private void setWhichParams()
    {
        if (actions.getSelectedItem().equals("Add tag to affiliates")) {
            username.setEnabled(true);
            username.setBackground(new Color(255, 255, 255));
            password.setEnabled(true);
            password.setBackground(new Color(255, 255, 255));
        }
        else {
            username.setEnabled(false);
            username.setBackground(new Color(230,231,232));
            password.setEnabled(false);
            password.setBackground(new Color(230,231,232));
        }
    }
    private boolean validateMandatoryInput(){
        switch (actions.getSelectedItem().toString())
        {
            case "Add tag to affiliates":
                if(domain.getText().isEmpty() || username.getText().isEmpty() || password.getText().isEmpty() || URI.getText().isEmpty())
                    return false;
                break;
            default:
                if (domain.getText().isEmpty() || URI.getText().isEmpty())
                    return false;
                break;
        }
        return true;
    }
    private void generateTags() throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String affID = values[0];
                String tag = values[1];
                builder.append(String.format(addTagURL,domain.getText(),username.getText(),password.getText(),affID,tag)).append('\n');
            }
            outPutArea.setText(builder.toString());
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"Error while reading the file !");
        }

    }
    private void markAsFraud() throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String affID = values[0];
                String userID = values[1];
                builder.append(String.format(markAsFraudURL,domain.getText(),affID,userID)).append('\n');
            }
            outPutArea.setText(builder.toString());
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"Error while reading the file !");
        }
    }
    private void generateManualCommissions() throws IOException {
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            br.readLine();
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                String affID = values[0];
                String date = values[1];
                String amount = values[2];
                String type = values[3];
                builder.append(String.format(addManualAdjURL,domain.getText(),affID,date,amount,type)).append('\n');
            }
            outPutArea.setText(builder.toString());
        }catch (IOException e){
            JOptionPane.showMessageDialog(null,"Error while reading the file !");
        }

    }
}
