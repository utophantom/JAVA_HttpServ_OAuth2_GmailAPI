# STEP-BY-STEP EXPLANATION
## Background
Unfortunately, I cannot upload the entire project due to the reason of version I have explained in the README.md in the root directory of this repo. Instead,
this folder contains two files: ```HttpServ.java``` and ```GmailQuickstart.java```, which is just a modified and combined version of HttpServ library and Gmail + OAuth2 API that I have
explained in a different folder. Use my code as a reference and try to re-create my project in your own way with more up-to-date syntax. 

```HttpServ.java``` will have the main method, and serves as a "controller". As the name implies, it will basically drives the flow of this web app and call necessary component
from GmailQuickstart java class. It will handle requests and responses, as well as html components. 

```GmailQuickstart.java``` will take care of a GmailQuickstart class which has OAuth2 and Gmail_API implementation. All data that is being requested from the HttpServ will be
taken care by this class, and the class will work with Gmail Service/Server to get the data and return back to the HttpServer, which is then returned to the client/user.


------------------
## HttpServer

First, we start off with HttpServer. Nothing is worth to be mentioned as it handles basic requests and return html as an entire string (aka ```response``` in the code)

One thing needs an explanation is how I implement a log out function in this web app. How do I log out the user and essentially require them to re-auth their credential?

Well, take a look at this part: 

```
File myObj = new File("tokens/StoredCredential"); 
            if(myObj.delete()) {
                System.out.println("SUCCESS");
            }
            else {
                System.out.println("FAIL");
            };
```

Yes, the way I solve this problem is just to delete the token file. That's about it


------------------
## GmailQuickstart
I have explained the way we implement Gmail_API and OAuth2 in the /JAVA_HttpServ_OAuth2_GmailAPI/Gmail_OAuth2_guide already, please check it if you don't understand.
Some additional clarification. In this method:

```
public String getMailByID(String mail_id) throws IOException, GeneralSecurityException{
```

My goal was to get the body content of the email. It took me quite some time to realize the structure of the received message, and how I can get the content out
of that returned Object (aka ```Message message_full```):

```
Message message_full = service.users().messages().get("me", mail_id).setFormat("full").execute();
        String encoded_body =  message_full.getPayload().getParts().get(0).getBody().getData();
        String decoded_body = new String(Base64.decodeBase64(encoded_body));
```
One thing that has helped me is to literally print out ```System.out.println(message_full.toPrettyString())``` and understand what kind of ```.get()``` was needed. 

Note: To get email content, you have to change the scope, which I have explained in /JAVA_HttpServ_OAuth2_GmailAPI/Gmail_OAuth2_guide





