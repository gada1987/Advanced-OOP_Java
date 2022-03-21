package Framework;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 *
 * @author irazsanlii
 */
public abstract class Controller implements KeyListener {
    
    public abstract void moveLeftPlayer();
    public abstract void moveRightPlayer();
    public abstract void moveUpPlayer();
    public abstract void moveDownPlayer();
    public abstract void restart();
    
    public void keyPressed(KeyEvent e) {
        
        int key = e.getKeyCode();
        
        try {
            
            if (key == KeyEvent.VK_LEFT) {
                    moveLeftPlayer();
            }
                    
            else if (key == KeyEvent.VK_RIGHT) {
                    moveLeftPlayer();
            }
                    
            else if (key == KeyEvent.VK_UP) {
                    moveLeftPlayer();
            }
                    
            else if (key == KeyEvent.VK_DOWN) {
                    moveLeftPlayer();
            }
                    
            else if (key == KeyEvent.VK_R) {
                    restart();
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();          
        }   
    }
}

