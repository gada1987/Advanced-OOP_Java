package Project;

import Framework.Model;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author irazsanlii
 */
public class Actor extends Model {
   
    private final int OFFSET=30;
    private final int SPACE=30;
    
    private final int LEFT_COLLISION = 1;
    private final int RIGHT_COLLISION = 2;
    private final int TOP_COLLISION = 3;
    private final int BOTTOM_COLLISION = 4;

    private final ArrayList<Wall> walls=new ArrayList<>();
    private final ArrayList<Baggage> baggs=new ArrayList<>();
    private final ArrayList<Area> areas=new ArrayList<>();
    
    private Player soko;
    
    public ArrayList<BufferedImage> usedPictureList;
    
    private boolean isCompleted = false;

    public Actor() {
        readImages(6);     
    }

    public int getWidth() {

        return this.w;
    }

    public int getHeight() {

        return this.h;
    } 
    
    @Override
    public int x() {
        
        return x;
    }
    
    @Override
    public int y() {
        
        return y;
    }

    @Override
    public void setX(int x) {
        
        this.x = x;
    }

    @Override
    public void setY(int y) {
        
        this.y = y;
    }
    
    
    @Override
    public void move(int x, int y) {
        
        int dx = x() + x;
        int dy = y() + y;
        
        setX(dx);
        setY(dy);
    }
/*
    @Override
    public void checkLeft() {
        
        for (int i = 0; i < walls.size(); i++) {

            Wall wall = walls.get(i);

            if (soko.isLeftCollision(wall)) {

                return;
            }
        }
    }

    @Override
    public void checkRight() {
 
        for (int i = 0; i < walls.size(); i++) {

            Wall wall = walls.get(i);

            if (soko.isRightCollision(wall)) {
                return;
            }
        }    
    }

    @Override
    public void checkUp() {

        for (int i = 0; i < walls.size(); i++) {

            Wall wall = walls.get(i);

            if (soko.isTopCollision(wall)) {

                return;
            }
        }
    }

    @Override
    public void checkDown() {
 
        for (int i = 0; i < walls.size(); i++) {

            Wall wall = walls.get(i);

            if (soko.isBottomCollision(wall)) {

                return;
            }
        }
    }*/

    @Override
    public boolean isLeftCollision(Model actor) {
        return x() - SPACE == actor.x() && y() == actor.y(); //x i bir birim azalt y sabit
    }

    @Override
    public boolean isRightCollision(Model actor) {
        return x() + SPACE == actor.x() && y() == actor.y();
    }

    @Override
    public boolean isTopCollision(Model actor) {
        return y() - SPACE == actor.y() && x() == actor.x();
    }

    @Override
    public boolean isBottomCollision(Model actor) {
        return y() + SPACE == actor.y() && x() == actor.x();
    }
    
    @Override
    public String getGameName() {
        return "Sokoban";
    }

    @Override
    public String getImageName(int val) {
        try {
            Thread.sleep(10);
            if(val==0) {return "wall.png";}
            else if(val==1){return "crate.png";}
            else if(val==2){return "blankmarked.png";}
            else if(val==3){return  "player.png";
            } 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
        
    
    @Override
    public void restartLevel() {
        
    }


}
