/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package camera;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

/**
 *
 * @author Coder
 */
public class cizgi extends JPanel {

    int x, y, cx, cy, i;
    float aci;
    int dizixy[][] = new int[10][2];
    private Image sun;

    cizgi() {
        init();
    }

    cizgi(boolean deger) {
        capture.setVisible(deger);
    }

    void init() {
        addMouseMotionListener(new mymouseadapter());
        addMouseListener(new mymouseadapter());

        capture.setPreferredSize(new Dimension(200, 50));
        add(capture);
        capture.setVisible(true);
        revalidate();
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        capture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    captureActionPerformed(evt);
                } catch (IOException ex) {
                    Logger.getLogger(cizgi.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        photoGet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                photoGetActionPerformed(evt);
            }
        });

        copy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyActionPerformed(evt);
            }
        });

    }
    boolean durum = false, durum2 = false;
    JButton button = new JButton("Çizgileri Birleştir");
    JButton capture = new JButton("Resim Çek");
    JButton photoGet = new JButton("Resmi Getir");
    JButton copy = new JButton("Açıyı Kopyala");

    void drawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        if (durum2) {
            sun = new ImageIcon("photo.png").getImage();
            g2d.drawImage(sun, 0, 60, getWidth(), getHeight() - 60, null);
            g2d.drawOval(x - 3, y - 3, 6, 6);
            g2d.drawOval(x - 1, y - 1, 2, 2);
            g2d.setColor(Color.red);
            for (int j = 0; j < i; j++) {
                g2d.drawOval(dizixy[j][0] - 3, dizixy[j][1] - 3, 6, 6);
                g2d.drawOval(dizixy[j][0] - 1, dizixy[j][1] - 1, 2, 2);
            }
        }

        if (i == 4) {
            button.setPreferredSize(new Dimension(200, 50));

            add(button);
            revalidate();
            i++;
        }
        g2d.setColor(Color.white);
        if (durum) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 3; k++) {
                    g2d.drawLine(dizixy[j][0], dizixy[j][1], dizixy[k + 1][0], dizixy[k + 1][1]);
                }
            }
//            Point birinci = new Point(dizixy[0][0], dizixy[0][1]);
//            Point ikinci = new Point(dizixy[1][0], dizixy[1][1]);
//            Point ucuncu = new Point(dizixy[2][0], dizixy[2][1]);
//            Point donduncu = new Point(dizixy[3][0], dizixy[3][1]);

            Line l1 = new Line(new Point(dizixy[0][0], dizixy[0][1]), new Point(dizixy[3][0], dizixy[3][1]));
            Line l2 = new Line(new Point(dizixy[1][0], dizixy[1][1]), new Point(dizixy[2][0], dizixy[2][1]));
            lineLineIntersection(l1, l2);
        }
    }

    private static class Line {

        Point s, e;

        Line(Point s, Point e) {
            this.s = s;
            this.e = e;
        }
    }

    public void lineLineIntersection(Line l1, Line l2) {
//        double a1 = l1.e.y - l1.s.y;
//        double b1 = l1.s.x - l1.e.x;
//        double c1 = a1 * l1.s.x + b1 * l1.s.y;
// 
//        double a2 = l2.e.y - l2.s.y;
//        double b2 = l2.s.x - l2.e.x;
//        double c2 = a2 * l2.s.x + b2 * l2.s.y;
// 
//        double delta = a1 * b2 - a2 * b1;
//        double x1=(b2 * c1 - b1 * c2) / delta;
//        double y1=(b2 * c1 - b1 * c2) / delta;
//        System.out.println("bulunanX: " + ((b2 * c1 - b1 * c2) / delta));
//        System.out.println("bulunanY: " + ((a1 * c2 - a2 * c1) / delta));

        double angle1 = Math.atan2(l1.s.y - l1.e.y,
                l1.s.x - l1.e.x);
        double angle2 = Math.atan2(l2.s.y - l2.e.y,
                l2.s.x - l2.e.x);
        float cl = (float) Math.toDegrees(angle1 - angle2);
        if (cl < 0) {
            cl += 360;
            cl = 360 - cl;
            System.out.println(cl);
            aci = cl;
            angletext.setText(Float.toString(cl));
            copy.setPreferredSize(new Dimension(200, 50));
            add(copy);
            revalidate();
        }

        //System.out.println(angleBetweenTwoPointsWithFixedPoint(l1.s.x,l1.s.y,l2.s.x,l2.s.y,x1,y1));
    }

//    public static double angleBetweenTwoPointsWithFixedPoint(double point1X, double point1Y, double point2X, double point2Y, double fixedX, double fixedY) {
//
//        double angle1 = Math.atan2(point1Y - fixedY, point1X - fixedX);
//        double angle2 = Math.atan2(point2Y - fixedY, point2X - fixedX);
//
//        return angle1 - angle2;
//    }
    JTextField angletext = new JTextField(10);

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        durum = true;
        repaint();
        button.setVisible(false);
        Font font1 = new Font("SansSerif", Font.BOLD, 20);
        angletext.setFont(font1);
        add(angletext);
        angletext.disable();
    }

    private void copyActionPerformed(java.awt.event.ActionEvent evt) {
        StringSelection stringSelection = new StringSelection(angletext.getText());
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    private void captureActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        Webcam webcam = Webcam.getDefault();

        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.open();
        ImageIO.write(webcam.getImage(), "PNG", new File("photo.png"));
        webcam.close();
        photoGet.setPreferredSize(new Dimension(200, 50));
        add(photoGet);
        capture.setVisible(false);
        revalidate();
        //Font f=new Font("Arial",Font.BOLD,15);
        photoGet.setForeground(Color.red);
    }

    private void photoGetActionPerformed(java.awt.event.ActionEvent evt) {

        durum2 = true;
        photoGet.setVisible(false);

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawing(g);
    }

    public class mymouseadapter extends MouseAdapter {

        @Override
        public void mouseMoved(MouseEvent e) {
            if (i < 4) {
                x = e.getX();
                y = e.getY();
                repaint();
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (durum2) {
                dizixy[i][0] = x;
                dizixy[i][1] = y;
                System.out.println("basX: " + x);
                System.out.println("basY: " + y);
                i++;

                repaint();
            }
        }
    }
}
