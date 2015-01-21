
import javax.swing.*;
import java.awt.*;

class PanBoard extends JPanel {

    Color NewGreen = new Color(26, 134, 58);
    public JLabel[] arLblPlayer;
    public JLabel[] arLblDealer;
    public JLabel[] arLblSplit;
    public BlackjackHand dealerHand;
    public BlackjackHand playerHand;
    public BlackjackHand splitHand;
    public PanInput panInput;
    public String sName;
    public Deck deck;
    public String sMessage;
    public int nBet;
    public int nTotal = 1000;

    public boolean bInGame = false;

    public PanBoard(BlackjackHand _dealerHand, BlackjackHand _playerHand, BlackjackHand _splitHand) {
        //  panInput = _panInput;
        dealerHand = _dealerHand;
        playerHand = _playerHand;
        splitHand = _splitHand;
        setLayout(new GridLayout(3, 6));
        Font cardlabel = new Font("Copperplate Gothic", Font.PLAIN, 14);
        setBackground(NewGreen);
        arLblPlayer = new JLabel[6];
        arLblDealer = new JLabel[6];
        arLblSplit = new JLabel[6];
        for (int i = 0; i < 6; i++) {
            arLblPlayer[i] = new JLabel();
            arLblDealer[i] = new JLabel();
            arLblSplit[i] = new JLabel();
        }
        for (int i = 0; i < 6; i++) {
            add(arLblDealer[i]);
        }
        for (int i = 0; i < 6; i++) {
            add(arLblPlayer[i]);
        }
        for (int i = 0; i < 6; i++) {
            add(arLblSplit[i]);
        }
        String sName;
        sName = JOptionPane.showInputDialog("Enter your name.");  
        if (sName == null || "".equals(sName)) {
            arLblPlayer[0].setText("Player's Cards");
        } else {
            arLblPlayer[0].setText(sName + "'s Cards");
        }
        arLblSplit[0].setText("Split Hand");
        arLblDealer[0].setText("Dealer's Cards");
        
        arLblPlayer[0].setFont(cardlabel);
        arLblPlayer[0].setForeground(Color.white);
        arLblDealer[0].setFont(cardlabel);
        arLblDealer[0].setForeground(Color.white);
        arLblSplit[0].setFont(cardlabel);
        arLblSplit[0].setForeground(Color.white);
        bInGame = true;
        deck = new Deck();
        playerHand = new BlackjackHand();
        dealerHand = new BlackjackHand();

    }

    public static String GetFileName(Card thisCard) {
        String sSuit = thisCard.getSuitAsString();
        String sValue = thisCard.getValueAsString();
        String FileName = sValue + "_of_" + sSuit + ".png";
        return FileName;
    }

    public void newGame() {
        bInGame = true;
        deck = new Deck();
        playerHand = new BlackjackHand();
        dealerHand = new BlackjackHand();
        deck.shuffle();
        //deals cards
        dealerHand.addCard(deck.dealCard());
        dealerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        playerHand.addCard(deck.dealCard());
        redraw();

    }

    public void redraw() {

        // boolean bInGame=panInput.getInGame();
        // 1) look at cards in playerHand and dealerHand and add/update labes in the "Grid"
        for (int i = 1; i < dealerHand.getCardCount() + 1; i++) {
            Card dealerCard = dealerHand.getCard(i - 1);
            arLblDealer[i].setIcon(new ImageIcon(GetFileName(dealerCard)));
        }
        for (int i = 1; i < playerHand.getCardCount() + 1; i++) {
            Card playerCard = playerHand.getCard(i - 1);
            arLblPlayer[i].setIcon(new ImageIcon(GetFileName(playerCard)));
        }
        if (bInGame) {
            arLblDealer[1].setIcon(new ImageIcon("backcard.png"));
        }
    }

    public void hit() {
        playerHand.addCard(deck.dealCard());
    }

    public void stand() {
        for (int i = 1; i < dealerHand.getCardCount() + 1; i++) {
            Card dealerCard = dealerHand.getCard(i - 1);
            arLblDealer[i].setIcon(new ImageIcon(GetFileName(dealerCard)));
        }
        if (bInGame = false) {
            arLblDealer[1].setIcon(new ImageIcon(""));
        }
    }

    int getMoney() {
        return nTotal;
    }

    String getMessage() {
        return sMessage;
    }

    int getBet() {
        return nBet;
    }
}
