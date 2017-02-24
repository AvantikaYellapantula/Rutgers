import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
	
	/*
	 * - main args accept socket number as a parameter 
	 * - starts server
	 */
    public static void main(String[] args) throws Exception {

    	ServerSocket server_socket;
    	Socket socket;
    	String string;
    	boolean end = false;
    	
        try {
          // create socket
          server_socket = new ServerSocket(Integer.parseInt(args[0]));
                    
          // repeatedly wait for connections, and process
          while(true) {
            socket = server_socket.accept();// a "blocking" call which waits until a connection is requested
            
            // open up IO streams
            DataOutputStream toClient = new DataOutputStream(socket.getOutputStream());
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            while ((string = fromClient.readLine()) != null) { // waits for data and reads it in until connection dies
            	System.out.println(string);//outputs read string from client
            	
                if (string.equals("#") || string.equals("$")) {//tests if the input string from client is a kill char
                	end = true;//set end to true to break out of both loops
                	break;//end loop
                }
                    
                toClient.writeBytes(invert(string));//sends inverted string back to client
            }
            
            // close IO streams, then socket
            toClient.close();
            fromClient.close();
            socket.close();
            
            if (end)//if true then ends the forever loop that is listening for connections
            	break;
          }
          
          server_socket.close();//closes server socket
          
        } catch (IOException e) {e.printStackTrace();}
    }
    
    /*
	 * - accept input string as a parameter
	 * - reverses the input string and returns it as a string
	 */
    public static String invert(String start){
		char[] end=new char[start.length()+1]; //creates a array of chars to hold the inverted string. size has been set to input string plus one for the \n
		
		for (int i=start.length(); i>0; i--){//loop through the input string and store char by char backwards in end array
			end[start.length()-i]=start.charAt(i-1);//reverses chars
		}
		end[start.length()]='\n';//adds \n to end of char array
		
    	return new String(end);//returns char array as string
    }
}