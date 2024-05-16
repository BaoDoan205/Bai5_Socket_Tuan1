package Bai2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TimeClient extends JFrame {
    private JLabel timeLabel;
    private Socket socket;
    private PrintWriter output;
    private BufferedReader input;

    public TimeClient() {
        // Setup the JFrame
        setTitle("Time Client");
        setSize(300, 100);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        timeLabel = new JLabel("Time will be displayed here", JLabel.CENTER);
        timeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(timeLabel, BorderLayout.CENTER);
    }

    private void startClient() {
        try {
            socket = new Socket("localhost", 1234);
            output = new PrintWriter(socket.getOutputStream(), true);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            new Thread(() -> {
                try {
                    while (true) {
                        output.println("time");
                        String serverResponse = input.readLine();
                        SwingUtilities.invokeLater(() -> timeLabel.setText(serverResponse));
                        Thread.sleep(1000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        TimeClient client = new TimeClient();
        client.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                client.startClient();
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    if (client.socket != null && !client.socket.isClosed()) {
                        client.socket.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        client.setVisible(true);
    }
}
