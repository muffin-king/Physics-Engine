package Physics;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;

public abstract class PhysicsObject {
    protected double x, y;
    protected double dx, dy;
    protected double width, height;
    private final PhysicsEnvironment environ;
    private final long ID;
    private static final Random RNG = new Random();

    public PhysicsObject(double x, double y, double width, double height, PhysicsEnvironment environ) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.environ = environ;
        dx = 0;
        dy = 0;
        ID = RNG.nextLong();
    }

    public final double getX() {
        return x;
    }

    public final void setX(double x) {
        this.x = x;
    }

    public final double getY() {
        return y;
    }

    public final void setY(double y) {
        this.y = y;
    }

    public final double getDx() {
        return dx;
    }

    public final void setDx(double dx) {
        this.dx = dx;
    }

    public final double getDy() {
        return dy;
    }

    public final void setDy(double dy) {
        this.dy = dy;
    }

    public long getID() {
        return ID;
    }

    public final Point2D getLocation() {
        return new Point2D.Double(x, y);
    }

    public final double getWidth() {
        return width;
    }

    public final void setWidth(double width) {
        this.width = width;
    }

    public final double getHeight() {
        return height;
    }

    public final void setHeight(double height) {
        this.height = height;
    }

    public PhysicsEnvironment getEnviron() {
        return environ;
    }

    public Rectangle2D getBox() {
        return new Rectangle2D.Double(x, y, width, height);
    }

    public boolean isColliding(PhysicsObject object) {
        return getBox().intersects(object.getBox());
    }

    public abstract void update();

    public abstract void draw(Graphics g);
}
