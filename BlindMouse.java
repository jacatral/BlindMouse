import java.applet.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.lang.String;

public class BlindMouse extends Applet implements KeyListener
{
    Image Mouse[] = new Image[4];
    Image tile[] = new Image[4];
    Image Fog;
    int m = 0, Score = 0, Moves = 0, lvl = 0, mplvl = lvl%3;
    int c = 0, d = 0;
    int mx = 0, my = 0, dx = 0, dy = 0;
    int length = 10, width = 10;
    int ViewMap [][] = new int[length][width];
    int level[][][] = new int[length][width][3];
    Label lblScore, lblMoves, lblLevel;
    
    public void init ()
    {
        setLayout(null);
        this.setFocusable(true);
        this.addKeyListener(this);
        
        for (int z = 0; z < 4; z++)
        {
            tile[z] = getImage(getCodeBase(),z+".png");
        }
        for (int q = 0; q < 4; q++)
        {
            Mouse[q] = getImage(getCodeBase(),"mouse"+q+".jpg");
        }
        Fog = getImage(getCodeBase(),"fog.png");
        
        for (int w = 0; w < 3; w++)
        {
            for (int x = 0; x < length; x++)
            {
                for (int y = 0; y < width; y++)
                {
                    level[x][y][lvl] = 0;
                    ViewMap[x][y] = 0;
                }
            }
        }
        
        lblLevel = new Label("Level: "+(lvl+1));
        add(lblLevel);
        lblLevel.setBounds(0,(width*50)+25,150,25);
        
        lblScore = new Label("Score: "+Score);
        add(lblScore);
        lblScore.setBounds(0,(width*50)+50,150,25);
        
        lblMoves = new Label("Number of Moves: "+Moves);
        add(lblMoves);
        lblMoves.setBounds(0,(width*50)+75,150,25);
        
        LightArea();
        
        for (int s = 0; s < 3; s++)
        {
            try
            {
                BufferedReader filein;
                filein = new BufferedReader (new FileReader("GridDATA"+(s+1)+".txt"));
                for (int i = 0; i < 10; i++)
                {
                    String inputLine = filein.readLine();
                    StringTokenizer st = new StringTokenizer(inputLine, " ");
                
                    for (int j = 0; j < 10; j++)
                    {
                        String eachNumber = st.nextToken();
                        level[j][i][s]= Integer.parseInt(eachNumber);
                    }
                }
                filein.close();
            }
            catch(IOException catche)
            {
            }
        }
    }    
    
    public void keyPressed (KeyEvent e)
    {
        int code = e.getKeyCode();
        if (code == KeyEvent.VK_LEFT)
        {//Left Arrow Key
            m = 1;
            if (CheckLR(mx-1))
            {
                dx = mx - 1;
                if (level[dx][my][mplvl]==0)
                {
                    mx = dx;
                    Moves ++;
                    LightArea();
                }
                else if (level[dx][my][mplvl]==2)
                {
                    level[dx][my][mplvl]=0;
                    mx = dx;
                    Score ++;
                    Moves ++;
                    LightArea();    
                }
                else if (level[dx][my][mplvl]==3)
                {
                    lvl++;
                    mplvl = lvl%3;
                    mx = 0;
                    my = 0;
                    Moves ++;
                    LightArea();
                }
            }
            
        }
        else if (code == KeyEvent.VK_RIGHT)
        {//Right Arrow Key
            m = 3;
            if (CheckLR(mx+1))
            {
                dx = mx + 1;
                if (level[dx][my][mplvl]==0)
                {
                    mx = dx;
                    Moves ++;
                    LightArea();
                }
                else if (level[dx][my][mplvl]==2)
                {
                    level[dx][my][mplvl]=0;
                    mx = dx;
                    Score ++;
                    Moves ++;
                    LightArea();
                }
                else if (level[dx][my][mplvl]==3)
                {
                    lvl++;
                    mplvl = lvl%3;
                    mx = 0;
                    my = 0;
                    Moves ++;
                    LightArea();
                }
            }
        }
        else if (code == KeyEvent.VK_DOWN)
        {//Down Arrow Key
            m = 0;
            if (CheckTB(my+1))
            {
                dy = my + 1;
                if (level[mx][dy][mplvl]==0)
                {
                    my = dy;
                    Moves ++;
                    LightArea();
                }
                else if (level[mx][dy][mplvl]==2)
                {
                    level[mx][dy][mplvl]=0;
                    my = dy;
                    Score ++;
                    Moves ++;
                    LightArea();
                }
                else if (level[mx][dy][mplvl]==3)
                {
                    lvl++;
                    mplvl = lvl%3;
                    mx = 0;
                    my = 0;
                    Moves ++;
                    LightArea();
                }
            }
        }
        else if (code == KeyEvent.VK_UP)
        {//Up Arrow Key
            m = 2;
            if (CheckTB(my-1))
            {
                dy = my - 1;
                if (level[mx][dy][mplvl]==0)
                {
                    my = dy;
                    Moves ++;
                    LightArea();
                }
                else if (level[mx][dy][mplvl]==2)
                {
                    level[mx][dy][mplvl]=0;
                    my = dy;
                    Score ++;
                    Moves ++;
                    LightArea();
                }
                else if (level[mx][dy][mplvl]==3)
                {
                    lvl++;
                    mplvl = lvl%3;
                    mx = 0;
                    my = 0;
                    Moves ++;
                    LightArea();
                }
            }
        }
        
        lblLevel.setText("Level: "+(lvl+1));
        lblScore.setText("Score: "+Score);
        lblMoves.setText("Number of Moves: " +Moves);
        repaint();
    }
    
    public boolean LightArea()
    {
        //Cover entire area
        for (int x = 0; x < length; x++)
        {
            for (int y = 0; y < width; y++)
            {
                ViewMap[x][y] = 0;
            }
        }
        //Make surrounding area around mouse "light" up via array data
        for (int s = -1; s <= 1; s++)
        {
            if ((CheckLR(mx+s))&&(CheckTB(my-1)))
            {
                c = mx+s;
                d = my-1;
                ViewMap[c][d]=1;
            }
            if (CheckLR(mx+s))
            {
                c = mx+s;
                d = my;
                ViewMap[c][d]=1;
                
            }
            if ((CheckLR(mx+s))&&(CheckTB(my+1)))
            {
                c = mx+s;
                d = my+1;
                ViewMap[c][d]=1;
            }
        }
        return true;
    }
    public boolean CheckLR(int e)
    {
    //Checks if the x boundries is passed (i.e. x = -1, 11)
        if (e < 0)
        {
            return false;
        }
        else if (e > length-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public boolean CheckTB(int e)
    {
    //Check if the y boundry is passed (i.e. y = -2, 12)
        if (e < 0)
        {
            return false;
        }
        else if (e > length-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    
    public void paint(Graphics g)
    {   
        //Draw level first
        for (int x = 0; x < length; x++)
        {
            for (int y = 0; y < width; y++)
            {
                g.drawImage(tile[level[x][y][mplvl]],(x*50),(y*50),50,50,this);
            }
        }
        //Draw fog over the map (unless it surrounds the character
        for (int x = 0; x < length; x++)
        {
            for (int y = 0; y < width; y++)
            {
                if (ViewMap[x][y]==0)
                {
                    g.drawImage(Fog,(x*50),(y*50),50,50,this);
                }
            }
        }
        g.drawImage(Mouse[m],(mx*50),(my*50),50,50,this);
    }
    
    public void actionPerformed (ActionEvent e)
    {
        String go = e.getActionCommand();
        repaint();
    }
    public void run()
    {
    }    
    public void keyTyped (KeyEvent e)
    {    
    }
    public void keyReleased (KeyEvent e)
    {
    }
    
}
