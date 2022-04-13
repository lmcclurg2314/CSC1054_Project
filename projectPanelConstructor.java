/*
Written by Levi McClurg
04/12/22
*/

import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.io.*;
//importing these with the .* doesn't work for whatever reason :(
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.Timer;


public class projectPanelConstructor extends JPanel
{

   //Initialize booleans for directions
   private boolean up;
   private boolean right;
   private boolean left;
   
   //make player
   player p1;
  
   //declare level data  
   public static ArrayList<ArrayList<gameObject>> levelData;
   
   //color var for blocks
   Color color;

   public projectPanelConstructor()
   {
      //read in data from file 
      
      try
      {
         File f = new File("projectLevelData.txt");
         Scanner scan = new Scanner(f);
         
         //get data at top of file
         int startX = scan.nextInt();
         int startY = scan.nextInt();
         int rows = scan.nextInt();
         int cols = scan.nextInt();
         
         //make levelData 2d alist
         levelData = new ArrayList<ArrayList<gameObject>>();
         
         for(int i = 0; i < rows; i++)
         {
            //make an arraylist for every i
            levelData.add(new ArrayList());
            for(int j = 0; j < cols; j++)
            {
               switch(scan.nextInt()) //add different kinds of objects based on read data
               {
                  case 0:
                     
                     levelData.get(i).add(null);
                     break;
                  
                  case 1:
                  
                     levelData.get(i).add(new gameObject(j*25, i*25, Color.BLUE));
                     break;
                     
                  case 2:
                  
                     levelData.get(i).add(new block(j*25, i*25, Color.GREEN)); //making this a block will be handy
                     break;
               }
            }
         } 
         
         //initialize player
         p1 = new player(startX * 25, startY * 25);
      }
      catch(FileNotFoundException fnfe)
      {
         System.out.println("Data file not found"); //catch if file not found
      }
      
      //Add key listener
      addKeyListener(new keylistener());
      setFocusable(true);
      
      //Create timer for 10 ms
      Timer t = new Timer(10, new buttonListener());
      t.start();
   }
   
   //create a listener for an action
   public class buttonListener implements ActionListener
   {
      public void actionPerformed(ActionEvent ae)
      {      
         if(left) //if left is true
         {
            p1.changeX(-1); //go left
         } 
           
         if(right) //if right is true
         {
            p1.changeX(1);
         }
        
         p1.moveY(); //always be trying to move in y
        
         if(up) //if up is true
         {
            p1.jump(); //call jump
         }  
        
         repaint(); //repaint everything 
      }
   }

   //Create a key listener class
   public class keylistener implements KeyListener
   {
      public void keyTyped(KeyEvent e){} //nothing happenes when button is pushed
         
      public void keyPressed(KeyEvent e) //when buttons are held down do
      {
              
         if(e.getKeyCode() == KeyEvent.VK_W) //up
         {
            up = true; //set up to true when w held
         }   
                
         if(e.getKeyCode() == KeyEvent.VK_A) //left
         {
            left = true; //set left to true when a held
         }
              
         if(e.getKeyCode() == KeyEvent.VK_D) //right
         {
            right = true;   //set right to true when d held
         }
      }   
         
      public void keyReleased(KeyEvent e)
      {
         if(e.getKeyCode() == KeyEvent.VK_W)
         {
            up = false; //set up to false when w is released
         }
         
         if(e.getKeyCode() == KeyEvent.VK_A)
         {
            left = false; //set left to false when a is released
         }
   
      if(e.getKeyCode() == KeyEvent.VK_D)
         {
            right = false; //set right to false when d is released
         }
   
      repaint();
      }
   }  

   
   public void paintComponent(Graphics g) 
   {
      super.paintComponent(g);
      
      //paint a black border
      g.setColor(Color.BLACK);
      for(int i = 0; i < levelData.size(); i++)
      {
         for(int j = 0; j < levelData.get(i).size(); j++)
         {
            //at top
            if(i == 0) 
            {
               g.fillRect(j*25 + 10, i*25, 25, 10);
            }
            
            //at bottom
            if(i == levelData.size() - 1)
            {
               g.fillRect(j*25, i*25+35, 45, 10);
            }
            
            //at left
            if(j == 0)
            {
               g.fillRect(j*25, i*25, 10, 35);
            }
            
            //at right
            if(j == levelData.get(i).size() - 1)
            {
               g.fillRect(j*25 + 35, i * 25, 10, 45);
            }            
         }
      }
      
      //read in level data
      for(int row = 0; row < levelData.size(); row++)
      {
         for(int col = 0; col < levelData.get(row).size(); col++)
         {
            
            if(levelData.get(row).get(col) == null) // if data null
            {
               g.setColor(Color.WHITE); //make white rect at position i j
               g.fillRect(col*25+10, row*25+10, 25, 25);

            } 
            else 
            {              
              levelData.get(row).get(col).draw(g); //else draw the data with go draw method
            }            
         }
      }
      
      p1.draw(g); //draw player
   }
   
   public static void main(String [] args)
   {
        projectPanelConstructor app = new projectPanelConstructor(); //create the app to start at 00
        
        //make the frame with its attributes
        JFrame f = new JFrame(); 
        f.setVisible(true);
        f.setSize(1000, 1000);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        //add the app
        f.add(app);

   }
}