
package clientpost;

import java.io.File;
import java.io.StringWriter;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * The class gets input information and converts it to XML.
 *
 * <command> <exec path="sort"> <stdin> 2 1 3 </stdin> </exec> </command>
 *
 *
 * @author Victor Butenko
 */
public class ConverterXML {

    public static void main(String[] args) {

        
        // inputData(); //Input data
        //Checking if the necessary command's data has been entered
        if (!commandName.equals("")) {}
        
//        //Test initializing for test 
//        testInit();
//
//        //Generate XML - form
//        String dataXML = generateDataToXML();
//        System.out.println(dataXML);
//        
//        //Save XML in the field
//        resultXML = dataXML;
        
    }
    
    //-----------------------------------------------------------------
    //Getters and Setters 
    
    public static String getResultXML() {
        testInit();
        resultXML = generateDataToXML();
        return resultXML;
    }

    public static void setResultXML(String resultXML) {
        ConverterXML.resultXML = resultXML;
    }
    //--------------------------------------------------------------------

    /**
     * Method converts input data to XML format
     */
    private static String generateDataToXML() {

        String stringResult = "";
        
        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root elements 
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement("command");
            doc.appendChild(rootElement);

            // Child 'Exec'
            Element exec = doc.createElement("exec");
            rootElement.appendChild(exec);

            //Set an attribute to 'exec' element
            exec.setAttribute("path", commandName);

            // 'Exec's child Args
            Element args = doc.createElement("args");
            exec.appendChild(args);

            //Args' child arg
            Element arg1 = doc.createElement("arg");
            args.appendChild(arg1);
            arg1.setAttribute("value", cmdArgs);    //Set arguments 
            
            Element arg2 = doc.createElement("arg");
            args.appendChild(arg2);
            arg2.setAttribute("value", "echo $" + envVariables);

            //Add element <env>
            Element env = doc.createElement("env");
            exec.appendChild(env);

            //Add element VAR
            Element var = doc.createElement("var");
            env.appendChild(var);
            var.setAttribute("name", envVariables);
            var.setAttribute("value", "Hello Wordl"); //TODO: fix!

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            //StreamResult result = new StreamResult(new File("~"));

            // Output to console for testing
            // StreamResult result = new StreamResult(System.out);

             //Convert StreamResult to String
             StringWriter sWriter   = new StringWriter();
             StreamResult sResult = new StreamResult(sWriter);
             transformer.transform(source, sResult);
             stringResult = sWriter.toString();

        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (TransformerException tfe) {
            tfe.printStackTrace();
        }

        return stringResult;
    }

    /**
     * Method for testing.
     */
    private static void testInit() {
        commandName = "/bin/sh";
        cmdArgs = "-c echo $MESSAGE";
        envVariables = "MESSAGE";
        inputStream = "I'm input stream";
        StringBuffer buffer = new StringBuffer();
        buffer.append(commandName).append(" ").append(cmdArgs).append(" $").
                append(envVariables).append(" ").append(inputStream);
        System.out.println(buffer.toString());
    }

    /**
     * Method for input data. User should enter four types of data: - Command
     * name; - Command line arguments; - Environment variables values; -
     * Standard input stream's contents
     */
    private static void inputData() {

        Scanner in = new Scanner(System.in);
        System.out.println("Input the command: ");

        System.out.println("Command name: ");
        commandName = in.nextLine();

        System.out.println("Command line arguments: ");
        cmdArgs = in.nextLine();

        System.out.println("Environment variables values: ");
        envVariables = in.nextLine();

        System.out.println("Standard input stream's contents: ");
        inputStream = in.nextLine();
    }
    //Fields (content of the commands)
    private static String commandName = "";
    private static String cmdArgs = "";
    private static String envVariables = "";
    private static String inputStream = "";
    
    private static String resultXML   = "";
}
