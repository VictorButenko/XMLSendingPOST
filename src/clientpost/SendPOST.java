package clientpost;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * The class for sending POST message to server
 *
 * @author victor
 */
public class SendPOST {

    /**
     * Extends the size of an array.
     */
    public void sendPostRequest() {

        //Build parameter string
        String data = ConverterXML.getResultXML();
        
        //Stop the programm if the data is empty
        if(data.equals("")) {
            System.err.println("data is empty!! ");
            return;
        }
        
        //Just for test 
        System.out.println(data);
        
        try {

            // Send the request
            URL url = new URL(Adresses.HOST);
            URLConnection conn = url.openConnection();
            conn.setDoOutput(true);
            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());

            //write parameters
            writer.write(data);
            writer.flush();

            // Get the response
            StringBuffer answer = new StringBuffer();
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                answer.append(line);
            }
            writer.close();
            reader.close();

            //Output the response
            System.out.println(answer.toString());

        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Starts the program
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new SendPOST().sendPostRequest();
    }
}
