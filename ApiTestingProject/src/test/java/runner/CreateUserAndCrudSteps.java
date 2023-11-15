package runner;

import FactoryRequest.FactoryRequest;
import config.Configuration;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import FactoryRequest.RequestInformation;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class CreateUserAndCrudSteps {

    Response response;
    RequestInformation requestInformation = new RequestInformation();
    Map<String, String> variables = new HashMap<>();
    Configuration configuration = new Configuration();

    @Given("I use the token of Todoly")
    public void iUseTheTokenOfTodoly() {
        String credentials = Base64.getEncoder()
                .encodeToString((configuration.getEmail()+":"+Configuration.password).getBytes());

        requestInformation.setUrl(Configuration.host+"/api/authentication/token.json")
                .setHeaders("Authorization","Basic "+credentials);
        response = FactoryRequest.make("get").send(requestInformation);

        String token = response.then().extract().path("TokenString");
        requestInformation = new RequestInformation();
        requestInformation.setHeaders("Token",token);
    }

    @Given("I don't require authentication")
    public void iDontRequireAuthentication() {
        requestInformation = new RequestInformation();
    }

    @When("I send a {} request to url {string} with the following body")
    public void iSendAPOSTRequestToUrlWithTheFollowingBody(String method, String url, String body) {
        requestInformation.setUrl(Configuration.host + replaceValues(url));
        requestInformation.setBody(body);
        response = FactoryRequest.make(method).send(requestInformation);
    }

    @Then("the response status should be {int}")
    public void theResponseStatusShouldBe(int expectedCode) {
        response.then().statusCode(expectedCode);
    }

    @And("the attribute {string} should be {string}")
    public void theAttributeShouldBe(String attribute, String expectedValue) {
        response.then().body(attribute, equalTo(expectedValue));
    }

    @And("save the {string} attribute in the variable {string}")
    public void saveTheIdAttributeInTheVariable(String attribute, String variableName) {
        variables.put(variableName, response.then().extract().path(attribute).toString());
        if (attribute.equals("Email")){
            configuration.setEmail(variables.get("Email"));
        }
    }

    private String replaceValues(String value){
        for (String key: variables.keySet()){
            value = value.replace(key, variables.get(key));
        }
        return value;
    }
}
