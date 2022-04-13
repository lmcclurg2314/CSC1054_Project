/*
Written by Levi McClurg 
03/30/22
*/

import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.util.*;

public class gameObject extends JPanel
{
   //Create variables for gameobject
   protected int x;
   protected int y;
   protected Color c;
   private static boolean grounded;
   
   public gameObject(int X, int Y, Color C)
   {
      //Assign proper coords so it draws correctly
      x = X;
      y = Y;
      c = C;
   }

   public boolean collides(gameObject go)
   {
   
      //translated pseudo code via DR. Mood
      
      //Edge case handling of go being the player
      if(go == null || (go.getX() == x && go.getY() == y))
      {
         return false;
      }
      else
      {
         //player sides
         int topThis = y;
         int leftThis = x;
         int bottomThis = y+25;
         int rightThis = x+25;
         
         //random game object sides
         int otherTop = go.getY();
         int otherLeft = go.getX();
         int otherBottom = go.getY()+25;
         int otherRight = go.getX()+25;
         
         //if we hit the top of another object we're grounded
         if(bottomThis < otherTop)
         {
            grounded = true;
         }
         else
         {
            grounded = false;
         }
         
         //if any edge hits any other edge bool is true
         boolean temp = !((bottomThis <= otherTop) || (topThis >= otherBottom) || (leftThis >= otherRight) || (rightThis <= otherLeft));
                
         //check if we win by hitting object
         if(temp)
         {
            go.win(go);
         }          
                   
         return temp;
      }
   }
   
   public void win(gameObject go)
   {
      if(go instanceof block) //if we hit an instance of block we win (everything else is null or a go)
      {
         JOptionPane jop = new JOptionPane();
         jop.showMessageDialog(null, "You Win!");
         System.exit(1);
      }
   }
   
   public void draw(Graphics g) 
   {
      super.paintComponent(g);
     
      //Set color and fill squares according to x and y of each go
      g.setColor(c);
      g.fillRect(x+10, y+10, 25, 25);
   }
   
   //getting an x var of a go
   public int getX()
   {
      return x;
   }
   
   //changing x coord
   public void changeX(int dx)
   {
      x = x + dx; //move right 
      
      if(collides())
      {
         //unless we're hitting something
         x = x - dx;
      }
   }
   
   //same with y
   public void changeY(int dy)
   {
      y = y + dy;
      if(collides()) 
      {
         y = y - dy;
      }
   }
      
   //get this go's y
   public int getY()
   {
      return y;
   }
   
   //get this go's color
   public Color getColor()
   {
      return c;
   }
   
   //check if something is grounded
   public boolean isGrounded(ArrayList<ArrayList<gameObject>> goList)
   {
      for(int row = 0; row < goList.size(); row++)
      {
         for(int col = 0; col < goList.get(0).size(); col++)
         {
            if(collides(goList.get(row).get(col))) //if the player colides with an object set grounded to true
            {
               grounded = true;
               return true;
            }
         }      
      }
      grounded = false; //if its not return false
      return grounded;
   }  
   
   //create a second bool collide 
   public boolean collides(ArrayList<ArrayList<gameObject>> goList)
   {
      for(int i = 0; i < goList.size(); i++)
      {
         for(int j = 0; j < goList.get(i).size(); j++)
         {
            if(this.collides(goList.get(i).get(j)))
            {
               return true; //if we are coliding return true
            }
         }
      }
      
      return false; //else make false
   }
   
   //make generalized colides method that takes n params and runs against whole level data
   public boolean collides(){
      return collides(projectPanelConstructor.levelData); 
   }
}