import java.awt.Image;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author irazsanlii
 */

public class Actor { // Base class for other actors in the game which encapsulates the basic funtionality on an object in the Sokoban game.
    
    private final int SPACE = 30;

    private int x;
    private int y;
    private Image image;

    public Actor(int x, int y) {
        
        this.x = x;
        this.y = y;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image img) {
        image = img;
    }

    public int x() {
        
        return x;
    }

    public int y() {
        
        return y;
    }

    public void setX(int x) {
        
        this.x = x;
    }

    public void setY(int y) {
        
        this.y = y;
    }

    // Check if the actor collides with another actor for each direction.
    public boolean isLeftCollision(Actor actor) { //Sol çarpışma 
        
        return x() - SPACE == actor.x() && y() == actor.y(); //x i bir birim azalt y sabit
    }

    public boolean isRightCollision(Actor actor) {
        
        return x() + SPACE == actor.x() && y() == actor.y();
    }

    public boolean isTopCollision(Actor actor) {
        
        return y() - SPACE == actor.y() && x() == actor.x();
    }

    public boolean isBottomCollision(Actor actor) {
        
        return y() + SPACE == actor.y() && x() == actor.x();
    }
}