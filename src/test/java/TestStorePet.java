




// Testes para o método Post, Get e Delete da entidade Store

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Test;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;

public class TestStorePet {

    //Atributos de Teste
    static String ct = "application/json"; // content-type
    static String uriPet = "https://petstore.swagger.io/v2/store/order"; //URL base
    static int id = 80; // nome usuario
    static int petId = 179621001; // id do pet
    static int quantity = 1; // quantidade
    static String shipDate = "2025-07-15T16:37:53.562+0000"; // data de envio
    static String status = "finalizado"; // status
    static boolean complete = true; // completo    static String status = "finalizado"; // status
   

//Função leitura Json
public static String lerArquivoJson (String arquivoJson) throws IOException {
        //Lê arquio JSON e retorna seu contrúdp como uma string
    return new String (Files.readAllBytes(Paths.get(arquivoJson)));

}
@Test
public void testPostStore () throws IOException{
      // Ignorando a verificação de SSL usando RestAssured
    io.restassured.RestAssured.useRelaxedHTTPSValidation();

    // carregar os dados do arquivo json do usuario pet

    String jsonBody = lerArquivoJson("src/test/resources/json/Store.json");

    // Começa teste via REST - ASSURED
    given()
        .contentType(ct)
        .body(jsonBody)
    .when()
        .post(uriPet)
    .then()
        .statusCode(200)
        .body("id", is(id))
        .body("petId", is(petId))
        .body("quantity", is(quantity))
        .body("shipDate", is(shipDate))
        .body("status", is(status))
        .body("complete", is(complete))
        .extract().response();
}

    @Test
    public void testGetStore () throws IOException{
    
     // Ignorando a verificação de SSL usando RestAssured
    io.restassured.RestAssured.useRelaxedHTTPSValidation();

    given()
        .contentType(ct)
        .log().all()
    .when()
        .get(uriPet + "/" + id)
    .then()
        .log().all()
        .statusCode(200)
        .body("id", is(id))
        .body("petId", is(petId))
        .body("quantity", is(quantity))
        .body("shipDate", is(shipDate))
        .body("status", is(status))
        .body("complete", is(complete))
        .extract().response();

}

@Test
public void testDeleteStore () throws IOException{
    // Ignorando a verificação de SSL usando RestAssured
    io.restassured.RestAssured.useRelaxedHTTPSValidation();

    given()
        .contentType(ct)
        .log().all()
    .when()
        .delete(uriPet + "/" + id)
    .then()
        .log().all()
        .statusCode(200)
        .body("code", is(200))
        .body("type", is("unknown"))
        .body("message", is(String.valueOf(id)))
        .extract().response();

}

@ParameterizedTest
@CsvFileSource (resources = "/csv/petMassaStore.csv", numLinesToSkip = 1, delimiter = ',')//numLinesToSkip = 1 ignora o cabeçalho do CSV )

public void testPostStorePetDDT(int Id, int petId, int quantity, String shipDate, String status, boolean complete) throws IOException {

    // Ignorando a verificação de SSL usando RestAssured
    io.restassured.RestAssured.useRelaxedHTTPSValidation();

    // Monta o corpo JSON dinamicamente usando os parâmetros do teste
    String jsonBody = String.format(
        "{ \"id\": %d, \"petId\": %d, \"quantity\": %d, \"shipDate\": \"%s\", \"status\": \"%s\", \"complete\": %s }",
        Id, petId, quantity, shipDate, status, complete);

    // Começa teste via REST - ASSURED
    given()
        .contentType(ct)
        .body(jsonBody)
    .when()
        .post(uriPet)
    .then()
        .statusCode(200)
        .body("id", is(Id))
        .body("petId", is(petId))
        .body("quantity", is(quantity))
        .body("shipDate", is(shipDate))
        .body("status", is(status))
        .body("complete", is(complete))
        .extract().response();
}
}
