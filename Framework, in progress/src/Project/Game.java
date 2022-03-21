package Project;

import Framework.View;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author irazsanlii
 */

class Wall extends Actor {

    public Wall() {
         
        initWall();
    }
    
    private void initWall() {
        
        ImageIcon iicon=new ImageIcon("wall.png");
        setImage(iicon.getImage());
    }
}

class Baggage extends Actor {

    public Baggage() {
         
        initBaggage();
    }
    
    private void initBaggage() {
        
        ImageIcon iicon=new ImageIcon("crate.png");
        setImage(iicon.getImage());
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y); //To change body of generated methods, choose Tools | Templates.
    }
}

class Area extends Actor {

    public Area() {
         
        initArea();
    }
    
    private void initArea() {
        
        ImageIcon iicon=new ImageIcon("blankmarked.png");
        setImage(iicon.getImage());
    }    
}

class Player extends Actor {

    public Player() {
        
        initPlayer ();
    }
    
    private void initPlayer() {
        
        ImageIcon iicon=new ImageIcon("player.png");
        setImage(iicon.getImage());
    }

    @Override
    public void move(int x, int y) {
        super.move(x, y); //To change body of generated methods, choose Tools | Templates.
    }   
}
public class Game extends Actor {
    
    public JFrame frame;
    public Container c;
    public JPanel backgroundPanel = new JPanel();
    public JPanel frontPanel = new JPanel();
    
    public Game() {
    
        super(); 
        
        frame = new JFrame(getGameName());
        
        c = frame.getContentPane();
        c.setLayout(new BorderLayout());
        frontPanel.setLayout(new BorderLayout());
        
        backgroundPanel.setLayout(new BorderLayout());
        frontPanel.setBounds(0,0,320,320);
        
        c.add(backgroundPanel,BorderLayout.CENTER);
        backgroundPanel.add(new Map());
        
        frame.setVisible(true);
        frame.setSize(getHeight(),getWidth());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
    }
  

    public class Map extends View {

        public Map() {

            initGame();        
        }

        private int w=0;
        private int h=0;
        
        private Player soko;

        private ArrayList<Wall> walls=new ArrayList<>();
        private ArrayList<Baggage> baggs=new ArrayList<>();
        private ArrayList<Area> areas=new ArrayList<>();
        
        private final int LEFT_COLLISION = 1;
        private final int RIGHT_COLLISION = 2;
        private final int TOP_COLLISION = 3;
        private final int BOTTOM_COLLISION = 4;

        private String level
            = "    ######\n"
            + "    ##   #\n"
            + "    ##$  #\n"
            + "  ####  $##\n"
            + "  ##  $ $ #\n"
            + "#### # ## #   ######\n"
            + "##   # ## #####  ..#\n"
            + "## $  $          ..#\n"
            + "###### ### #@##  ..#\n"
            + "    ##     #########\n"
            + "    ########\n";  


        private void initGame() {        
            setFocusable(true);
            initWorld();
        }     

        public void initWorld() {  // Initiate the game world. It goes through the level string and fills the above mentioned lists.

            int x = OFFSET;
            int y = OFFSET;

            Wall wall;
            Baggage b;
            Area a;

            for (int i = 0; i < level.length(); i++) {

                char item = level.charAt(i);

                switch (item) {

                    case '\n':
                        y += SPACE;

                        if (this.w < x) {
                            this.w = x;
                        }

                        x = OFFSET;
                        break;

                    case '#':
                        wall = new Wall();
                        walls.add(wall);
                        x += SPACE;
                        break;

                    case '$':
                        b = new Baggage();
                        baggs.add(b);
                        x += SPACE;
                        break;

                    case '.':
                        a = new Area();
                        areas.add(a);
                        x += SPACE;
                        break;

                    case '@':
                        soko = new Player();
                        x += SPACE;
                        break;

                    case ' ':
                        x += SPACE;
                        break;

                    default:
                        break;
                }

                h = y;
            }
        }
        
        
    private class TAdapter extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) { // Check what keys were pressed.

            if (isCompleted) {
                return;
            }

            int key = e.getKeyCode();

            switch (key) {
                
                case KeyEvent.VK_LEFT: // Check if the sokoban does not collide with a ball or a baggage, move the sokoban to the left.
                    
                    if (checkWallCollision(soko,LEFT_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(LEFT_COLLISION)) {
                        return;
                    }
                    
                    soko.move(-SPACE, 0);
                    
                    break;
                    
                case KeyEvent.VK_RIGHT: // Check if the sokoban does not collide with a ball or a baggage, move the sokoban to the right.
                    
                    if (checkWallCollision(soko, RIGHT_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(RIGHT_COLLISION)) {
                        return;
                    }
                    
                    soko.move(SPACE, 0);
                    
                    break;
                    
                case KeyEvent.VK_UP: // Check if the sokoban does not collide with a ball or a baggage, move the sokoban to the up.
                    
                    if (checkWallCollision(soko, TOP_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(TOP_COLLISION)) {
                        return;
                    }
                    
                    soko.move(0, -SPACE);
                    
                    break;
                    
                case KeyEvent.VK_DOWN: // Check if the sokoban does not collide with a ball or a baggage, move the sokoban to the down.
                    
                    if (checkWallCollision(soko, BOTTOM_COLLISION)) {
                        return;
                    }
                    
                    if (checkBagCollision(BOTTOM_COLLISION)) {
                        return;
                    }
                    
                    soko.move(0, SPACE);
                    
                    break;
                    
                case KeyEvent.VK_R:
                    
                    restartLevel();
                    
                    break;
                    
                default:
                    break;
            }

            repaint();
        }
        
    }   

         public boolean checkWallCollision(Actor actor, int type) { // Provide that the sokoban or a baggage do not pass the wall for each direction.

            switch (type) {

                case LEFT_COLLISION:

                    for (int i = 0; i < walls.size(); i++) {

                        Wall wall = walls.get(i);

                        if (actor.isLeftCollision(wall)) {

                            return true;
                        }
                    }

                    return false;

                case RIGHT_COLLISION:

                    for (int i = 0; i < walls.size(); i++) {

                        Wall wall = walls.get(i);

                        if (actor.isRightCollision(wall)) {
                            return true;
                        }
                    }

                    return false;

                case TOP_COLLISION:

                    for (int i = 0; i < walls.size(); i++) {

                        Wall wall = walls.get(i);

                        if (actor.isTopCollision(wall)) {

                            return true;
                        }
                    }

                    return false;

                case BOTTOM_COLLISION:

                    for (int i = 0; i < walls.size(); i++) {

                        Wall wall = walls.get(i);

                        if (actor.isBottomCollision(wall)) {

                            return true;
                        }
                    }

                    return false;

                default:
                    break;
            }

            return false;
        }


        public boolean checkBagCollision(int type) {
            
            switch (type) {

                case LEFT_COLLISION:

                    for (int i = 0; i < baggs.size(); i++) {

                        Baggage bag = baggs.get(i);

                        if (soko.isLeftCollision(bag)) {

                            for (int j = 0; j < baggs.size(); j++) {

                                Baggage item = baggs.get(j);

                                if (!bag.equals(item)) {

                                    if (bag.isLeftCollision(item)) {
                                        return true;
                                    }
                                }

                                if (checkWallCollision(bag, LEFT_COLLISION)) {
                                    return true;
                                }
                            }

                            bag.move(-SPACE, 0);
                            isCompleted();
                        }
                    }

                    return false;

                case RIGHT_COLLISION:

                    for (int i = 0; i < baggs.size(); i++) {

                        Baggage bag = baggs.get(i);

                        if (soko.isRightCollision(bag)) {

                            for (int j = 0; j < baggs.size(); j++) {

                                Baggage item = baggs.get(j);

                                if (!bag.equals(item)) {

                                    if (bag.isRightCollision(item)) {
                                        return true;
                                    }
                                }

                                if (checkWallCollision(bag, RIGHT_COLLISION)) {
                                    return true;
                                }
                            }

                            bag.move(SPACE, 0);
                            isCompleted(); 
                        }
                    }
                    return false;

                case TOP_COLLISION:

                    for (int i = 0; i < baggs.size(); i++) {

                        Baggage bag = baggs.get(i);

                        if (soko.isTopCollision(bag)) {

                            for (int j = 0; j < baggs.size(); j++) {

                                Baggage item = baggs.get(j);

                                if (!bag.equals(item)) {

                                    if (bag.isTopCollision(item)) {
                                        return true;
                                    }
                                }

                                if (checkWallCollision(bag, TOP_COLLISION)) {
                                    return true;
                                }
                            }

                            bag.move(0, -SPACE);
                            isCompleted();
                        }
                    }

                    return false;

                case BOTTOM_COLLISION:

                    for (int i = 0; i < baggs.size(); i++) {

                        Baggage bag = baggs.get(i);

                        if (soko.isBottomCollision(bag)) {

                            for (int j = 0; j < baggs.size(); j++) {

                                Baggage item = baggs.get(j);

                                if (!bag.equals(item)) {

                                    if (bag.isBottomCollision(item)) {
                                        return true;
                                    }
                                }

                                if (checkWallCollision(bag,BOTTOM_COLLISION)) {

                                    return true;
                                }
                            }

                            bag.move(0, SPACE);
                            isCompleted();
                        }
                    }

                    break;

                default:
                    break;
            }

            return false;
        } 
               
/**
 * @precondition The container object must not be equal to null
 * Returns the given Container, which adapts the frame of the GUI
 * @return Container object
 */        
        @Override
        public Container getTheContainer() {
                if(c!=null) {return c;}
                else 
                        return null;
        }

    
        @Override
        public void restartLevel() {

            areas.clear();
            baggs.clear();
            walls.clear();

            initWorld();

            if (isCompleted) {
                isCompleted = false;
            }
        }
        
        @Override
        public void isCompleted() {
            
            int nOfBags = baggs.size();
            int finishedBags = 0;

            for (int i = 0; i < nOfBags; i++) {

                Baggage bag = baggs.get(i);

                for (int j = 0; j < nOfBags; j++) {

                    Area area =  areas.get(j);

                    if (bag.x() == area.x() && bag.y() == area.y()) {

                        finishedBags += 1;
                    }
                }
            }

                if (finishedBags == nOfBags) {

                    isCompleted = true;
                    c.repaint();
                }
        }   
    }    
}



