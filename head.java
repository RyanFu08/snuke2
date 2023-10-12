import java.util.ArrayList;
import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

import kareltherobot.World;
public class head extends automata {
    ArrayList<automata> seg;
    public head(int x1, int y1, Direction dir, int numBeepers) {
        super(x1, y1, dir, numBeepers);
        seg=new ArrayList<automata>();
        seg.add(this);
    }
    public void turnleft() {
        this.turnLeft();
    }
    public void turnright() {
        this.turnleft();
        this.turnleft();
        this.turnleft();
    }
    public boolean turnNorth() {
        if (this.facingEast() || this.facingWest()) {this.faceNorth(); return true;}
        return false;
    }
    public boolean turnSouth() {
        if (this.facingEast() || this.facingWest()) {this.faceSouth(); return true;}
        return false;
    }
    public boolean turnEast() {
        if (this.facingNorth() || this.facingSouth()) {this.faceEast(); return true;}
        return false;
    }
    public boolean turnWest() {
        if (this.facingNorth() || this.facingSouth()) {this.faceWest(); return true;}
        return false;
    }
    public boolean frontClear() {
        if (!this.frontIsClear()) {return false;}
        this.move();
        if (this.nextToARobot()) {return false;}
        this.turnleft(); this.turnleft(); this.move();
        this.turnleft(); this.turnleft();
        return true;
    }
    public int randInt(int lo,int hi) {
        return ThreadLocalRandom.current().nextInt(lo,++hi);
    }
    public boolean goForward() {
        if (!this.frontClear()) {return false;}
        this.move1();
        if (this.nextToABeeper()) {
            this.pickBeeper();
            this.goBackward();
            seg.add(1,new automata(this.x, this.y, this.dir, 0));
            this.move1();
            World.placeBeepers(randInt(2,8), randInt(2, 8), 1);
        } else {
            this.goBackward();
            for (int i=seg.size()-1; i>0; i--) {
                seg.get(i).goToPosition(seg.get(i-1).x, seg.get(i-1).y);
            }
            this.move1();
        }
        return true;
    }
}
