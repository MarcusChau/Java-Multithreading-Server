/*
 * Has 2 classes one class:
 * Server to create a Server
 * CLientHandler ot handle clients using multithreading
 * 
 * Life Cycle of multithreads:
 * New 
 *  - New Thread created, is in new state, the code has not been run yet
 * Active
 *  -(New thread created when invokes start(), it moves from new to active state)
 *  -Runnable: Ready to run and is moved to runnable state, ready to run at anytime
 *  -Running: Thread gets CPU, moves from runnable to running state. (Generally from runnable to running and again back to runnable)
 * Blocked / Writing
 *  - Whenever a thread is inactive for a span of time(not permanently) then the thread is either in blocked or waiting state
 * Timed Waiting
 *  -Sometimes waiting for leads to starvation, For example, a thread (its name is A) has entered the critical section of a code and is not willing to leave that critical section. In such a scenario, another thread (its name is B) has to wait forever, which leads to starvation. To avoid such scenario, a timed waiting state is given to thread B. Thus, thread lies in the waiting state for a specific span of time, and not forever. A real example of timed waiting is when we invoke the sleep() method on a specific thread. The sleep() method puts the thread in the timed wait state. After the time runs out, the thread wakes up and start its execution from when it has left earlier.
 *  -sleep() normally used for timed waiting
 * Terminated
 *  - Multiple ways that a thread can terminate
 *      - When a thread finishes its job, it exits normally
 *      - Abnormal Termination:
 *          - Occurs when some unusual events such as unhandled exception or segmentation fault
 */
import java.io.*;
import java.net.*;


// Server class
class Server {
    public static void main(String[] args) {
        ServerSocket server = null;

        try {

            // server is listening on port "1234"
            server = new ServerSocket(1234);
            server.setReuseAddress(true);


            // running infinite loop for geting client request
            while(true) {

                // returns new socket
                Socket client = server.accept();
                // returns remote IP address to which the socket is connected or returns null is the socket is not connected
                InetAddress addy = client.getInetAddress();
                // returns raw IP address in a string format
                String remoteIp = addy.getHostAddress();


                // Displaying that new client is connected to server
                System.out.println("New client connected" + addy + remoteIp);

                // create a new thread object
                ClientHandler clientSock = new ClientHandler(client);

                // This thread will handle the client seperately 
                new Thread(clientSock).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (server != null) {
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // ClientHandler class
    private static class ClientHandler implements Runnable {

        private final Socket clientSocket;

        // Construtor
        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            PrintWriter out = null;
            BufferedReader in = null;

            try{

                // get the outputstream of client
                out = new PrintWriter(clientSocket.getOutputStream(), true);

                // get the inputStream of client
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                String line;
                while ((line = in.readLine()) != null) {

                    // Writing the recieved message from client
                    System.out.printf(" Sent from the client: %s\n", line);
                    out.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                } 
            }
        }

    }
    
}
