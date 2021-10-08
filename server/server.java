
import java.io.* ;
import java.net.* ;
import java.util.ArrayList;

import javax.swing.*;

// import jdk.internal.net.http.ResponseBodyHandlers.FileDownloadBodyHandler;

import java.awt.*;
import java.awt.event.*;
import java.awt.color.*;
public final class server{
    public static class Note{
        int x;
        int y; 
        int width;
        int height;
        String colour;
        String message;
        boolean pinned;
        JTextArea notes;
        public Note(int x, int y, int width, int height, String colour,String message, boolean pinned, JTextArea notes){
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.colour = colour;
            this.message = message;
            this.pinned = pinned;
            this.notes = notes;
        }
        
    }

    public static void main(String argv[]) throws Exception {
        //initializing values
        int port = 0;
        int b_width = 0;
        int b_height = 0;
        ArrayList<Note> board_list = new ArrayList<Note>(); 
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
        
        
        //adding action to post button
        post.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String command = tf.getText();
                String cmd[] = command.split(" ");
                System.out.println(cmd[0]);
                int x = Integer.parseInt(cmd[0]);
                int y = Integer.parseInt(cmd[1]);
                int w = Integer.parseInt(cmd[2]);
                int h = Integer.parseInt(cmd[3]);

                String col = cmd[4];
                String msg = "";
                for(int i = 5; i<=cmd.length-1; i++){
                    msg = msg + cmd[i] + " ";
                }


                JTextArea postit = new JTextArea();
                postit.setText(msg + "\n");
                postit.setBounds(x,y,w,h);

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
                Note note = new Note(x,y,w,h, col, msg, false, postit);
                board_list.add(note);
                // // For debugging
                // for(Note n: board_list){
                //     System.out.println(n.message);
                // }
                // //
                frame.setVisible(true);
                //postit.setVisible(true);
            }
        });
        
        disconnect.addActionListener((new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        }));

        get.addActionListener((new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                //getting command
                String command = tf.getText();
                String cmd[] = command.split("=");
                //initializing variables
                String find_col = "";
                int x = -1;
                int y = -1;
                String msg = "";
                String[] coordinates;
                //getting each user request
                for(int i = 0; i<cmd.length; i++){
                    if(cmd[i].trim().toLowerCase().equals("color")){
                        find_col = find_col + cmd[i+1].trim().toLowerCase();
                    }else if(cmd[i].trim().toLowerCase().equals("contains")){
                        
                        coordinates = cmd[i+1].trim().split(" ");

                        x = Integer.parseInt(coordinates[0].trim());
                        y = Integer.parseInt(coordinates[1].trim());
                        System.out.println(x);
                        System.out.println(y);
                    }else if(cmd[i].trim().toLowerCase().equals("refersto")){
                        msg = msg + cmd[i+1];
                    }
                }
                //comparing user request to notes on board, then removing notes that do not correlate to request
                for(Note n: board_list){

                    System.out.println("msg"+ msg);
                    System.out.println(n.message.contains(msg));
                    //System.out.println(!(n.x == x) && !(n.y == y));
                    if (find_col.equals("") == false){
                        if(n.colour.equals(find_col) == false){
                            n.notes.setVisible(false);
                        }
                    }if(x != -1 && y!= -1){
                        if(n.x != x){
                            if(n.y != y){
                                n.notes.setVisible(false);
                            }
                        }
                    }if(msg.equals("")==false){
                        if(n.message.contains(msg) == false){
                            n.notes.setVisible(false);
                        }
                    }
                }
                //
            }
        }));
        pin.addActionListener((new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String str_coordinates = tf.getText();
                String lst_coordinates[];
                int x;
                int y;

                lst_coordinates = str_coordinates.split(",");
                x = Integer.parseInt(lst_coordinates[0].trim());
                y = Integer.parseInt(lst_coordinates[1].trim());

                for(Note n: board_list){
                    if(n.x == x && n.y == y){
                        n.pinned = true;
                        n.notes.append("*");
                    }
                }

            }
        }));
        unpin.addActionListener((new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String str_coordinates = tf.getText();
                String lst_coordinates[];
                int x;
                int y;
                String message = "";
                String message2 = "";

                lst_coordinates = str_coordinates.split(",");
                x = Integer.parseInt(lst_coordinates[0].trim());
                y = Integer.parseInt(lst_coordinates[1].trim());

                for(Note n: board_list){
                    if(n.x == x && n.y == y){
                        n.pinned = false;
                        message = message + n.notes.getText();
                        message2 = message.substring(0,message.length()-1);
                        n.notes.setText(message2);
                    }
                }
            }
        }));

        clear.addActionListener((new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                for(Note n: board_list){
                    n.notes.setVisible(false);
                }
                board_list.clear();
            }
        }));


        shake.addActionListener((new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                for(Note n: board_list){
                    if(n.pinned == false){
                        n.notes.setVisible(false);
                        board_list.remove(n);
                    }
                }
            }
        }));

        frame.setVisible(true);
    }    
}
