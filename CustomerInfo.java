package siteSurvey;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class CustomerInfo extends JPanel {
    
    private JTextField custName;
    private JTextField custAddr;
    private JComboBox day;
    private JComboBox month;
    private JComboBox year;
    
    private CustomerInfoListener customerInfoListener;
    
    public CustomerInfo() {
        custName = new JTextField(15);
        custAddr = new JTextField(20);
        day = new JComboBox();
        month = new JComboBox();
        year = new JComboBox();
        
        setDate();
        setFields();
        
        Border innerBorder = BorderFactory.createTitledBorder("Customer Info");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5 );
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
    }
    
    private void setDate() {
        DefaultComboBoxModel dd = new DefaultComboBoxModel();
        for(int i = 0; i <= 30; i++) {
            String d = Integer.toString(i+1);
            dd.addElement(d);
        }
        day.setModel(dd);
        day.setPrototypeDisplayValue("dd");
        DefaultComboBoxModel mm = new DefaultComboBoxModel();
        for(int i = 0; i < 12; i++) {
            String m = Integer.toString(i+1);
            mm.addElement(m);
        }
        month.setModel(mm);
        month.setPrototypeDisplayValue("mm");
        DefaultComboBoxModel yyyy = new DefaultComboBoxModel();
        for(int i = 0; i < 80; i++) {
            int yr = 2020 + i;
            String yy = Integer.toString(yr);
            yyyy.addElement(yy);
        }
        year.setModel(yyyy);
        year.setPrototypeDisplayValue("yyyy");
    }
    private void setFields() {
        
        setLayout(null);
        JLabel cxName = new JLabel("Customer Name: ");
        JLabel custAdr = new JLabel("Customer Address: ");
        JLabel date = new JLabel("Date (dd/mm/yyyy): ");
        
        cxName.setBounds(20, 30, 130, 20);
        add(cxName);
        custName.setBounds(145, 30, 150, 20);
        add(custName);
        custAdr.setBounds(320, 30, 150, 20);
        add(custAdr);
        custAddr.setBounds(460, 30, 200, 20);
        add(custAddr);
        date.setBounds(120, 60, 150, 20);
        add(date);
        day.setBounds(260, 60, 50, 20);
        add(day);
        month.setBounds(320, 60, 50, 20);
        add(month);
        year.setBounds(380, 60, 70, 20);
        add(year);
       
//        setLayout(new GridBagLayout());
//        GridBagConstraints gc = new GridBagConstraints();
//        gc.weightx = 1;
//        gc.weighty = 1;
//        
//        // first row //
//        gc.gridx = 0;
//        gc.gridy = 0;
//        gc.anchor = GridBagConstraints.LINE_END;
//        add(new JLabel("Customer Name: "), gc);
//        gc.gridx = 1;
//        gc.anchor = GridBagConstraints.LINE_START;
//        add(custName, gc);
//        
//        gc.gridx = 2;
//        gc.anchor = GridBagConstraints.LINE_END;
//        add(new JLabel("Customer Address: "), gc);
//        gc.gridx = 3;
//        gc.anchor = GridBagConstraints.LINE_START;
//        add(custAddr, gc);
//        
//        // next row //
//        gc.gridx = 0;
//        gc.gridy++;
//        gc.anchor = GridBagConstraints.LINE_END;
//        add(new JLabel("Date of Survey: "), gc);
//        gc.gridx = 1;
//        gc.anchor = GridBagConstraints.LINE_START;
//        add(day, gc);
//        gc.gridx = 2;
//        add(month, gc);
//        gc.gridx = 3;
//        gc.weightx = 3;
//        add(year, gc);
        
       
    }
    public void getCustomerInfo() {
        String name = custName.getText();
        String addr = custAddr.getText();
        String date = (String)day.getSelectedItem() + "/" 
                    + (String)month.getSelectedItem() + "/" 
                    + (String)year.getSelectedItem();
        FormEvent ev = new FormEvent(this, name, addr, date);
        if(customerInfoListener != null) {
            customerInfoListener.custEventOccured(ev);
        }
    }
    public void setCustListener(CustomerInfoListener listener) {
        this.customerInfoListener = listener;
    }
}
