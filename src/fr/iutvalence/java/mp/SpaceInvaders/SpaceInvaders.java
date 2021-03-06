package fr.iutvalence.java.mp.SpaceInvaders;

import java.util.Random;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 * class SpaceInvaders : A new game
 * @author thevenim
 */
public class SpaceInvaders
{



    /**
     * To control our space craft with q and d
     */
    private Scanner Request = new Scanner(System.in);

    /**
     * to save the request about movement
     */
    private char requestSlot;

    /**
     * size of the grid ( 15 per 15 )
     */
    public final static int CONSTANT_GRID = 15;

    /**
     * constant which represent the Space craft
     */
    public final static int CONSTANT_SPACECRAFT = 7;

    /**
     * constant which represent the Enemy
     */
    public final static int CONSTANT_ENEMY = 6;

    /**
     * constant which represent the Fire
     */
    public final static int CONSTANT_FIRE = 1;

    /**
     * constant which represent the Void
     */
    public final static int CONSTANT_EMPTY = 0;

    /**
     * constant which represent where the enemy is down
     */
    public final static int CONSTANT_ENEMY_DOWN = 9;

    /**
     * Declaration of the place of Space craft on the grid at the beginning (Line)
     */
    public final static int CONSTANT_PLACE_SPACECRAFT_X = 14;

    /**
     * Declaration of the place of Spacecraft on the grid at he beginning (Column)
     */
    public final static int CONSTANT_PLACE_SPACECRAFT_Y = 7;

    /**
     * Declaration of the Grid to put our elements in it, like Spacecraft, enemies etc ..
     */
    private int[][] grid;

    /**
     * declaration of empty grid to erase a line
     */
    private int[] gridEmpty;

    /**
     * Declaration of the current enemies line
     */
    private int currentEnemiesLine;

    /**
     * Declaration of enemies line limit
     */
    private int nextLineAfterEnemies;

    /**
     * Position Y of the Space Craft during the game
     */
    private int POS_Y;

    /**
     * Position X of the fire during the game
     */
    private int POS_X_Fire;

    // TODO (fix) rewrite comment, say how is the game once created
    /**
     * Declaration of the SpaceInvaders constructor
     */
    public SpaceInvaders()
    {

        this.grid = new int [CONSTANT_GRID][CONSTANT_GRID];
        this.currentEnemiesLine = 0;
        this.nextLineAfterEnemies = 3;
        this.gridEmpty = new int [CONSTANT_GRID];
        this.fillGrid();
        this.putEnemies();
        this.POS_Y=CONSTANT_PLACE_SPACECRAFT_Y;
        this.POS_X_Fire=CONSTANT_PLACE_SPACECRAFT_X-1;
        for(int x = 0; x<15; x++)
        {
            this.gridEmpty[x] = CONSTANT_EMPTY;
        }

    }

    /**
     * Method to place elements in the grid cell
     */
    // TODO (fixed) rename method
    private void fillGrid()
    {
        for(int x=0; x<15; x++)
        {
            for (int y=0; y<15; y++)
            {
                this.grid[x][y]= CONSTANT_EMPTY;
            }
        }
        this.grid[CONSTANT_PLACE_SPACECRAFT_X][CONSTANT_PLACE_SPACECRAFT_Y]=CONSTANT_SPACECRAFT;
    }

    /**
     * Method to display the grid with elements
     */
    private void displayGrid()
    {
        for(int x=0; x<15; x++)
        {
            for(int y = 0; y<15; y++)
            {
                if (this.grid[x][y]==CONSTANT_EMPTY ) /* if we have some empty box, we put V */
                {
                    System.out.print("V");
                }
                else
                {
                    System.out.print(this.grid[x][y]); // display the grid normally //
                }
            }
            System.out.println();
        }
    }
    /**
     * to place the enemies in the grid
     */
    private void putEnemies()
    {
        for(int x=this.currentEnemiesLine; x<this.nextLineAfterEnemies; x++)
        {
            for(int y = 0; y<13; y++)
            {
                this.grid[x][y]=CONSTANT_ENEMY;
            }
        }
    }

    /**
     * Method to move elements to the left
     */
    private void left()
    {
        for(int x=this.currentEnemiesLine; x<this.nextLineAfterEnemies; x++)
        {
            for(int y = 0; y<13; y++)
            {
                this.grid[x][y]=this.grid[x][y+1];
            }
            this.grid[x][13]=CONSTANT_EMPTY;
        }
    }

    /**
     * Method to move elements to the center from the left
     */
    private void centerFromLeft()
    {
        for(int x=this.currentEnemiesLine; x<this.nextLineAfterEnemies; x++)
        {
            for(int y = 14; y>0; y--)
            {
                this.grid[x][y]=this.grid[x][y-1];
            }
            this.grid[x][0]=CONSTANT_EMPTY;
        }
    }

    /**
     * Method to move elements to the center from the right
     */
    private void centerFromRight()
    {
        for(int x=this.currentEnemiesLine; x<this.nextLineAfterEnemies; x++)
        {
            for(int y = 1; y<14; y++)
            {
                this.grid[x][y]=this.grid[x][y+1];
            }
            this.grid[x][14]=CONSTANT_EMPTY;
        }
    }

    /**
     * Method to move elements to the right
     */
    private void right() 
    {
        for(int x=this.currentEnemiesLine; x<this.nextLineAfterEnemies; x++)
        {
            for(int y = 14; y>1; y--)
            {
                this.grid[x][y]=this.grid[x][y-1];
            }
            this.grid[x][1]=CONSTANT_EMPTY;
        }
    }

    /**
     * Method to down : erase all enemies and create enemies on down line
     */
    private void changeLine()
    {
        if (this.nextLineAfterEnemies == 14)
        {
            int x=this.currentEnemiesLine; 
            this.grid[x+3] = this.grid[x+2];
            this.grid[x+2] = this.grid[x+1];
            this.grid[x+1] = this.grid[x];
            this.grid[x] = this.gridEmpty;
            this.displayGrid();

            JOptionPane.showMessageDialog(null, "GAME OVER", "", JOptionPane.WARNING_MESSAGE);

            while(true);
        }
        else
        {
            
            int x=this.currentEnemiesLine; 
            this.grid[x+3] = this.grid[x+2];
            this.grid[x+2] = this.grid[x+1];
            this.grid[x+1] = this.grid[x];
            this.grid[x] = this.gridEmpty;
            this.currentEnemiesLine ++;
            this.nextLineAfterEnemies ++;
        }
    }

    /**
     * Method to shoot enemies
     */
    private void Shoot()
    {
        int POS_Y_Fire = this.POS_Y; // To save the column of the fire



        {
            while(this.grid[this.POS_X_Fire][POS_Y_Fire]==CONSTANT_EMPTY && this.POS_X_Fire>this.currentEnemiesLine-1)
            {
                this.grid[this.POS_X_Fire][POS_Y_Fire]=CONSTANT_FIRE;
                this.POS_X_Fire--;
                this.displayGrid();
                this.grid[this.POS_X_Fire+1][POS_Y_Fire]=CONSTANT_EMPTY;                
                this.pauseShoot();
            }

            if(this.grid[this.POS_X_Fire][POS_Y_Fire]==CONSTANT_ENEMY)
            {
                this.grid[this.POS_X_Fire][POS_Y_Fire]=CONSTANT_EMPTY;
            }
            this.POS_X_Fire=CONSTANT_PLACE_SPACECRAFT_X-1;
        }
    }
    /**
     * To ask if you want to move and where or shoot
     */
    public void  askToMoveOrShoot()
    {
        do
        {
            // System.out.println("Left or Right ? (q or d) or shoot ? (z) ");
            this.requestSlot = this.Request.nextLine().charAt(0); // " charAt(0)" to take the char in the zero position
        }while(this.requestSlot!='q' && this.requestSlot!='d' && this.requestSlot !='z');

        if (this.requestSlot =='z')
        {
            this.Shoot();
        }
        else
        {
            this.Movement();
        }

    }

    /**
     * Method to move Space Craft
     */
    private void Movement()
    {
        if (this.requestSlot == 'q')
        {
            if (this.POS_Y == 0)
            {
                this.grid[CONSTANT_PLACE_SPACECRAFT_X][this.POS_Y]=CONSTANT_SPACECRAFT;

            }
            else
            {
                this.grid[CONSTANT_PLACE_SPACECRAFT_X][this.POS_Y-1]=CONSTANT_SPACECRAFT;
                this.grid[CONSTANT_PLACE_SPACECRAFT_X][this.POS_Y]=CONSTANT_EMPTY;
                this.POS_Y--;
            }

        }
        else if(this.requestSlot == 'd')
        {
            if (this.POS_Y == 14)
            {
                this.grid[CONSTANT_PLACE_SPACECRAFT_X][this.POS_Y]=CONSTANT_SPACECRAFT;

            }
            else
            {
                this.grid[CONSTANT_PLACE_SPACECRAFT_X][this.POS_Y+1]=CONSTANT_SPACECRAFT;
                this.grid[CONSTANT_PLACE_SPACECRAFT_X][this.POS_Y]=CONSTANT_EMPTY;
                this.POS_Y++;
            }
        }
    }

    /**
     * Method to pause
     */
    private void pause()
    {
        try
        {
            Thread.sleep(1500); // wait 1,5s //
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Method to pause the shoot
     */
    private void pauseShoot()
    {
        try
        {
            Thread.sleep(150); // wait 0,15sec
        }
        catch (InterruptedException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    /**
     * Method to play
     */
    public void play()
    {
        // TODO (fixed) go on with realistic game algorithm
        // where the player is asked to move or shoot ?

        this.displayGrid();
        //this.askToMoveOrShoot();
        this.pause();
        this.centerFromLeft();
        this.displayGrid();
        //this.askToMoveOrShoot();
        this.pause();
        this.right();
        this.displayGrid();
        //this.askToMoveOrShoot();
        this.pause();
        this.centerFromRight();
        this.displayGrid();
        //this.askToMoveOrShoot();
        this.pause();
        this.left();
        this.displayGrid();
        //this.askToMoveOrShoot();
        this.pause();
        this.changeLine();
    }
}