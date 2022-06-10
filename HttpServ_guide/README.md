# STEP-BY-STEP EXPLANATION

1. After copy&paste the code, you should be able to run it without installing any additional library outside "OUT-OF-THE-BOX JAVA".
2. We have the main controller, the main code that drives the entire "web app" or this HttpServer, which is the main method. What is important is:

```
server.createContext("/test", new MyHandler());
```

Basically, you can add more lines like this, each with different directory and a different (or same, but most of the time not really) Handler. I would recommend to make several new Handler class to handle each context.


Also, this part:

```
t.sendResponseHeaders(200, response.length());
```

We can replace response.length() to 0, which will tell the app to not to check the length of the response body. I would not recommend this, since the app should check for the length of the response and the actual length being printed, but for some reason, I usually get a ERR_CONTENT_LENGTH_MISMATCH. I have not figured this out yet. 


And that's about it, the rest is straight-forward enough. Good luck,
