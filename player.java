/*
Written by Levi McClurg
04/08/22
*/

import java.awt.*;
import java.util.*;

public class player extends gameObject
{
   //make a double gravity var
   private double gravity = 0;
   
   //make bool grounded var
   private boolean grounded;

   //player maker 
   public player(int x, int y)
   {
      super(x, y, Color.CYAN);
   }

   //checker to see if its on ground 
   public boolean isOnGround(ArrayList<ArrayList<gameObject>> goList)
   {  
      if (this.isGrounded(goList)){
         gravity = 0; //set gravity to 0 if we're on the ground 
         return true;
      }

      return false;
   }
   
   public void move(int dx, int dy)
   {
      //pretty self explanatory but move by the specified amount 
      this.changeX(dx);
      this.changeY(dy);
   }
   
   public boolean touchChecker()
   {
      y++; //move down at start
      
      if(collides()) //always move back even if colide, this method only checks if we hit something
      {
         if (gravity > 0) //jumping 
         {
            y--;
            return false; 
         } 
         else 
         {
            y--;
            return true;
         }  
      }
      y--; //move back
      return false;
   }
   
   public void moveY()
   {
      //make gravity negative 
      gravity -= 0.1;
      
      //check if gravitu is greater than -1
      for(int i = -1; i < Math.floor(Math.abs(gravity)); i++){
         if (gravity >= 0) //if it is positive 
         {
            y--; //go up
         } 
         else 
         {
            y++; //go down
         }
         
         if(collides())
         {
            if (gravity > 0)
            {
               y++; //if we're hitting something but gravity is somehow greater than 0 go down
            } 
            else 
            {
               //go up and make grounded 
               y--;
               grounded = true;
            }  
            //return gravity to 0
            gravity = 0;
            
            //break once gravity is stopped
            break;
         }
      }
      
      //make grounded false at end of method 
      grounded = false;
   }
   
   public void jump()
   {
      if(touchChecker()) //if we are touching something
      {
          gravity = 4; //make gravity very positive 
          grounded = false; //make grounded false
      }     
   }
}