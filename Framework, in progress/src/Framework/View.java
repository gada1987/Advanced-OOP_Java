package Framework;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author irazsanlii
 */
public abstract class View extends JPanel {
    
    public String level;
    
    private int w=0;
    private int h=0;
    
    public final int OFFSET=30;
    public final int SPACE=30;

    public abstract Container getTheContainer();
              
    public abstract void restartLevel();
    public abstract void isCompleted();
    
    public boolean isCompleted=false;
    
    @Override
    public void paintComponent(Graphics g) { // As a intermediatory to call the buildWorld.
        super.paintComponent(g);

        buildWorld(g);
    }    
        
    private void buildWorld(Graphics g) { // Draw the game on the window.

        g.setColor(Color.lightGray);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        ArrayList<Model> world = new ArrayList<>();
        
        for (int i = 0; i < world.size(); i++) {

            Model item = world.get(i);  
    
                g.drawImage(item.picList.get(i), item.x(), item.y(), this);

            if (isCompleted) {
                
                String msg="Level completed.";
                JOptionPane.showMessageDialog(this, msg); // If the level is completed, alert.
                System.exit(0);

            }
        }
    }    
}
