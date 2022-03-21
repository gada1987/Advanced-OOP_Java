import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author irazsanlii
 */

class Wall extends Actor { 
    
    private Image image;

    public Wall(int x,int y) {
        super(x, y);
        
        initWall();
    }
    
    private void initWall() { //0 Load the wall image from the resource.
        
        ImageIcon iicon = new ImageIcon("wall.png");
        image = iicon.getImage();
        setImage(image);
    }
}

class Baggage extends Actor { 

    public Baggage(int x, int y) {
        super(x, y);
        
        initBaggage();
    }
    
    private void initBaggage() { // 1 Load the baggage image from the resource.
        
        ImageIcon iicon = new ImageIcon("crate.png");
        Image image = iicon.getImage();
        setImage(image);
    }

    public void move(int x, int y) { // Move the baggage.
        
        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
}

class Area extends Actor {

    public Area(int x, int y) {
        super(x, y);
        
        initArea();
    }
    
    private void initArea() { // Load the image which will be used to show the place where should the baggages move to.

        ImageIcon iicon = new ImageIcon("blankmarked.png");
        Image image = iicon.getImage();
        setImage(image);
    }
}

class Player extends Actor {

    public Player(int x, int y) {
        super(x, y);

        initPlayer();
    }

    private void initPlayer() { // Load the player image from the resource.

        ImageIcon iicon = new ImageIcon("player.png");
        Image image = iicon.getImage();
        setImage(image);
    }

    public void move(int x, int y) { // Move the player inside the world.

        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
}

class Game extends JPanel {
    
    private final int OFFSET = 30; // The distance between the border of the window and the game world.
    private final int SPACE = 30; // According to image size, this reflects the space constant. 
    
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    // Special containers which hold all the walls, bags, areas of the game.
    private ArrayList<Wall> walls=new ArrayList<>();
    private ArrayList<Baggage> baggs=new ArrayList<>();
    private ArrayList<Area> areas=new ArrayList<>();
    
    private Player soko;
    private int w = 0;
    private int h = 0;
    
    private boolean isCompleted = false;
    
    /* Level of the game. T
    The hash (#) stands for a wall. 
    The dollar ($) represents the box to move. 
    The dot (.) character represents the place where we must move the box. 
    The at character (@) is the sokoban. 
    And finally the new line character (\n) starts a new row of the world.*/    
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
    
    public Game() {
        
        initGame();
    }
 
    private void initGame() {
 
        addKeyListener(new TAdapter());
        setFocusable(true);
        initWorld();
    }

    public int getWidth() {

        return this.w;
    }

    public int getHeight() {

        return this.h;
    }
     
    private void initWorld() {  // Initiate the game world. It goes through the level string and fills the above mentioned lists.
        
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
                    wall = new Wall(x, y);
                    walls.add(wall);
                    x += SPACE;
                    break;

                case '$':
                    b = new Baggage(x, y);
                    baggs.add(b);
                    x += SPACE;
                    break;

                case '.':
                    a = new Area(x, y);
                    areas.add(a);
                    x += SPACE;
                    break;

                case '@':
                    soko = new Player(x, y);
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
        
    private void buildWorld(Graphics g) { // Draw the game on the window.

        g.setColor(Color.lightGray);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        // Create a list which includes all the game objects.
        ArrayList<Actor> world = new ArrayList<>();

        world.addAll(walls);
        world.addAll(areas);
        world.addAll(baggs);
        world.add(soko);

        for (int i = 0; i < world.size(); i++) {

            Actor item = world.get(i);  

            if (item instanceof Player || item instanceof Baggage) { // Because of image size, add 2px to coordinates of player and baggages to center them.
                
                g.drawImage(item.getImage(), item.x() + 2, item.y() + 2, this);
            } else {
                
                g.drawImage(item.getImage(), item.x(), item.y(), this);
            }

            if (isCompleted) {
                
                String msg="Level completed.";
                JOptionPane.showMessageDialog(this, msg); // If the level is completed, alert.
                System.exit(0);

            }
        }
    }
    
    @Override
    public void paintComponent(Graphics g) { // As a intermediatory to call the buildWorld.
        super.paintComponent(g);

        buildWorld(g);
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

    private boolean checkWallCollision(Actor actor, int type) { // Provide that the sokoban or a baggage do not pass the wall for each direction.

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

    private boolean checkBagCollision(int type) { // Provide to move the baggage if it collides with sokoban and does not with another baggage or a wall for each direction. 

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

    public void isCompleted() { // When the baggages are moved, check if the level is completed. 

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
            repaint();
        }
    }

    public void restartLevel() { // Give a chance to user to try again. Delete all object from the lists and initiate the world again.

        areas.clear();
        baggs.clear();
        walls.clear();

        initWorld();

        if (isCompleted) {
            isCompleted = false;
        }
    }
}
