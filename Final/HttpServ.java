import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;


public class HttpServ {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/login", new MyHandler());
        server.createContext("/list_mail", new MyHandler1());
        server.createContext("/result", new MyHandler2());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            File myObj = new File("tokens/StoredCredential"); 
            if(myObj.delete()) {
                System.out.println("SUCCESS");
            }
            else {
                System.out.println("FAIL");
            };
            
            String response = "<html> Hello, please sign in below. <br> <form method=\"get\" action=\"/list_mail\"> <button type=\"submit\"> Sign in with Google </button> </form> </html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class MyHandler1 implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            // Handle Gmail access here

            String response;
            
            try {
                GmailQuickstart mail1 = new GmailQuickstart();
                mail1.activate();
                response = "<html> Logged in successful. <br> Welcome, <b> <i>" + mail1.getMailProfile() + " </i> </b> <br> This is the list of your Email INBOX by IDs. Copy&paste ID to the search box to check email.<br> \"";

                // Input + Submit
                response += " <form action=\"/result\"> <label for=\"mail_id\"> Email_ID: </label><input type=\"text\" id=\"mail_id\" name=\"mail_id\"><br><br><input type=\"submit\" value=\"Submit\"> </form> ";
                // Get mail with "INBOX" label only
                List<String> list = Arrays.asList(new String[]{"INBOX"});
                response += mail1.listMessagesWithLabels("me", list);              
                response += "</html>";
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
                response = "Page did not load";
            }


            
            t.sendResponseHeaders(200, 0);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class MyHandler2 implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            String response;
            
            try {
                GmailQuickstart mail1 = new GmailQuickstart();
                mail1.activate();
                response = "<html>The content of the Email is: <br>  ";

                System.out.println("hehe" + t.getRequestURI().toString());

                String mail_id = t.getRequestURI().toString().split("mail_id=")[1];
                System.out.println(mail_id);

                response += mail1.getMailByID(mail_id);
                response += "<br> <form method=\"get\" action=\"/login\"> <button type=\"submit\"> Log out </button> </form>";

                response += "\"</html>";
            } catch (GeneralSecurityException e) {
                e.printStackTrace();
                response = "Page did not load";
            }
            
            t.sendResponseHeaders(200, 0);
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}
