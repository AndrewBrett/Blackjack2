
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class PanBet extends JPanel {

    public static int nBet = 0;
    Color NewGreen = new Color(26, 134, 58);
    public JRadioButton btn5 = new JRadioButton("$5", true);
    public JRadioButton btn10 = new JRadioButton("$10");
    public JRadioButton btn20 = new JRadioButton("$20");
    public JRadioButton btn50 = new JRadioButton("$50");
    public JRadioButton btn100 = new JRadioButton("$100");
    public JRadioButton btnAllIn = new JRadioButton("All In");
    public JLabel lblStatus;
    public static String sMessage = "Please place your bet.";
    public static String sMoney;
    public static String sBet;
    public JLabel lblMoney;
    public JLabel lblBet;
    public static int nTotal = 1000;
   //PanInput panInput;
    // private int nBet = 0;

    public PanBet() {
        setLayout(new GridLayout(8, 1));
        //panInput=_panInput;
        ButtonGroup betgroup = new ButtonGroup();
        setBackground(NewGreen);
        btn5.setBackground(NewGreen);
        btn10.setBackground(NewGreen);
        btn20.setBackground(NewGreen);
        btn50.setBackground(NewGreen);
        btn100.setBackground(NewGreen);
        btnAllIn.setBackground(NewGreen);
        Font font = new Font("Copperplate Gothic Bold", Font.PLAIN, 18);
        //sBet = "";
        sMoney = "Money: $" + nTotal;
        lblMoney = new JLabel(sMoney);
        lblBet = new JLabel(sBet);
        lblMoney.setText(sMoney);
        lblMoney.setFont(font);
        lblBet.setFont(font);
        lblBet.setForeground(Color.white);
        lblMoney.setForeground(Color.white);

        betgroup.add(btn5);
        betgroup.add(btn10);
        betgroup.add(btn20);
        betgroup.add(btn50);
        betgroup.add(btn100);
        betgroup.add(btnAllIn);
        add(lblMoney);
        add(lblBet);
        add(btn5);
        add(btn10);
        add(btn20);
        add(btn50);
        add(btn100);
        add(btnAllIn);
    }

    //int getBet(){
    //  return nBet;   nBet = panBet.getBet();
    //}
}
