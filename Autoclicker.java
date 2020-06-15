package clicker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class Autoclicker {
    private Robot robot;
    private int delay;

    public Autoclicker() {
        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
        delay = 250;
    }

    public void clickmouse(int button) {
        try {
            robot.mousePress(button);
            robot.delay(200);
            robot.mouseRelease(button);
            robot.delay(delay);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setDelay(int ms) {
        this.delay = ms;
    }
}

class ClickerLogic extends Thread {
    private boolean running = false;
    private boolean program_running = true;
    private int delay = 250;
    private Autoclicker clicker;

    @Override
    public void run() {
        while (program_running) {
            while (running) {
                clicker = new Autoclicker();
                //clicker.setDelay();//default: 250
                clicker.clickmouse(MouseEvent.BUTTON1_MASK);
            }
        }
    }

    public void start_clicking() {
        running = true;
    }

    public void stop_clicking() {
        running = false;
    }

    public void exit_clicking() {
        stop_clicking();
        program_running = false;
    }

}

class Form1 extends JFrame {
    private JLabel lblStatus, lblInfo;
    private JPanel panel_color;
    private JButton btnClose, btnStart, btnStop;
    private ClickerLogic hilo1;

    public Form1() {
        setLayout(null);
        setTitle("AutoClicker");
        hilo1 = new ClickerLogic();
        initComponents();
        hilo1.start();
    }

    private void initComponents() {
        lblStatus = new JLabel("Status: ");
        lblStatus.setBounds(20, 5, 100, 30);
        add(lblStatus);

        panel_color = new JPanel();
        panel_color.setBounds(75, 12, 30, 20);
        panel_color.setBackground(Color.RED);
        add(panel_color);

        lblInfo = new JLabel("OFF");
        lblInfo.setBounds(115, 5, 100, 30);
        add(lblInfo);

        btnClose = new JButton("Close");
        btnClose.setBounds(45, 60, 90, 30);
        add(btnClose);
        btnClose.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnClose_click(e);
            }
        });

        btnStart = new JButton("Start");
        btnStart.setBounds(145, 60, 90, 30);
        add(btnStart);
        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStart_click(e);
            }
        });

        btnStop = new JButton("Stop");
        btnStop.setBounds(245, 60, 90, 30);
        add(btnStop);
        btnStop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnStop_click(e);
            }
        });
    }

    private void btnClose_click(ActionEvent e) {
        try {
            hilo1.exit_clicking();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    private void btnStart_click(ActionEvent e) {
        try {
            hilo1.start_clicking();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            lblInfo.setText("ON");
            panel_color.setBackground(Color.GREEN);
        }
    }

    private void btnStop_click(ActionEvent e) {
        try {
            hilo1.stop_clicking();
        } catch (Exception err) {
            err.printStackTrace();
        } finally {
            lblInfo.setText("OFF");
            panel_color.setBackground(Color.RED);
        }
    }

    public static void main(String[] args) {
        Form1 app = new Form1();
        app.setSize(new Dimension(400, 250));
        app.setMinimumSize(new Dimension(400, 250));
        app.setAlwaysOnTop(true);
        app.setResizable(false);
        app.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        app.setVisible(true);
        app.setLocationRelativeTo(null);
    }
}