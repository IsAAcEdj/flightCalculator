import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import java.awt.Font;


public class Application {

    static JFrame frame;
    static JLabel projectile;
    static JPanel panel;
    static JSlider angle1;
    static JSlider velocity1;
    static JTextArea angle;
    static JTextArea velocity;
    static JButton button;
    static JTextArea velocityArea;
    static JTextArea angleArea;
    static JTextArea timerArea;
    static JTextArea bounceArea;
    static double[] bounceTimes = new double[2];
    static double nX = 0;
    static double nY = 0;


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createGUI();
        });
    }


    public static void createGUI() {

        frame = new JFrame();
        frame.setBounds(100, 100, 1200, 600);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        projectile = new JLabel("//");
        projectile.setBounds(30, 300, 10, 10);

        angle = new JTextArea();
        velocity = new JTextArea();
        angle.setBounds(500, 500, 100, 20);
        velocity.setBounds(650, 500, 100, 20);
        angle.setVisible(false);
        velocity.setVisible(false);
        angle.setText("45");
        velocity.setText("25");

        angle1 = new JSlider(0,90);
        velocity1 = new JSlider(0, 80);
        angle1.setBounds(500,500,100,20);
        velocity1.setBounds(650,500,100,20);
        angle1.setVisible(true);
        velocity1.setVisible(true);

        timerArea = new JTextArea();
        timerArea.setBounds(30, 30,40, 20);
        timerArea.setVisible(true);
        timerArea.setEditable(false);
        timerArea.setFocusable(false);

        bounceArea = new JTextArea();
        bounceArea.setBounds(30, 60, 40, 40);
        bounceArea.setVisible(true);
        bounceArea.setEditable(false);
        bounceArea.setFocusable(false);

        velocityArea = new JTextArea();
        velocityArea.setBounds(75, 30, 65, 20);
        velocityArea.setText("VELOCITY");
        velocityArea.setFocusable(false);
        velocityArea.setEditable(false);

        angleArea = new JTextArea();
        angleArea.setBounds(75, 60, 65, 20);
        angleArea.setText("ANGLE");
        angleArea.setFocusable(false);

        button = new JButton("Fire");
        button.setBounds(350, 500, 100, 20);
        button.setVisible(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double[] fX = new double[2];


                JLabel projectileB = new JLabel("o", SwingConstants.CENTER);
                projectileB.setBounds(30,300,300,300);
                projectileB.setVisible(true);
                frame.add(projectileB);

                bounceArea.setText("");

                double lA = angle1.getValue();
                fX[0] = velocity1.getValue();
                fX[1] = 30;

                velocityArea.setText(velocity1.getValue() + " m/s");
                angleArea.setText(angle1.getValue() + "°");

                Projectile flightTimeGetter = new Projectile(lA, fX[0]);

                double time = flightTimeGetter.calculateAirTime() * 1000;

                    System.out.println("LOOP " + fX[0]);
                    launch(fX[0], lA, (int) fX[1], 300, projectileB);

                    fX[1]  += flightTimeGetter.calculateX(flightTimeGetter.calculateAirTime()) * 4;
                    Timer t = new Timer((int) time, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Timer t = (Timer) e.getSource();


                            t.setDelay((int) flightTimeGetter.calculateAirTime() * 1000);

                            launch(fX[0] /= 2 , lA, (int) fX[1], 300, projectileB);
                            flightTimeGetter.setLaunchVelocity(fX[0] / 2);


                            t.stop();


                        }
                    });
                    t.start();

            }
        });


        projectile.setVisible(true);
        frame.add(projectile);
        frame.add(angle1);
        frame.add(velocity1);
        frame.add(button);
        frame.add(timerArea);
        frame.add(bounceArea);
        frame.add(velocityArea);
        frame.add(angleArea);
    }

    public static void launch(double lV, double lA, int x, int y, JLabel label) {
        System.out.println("Timer starting");


            double startTime = System.currentTimeMillis();

            Projectile P = new Projectile(lA, lV);

            double t = P.calculateAirTime();


            Timer timer = new Timer(16, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    double instantTime = System.currentTimeMillis();
                    double elapsedTime = (instantTime - startTime) / 1000;

                    String elapse = elapsedTime + "s";

                    timerArea.setText(elapse);

                    double[] pos = P.getlocation(elapsedTime);
                    nX = x + (4 * pos[0]);
                    nY = y - (4 * pos[1]);


                    JLabel trail = new JLabel(".");
                    trail.setBounds((int) nX + 3, (int) nY - 3, 10, 10);
                    trail.setVisible(true);
                    frame.add(trail);


                    label.setBounds((int) nX, (int) nY, 10, 10);

                    if (elapsedTime >= t) {
                        ((Timer) e.getSource()).stop();
                        bounceArea.append(elapse + "\n");
                        label.setText("o");
                        JLabel distanceLabel = new JLabel((((int) nX  - 30) / 4) + " m");
                        distanceLabel.setBounds((int) nX - 10, (int) nY + 10, 300, 12);
                        distanceLabel.setVisible(true);
                        frame.add(distanceLabel);
                    }

                    frame.repaint();
                }


            });
            timer.start();
            System.out.println(lV);
        }


}
