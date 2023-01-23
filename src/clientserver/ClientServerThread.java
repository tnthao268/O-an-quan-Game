package clientserver;

import view.GameView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ClientServerThread extends Thread {

    private final String ip;

    private final int port;
    private ServerSocket serversocket;
    private Socket socket;

    private GameView view;

    private ObjectOutputStream oos;

    private ObjectInputStream ois;

    private ClientServerThread(GameView view, String ip, int port) {
        this.view = view;
        this.ip = ip;
        this.port = port;
    }

    /**
     * Constructor for a server thread.
     * @param port      Port to use for the socket
     * @param view      Object for callbacks
     */
    public static ClientServerThread newServer(int port, GameView view) {
        var cst = new ClientServerThread(view, "localhost", port);
        try {
            cst.serversocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cst;
    }

    /**
     * Constructor for a client thread.
     * @param ip        IP to connect to
     * @param port      Port to connect to
     * @param view     Object for callbacks
     */
    public static ClientServerThread newClient(String ip, int port, GameView view) {
        var cst = new ClientServerThread(view, ip, port);
        try {
            cst.socket = new Socket(ip, port);
            cst.oos = new ObjectOutputStream(cst.socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cst;
    }


    /**
     * Constructor for either a client or server.
     * @param ip        IP to connect to
     * @param port      Port to connect to
     * @param view     Object for callbacks
     */
    public static ClientServerThread newAny(String ip, int port, GameView view) {
        var cst = new ClientServerThread(view, ip, port);
        cst.reconnect();
        return cst;
    }

    /**
     * Creates a new connection either as a client or as a server.
     * @throws IOException
     */
    private void reconnect() {
        System.out.println("Reconnect");
        // Close all previously active sockets and streams before reconnecting
        if(socket != null) {
            try {
                socket.close();
            } catch (IOException e) {}
            socket = null;
        }
        if(serversocket != null) {
            try {
                serversocket.close();
            } catch (IOException e) {}
            serversocket = null;
        }
        if(oos != null) {
            try {
                oos.close();
            } catch (IOException e) {}
            oos = null;
        }

        // Initialize a new connection
        try {
            // Try to connect as a client first
            socket = new Socket(ip, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("This is a client");
        } catch (IOException e) {
            // Create a server if connection is not possible
            try {
                serversocket = new ServerSocket(port);
                System.out.println("This is a server");
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    /**
     * Returns if this thread is connected to a client or server.
     * @return true if it is connected, false otherwise.
     */
    public boolean isConnected() {
        return socket != null && socket.isConnected();
    }

    /**
     * Returns if this thread represents a server.
     * @return true if it is a server, false if it is a client.
     */
    public boolean isServer() {
        return serversocket != null;
    }

    /**
     * Sends the object to the other program.
     * @param obj Object to send
     */
    public void send(Object obj) {
        try {
            if(isConnected() && oos != null) {
                oos.reset();
                oos.writeObject(obj);
            }
        } catch (IOException e) {
            // Nothing. If sending not possible, do not send anything.
        }
    }


    public boolean received()  {
        try{
        if (isConnected() && ois != null) {
            ois.reset();
            return true;
        }
        }
        catch(IOException e) {}

        return false;
    }

    /**
     * Method to run the thread
     */

    public void run() {
        // Outer while handles reconnection
        while(true) {
            try {
                // If this is a server accept one client
                if (socket == null) {
                    socket = serversocket.accept();
                    oos = new ObjectOutputStream(socket.getOutputStream());
                }

                // Read objects
                ois = new ObjectInputStream(socket.getInputStream());

                while (true) {
                    Object obj = ois.readObject();
                    if (obj instanceof GameState) {
                        view.setGameState((GameState) obj);
                    }
                    if (obj instanceof MouseInput) {
                        view.handleClientInput((MouseInput) obj);
                    }
                    if(obj instanceof CompMove){
                        view.handleCompMove((CompMove)obj);
                    }
                }
            } catch (IOException e) {
                // Connection lost, try reconnecting.
                reconnect();
            } catch (ClassNotFoundException e) {
                // This should not happen, since all classes are known in both server and client.
                throw new RuntimeException(e);
            }
        }
    }



















}
