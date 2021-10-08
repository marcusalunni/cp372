
import java.io.* ;
import java.net.* ;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.color.*;
public final class server{
    public static void main(String argv[]) throws Exception {
        //initializing values
        int port = 0;
        int b_width = 0;
        int b_height = 0;
        String colour[] = new String[argv.length - 3];

        //taking input from client/ error handle

        if (argv.length < 4){
            System.out.println("input not valid");
            System.out.println("Enter inputs in form: ");
            System.out.println("java server <port #> <width> <height> <colour>");
        }else{
            port = Integer.valueOf(argv[0]);
            b_width = Integer.valueOf(argv[1]);
            b_height = Integer.valueOf(argv[2]);
            //adding colours to colour array
            for(int i = 3; i < argv.length; i++){
                colour[i - 3] = String.valueOf(argv[i]);
            }
            
        }

        

        ServerSocket ss = new ServerSocket(port);
        Socket s = ss.accept();

        InetAddress ip;
        ip = InetAddress.getLocalHost();

        //displaying what we know
        System.out.println("length: " + argv.length);
        System.out.println("Connected");
        System.out.println("port: "+ port);
        System.out.println("B_width: " + b_width);
        System.out.println("B_height: " + b_height);
        System.out.println("ip: " + ip);
        System.out.println("Colour(s): ");
        String show_colour = "";
        for (int i = 0; i<=argv.length-4; i++){
            System.out.print(colour[i] + ", ");
            show_colour = show_colour + colour[i] + ", ";
        } 

        //GUI
        //frame
        JFrame frame = new JFrame("BBoard");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(b_width,b_height);

        // creating panels
        JPanel panel = new JPanel();
        //JPanel panel2 = new JPanel();
        JPanel panel3 = new JPanel();
        JPanel board = new JPanel();
        board.setLayout(null);

        //labels and text fields
        JLabel label = new JLabel("Enter Text:");
        JLabel dimlabel = new JLabel("width: " + b_width + " height: " + b_height);
        JLabel colourlabel = new JLabel("colour(s): " + show_colour);
        JTextField showip = new JTextField("ip: "+ ip + "port number: "+ port);

        JTextField tf = new JTextField(20); // accepts upto 20 characters

        //buttons
        JButton get = new JButton("GET");
        JButton post = new JButton("POST");
        JButton disconnect = new JButton("Disconnect");
        JButton pin = new JButton("PIN");
        JButton shake = new JButton("SHAKE");
        JButton clear = new JButton("CLEAR");
        JButton unpin = new JButton("UNPIN");
        //adding buttons to panels
        panel.add(label); 
        panel.add(tf);
        panel.add(get);
        panel.add(post);
        panel3.add(disconnect);
        panel3.add(pin);
        panel3.add(unpin);
        panel3.add(shake);
        panel3.add(clear);
        panel3.add(dimlabel);
        panel3.add(colourlabel);
        panel3.add(showip);
        //for user input
        //JTextArea ta = new JTextArea();

        //positioning panels
        //frame.getContentPane().add(BorderLayout.CENTER, ta);
        frame.getContentPane().add(BorderLayout.SOUTH, panel);
        //frame.getContentPane().add(BorderLayout.NORTH, panel2);
        frame.getContentPane().add(BorderLayout.NORTH, panel3);
        frame.getContentPane().add(BorderLayout.CENTER, board);
        
        disconnect.addActionListener((new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        }));
        //adding action to post button
        post.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = tf.getText();
                String cmd[] = command.split(" ");
                System.out.println(cmd[0]);
                int x = Integer.parseInt(cmd[0]);
                System.out.println(x);
                int y = Integer.parseInt(cmd[1]);
                System.out.println(y);
                int w = Integer.parseInt(cmd[2]);
                System.out.println(w);
                int h = Integer.parseInt(cmd[3]);
                System.out.println(h);
                String col = cmd[4];
                System.out.println(col);
                String msg = "";
                for(int i = 5; i<=cmd.length-1; i++){
                    msg = msg + cmd[i] + " ";
                }
                System.out.println(msg);
                JTextArea postit = new JTextArea();
                System.out.println("size: "+ postit.getSize());
                System.out.println("size: "+ postit.getSize());

                postit.setText(msg);
                postit.setBounds(x,y,w,h);
                System.out.println("7"+col+"7");
                System.out.println(col.toLowerCase().equals("red"));
                if (col.toLowerCase().equals("black")){
                    postit.setBackground(Color.black);
                }else if (col.toLowerCase().equals("blue")){
                    postit.setBackground(Color.blue);
                }else if (col.toLowerCase().equals("cyan")){
                    postit.setBackground(Color.cyan);
                }else if (col.toLowerCase().equals("gray")){
                    postit.setBackground(Color.gray);
                }else if (col.toLowerCase().equals("green")){
                    postit.setBackground(Color.green);
                }else if (col.toLowerCase().equals("magenta")){
                    postit.setBackground(Color.magenta);
                }else if (col.toLowerCase().equals("orange")){
                    postit.setBackground(Color.orange);
                }else if (col.toLowerCase().equals("pink")){
                    postit.setBackground(Color.pink);
                }else if (col.toLowerCase().equals("red")){
                    postit.setBackground(Color.red);
                }else if (col.toLowerCase().equals("white")){
                    postit.setBackground(Color.white);
                }else if (col.toLowerCase().equals("yellow")){
                    postit.setBackground(Color.yellow);
                }
                board.add(postit);
                frame.setVisible(true);
                //postit.setVisible(true);
            }
        });
        frame.setVisible(true);
    }    
}
