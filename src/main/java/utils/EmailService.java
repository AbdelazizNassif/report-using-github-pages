package utils;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.List;

public class EmailService {
//        String baseUri_emailService = "https://api.mail.gw/";
    String baseUri_emailService = "https://api.mail.gw/";
    String domainsEP = "domains";
    String accountsEP = "accounts";
    String tokenEp = "token";
    String messagesEP = "messages";
    String staticPassword = "someStringPassword";

    public EmailService() {
    }

    @Step("Create new inbox")
    public synchronized String createUniqueEmail(String adminName) {
        String emailAddress = adminName + "@" + getValidDomain();
        JSONObject newAccountData = new JSONObject();
        newAccountData.put("address", emailAddress);
        newAccountData.put("password", staticPassword);
        Response response = null;
        int retryCounter = 0;
        do {
            response = RestAssured.given()
                    .filter(new AllureRestAssured())
                    .baseUri(baseUri_emailService)
                    .contentType(ContentType.JSON)
                    .header("accept", "application/json")
                    .body(newAccountData)
                    .when()
                    .post(accountsEP);
            if (retryCounter > 0) new WaitUtility().waitForInterval(4000);
            retryCounter ++ ;
            if (retryCounter == 5) break;
        }
        while ( (response.statusCode() != 201) );
        Assert.assertNotNull(response.jsonPath().getString("address"));
        return response.jsonPath().getString("address");
    }

    public synchronized String getAccountToken(String accountEmail) {
        JSONObject accountData = new JSONObject();
        accountData.put("address", accountEmail);
        accountData.put("password", staticPassword);
        Response response = RestAssured.given()
                .filter(new AllureRestAssured())
                .baseUri(baseUri_emailService)
                .contentType(ContentType.JSON)
                .header("accept", "application/json")
                .body(accountData)
                .when()
                .post(tokenEp);
        response.then().statusCode(200);
        return response.jsonPath().getString("token");
    }

    @Step("Get email messages")
    public synchronized JSONObject getAllMessages(String accountEmail) {
        Response allMessageesResponse = RestAssured.given().filter(new AllureRestAssured())
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + getAccountToken(accountEmail))
                .baseUri(baseUri_emailService)
                .get(messagesEP);
        allMessageesResponse.then().statusCode(200);
        String responseText = allMessageesResponse.asString();
        JSONParser j = new JSONParser();
        JSONObject responseAsJson = null;
        try {
            responseAsJson = (JSONObject) j.parse(responseText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return responseAsJson;
    }

    @Step("Get email subjects")
    public synchronized List<String> getAllEmailsSubjects(String accountEmail) {
        List<String> allEmailSubjects  = new ArrayList<>();
        JSONArray ja = (JSONArray) getAllMessages(accountEmail).get("hydra:member");
        for (int i = 0; i < ja.size(); i++) {
            JSONObject jo = (JSONObject) ja.get(i);
            allEmailSubjects.add((String) jo.get("subject"));
        }
        return allEmailSubjects;
    }

    @Step("Get email bodies")
    public synchronized List<String> getAllEmailsBodies(String accountEmail) {
        List<String> allEmailBodies  = new ArrayList<>();
        JSONArray ja = (JSONArray) getAllMessages(accountEmail).get("hydra:member");
        for (int i = 0; i < ja.size(); i++) {
            JSONObject jo = (JSONObject) ja.get(i);
            allEmailBodies.add((String) jo.get("intro"));
        }
        return allEmailBodies;
    }
    public synchronized String getValidDomain() {
        Response getValidDomainResponse = RestAssured.given()
                .header("content-type", "application/json")
                .baseUri(baseUri_emailService)
                .get(domainsEP);
        String responseText = getValidDomainResponse.asString();
        JSONParser j = new JSONParser();
        JSONObject responseAsJson = null;
        try {
            responseAsJson = (JSONObject) j.parse(responseText);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONArray ja = (JSONArray) responseAsJson.get("hydra:member");
        System.out.println(ja);
        JSONObject jo = (JSONObject) ja.get(0);
        System.out.println(jo);
        return (String) jo.get("domain");
    }
    public synchronized String getEmailBodyByPartialContent (String emailAddress, String partOfEmailBody)
    {
        String targetedEmailBody = null;
        List<String> emailBodies = new EmailService().getAllEmailsBodies(emailAddress);
        for (int i = 0; i < emailBodies.size(); i++)
        {
            if (emailBodies.get(i).toLowerCase().contains(partOfEmailBody.toLowerCase()))
            {
                targetedEmailBody = emailBodies.get(i);
            }
        }
        return targetedEmailBody;
    }

    public synchronized boolean waitUntilInboxToHaveEmails(String accountEmail, int numberOfExpectedEmails) {
        Long actualNumberOfMessages = (Long) getAllMessages(accountEmail).get("hydra:totalItems");
        if (actualNumberOfMessages.intValue() >= numberOfExpectedEmails) {
            return true;
        } else {
            // check event status for 60 seconds
            long start = System.currentTimeMillis();
            long end = start + (45 * 1000);
            // check event status for 60 seconds
            while (System.currentTimeMillis() < end) {
                // poll on event status every 5 seconds ...
                new WaitUtility().waitForInterval(5000);
                Long currentNumberOfMessages = (Long) getAllMessages(accountEmail).get("hydra:totalItems");
                if (currentNumberOfMessages.intValue() >= numberOfExpectedEmails) {
                    return true;
                }
            }
        }
        return false;
    }

}
