package Framework;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

/**
*
* @author irazsanlii
*/
public abstract class Model {
      
    public String level;
    
    public int w=0;
    public int h=0;

    public int x;
    public int y;
   
    public abstract int x();
    public abstract int y();
    public abstract void setX(int x);
    public abstract void setY(int y);  
    
    public abstract void move(int x,int y);
   /* public abstract void checkLeft();
    public abstract void checkRight();
    public abstract void checkUp();
    public abstract void checkDown();*/
    
    
    public abstract boolean isLeftCollision(Model actor);
    public abstract boolean isRightCollision(Model actor);
    public abstract boolean isTopCollision(Model actor);
    public abstract boolean isBottomCollision(Model actor);
   
    public abstract String getGameName();
           
    public abstract String getImageName(int val);
    public Image pic;
    public BufferedImage image;
    public ArrayList<BufferedImage> picList= new ArrayList<>();
    
    public abstract void restartLevel();
    

    public Model() {
        new javafx.embed.swing.JFXPanel();
    }
    
    public Image getImage() {
        return pic;
    }
    
    public void setImage(Image img) {
        pic = img;
    }
   
/**
* The method reads a given number of pictures, where zero is the
* lowest value and indicates one file, and puts them inside
* an ArrayList called picList of the type BufferedImage.
* @param nbrOfPics
* @precondition nbrOfPics must be greater or equal to zero
* @postcondition the method will return an picture, else it will throw an exception
*/
    public void readImages(int nbrOfPics) {
        try {
            if(nbrOfPics>=0){
                for(int i=0; i<nbrOfPics; i++) {
                    if(getImageName(i)!=null) {
                        image = ImageIO.read(new File(getImageName(i)));
                        picList.add(image);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
}    
            
/**
* The method will return an ArrayList of the type BufferedImage
* @return BufferedImage list
* @precondition picList!=null
* @postcondition the given list will be returned, otherwise it will throw
* an exception and return null
*/
    public ArrayList<BufferedImage> getPictureList() {
        try{
            if(picList!=null){
                return picList;
            } 
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }  
}
