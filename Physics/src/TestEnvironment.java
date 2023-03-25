package Physics;

import java.awt.*;
import java.util.Random;

public class TestEnvironment extends PhysicsEnvironment {
    private static final Random RANDOM = new Random();
    public TestEnvironment(double gravity, int w, int h) {
        super(gravity, w, h);
        start();
        for(int i = 0; i < 100; i++) {
            Ball ball = new Ball(RANDOM.nextInt(getWidth()), RANDOM.nextInt(getHeight()), 10, this);
            ball.setDx(RANDOM.nextDouble(4)-2);
            ball.setDy(RANDOM.nextDouble(4)-2);
            addObject(ball);
        }
        setBackground(Color.WHITE);
    }

    public static void main(String[] args) {
        GenericFrame.newFrame("Environment", new TestEnvironment(9.8, 800, 800));
    }
}
