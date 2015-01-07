import javax.swing.*;

import org.json.JSONException;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PostmanCollectionGenerator implements ActionListener {

	
	JTextField collectionNameField;
	JRadioButton dev, test, staging, demo, qa, qa2, prod, dis_lookup_dev, localhost;
	JButton chooseFile;
	ButtonGroup envs;
	GroupButtonUtils buttonUtils;
	JTextField portField;
	JCheckBox minOutput;
	
	String inputFilePath, collectionName, backendAPI;
	boolean minifyOutput;

    public static void main (String [] args) {
    	PostmanCollectionGenerator gui = new PostmanCollectionGenerator();
        gui.go();
    }

    public void go() {
    	
    	buttonUtils = new GroupButtonUtils();
    	JFrame frame = new JFrame("Postman Collection Generator");
    	
    	
    	JPanel collectionNamePanel = new JPanel(new GridBagLayout());
    	
    	JPanel topPanel = new JPanel();
    	JPanel middlePanel = new JPanel();
    	JPanel bottomPanel = new JPanel();
    	
    	JPanel groupButtonPanel = new JPanel();
    	JLabel groupButtonLabel = new JLabel("Please choose your backend enviroment.");    	
    	
    	localhost = new JRadioButton("localhost");
    	

    	groupButtonPanel.add(localhost);
    	JLabel portLabel = new JLabel(":");
    	portField = new JTextField("3000");
    	portField.setPreferredSize(new Dimension(100, 22));
    	portField.setEditable(false);
    	groupButtonPanel.add(portLabel);
    	groupButtonPanel.add(portField);
    	
    	localhost.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (localhost.isSelected()) {
					portField.setEditable(true);
				}
			}
		});
    	
    	
    	
    	chooseFile = new JButton("Choose HAR file.");
    	topPanel.add(BorderLayout.CENTER, chooseFile);
    	chooseFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
				    inputFilePath = selectedFile.getAbsolutePath();
				}
			}
		});
    	
    	minOutput = new JCheckBox("Minify output file.");
    	bottomPanel.add(BorderLayout.WEST, minOutput);
    	
    	JLabel label = new JLabel("Please enter the collection name. This will be the name of your Postman collection.");
    	collectionNameField = new JTextField("i.e. My Project");
    	collectionNameField.setPreferredSize(new Dimension(150, 22));
    	
    	JButton start = new JButton("Start!");
    	bottomPanel.add(BorderLayout.EAST, start);
    	
    	
    	
    	start.addActionListener(this);
    	
    	GridBagConstraints gbc = new GridBagConstraints();
    	gbc.insets = new Insets(3, 3, 3, 3);
    	gbc.gridx = 0;
    	gbc.gridy = 0;
    	collectionNamePanel.add(label, gbc);
    	
    	gbc.gridx = 0;
    	gbc.gridy = 1;    	
    	collectionNamePanel.add(collectionNameField, gbc);
    	
    	
    	middlePanel.add(BorderLayout.NORTH, collectionNamePanel);
    	middlePanel.add(BorderLayout.CENTER, groupButtonLabel);
    	middlePanel.add(BorderLayout.SOUTH, groupButtonPanel);
    	
    	frame.getContentPane().add(BorderLayout.NORTH, topPanel);
    	frame.getContentPane().add(BorderLayout.CENTER, middlePanel);
    	frame.getContentPane().add(BorderLayout.SOUTH, bottomPanel);
    	frame.setSize(750,220);
    	frame.setVisible(true);
    	frame.setResizable(false);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	

    }

    @Override
    public void actionPerformed(ActionEvent event) {
    	


    	
    	Map<String, String> envsMap = new HashMap<String, String>();
    	envsMap.put("dev", "api.dev.schoolmint.net");
    	envsMap.put("test", "api-staging.schoolmint.net");
    	envsMap.put("staging", "api-staging.schoolmint.net");
    	envsMap.put("demo", "api.demo6.schoolmint.net");
    	envsMap.put("qa", "api.qa.schoolmint.net");
    	envsMap.put("qa2", "api.qa2.schoolmint.net");
    	envsMap.put("prod", "api-prod.schoolmint.net");
    	envsMap.put("dis_lookup_dev", "dev.districtlookup.schoolmint.net");
    	envsMap.put("localhost", "localhost:" + portField.getText());
    	collectionName = collectionNameField.getText();
    	
    	if (inputFilePath == null 
    			|| collectionName == null 
    			|| envsMap.get(buttonUtils.getSelectedButtonText(envs)) == null 
    			|| Integer.parseInt(portField.getText()) < 0 
    			|| Integer.parseInt(portField.getText()) > 65536) {
    		JOptionPane.showMessageDialog(null, "Invalid Input.");
			return;
		}
    	
    	Main m = new Main(inputFilePath, collectionName, envsMap.get(buttonUtils.getSelectedButtonText(envs)), minOutput.isSelected());
    	try {
			m.start();
		} catch (JSONException e) {
			JOptionPane.showMessageDialog(null, "Error When Generating JSON ouput.");
			return;
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Error When Open Input File.");
			return;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Unknown Error Accured.");
			return;
		}
		JOptionPane.showMessageDialog(null, "Done!");
    }
}

