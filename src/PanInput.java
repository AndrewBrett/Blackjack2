
import java.awt.*;
import java.awt.event.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

class PanInput extends JPanel {

    Color NewGreen = new Color(26, 134, 58);
    public JButton btnHit;
    public JButton btnStand;
    public JButton btnNewGame;
    public JButton btnBet;
    public JButton btnDDown;
    public JButton btnSplit;
    public BlackjackHand playerHand;
    public JLabel[] arLblPlayer;
    public BlackjackHand dealerHand;
    public JLabel[] arLblDealer;
    public BlackjackHand splitHand;
    public JLabel[] arLblSplit;
    public Deck deck;
    public static int nTotal = 1000;
    public static int nBet;
    public static String sMessage = "Please place your bet.";
    public static String sMoney;
    public JLabel lblMoney;
    public JLabel lblBet;
    public JLabel lblStatus;
    public JRadioButton btn5 = new JRadioButton("$5", true);
    public JRadioButton btn10 = new JRadioButton("$10");
    public JRadioButton btn20 = new JRadioButton("$20");
    public JRadioButton btn50 = new JRadioButton("$50");
    public JRadioButton btn100 = new JRadioButton("$100");
    public JRadioButton btnAllIn = new JRadioButton("All In");
    PanBet panBet;
    PanStatus panStatus;
    PanBoard panBoard;
    //Card card;
    public boolean bInGame = false;

    public PanInput(PanBet _panBet, PanStatus _panStatus, PanBoard _panBoard) {
        Color NewGreen = new Color(26, 134, 58);
        setBackground(NewGreen);
        panBet = _panBet;
        panBoard = _panBoard;
        panStatus = _panStatus;
        //card = _card;
        btnSplit = new JButton("Split");
        btnSplit.addActionListener(new SplitActionListener());
        btnDDown = new JButton("Double Down");
        btnDDown.addActionListener(new DoubleDownActionListener());
        btnHit = new JButton("Hit");
        btnHit.addActionListener(new HitActionListener());
        btnStand = new JButton("Stand");
        btnStand.addActionListener(new StandActionListener());
        btnNewGame = new JButton("New game");
        btnNewGame.addActionListener(new NewgameActionListener());
        btnBet = new JButton("Bet");
        btnBet.addActionListener(new BetActionListener());
        add(btnBet);
        add(btnHit);
        add(btnStand);
        add(btnDDown);
        add(btnSplit);
        add(btnNewGame);
        btnNewGame.setEnabled(false);
        btnHit.setEnabled(false);
        btnStand.setEnabled(false);
        btnDDown.setEnabled(false);
        btnSplit.setEnabled(false);

    }
    // actionListeners for buttons

    class SplitActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnSplit) {
            }
        }
    }

    class HitActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            {
                panBoard.hit();
                btnDDown.setEnabled(false);
                if (panBoard.playerHand.getBlackjackValue() > 21) {
                    sMessage = "You've busted! You lose.";
                    bInGame = false;
                    nTotal = nTotal - nBet;
                    nBet = 0;
                    btnNewGame.setEnabled(true);
                    btnHit.setEnabled(false);
                    btnStand.setEnabled(false);
                } else if (panBoard.playerHand.getCardCount() == 5) {
                    sMessage = "You win by taking 5 cards without going over 21.";
                    bInGame = false;
                    nTotal = nTotal + nBet;
                    nBet = 0;
                    btnNewGame.setEnabled(true);
                    btnHit.setEnabled(false);
                    btnStand.setEnabled(false);
                } else {
                    sMessage = "Hit or Stand?";
                }
                panBoard.redraw();
                panStatus.lblStatus.setText(sMessage);
                if (bInGame == false) {
                    panBoard.stand();
                }
            }
        }
    }

    class DoubleDownActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btnDDown) {
                nBet = nBet * 2;
                btnDDown.setEnabled(false);
                btnHit.setEnabled(false);
                sMessage = "Your bet has been doubled";
                panStatus.lblStatus.setText(sMessage);
                panBet.lblBet.setText("Your bet is now $" + nBet);
                panBoard.playerHand.addCard(panBoard.deck.dealCard());
                bInGame = false;
                panBoard.redraw();
                panBoard.stand();
                btnStand.doClick();
            }
        }
    }

    class StandActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            bInGame = false;
            while (panBoard.dealerHand.getBlackjackValue() <= 16 && panBoard.dealerHand.getCardCount() < 5) {
                panBoard.dealerHand.addCard(panBoard.deck.dealCard());
            }
            if (e.getSource() == btnDDown) {
                nBet = nBet * 2;
            }
            if (panBoard.playerHand.getBlackjackValue() > 21) {
                sMessage = "You've busted!  Sorry, you lose.";
                nTotal = nTotal - nBet;
                nBet = 0;
            } else if (panBoard.dealerHand.getBlackjackValue() > 21) {
                sMessage = "You win!  Dealer has busted with " + panBoard.dealerHand.getBlackjackValue() + ".";
                nTotal = nTotal + nBet;
                nBet = 0;
            } else if (panBoard.dealerHand.getCardCount() == 5) {
                sMessage = "You lose.  Dealer took 5 cards without going over 21.";
                nTotal = nTotal - nBet;
                nBet = 0;
            } else if (panBoard.dealerHand.getBlackjackValue() > panBoard.playerHand.getBlackjackValue()) {
                sMessage = "You lose, " + panBoard.dealerHand.getBlackjackValue()
                        + " to " + panBoard.playerHand.getBlackjackValue() + ".";
                nTotal = nTotal - nBet;
                nBet = 0;
            } else if (panBoard.dealerHand.getBlackjackValue() == panBoard.playerHand.getBlackjackValue()) {
                sMessage = "You lose.  Dealer wins on a tie.";
                nTotal = nTotal - nBet;
                nBet = 0;
            } else if (panBoard.playerHand.getBlackjackValue() < 22) {
                sMessage = "You win, " + panBoard.playerHand.getBlackjackValue()
                        + " to " + panBoard.dealerHand.getBlackjackValue() + "!";
                nTotal = nTotal + nBet;
                nBet = 0;
            } else {
                sMessage = "You lose " + panBoard.dealerHand.getBlackjackValue() + " to " + panBoard.playerHand.getBlackjackValue();
                nTotal = nTotal - nBet;
                nBet = 0;
            }
            panBoard.redraw();
            panBoard.stand();
            panStatus.lblStatus.setText(sMessage);
            btnNewGame.setEnabled(true);
            btnHit.setEnabled(false);
            btnStand.setEnabled(false);
            btnDDown.setEnabled(false);
        }
    }

    class NewgameActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            if (bInGame == true) {
                sMessage = "You still have to finish this game!";
                panBoard.redraw();
            } else {
                panBet.btn5.setEnabled(true);
                panBet.btn10.setEnabled(true);
                panBet.btn20.setEnabled(true);
                panBet.btn50.setEnabled(true);
                panBet.btn100.setEnabled(true);
                panBet.btnAllIn.setEnabled(true);
                btnBet.setEnabled(true);
                btnHit.setEnabled(false);
                btnStand.setEnabled(false);
                btnDDown.setEnabled(false);
                btnNewGame.setEnabled(false);
                sMoney = "Money: $" + nTotal;
                panBet.lblMoney.setText(sMoney);
                panBet.lblBet.setText("");
                sMessage = "Please place your bet";
                panStatus.lblStatus.setText(sMessage);
                //clear board

                for (int i = 0; i < 6; i++) {
                    panBoard.arLblPlayer[i].setIcon(null);
                    panBoard.arLblDealer[i].setIcon(null);
                }
            }
        }
    }

    class BetActionListener implements ActionListener {

        public void actionPerformed(ActionEvent e) {
            //BlackjackHand blackjackhand = new BlackjackHand();
            chips();
            cards();
            if (e.getSource() == btnBet) {
                if (panBet.btn5.isSelected() == true) {
                    nBet = 5;
                }
                if (panBet.btn10.isSelected() == true) {
                    nBet = 10;
                }
                if (panBet.btn20.isSelected() == true) {
                    nBet = 20;
                }
                if (panBet.btn50.isSelected() == true) {
                    nBet = 50;
                }
                if (panBet.btn100.isSelected() == true) {
                    nBet = 100;
                }
                if (panBet.btnAllIn.isSelected() == true) {
                    nBet = nTotal;
                }
                if (nTotal == 0) {
                    sMessage = "Game over, you're all out of money.";
                    panStatus.lblStatus.setText(sMessage);
                    return;
                }
                if (nBet > nTotal) {
                    sMessage = "Sorry insufficent funds";
                    panStatus.lblStatus.setText(sMessage);
                    return;
                } else {
                    panBoard.newGame();
                    panBet.lblBet.setText("Your bet is $" + nBet);
                    sMessage = "Hit or Stand?";
                    panStatus.lblStatus.setText(sMessage);
                    btnHit.setEnabled(true);
                    btnStand.setEnabled(true);
                    btnBet.setEnabled(false);
                    if (nBet * 2 > nTotal) {
                    } else {
                        btnDDown.setEnabled(true);
                    }
                    panBet.btn5.setEnabled(false);
                    panBet.btn10.setEnabled(false);
                    panBet.btn20.setEnabled(false);
                    panBet.btn50.setEnabled(false);
                    panBet.btn100.setEnabled(false);
                    panBet.btnAllIn.setEnabled(false);
                }
                if (panBoard.dealerHand.getBlackjackValue() == 21) {
                    sMessage = "You lose.  Dealer has Blackjack.";
                    bInGame = false;
                    panBoard.stand();
                    nTotal = nTotal - nBet;
                    nBet = 0;
                    btnNewGame.setEnabled(true);
                    btnHit.setEnabled(false);
                    btnStand.setEnabled(false);
                    btnDDown.setEnabled(false);
                } else if (panBoard.playerHand.getBlackjackValue() == 21) {
                    sMessage = "You win!  You have Blackjack.";
                    bInGame = false;
                    panBoard.stand();
                    nTotal = nTotal + nBet;
                    nBet = 0;
                    btnNewGame.setEnabled(true);
                    btnHit.setEnabled(false);
                    btnStand.setEnabled(false);
                    btnDDown.setEnabled(false);

                } else {
                    bInGame = true;
                }
                /*if (panBoard.playerHand.getBlackjackValue() / BlackjackHand.nCardVal() == 2) {
                 btnSplit.setEnabled(true);
                 }*/
                panStatus.lblStatus.setText(sMessage);

            }
        }

        public void chips() {
            AudioPlayer MGP = AudioPlayer.player;
            AudioStream BGM;
            AudioData MD;

            ContinuousAudioDataStream loop = null;

            try {
                InputStream test = new FileInputStream("poker chips.wav");
                BGM = new AudioStream(test);
                AudioPlayer.player.start(BGM);

            } catch (FileNotFoundException e) {
                System.out.print(e.toString());
            } catch (IOException error) {
                System.out.print(error.toString());
            }
            MGP.start(loop);
        }

        public void cards() {
            AudioPlayer MGP = AudioPlayer.player;
            AudioStream BGM;
            AudioData MD;

            ContinuousAudioDataStream loop = null;

            try {
                InputStream test = new FileInputStream("cards.wav");
                BGM = new AudioStream(test);
                AudioPlayer.player.start(BGM);

            } catch (FileNotFoundException e) {
                System.out.print(e.toString());
            } catch (IOException error) {
                System.out.print(error.toString());
            }
            MGP.start(loop);
        }
    }

    int getMoney() {
        return nTotal;
    }

    String getMessage() {
        return sMessage;
    }

    boolean getInGame() {
        return bInGame;
    }
}
