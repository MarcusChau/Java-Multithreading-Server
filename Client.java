/*
 * Client contains the class to create a client
 */
import java.io.*;
import java.net.*;
import java.util.*;


// Client Class
public class Client {

    //drive code
    public static void main(String[] args) {
        // established a connection by provided host and port number
        try( Socket socket = new Socket("localhost", 1234)) {

            // writing to server
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

            // reading from server
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // object of scanner class
            Scanner sc = new Scanner(System.in);
            String line = null;

            while(!"exit".equalsIgnoreCase(line)) {

                // reading from user
                line = sc.nextLine();

                // sending the user input to server
                out.println(line);
                // clearing stream
                out.flush();

                // displaying server reply
                System.out.println("Server replied " + in.readLine());
            }

            // closing the scanner object
            sc.close();

        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

}