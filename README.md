# JAVA_HttpServ_OAuth2_GmailAPI
This repository gives an example of using HttpServ, OAuth2 and Gmail API in Java. The purpose of this code is mainly for demonstration and reference only.

In this repo, I will not upload my entire project, but instead only the main/essential files. The reason for this is because I believe there will always be version differences (in libraries, IDE, ...) across different time people accessing this repo. **THIS WILL BE A GUIDE ON HOW THIS PROJECT WAS INSTALLED AND CODED**, so people can follow and learn the implementation, which is more valuable than just downloading and running the code. 

## BACKGROUND
I had an internship and this was my first assignment: 
> show me that you can use Google OAuth2 to authenticate, and access a user's mail box, ONLY NATIVE JAVA (no framework) -> THIS IS OUR GOAL.

## HOW-TO START

1. First, I need to make a simple Http Server to take and send request. The simplest method was to use HttpServ built-in Java library. 

> Check this -> https://stackoverflow.com/questions/3732109/simple-http-server-in-java-using-only-java-se-api

Alternatively, check my HttpServ_guide directory and read README for baby-step explanation.


2. Gmail_API + OAuth2 

After understanding that, I checked this starter guide for Gmail_API, which **INCLUDES** the OAuth2 implementation from Google. This is important to understand. 

> Check this starter code here -> https://developers.google.com/gmail/api/quickstart/java 

Alternatively, you can check my Gmail_OAuth2_guide directory for baby-step explanation. 


3. And finally, the last step was to just combine everything, make a little tweak here and there, and voila, I passed my first assignement (an entrance test from my boss, actually)

I will explain in more detail in the "final" directory. A lot of things were needed to modify, and I can only cover the main things, so make sure to have a basic understanding of the two previous concepts before proceeding to my final directory.

## FINAL
Have a nice day,

P/s: I am in no way near "a professional", and my code + this repo only shows my learning progress, so please forgive me if I have stated something wrong. 
