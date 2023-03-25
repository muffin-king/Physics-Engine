package Physics;

import betterthreadpool.ScheduledThreadPool;
import betterthreadpool.ThreadPoolTask;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public abstract class PhysicsEnvironment extends JPanel {
    private final Map<Long, PhysicsObject> objects;
    private ScheduledThreadPool executor;
    private ThreadPoolTask task;
    private double gravity;
    private int w, h;
    private final EventListenerList listeners;

    public PhysicsEnvironment(double gravity, int w, int h) {
        this.gravity = gravity;
        this.w = w;
        this.h = h;
        objects = new HashMap<>();
        listeners = new EventListenerList();
        setPreferredSize(new Dimension(w, h));
        setBackground(Color.BLACK);
    }

    public void addUpdateListener(ActionListener listener) {
        listeners.add(ActionListener.class, listener);
    }

    private void fireUpdateListeners() {
        for(ActionListener listener : listeners.getListeners(ActionListener.class))
            listener.actionPerformed(new ActionEvent(this, 1, null));
    }

    public void start() {
        executor = new ScheduledThreadPool(4);
        task = executor.scheduleTask(new UpdateTask(), 1, 0, TimeUnit.MILLISECONDS);
    }

    public void terminate() {
        task.cancel();
        executor.close();
    }

    public void addObject(PhysicsObject object) {
        objects.put(object.getID(), object);
    }

    public void removeObject(PhysicsObject object) {
        objects.remove(object.getID());
    }

    public final double getGravity() {
        return gravity;
    }

    public final void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public final int getWidth() {
        return w;
    }

    public final void setWidth(int w) {
        this.w = w;
    }

    public final int getHeight() {
        return h;
    }

    public final void setHeight(int h) {
        this.h = h;
    }

    private void drawObjects(Graphics g) {
        for(PhysicsObject object : objects.values()) {
            object.draw(g);
        }
    }

    public final void updateObjects() {
        for(PhysicsObject object : objects.values()) {
            object.update();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawObjects(g);
    }

    private class UpdateTask implements Runnable {
        public void run() {
            updateObjects();
            fireUpdateListeners();
            repaint();
        }
    }
}
