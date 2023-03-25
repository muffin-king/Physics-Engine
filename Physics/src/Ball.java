package Physics;

import java.awt.*;
import java.util.Random;

public class Ball extends PhysicsObject // Keep in mind, this is a representation of a 3d sphere in a 2d space, the x and y coordinates have no z component because it is on a flat plane with no incline
{
    // minimum speed of the ActiveBall (to prevent it from moving forever)
    public static final double MIN_SPEED = 0.01;
    private double mass; // Mass of the ActiveBall
    private Color color; // Default color of ActiveBall
    private double coFric; // Coefficient of friction and gravity constant
    private static final Random RANDOM = new Random(); // Random object for randomizing ActiveBall properties
    private static final double MIN = 156, MAX = 170; // Minimum and maximum mass of the ActiveBall

    public Ball(double x, double y, double radius, PhysicsEnvironment environment)
    {
        super(x, y, radius, radius, environment);
        coFric = 0.03;
        // Randomize the mass of the ActiveBall between (min) and (max)
        this.mass = RANDOM.nextDouble(MAX - MIN) + MIN;
        color = Color.WHITE;
    }

    public void draw(Graphics g) // Draws the ActiveBall
    {
            g.fillOval((int)this.x, (int)this.y, (int)this.width *2, (int)this.width *2); // Draws the ActiveBall at its given place

    }
    
    public boolean collidesWith(Ball other) // Checks if the ActiveBall collides with another ActiveBall (other)
    {
        double distance = this.getLocation().distance(other.getLocation());
        return distance <= this.width + other.width;
    }

    public boolean collidesWithWall() // Checks if the ActiveBall collides with the wall
    {
        return this.x + this.dx <= 650 || this.x + this.dx >= (getEnviron().getWidth()-this.width *2)+150 || this.y + this.dy <= 0 || this.y + this.dy >= this.getEnviron().getHeight()-this.width *2;
    }

    public void collideWall() // Responds to collision with wall
    {
        if(this.x + this.dx <= 150 || this.x + this.dx >= (getEnviron().getWidth()-this.width *2)+150)
            this.dx *= -1;
        if(this.y + this.dy <= 0 || this.y + this.dy >= getEnviron().getHeight()-this.width *2)
            this.dy *= -1;
    }
    
    public void update() // Updates the position of the ActiveBall and account for friction
    {
        double frictionForce = getEnviron().getGravity() * this.coFric; // Friction force
        double frictionVelocityX = (frictionForce / this.mass) * Math.abs(this.dx); // Friction velocity in the x direction
        double frictionVelocityY = (frictionForce / this.mass) * Math.abs(this.dy); // Friction velocity in the y direction

        this.dx += (this.dx != 0)? ((this.dx > 0) ? -frictionVelocityX : frictionVelocityX) : 0; // Updates the x velocity of the ActiveBall
        this.dy += (this.dy != 0)? ((this.dy > 0) ? -frictionVelocityY : frictionVelocityY) : 0; // Updates the y velocity of the ActiveBall

        if(Math.abs(this.dx) < MIN_SPEED && Math.abs(this.dy) < MIN_SPEED) // Sets the x and y velocities to 0 if they are both less than the minimum speed
        {
            this.dx = 0;
            this.dy = 0;
        }

        this.x += this.dx; // Updates the x position of the ActiveBall
        this.y += this.dy; // Updates the y position of the ActiveBall
    }
    
    public void collide(Ball other) // Responds to collision with another ActiveBall (other)
    {
        double distanceX = other.x - this.x; // Distance between the x coordinates of the ActiveBalls
        double distanceY = other.y - this.y; // Distance between the y coordinates of the ActiveBalls

        // Calculate the angle between the ActiveBall's centers
        double angle = Math.atan2(distanceY, distanceX);

        // Calculate the magnitude of each ActiveBall's velocity vector
        double velocity1 = Math.sqrt(Math.pow(this.dx, 2) + Math.pow(this.dy, 2));
        double velocity2 = Math.sqrt(Math.pow(other.dx, 2) + Math.pow(other.dy, 2));

        // Calculate the direction of each ActiveBall's velocity vector
        double direction1 = Math.atan2(this.dy, this.dx);
        double direction2 = Math.atan2(other.dy, other.dx);

        // Calculate the velocity components of each ActiveBall in the direction of the collision
        double velocityX1 = velocity1 * Math.cos(direction1 - angle);
        double velocityY1 = velocity1 * Math.sin(direction1 - angle);
        double velocityX2 = velocity2 * Math.cos(direction2 - angle);
        double velocityY2 = velocity2 * Math.sin(direction2 - angle);

        // Calculate the new velocity components of each ActiveBall after the collision
        double newVelocityX1 = ((velocityX1 * (this.mass - other.mass)) + ((2 * other.mass) * velocityX2)) / (this.mass + other.mass);
        double newVelocityX2 = ((velocityX2 * (other.mass - this.mass)) + ((2 * this.mass) * velocityX1)) / (this.mass + other.mass);
        double newVelocityY1 = velocityY1;
        double newVelocityY2 = velocityY2;

        // Update the velocities of the balls
        this.dx = Math.cos(angle) * newVelocityX1 + Math.cos(angle + Math.PI / 2) * newVelocityY1;
        this.dy = Math.sin(angle) * newVelocityX1 + Math.sin(angle + Math.PI / 2) * newVelocityY1;
        other.dx = Math.cos(angle) * newVelocityX2 + Math.cos(angle + Math.PI / 2) * newVelocityY2;
        other.dy = Math.sin(angle) * newVelocityX2 + Math.sin(angle + Math.PI / 2) * newVelocityY2;

        // Move the balls so they are no longer intersecting
        double overlap = this.width + other.width - this.getLocation().distance(other.getLocation());
        this.x -= overlap / 2 * Math.cos(angle);
        this.y -= overlap / 2 * Math.sin(angle);
        other.x += overlap / 2 * Math.cos(angle);
        other.y += overlap / 2 * Math.sin(angle);
    }

    public double getMass() // Returns mass
    {
        return mass;
    }

    public void setMass(double mass) // Overrides the mass with the value passed into the method
    {
        this.mass = mass;
    }

    public Color getColor() // Returns color
    {
        return color;
    }

    public void setColor(Color color) // Overrides the color with the value passed into the method
    {
        this.color = color;
    }

    public double getCoFric() // Returns coefficient of friction
    {
        return coFric;
    }

    public void setCoFric(double coFric) // Overrides the coefficient of friction with the value passed into the method
    {
        this.coFric = coFric;
    }
}