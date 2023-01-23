package simulacaoDeCredito;

import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.CoreMatchers.containsString;

public class simulacoes {

    @Test
    public void cadastroSimulacao() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"nome\": \"Ana Maria Braga\",\n" +
                        "  \"cpf\": \"99565883001\",\n" +
                        "  \"email\": \"maisvoce@maisvoce.com\",\n" +
                        "  \"valor\": 3000,\n" +
                        "  \"parcelas\": 3,\n" +
                        "  \"seguro\": true\n" +
                        "}")
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/simulacoes")
                .then()
                .body("nome", is("Ana Maria Braga"))
                .body("cpf", is("99565883001"))
                .body("email", is("maisvoce@maisvoce.com"))
                .body("valor", is(3000))
                .body("parcelas", is(3))
                .body("seguro", is(true))
                .log().all()
                .statusCode(201);
    }

    @Test
    public void cadastroSimulacaoComFalha() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"nome\": \"Ana Maria\",\n" +
                        "  \"email\": \"maisvoce@maisvoce.com\",\n" +
                        "  \"valor\": 3000,\n" +
                        "  \"parcelas\": 3,\n" +
                        "  \"seguro\": true\n" +
                        "}")
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/simulacoes")
                .then()
                .body(containsString("CPF não pode ser vazio"))
                .log().all()
                .statusCode(400);
    }

    @Test
    public void cadastroSimulacaoCpfExistente() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"nome\": \"Ana Maria Braga\",\n" +
                        "  \"cpf\": \"99565883001\",\n" +
                        "  \"email\": \"maisvoce@maisvoce.com\",\n" +
                        "  \"valor\": 3000,\n" +
                        "  \"parcelas\": 3,\n" +
                        "  \"seguro\": true\n" +
                        "}")
                .log().all()
                .when()
                .post("http://localhost:8080/api/v1/simulacoes")
                .then()
                .body(containsString("CPF duplicado"))
                .log().all()
                .statusCode(400);
    }

    @Test
    public void alteracaoSimulacao() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"nome\": \"Ana Maria Braga\",\n" +
                        "  \"cpf\": \"99565883001\",\n" +
                        "  \"email\": \"maisvoce@desafio.com\",\n" +
                        "  \"valor\": 4000,\n" +
                        "  \"parcelas\": 3,\n" +
                        "  \"seguro\": true\n" +
                        "}")
                .log().all()
                .when()
                .put("http://localhost:8080/api/v1/simulacoes/99565883001")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void alteracaoSimulacaoCpfNaoEncontrado() {
        given()
                .contentType("application/json")
                .body("{\n" +
                        "  \"nome\": \"Ana Maria Braga\",\n" +
                        "  \"cpf\": \"89845637060\",\n" +
                        "  \"email\": \"maisvoce@desafio.com.br\",\n" +
                        "  \"valor\": 4000,\n" +
                        "  \"parcelas\": 3,\n" +
                        "  \"seguro\": true\n" +
                        "}")
                .log().all()
                .when()
                .put("http://localhost:8080/api/v1/simulacoes/89845637060")
                .then()
                .log().all()
                .body(containsString("CPF 89845637060 não encontrado"))
                .statusCode(404);
    }

    @Test
    public void simulacoesCadastradas() {
        given()
                .contentType("application/json")
                .log().all()
                .when()
                .get("http://localhost:8080/api/v1/simulacoes")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void consultaCpf() {
        given()
                .contentType("application/json")
                .log().all()
                .when()
                .get("http://localhost:8080/api/v1/simulacoes/99565883001")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void consultaCpfInexistente() {
        given()
                .contentType("application/json")
                .log().all()
                .when()
                .get("http://localhost:8080/api/v1/simulacoes/31602956805")
                .then()
                .log().all()
                .body(containsString("CPF 31602956805 não encontrado"))
                .statusCode(404);
    }

    @Test
    public void exclusaoIdSimulacao() {
        given()
                .contentType("application/json")
                .log().all()
                .when()
                .delete("http://localhost:8080/api/v1/simulacoes/15")
                .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void exclusaoIdInexistente() {
        given()
                .contentType("application/json")
                .log().all()
                .when()
                .delete("http://localhost:8080/api/v1/simulacoes/28")
                .then()
                .log().all()
                .statusCode(404);
    }
}
