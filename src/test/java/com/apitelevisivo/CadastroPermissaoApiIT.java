package com.apitelevisivo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CadastroPermissaoApiIT {

    @LocalServerPort
    private int port;

    @Test
    public void retornarStatus200Listar() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.given().basePath("/api/permissao").port(port).accept(ContentType.JSON).when().get("/listar").then().statusCode(HttpStatus.OK.value());
    }

    @Test
    public void retornarStatus201Cadastrar() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.given().basePath("/api/permissao").body("{ \"nome\":\"GUEST\"}").port(port).contentType(ContentType.JSON).accept(ContentType.JSON).when().post("/adicionar").then().statusCode(HttpStatus.CREATED.value());
    }
}