package siteSurvey;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Survey extends JPanel {
    
    private JTextField towerTested;
    private JComboBox techUsed;
    private JTextField snrTower;
    private JTextField snrClient;
    private JTextField rssiTower;
    private JTextField rssiClient;
    private JTextField testDown;
    private JTextField testUp;
    
    private SurveyListener surveyListener;
    
    public Survey() {
        towerTested = new JTextField(10);
        techUsed = new JComboBox();
        snrTower = new JTextField(10);
        snrClient = new JTextField(10);
        rssiTower = new JTextField(10);
        rssiClient = new JTextField(10);
        testDown = new JTextField(10);
        testUp = new JTextField(10);
        
        setTechs();
        setFields();
        
        Border innerBorder = BorderFactory.createTitledBorder("Survey");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5 );
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    
    private void setFields() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        
        gc.weightx = 1;
        gc.weighty = 1;
        
        // first row //
        gc.gridx = 0;
        gc.gridy = 0;
        gc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Tower Tested: "), gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(towerTested, gc);
        
        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Technology Used: "), gc);
        gc.gridx = 3;
        gc.anchor = GridBagConstraints.LINE_START;
        add(techUsed, gc);
        
        // next row //
        gc.gridx = 0;
        gc.gridy++;
        gc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("SNR @ Tower: "), gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(snrTower, gc);
        
        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("SNR @ Client: "), gc);
        gc.gridx = 3;
        gc.anchor = GridBagConstraints.LINE_START;
        add(snrClient, gc);
        
        // next row //
        gc.gridx = 0;
        gc.gridy++;
        gc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("RSSI @ Tower: "), gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(rssiTower, gc);
        
        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("RSSI @ Client: "), gc);
        gc.gridx = 3;
        gc.anchor = GridBagConstraints.LINE_START;
        add(rssiClient, gc);
        
        // next row //
        gc.gridx = 0;
        gc.gridy++;
        gc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Average Download (5 Tests): "), gc);
        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        add(testDown, gc);
        
        gc.gridx = 2;
        gc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Average Upload (5 Tests): "), gc);
        gc.gridx= 3;
        gc.anchor = GridBagConstraints.LINE_START;
        add(testUp, gc);
    }
    private void setTechs() {
        DefaultComboBoxModel techs = new DefaultComboBoxModel();
        techs.addElement("Ubiquiti");
        techs.addElement("Telrad");
        techs.addElement("Mimosa");
        techs.addElement("Cambium");
        techUsed.setModel(techs);
    }
    public void getSurvey() {
        String tower = towerTested.getText();
        String tech = (String) techUsed.getSelectedItem();
        String snrT = snrTower.getText();
        String snrC = snrClient.getText();
        String rssiT = rssiTower.getText();
        String rssiC = rssiClient.getText();
        String down = testDown.getText();
        String up = testUp.getText();
        
        FormEvent ev = new FormEvent(this, tower, tech, snrT, snrC, rssiT, rssiC, down, up);
        if(surveyListener != null) {
            surveyListener.surveyEventOccured(ev);
        }
    }
    public void setSurveyListener(SurveyListener listener) {
        this.surveyListener = listener;
    }
}
