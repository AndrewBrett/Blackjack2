
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PanStatus extends JPanel {
    private String sMessage = "Please place your bet.";
    public JLabel lblStatus;

    public PanStatus() {
        Color NewGreen = new Color(26, 134, 58);
        Font font = new Font("Copperplate Gothic Bold", Font.PLAIN, 22);
        lblStatus = new JLabel(sMessage);
        setBackground(NewGreen);
        lblStatus.setFont(font);
        lblStatus.setForeground(Color.white);
        add(lblStatus);
    }
    public void update() {
        //sMessage=panInput.getMessage();
        lblStatus.setText(sMessage);
    }
}
