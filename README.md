# SendGrid-JavaSpark
A light weight micro-service built with JavaSpark framework to send emails using SendGrid 

Steps to run:
1) Clone this repository
2) Import the project as a java project
3) Assign sendgrid api key to the variable sendGridApiKey in SendGridService.
3) Assuming that you have docker on the environment- Create a docker image using the command `docker build -t youneedsomeshanti/notification-service .`
 The dockerfile exposes the port 4567 , which is the port on which spark-java framework runs by default
4) Run the docker image using the command `docker run -p 4567:4567 youneedsomeshanti/notification-service`


To send an email:
send a post request to /email with json body:

{
"toEmail": ["email-id1", "email-id2"],
"fromEmail":"your-email-id",
"subject":"Test Email",
"body":"Hello this is a test email"
}

Hope this helps!

