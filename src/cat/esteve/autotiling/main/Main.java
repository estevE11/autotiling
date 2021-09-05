package cat.esteve.autotiling.main;

import cat.esteve.autotiling.gfx.MainCanvas;
import cat.esteve.autotiling.input.MouseInput;
import cat.esteve.autotiling.level.Level;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class Main implements Runnable {
    private int WIDTH = 1280, HEIGHT = 720;
    private String frame_name = "Autotiling";
    private boolean frame_resizeable = false;

    private JFrame frame;
    private MainCanvas canvas;
    private MouseInput mouse;
    private Level level;

    private int mbtn = -1;

    private int fps, tps;

    private void start_thread(String thread_name) {
        Thread thread = new Thread(this, thread_name);
        thread.start();
    }

    private void init() {
        this.level = new Level();

        this.mouse = new MouseInput(this);
        this.canvas.addMouseListener(this.mouse);
        this.canvas.addMouseMotionListener(this.mouse);
    }

    public  void run() {
        this.canvas.requestFocus();
        init();
        int fps = 0;
        long lastTime = System.nanoTime();
        double unprocessed = 0;
        double nsPerTick = 1000000000.0 / 60;
        long lastTimer1 = System.currentTimeMillis();

        while (true) {
            long now = System.nanoTime();
            unprocessed += (now - lastTime) / nsPerTick;
            lastTime = now;
            while (unprocessed >= 1) {
                tps++;
                update();
                unprocessed -= 1;
            }

            fps++;
            render();

            if (System.currentTimeMillis() - lastTimer1 > 1000) {
                lastTimer1 += 1000;
                this.frame.setTitle("FPS: " + fps + ", TPS: " + tps);
                fps = 0;
                tps = 0;
            }
        }    }

    private void update() {
        this.level.update();
    }

    private void render() {
        if(!canvas.start()) return;
        canvas.clear(new Color(33, 38, 63));

        this.level.render(this.canvas);

        canvas.end();
    }

    public void mousePressed(MouseEvent evt) {
        this.mbtn = evt.getButton();
        this.level.mousePressed(evt.getX(), evt.getY(), this.mbtn);
    }

    public void mouseReleassed(MouseEvent evt) {
        this.mbtn = -1;
        this.level.mousePressed(evt.getX(), evt.getY(), this.mbtn);
    }

    public void mouseDragged(MouseEvent evt) {
        this.level.mouseDragged(evt.getX(), evt.getY(), this.mbtn);
    }

    public void mouseMoved(MouseEvent evt) {
        this.level.mouseMoved(evt.getX(), evt.getY(), this.mbtn);
    }

    public void start() {
        this.frame = new JFrame(this.frame_name);
        this.canvas = new MainCanvas();

        this.canvas.setSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.frame.setPreferredSize(new Dimension(this.WIDTH, this.HEIGHT));
        this.frame.setMinimumSize(new Dimension(this.WIDTH, this.HEIGHT));

        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(this.frame_resizeable);
        this.frame.setVisible(true);

        this.frame.add(canvas);

        this.frame.requestFocus();

        this.start_thread("Main Thread");
    }

}
