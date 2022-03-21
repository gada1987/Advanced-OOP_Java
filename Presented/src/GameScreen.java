
import java.awt.EventQueue;
import javax.swing.JFrame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author irazsanlii
 */
public class GameScreen extends JFrame {
    
    private final int OFFSET=30;
    
    public GameScreen() {
        
        initUI();
    }

    private void initUI() {
        
        Game game=new Game();
        add(game);
        
        setTitle("Sokoban");
        setSize(game.getWidth()+OFFSET, game.getHeight()+2*OFFSET);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }
    
    public static void main(String[] args) {
        
        EventQueue.invokeLater(()-> {
            
            GameScreen screen=new GameScreen();
            screen.setVisible(true);
       });
    }
    
    
}
