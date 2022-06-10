import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.Profile;
import com.google.api.services.gmail.model.ListMessagesResponse;


import org.apache.commons.codec.binary.Base64;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

/* class to demonstrate use of Gmail list labels API */
public class GmailQuickstart {
    /** Application name. */
    private static final String APPLICATION_NAME = "Gmail API Java Quickstart";
    /** Global instance of the JSON factory. */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /** Directory to store authorization tokens for this application. */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = GmailQuickstart.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        
        //new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH));
        //MemoryDataStoreFactory.getDefaultInstance();
        File token_file = new File(TOKENS_DIRECTORY_PATH);
        token_file.deleteOnExit();
        token_file.setWritable(true, false);
        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(token_file))
                .setAccessType("offline")
                .setApprovalPrompt("force")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }

    public String activate() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Credential return_cred = getCredentials(HTTP_TRANSPORT);
        System.out.println(return_cred.getAccessToken());

        return return_cred.getAccessToken();
    }

    public String getMailProfile() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
    
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                    .setApplicationName(APPLICATION_NAME)
                    .build();
    
        Profile profileResponse = service.users().getProfile("me").execute();
        
        String result = profileResponse.getEmailAddress() + "<br> Total message: " + profileResponse.getMessagesTotal();
        return result;
    }

    public String listMessagesWithLabels(String userId, 
                    List<String> labelIds) throws IOException, GeneralSecurityException{

    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        
    Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
                
    //Gmail.Users.Messages.List request = service.users().messages().list("me").setQ("Uber");
    //ListMessagesResponse response = request.execute();
    
    String list_result = "";
    ListMessagesResponse listResponse = service.users().messages().list("me").execute();
    for(int i=0; i<10; ++i) {
        list_result += "<br>" + listResponse.getMessages().get(i).getId();

    }
    System.out.println(listResponse.toPrettyString());

    return list_result;
    
    /* List<Message> messages = new ArrayList<Message>();
    if (response.getMessages() != null) {
        messages.addAll(response.getMessages());        
    }

    for (Message message : messages) {
        System.out.println(message.getSnippet());
        //System.out.println(service.users().messages().get("me", message.getId()).setFormat("raw").execute().getSnippet() );
    } */

}
        
    public String getMailByID(String mail_id) throws IOException, GeneralSecurityException{

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        
    
        Message message_full = service.users().messages().get("me", mail_id).setFormat("full").execute();
        String encoded_body =  message_full.getPayload().getParts().get(0).getBody().getData();
        String decoded_body = new String(Base64.decodeBase64(encoded_body));
        //String real_message = StringUtils.newStringUtf8(message.decodeRaw());
        //String real_message = new String(Base64.decodeBase64(message.getPayload().getBody().getData().getBytes("UTF-8")));
        //String reasl_message = new String(message_full.decodeRaw());
        System.out.println("THIS IS MESS ID: " + mail_id);
        System.out.println("\nTHIS IS EMAIL CONTENT:\n" + decoded_body);

        return decoded_body;
            
    }
}
