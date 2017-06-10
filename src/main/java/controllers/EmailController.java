package controllers;

import static spark.Spark.get;
import static spark.Spark.post;
import Services.SendGridService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import model.*;
import org.json.JSONObject;

public class EmailController {

    private static final int HTTP_BAD_REQUEST = 400;
    private static final int HTTP_INTERNAL_SERVER_ERROR = 500;

    public static void main( String[] args) {

        // insert a post (using HTTP post method)
        post("/email", (request, response) -> {
            try {
                ObjectMapper mapper = new ObjectMapper();
                Email incomingEmailRequestBody = mapper.readValue(request.body(), Email.class);
                if (!incomingEmailRequestBody.isValid()) {
                    response.status(HTTP_BAD_REQUEST);
                    return "";
                }
                SendGridService sendGridService = new SendGridService();
                JSONObject preparedSendGridRequestJson = sendGridService .convertToSendGridRequestJson(incomingEmailRequestBody);
                HttpResponse<String> sendGridResponse = sendGridService.
                    sendEmailRequestToSendGrid(preparedSendGridRequestJson);

                response.status(sendGridResponse.getStatus());
                response.type("application/json");
                return "Email Sent Sucessfully";
            } catch (JsonParseException jpe) {
                response.status(HTTP_BAD_REQUEST);
                return "Please fix your json";
            }

            catch (UnirestException unirestExeption) {
                response.status(HTTP_INTERNAL_SERVER_ERROR);
                return "Unirest Issues";
            }

            catch (Exception ex) {
                response.status(HTTP_INTERNAL_SERVER_ERROR);
                return "Something is wrong, please try again later";
            }
        });

        // get all post (using HTTP get method)
        get("/ping", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return "OK";
        });
    }
}

