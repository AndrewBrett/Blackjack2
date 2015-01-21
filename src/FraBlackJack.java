
import java.awt.Dimension;
import javax.swing.JFrame;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import sun.audio.AudioData;
import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
import sun.audio.ContinuousAudioDataStream;

public class FraBlackJack extends JFrame {

    BlackjackHand blackjackHand = new BlackjackHand();
    PanBet panBet = new PanBet();
    PanStatus panStatus = new PanStatus();
    PanBoard panBoard = new PanBoard(blackjackHand, blackjackHand, blackjackHand);
    PanInput panInput = new PanInput(panBet, panStatus, panBoard);

    public FraBlackJack() {
        setPreferredSize(new Dimension(1100, 600));
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setTitle("Blackjack");
        setLayout(new BorderLayout());
        add(panInput, BorderLayout.SOUTH);
        add(panStatus, BorderLayout.NORTH);
        add(panBoard, BorderLayout.CENTER);
        add(panBet, BorderLayout.EAST);
        music();
    }

    public static void music() {
        AudioPlayer MP = AudioPlayer.player;
        AudioStream BGM;
        AudioData MD;
        ContinuousAudioDataStream loop = null;

        try {

            InputStream test = new FileInputStream("Blackjack Music3.wav");
            BGM = new AudioStream(test);
            AudioPlayer.player.start(BGM);
            MD = BGM.getData();
            AudioPlayer.player.start(loop);
            loop = new ContinuousAudioDataStream(MD);

        } catch (FileNotFoundException e) {
            System.out.print(e.toString());
        } catch (IOException error) {
            System.out.print(error.toString());
        }
    }
}
