package cat.esteve.autotiling.input;

import cat.esteve.autotiling.main.Main;

import javax.swing.event.MouseInputListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseInput implements MouseInputListener, MouseWheelListener {
    private Main main;

    public MouseInput(Main main) {
        this.main = main;
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        this.main.mousePressed(mouseEvent);
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        this.main.mouseReleassed(mouseEvent);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        this.main.mouseDragged(mouseEvent);
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        this.main.mouseMoved(mouseEvent);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        //this.main.mouseWheelMoved(mouseWheelEvent);
    }
}
