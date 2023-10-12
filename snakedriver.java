import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.util.concurrent.TimeUnit;
import kareltherobot.*;

public class snakedriver extends JFrame implements KeyListener,Directions {
    JLabel label;
    head aut;
    WorldBuilder wb;
    boolean cont=true;
    public snakedriver(String s) throws Exception {
        super(s);
        JPanel p = new JPanel();
        label = new JLabel("Game Ongoing!");
        p.add(label);
        add(p);
        addKeyListener(this);
        setSize(200, 100);
        setVisible(true);
        worldbuilder wb=new worldbuilder();
        wb.setDimensions(10,10);
        wb.createRectangle(2,5,7,10);
        //wb.createWithDoor("test");
        World.readWorld("test.wld");
        World.setVisible(true); World.setDelay(0);
        aut=new head(2,5,North,0);
        java.util.Timer t = new java.util.Timer();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                if (!cont) {return;}
                if (!aut.goForward()) {
                    cont=false;
                }
            }
        }, 300, 500);
    }
    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {}
    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            aut.turnEast();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            aut.turnWest();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            aut.turnSouth();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            aut.turnNorth();
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (!aut.goForward()) {
                System.out.println("bad");
            }
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            System.out.println(aut.nextToABeeper());
        }
    }
    public static void main(String[] args) throws Exception {
        worldbuilder wb=new worldbuilder();
        wb.createRectangle(2, 2, 8, 8);
        wb.placeBeeper(3, 3, 1);
        wb.create("test");
        new snakedriver("Key Listener Tester");
    }
}