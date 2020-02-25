package siteSurvey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class MainFrame extends JFrame implements SurveyListener, CustomerInfoListener {

    private CustomerInfo customerInfo;
    private Survey survey;
    private Notes note;

    private JButton submit;
    private JButton newSurvey;
    private JButton reset;
    private JLabel taken;

    private String tower;
    private String tech;
    private String snrT;
    private String snrC;
    private String rssiT;
    private String rssiC;
    private String down;
    private String up;
    private String name;
    private String addr;
    private String date;
    private String notes;

    private String[] s0 = {""};
    private String[] s1 = {""};
    private String[] s2 = {""};
    private String[] s3 = {""};

    private int surveys = 0;
    
    private static final PDFont FONT = PDType1Font.TIMES_ROMAN;
    private static final float FONT_SIZE = 10;
    private static final float LEADING = -1.5f * FONT_SIZE;

    public MainFrame() {
        super("Resound Site Survey");
        setLayout(null);
        customerInfo = new CustomerInfo();
        survey = new Survey();
        note = new Notes();
        newSurvey = new JButton("New Survey");
        submit = new JButton("Submit");
        reset = new JButton("Make PDF");
        taken = new JLabel("Surveys Taken: " + (surveys+1) + "/4");
        s0 = new String[8];
        s1 = new String[8];
        s2 = new String[8];
        s3 = new String[8];

        newSurvey.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                remove(survey);
                remove(taken);
                survey = new Survey();
                survey.setBounds(20, 125, 700, 200);
                add(survey);
                surveys++;
                if(surveys == 3) {
                    newSurvey.setEnabled(false);
                }
                taken = new JLabel("Surveys Taken: " + (surveys+1) + "/4");
                taken.setBounds(555, 100, 180, 30);
                add(taken);
                validate();
                repaint();
            }
        });

        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                getSurvey();
                getCustomer();
                setInfo();
                note.setStringListener(new StringListener() {
                    public void textEmitted(String text) {
                        notes = text;
                    }
                });
            }
        });

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                makePdf();
                remove(customerInfo);
                remove(survey);
                remove(note);
                remove(taken);
                customerInfo = new CustomerInfo();
                survey = new Survey();
                note = new Notes();
                tower = "";
                tech = "";
                snrT = "";
                snrC = "";
                rssiT = "";
                rssiC = "";
                down = "";
                up = "";
                name = "";
                addr = "";
                date = "";
                notes = "";
                customerInfo.setBounds(20, 10, 700, 100);
                add(customerInfo);
                survey.setBounds(20, 125, 700, 200);
                add(survey);
                note.setBounds(20, 330, 700, 150);
                add(note);
                surveys = 0;
                taken = new JLabel("Surveys Taken: " + (surveys+1) + "/4");
                taken.setBounds(555, 100, 180, 30);
                add(taken);
                s0 = new String[8];
                s1 = new String[8];
                s2 = new String[8];
                s3 = new String[8];
                newSurvey.setEnabled(true);
                validate();
                repaint();
            }
        });

        customerInfo.setBounds(20, 10, 700, 100);
        add(customerInfo);
        survey.setBounds(20, 125, 700, 200);
        add(survey);
        taken.setBounds(555, 100, 180, 30);
        add(taken);
        note.setBounds(20, 330, 700, 150);
        add(note);
        newSurvey.setBounds(200, 500, 117, 30);
        add(newSurvey);
        submit.setBounds(350, 500, 100, 30);
        add(submit);
        reset.setBounds(480, 500, 120, 30);
        add(reset);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setSize(750, 600);
        setResizable(false);
    }
    private void getSurvey() {
        survey.setSurveyListener(this);
        survey.getSurvey();
    }
    private void getCustomer() {
        customerInfo.setCustListener(this);
        customerInfo.getCustomerInfo();
    }
    public void setInfo() {
        if(surveys == 0) {
            s0[0] = tower;
            s0[1] = tech;
            s0[2] = snrT;
            s0[3] = snrC;
            s0[4] = rssiT;
            s0[5] = rssiC;
            s0[6] = down;
            s0[7] = up;
        } else if(surveys == 1) {
            s1[0] = tower;
            s1[1] = tech;
            s1[2] = snrT;
            s1[3] = snrC;
            s1[4] = rssiT;
            s1[5] = rssiC;
            s1[6] = down;
            s1[7] = up;
        } else if(surveys == 2) {
            if(tower != null)s2[0] = tower;
            if(tech != null)s2[1] = tech;
            if(snrT != null)s2[2] = snrT;
            if(snrC != null)s2[3] = snrC;
            if(rssiT != null)s2[4] = rssiT;
            if(rssiC != null)s2[5] = rssiC;
            if(down != null)s2[6] = down;
            if(up != null)s2[7] = up;
        } else if(surveys == 3) {
            if(tower != null)s3[0] = tower;
            if(tech != null)s3[1] = tech;
            if(snrT != null)s3[2] = snrT;
            if(snrC != null)s3[3] = snrC;
            if(rssiT != null)s3[4] = rssiT;
            if(rssiC != null)s3[5] = rssiC;
            if(down != null)s3[6] = down;
            if(up != null)s3[7] = up;
        }
    }

    public void custEventOccured(FormEvent ev) {
        this.name = ev.getName();
        this.addr = ev.getAddr();
        this.date = ev.getDate();
    }
    public void surveyEventOccured(FormEvent ev) {
        this.tower = ev.getTower();
        this.tech = ev.getTech();
        this.snrT = ev.getSnrT();
        this.snrC = ev.getSnrC();
        this.rssiT = ev.getRssiT();
        this.rssiC = ev.getRssiC();
        this.down = ev.getDown();
        this.up = ev.getUp();
    }
    private void makePdf() {
        PDDocument doc = new PDDocument();
        PDPage page = new PDPage();
        doc.addPage(page);
        String fileName = name.replaceAll("\\s+", "");
        fileName += "_SiteSurvey.pdf";
        PDFont font = PDType1Font.TIMES_ROMAN; // Or whatever font you want.
        int fontSize = 10; // Or whatever font size you want.
        int paragraphWidth = 200;

        try {
            PDPageContentStream cs = new PDPageContentStream(doc, page);
            cs.beginText();
            cs.setFont(PDType1Font.TIMES_BOLD, 16);
            cs.newLineAtOffset(140, 700);
            cs.showText("Resound Networks Site Survey Results Document");
            cs.setFont(PDType1Font.TIMES_ROMAN, 10);
            cs.newLineAtOffset(-120, -50);
            cs.showText("Customer Name: " + name);
            cs.newLineAtOffset(300, 0);
            cs.showText("Customer Address: " + addr);
            cs.newLineAtOffset(-300, 100);
            cs.showText("Date: " + date);
            if(surveys == 0) {
                cs.newLineAtOffset(0, -150);
                cs.setFont(PDType1Font.TIMES_BOLD, 12);
                cs.showText("Survey #1");
                cs.setFont(PDType1Font.TIMES_ROMAN, 10);
                cs.newLineAtOffset(0, -20);
                cs.showText("Tower Tested: " + s0[0]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Technology Used: " + s0[1]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("SNR @ Tower: " + s0[2]);
                cs.newLineAtOffset(200, 0);
                cs.showText("SNR @ Client: " + s0[3]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("RSSI @ Tower: " + s0[4]);
                cs.newLineAtOffset(200, 0);
                cs.showText("RSSI @ Client: " + s0[5]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("Average Download Speed (5 Tests): " + s0[6]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Average Upload Speed (5 Tests): " + s0[7]);
            }else if(surveys == 1) {
                // first survey
                cs.newLineAtOffset(0, -150);
                cs.setFont(PDType1Font.TIMES_BOLD, 12);
                cs.showText("Survey #1");
                cs.setFont(PDType1Font.TIMES_ROMAN, 10);
                cs.newLineAtOffset(0, -20);
                cs.showText("Tower Tested: " + s0[0]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Technology Used: " + s0[1]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("SNR @ Tower: " + s0[2]);
                cs.newLineAtOffset(200, 0);
                cs.showText("SNR @ Client: " + s0[3]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("RSSI @ Tower: " + s0[4]);
                cs.newLineAtOffset(200, 0);
                cs.showText("RSSI @ Client: " + s0[5]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("Average Download Speed (5 Tests): " + s0[6]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Average Upload Speed (5 Tests): " + s0[7]);
                // survey 2
                cs.newLineAtOffset(-200, -30);
                cs.setFont(PDType1Font.TIMES_BOLD, 12);
                cs.showText("Survey #2");
                cs.setFont(PDType1Font.TIMES_ROMAN, 10);
                cs.newLineAtOffset(0, -20);
                cs.showText("Tower Tested: " + s1[0]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Technology Used: " + s1[1]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("SNR @ Tower: " + s1[2]);
                cs.newLineAtOffset(200, 0);
                cs.showText("SNR @ Client: " + s1[3]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("RSSI @ Tower: " + s1[4]);
                cs.newLineAtOffset(200, 0);
                cs.showText("RSSI @ Client: " + s1[5]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("Average Download Speed (5 Tests): " + s1[6]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Average Upload Speed (5 Tests): " + s1[7]);
            } else if(surveys == 2) {
                // first survey
                cs.newLineAtOffset(0, -150);
                cs.setFont(PDType1Font.TIMES_BOLD, 12);
                cs.showText("Survey #1");
                cs.setFont(PDType1Font.TIMES_ROMAN, 10);
                cs.newLineAtOffset(0, -20);
                cs.showText("Tower Tested: " + s0[0]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Technology Used: " + s0[1]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("SNR @ Tower: " + s0[2]);
                cs.newLineAtOffset(200, 0);
                cs.showText("SNR @ Client: " + s0[3]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("RSSI @ Tower: " + s0[4]);
                cs.newLineAtOffset(200, 0);
                cs.showText("RSSI @ Client: " + s0[5]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("Average Download Speed (5 Tests): " + s0[6]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Average Upload Speed (5 Tests): " + s0[7]);
                // survey 2
                cs.newLineAtOffset(-200, -30);
                cs.setFont(PDType1Font.TIMES_BOLD, 12);
                cs.showText("Survey #2");
                cs.setFont(PDType1Font.TIMES_ROMAN, 10);
                cs.newLineAtOffset(0, -20);
                cs.showText("Tower Tested: " + s1[0]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Technology Used: " + s1[1]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("SNR @ Tower: " + s1[2]);
                cs.newLineAtOffset(200, 0);
                cs.showText("SNR @ Client: " + s1[3]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("RSSI @ Tower: " + s1[4]);
                cs.newLineAtOffset(200, 0);
                cs.showText("RSSI @ Client: " + s1[5]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("Average Download Speed (5 Tests): " + s1[6]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Average Upload Speed (5 Tests): " + s1[7]);
                // survey 3
                cs.newLineAtOffset(-200, -30);
                cs.setFont(PDType1Font.TIMES_BOLD, 12);
                cs.showText("Survey #3");
                cs.setFont(PDType1Font.TIMES_ROMAN, 10);
                cs.newLineAtOffset(0, -20);
                cs.showText("Tower Tested: " + s2[0]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Technology Used: " + s2[1]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("SNR @ Tower: " + s2[2]);
                cs.newLineAtOffset(200, 0);
                cs.showText("SNR @ Client: " + s2[3]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("RSSI @ Tower: " + s2[4]);
                cs.newLineAtOffset(200, 0);
                cs.showText("RSSI @ Client: " + s2[5]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("Average Download Speed (5 Tests): " + s2[6]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Average Upload Speed (5 Tests): " + s2[7]);
            } else if(surveys == 3) {
                // first survey
                cs.newLineAtOffset(0, -150);
                cs.setFont(PDType1Font.TIMES_BOLD, 12);
                cs.showText("Survey #1");
                cs.setFont(PDType1Font.TIMES_ROMAN, 10);
                cs.newLineAtOffset(0, -20);
                cs.showText("Tower Tested: " + s0[0]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Technology Used: " + s0[1]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("SNR @ Tower: " + s0[2]);
                cs.newLineAtOffset(200, 0);
                cs.showText("SNR @ Client: " + s0[3]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("RSSI @ Tower: " + s0[4]);
                cs.newLineAtOffset(200, 0);
                cs.showText("RSSI @ Client: " + s0[5]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("Average Download Speed (5 Tests): " + s0[6]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Average Upload Speed (5 Tests): " + s0[7]);
                // survey 2
                cs.newLineAtOffset(-200, -30);
                cs.setFont(PDType1Font.TIMES_BOLD, 12);
                cs.showText("Survey #2");
                cs.setFont(PDType1Font.TIMES_ROMAN, 10);
                cs.newLineAtOffset(0, -20);
                cs.showText("Tower Tested: " + s1[0]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Technology Used: " + s1[1]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("SNR @ Tower: " + s1[2]);
                cs.newLineAtOffset(200, 0);
                cs.showText("SNR @ Client: " + s1[3]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("RSSI @ Tower: " + s1[4]);
                cs.newLineAtOffset(200, 0);
                cs.showText("RSSI @ Client: " + s1[5]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("Average Download Speed (5 Tests): " + s1[6]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Average Upload Speed (5 Tests): " + s1[7]);
                // survey 3
                cs.newLineAtOffset(-200, -30);
                cs.setFont(PDType1Font.TIMES_BOLD, 12);
                cs.showText("Survey #3");
                cs.setFont(PDType1Font.TIMES_ROMAN, 10);
                cs.newLineAtOffset(0, -20);
                cs.showText("Tower Tested: " + s2[0]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Technology Used: " + s2[1]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("SNR @ Tower: " + s2[2]);
                cs.newLineAtOffset(200, 0);
                cs.showText("SNR @ Client: " + s2[3]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("RSSI @ Tower: " + s2[4]);
                cs.newLineAtOffset(200, 0);
                cs.showText("RSSI @ Client: " + s2[5]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("Average Download Speed (5 Tests): " + s2[6]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Average Upload Speed (5 Tests): " + s2[7]);
                // survey 4
                cs.newLineAtOffset(-200, -30);
                cs.setFont(PDType1Font.TIMES_BOLD, 12);
                cs.showText("Survey #4");
                cs.setFont(PDType1Font.TIMES_ROMAN, 10);
                cs.newLineAtOffset(0, -20);
                cs.showText("Tower Tested: " + s3[0]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Technology Used: " + s3[1]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("SNR @ Tower: " + s3[2]);
                cs.newLineAtOffset(200, 0);
                cs.showText("SNR @ Client: " + s3[3]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("RSSI @ Tower: " + s3[4]);
                cs.newLineAtOffset(200, 0);
                cs.showText("RSSI @ Client: " + s3[5]);
                cs.newLineAtOffset(-200, -20);
                cs.showText("Average Download Speed (5 Tests): " + s3[6]);
                cs.newLineAtOffset(200, 0);
                cs.showText("Average Upload Speed (5 Tests): " + s3[7]);
            }
            cs.newLineAtOffset(-200, -30);
            cs.setFont(PDType1Font.TIMES_BOLD, 12);
            cs.showText("Notes: ");
            cs.setFont(PDType1Font.TIMES_ROMAN, 10);
            cs.newLineAtOffset(0, -10);
            int start = 0;
            int end = 0;
            
            String nt = notes.replaceAll("\\n+", " ");

            for ( int i : possibleWrapPoints(nt) ) {
                float width = font.getStringWidth(nt.substring(start,i)) / 2500 * fontSize;
                if ( start < end && width > paragraphWidth ) {
                    // Draw partial text and increase height
                    cs.newLineAtOffset(0, -10);
                    cs.showText(nt.substring(start,end));
                    start = end;
                }
                end = i;
            }
            cs.newLineAtOffset(0 , -10);
            cs.showText(nt.substring(start));
            cs.endText();
            PDImageXObject image = PDImageXObject.createFromFile("logo1.png", doc);
            cs.drawImage(image, 500, 700);
            cs.close();
            doc.save(fileName);
            doc.close();
        } catch(IOException e) {
            System.out.println(e);
        }
    }
    private int[] possibleWrapPoints(String text) {
        String[] split = text.split("(?<=\\W)");
        int[] ret = new int[split.length];
        ret[0] = split[0].length();
        for ( int i = 1 ; i < split.length ; i++ )
            ret[i] = ret[i-1] + split[i].length();
        return ret;
    }

}
