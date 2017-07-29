package gameproject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.regex.Pattern;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GameProject extends JFrame implements KeyListener, Runnable {

    private static final String TITLE = "Jump guru";
    private static final int WIDTH = 680;
    private static final int HEIGHT = 510;
    private String servername = "servername", clientname = "clientname";

    public GameProject() {

    }

    public void run() {
        this.setVisible(true);
        this.setTitle(TITLE);
        this.setSize(WIDTH, HEIGHT);
        this.setResizable(false);
        this.addKeyListener(this);
    }

    public void paint(Graphics g) {

        Toolkit t = Toolkit.getDefaultToolkit();
        Image i = t.getImage("D:\\JAVA\\GameProject\\src\\gameproject\\guru.jpg");
        g.drawImage(i, 0, 0, this);

    }

    public static void main(String[] args) {
        GameProject gp = new GameProject();
        gp.run();

    }

    public void keyPressed(KeyEvent arg0) {
        int keyCode = arg0.getKeyCode();
        String portAdd = null;
        String ipAdd = null;

        // - Create a Server - //
        if (keyCode == KeyEvent.VK_S) {//Virtual key type

            // - Input Dialog for get a port address - //
            portAdd = JOptionPane.showInputDialog(null, "Example. 1024", "Enter server port:", 1);

            // - Alert Message - //
            if (portAdd != null) {
                if (!isPort(portAdd)) {
                    JOptionPane.showMessageDialog(null, "Enter port number as a right format!", "Error!", 1);
                } else {

                    // - Input Dialog for get a nick name for server player - //
                    servername = JOptionPane.showInputDialog(null, "Nick name:", "Enter server player name:", 1);
                    servername += "";

                    // - Alert Message - //
                    if (servername.length() > 10 || servername.length() < 3 || servername.startsWith("null")) {
                        JOptionPane.showMessageDialog(null, "Enter name as a right format!", "Error!", 1);

                    } // - Create a server - //
                    else {

                        JumpGuruServer myServer = new JumpGuruServer(servername, portAdd);
                        Thread myServerT = new Thread(myServer);
                        myServerT.start();
                        this.setVisible(false);
                    }
                }
            }
        }

        // - Create a Client - //
        if (keyCode == KeyEvent.VK_C) {

            // - Input Dialog [IP Address] - // 
            ipAdd = JOptionPane.showInputDialog(null, "Example. 127.0.0.1", "Enter server ip:", 1);

            if (ipAdd != null) {

                // - Alert Message - //
                if (!isIPAddress(ipAdd)) {
                    JOptionPane.showMessageDialog(null, "Enter ip number as a right format!", "Enter server ip:", 1);
                } else {
                    // - Input Dialog [Port Number] - // 
                    portAdd = JOptionPane.showInputDialog(null, "Example. 1024", "Enter server port number:", 1);

                    // - Alert Message - //
                    if (portAdd != null) {
                        if (!isPort(portAdd)) {
                            JOptionPane.showMessageDialog(null, "Enter port number as a right format!", "Error!:", 1);
                        } // - Input Dialog for get a nick name for client player - //
                        else {
                            clientname = JOptionPane.showInputDialog(null, "Nick name:", "Enter server name:", 1);
                            clientname += "";
                            if (clientname.length() > 10 || clientname.length() < 3 || clientname.startsWith("null")) {
                                JOptionPane.showMessageDialog(null, "Enter name as a right format!", "Error!", 1);
                            } // - Start a Client - //
                            else {
                                JumpGuruClient myclient = new JumpGuruClient(clientname, portAdd, ipAdd);
                                Thread myClientT = new Thread(myclient);
                                myClientT.start();
                                this.setVisible(false);
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent arg0) {

    }

    @Override
    public void keyTyped(KeyEvent arg0) {

    }
//Port number type

    private boolean isPort(String str) {
        Pattern port = Pattern.compile("\\d{1,4}");//Regular Expressions
        return port.matcher(str).matches();
    }

    // IP address type 
    private boolean isIPAddress(String str) {
        Pattern ip = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}");//Regular Expressions
        return ip.matcher(str).matches();
    }

}
