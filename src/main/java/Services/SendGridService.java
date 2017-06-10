package Services;


import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequestWithBody;
import model.Email;
import org.json.JSONArray;
import org.json.JSONObject;

public class SendGridService {
    String sendGridUrl = "https://api.sendgrid.com/v3/mail/send";
    String sendGridApiKey = "YOUR SENDGRID API KEY";

    public JSONObject convertToSendGridRequestJson (Email email) {

        JSONArray personalizationsArray = new JSONArray();
        JSONObject personalizationObject = new JSONObject();
        JSONArray sendTo = new JSONArray();
        for (String toEmailAddress: email.getToEmail()) {
            JSONObject toJsonObject = new JSONObject();
            toJsonObject.put("email", toEmailAddress);
            sendTo.put(toJsonObject);
        }
        personalizationObject.put("to", sendTo);
        personalizationObject.put("subject", email.getSubject());
        personalizationsArray.put(personalizationObject);

        JSONArray contentArray = new JSONArray();
        JSONObject contentJsonObject = new JSONObject();
        contentJsonObject.put("type", "text/plain");
        contentJsonObject.put("value", email.getBody());
        contentArray.put(contentJsonObject);

        JSONObject sendGridEmailRequestJson = new JSONObject();
        sendGridEmailRequestJson.put("personalizations", personalizationsArray);
        sendGridEmailRequestJson.put("from", new JSONObject().put("email", email.getFromEmail()));
        sendGridEmailRequestJson.put("content", contentArray);

        return sendGridEmailRequestJson;
    }

    public HttpResponse<String> sendEmailRequestToSendGrid (JSONObject requestbody) throws UnirestException {
        try {
            HttpRequestWithBody requestWithBody = Unirest.post(sendGridUrl);
            requestWithBody.header("Content-Type", "application/json");
            requestWithBody.header("Authorization", "bearer" + " " + sendGridApiKey );
            requestWithBody.body(requestbody);
            HttpResponse<String> response = requestWithBody.asString();
            return response;
        } catch (Exception ex) {
            return null;
        }
    }

}
