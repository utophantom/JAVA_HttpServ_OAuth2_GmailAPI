# STEP-BY-STEP EXPLANATION
First, this will be a separate project from the HttpServ, so you should try to setup and run this app from the Google Gmail API example guide. The link is as follow:

> Website: https://developers.google.com/gmail/api/quickstart/java

> GitHub: https://github.com/googleworkspace/java-samples/blob/master/gmail/quickstart/src/main/java/GmailQuickstart.java

Remember, there is a setup step that is fully explained on the Website link, but in general, you only need the main code, the rest can be adjusted by the way you use Java
and how you manage your project. 

Next, let's understand the code for Authentication (OAuth2 only): 

```
private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
```

This method returns the Credential object type. This is not too important, because after running this method, it essentially uses the credential we provided (as a json file)
and get an access token (with a request token). The IMPORTANT PART is the token:

```
.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
```

This line essentially makes a new file in /token directory, and saves the token as a binary file in there. Also, only Owner can delete this file. Read more here:

> File implementation: https://googleapis.dev/java/google-http-client/latest/com/google/api/client/util/store/FileDataStoreFactory.html
> All possible implementation: https://googleapis.dev/java/google-http-client/latest/com/google/api/client/util/store/DataStoreFactory.html

So, if you need to re-authenticate, aka Log out, just delete this file (assume you use the "store token to a file" approach, aka FileDataStoreFactory) :D 
Learn about how OAuth2 in general works if you do not understand Credential and Token.

And finally, we have the Gmail_API: 

```
 Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
                
```

```
ListLabelsResponse listResponse = service.users().labels().list(user).execute();
```

Service represents Gmail server, and then we will add different .users() and .labels() as well as many other possible things, which simply just a way for us to modify
the request URL being sent to Gmail Server. Read more about what kind of information you can request here, and remember to change the Object that store the received value
accordingly to what kind of value you are going to get:

> Read more: https://developers.google.com/gmail/api/reference/rest/v1/users.labels/list

One CRUCIAL thing to realize is to understand the structure of the data that Gmail returns, understandable by reading their JSON representation and just call method 
to get and get and get and get until you reach what you want. I learned this the hard way, by literally System.out.println to realize this structure, as well as the
data type they return. 

Also, you have to be aware of the Gmail Scope, as Gmail will use those scope as restriction/permission. It will be on this line:

```
private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_LABELS);
```
> Check this: https://developers.google.com/gmail/api/auth/scopes
> And this: https://developers.google.com/resources/api-libraries/documentation/gmail/v1/java/latest/com/google/api/services/gmail/GmailScopes.html

And that's about it. Good luck.

