package simulacaoDeCredito;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.CoreMatchers.containsString;

public class restricoes {

    @Test
    public void cpfSemRestricaoTest() {
        given()
                .contentType("application/json")
                .when()
                .get("http://localhost:8080/api/v1/restricoes/31602956804")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    public void cpfComRestricaoTest() {
        given()
                .contentType("application/json")
                .when()
                .get("http://localhost:8080/api/v1/restricoes/97093236014")
                .then()
                .log().all()
                .statusCode(200)
                .body(containsString("O CPF 97093236014 tem problema"));
    }

}
