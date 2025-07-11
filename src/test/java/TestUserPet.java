
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.jar.Attributes.Name;

import static org.hamcrest.CoreMatchers.is;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;


// Testes para o método Post, Get, Put e Delete da entidade User
public class TestUserPet {


    //Atributos de Teste
    static String ct = "application/json"; // content-type
    static String uriPet = "https://petstore.swagger.io/v2/user"; //URL base
    static String userName = "Emerson"; // nome usuario



    //Função leitura Json
    public static String lerArquivoJson (String arquivoJson) throws IOException {
        //Lê arquio JSON e retorna seu contrúdp como uma string
        return new String (Files.readAllBytes(Paths.get(arquivoJson)));
    }
    
    @Test
    public void testPostUser () throws IOException{
      // Ignorando a verificação de SSL usando RestAssured
    io.restassured.RestAssured.useRelaxedHTTPSValidation();

    // carregar os dados do arquivo json do usuario pet

    String jsonBody = lerArquivoJson("src/test/resources/json/User1.json");

    // Começa teste via REST - ASSURED
    given()
      .contentType(ct)
      .log().all()
      .body(jsonBody)
    .when()
      .post(uriPet + "/" + "createWithList")
    .then()
      .log().all()
      .statusCode(200)
      .body("type", is ("unknown"))
      .body("message",is("ok"))
      .extract().response();

    }
 

    @Test
    public void testGetUser () throws IOException{
    
     // Ignorando a verificação de SSL usando RestAssured
    io.restassured.RestAssured.useRelaxedHTTPSValidation();

    given()
      .contentType(ct)
      .log().all()
    .when()
      .get(uriPet + "/" + userName)
    .then()
      .log().all()
      .statusCode(200)
      .extract().response();

    }

   @Test
   public void testPutUser () throws IOException{
    
     // Ignorando a verificação de SSL usando RestAssured
    io.restassured.RestAssured.useRelaxedHTTPSValidation();

    // carregar os dados do arquivo json do usuario pet

    String jsonBody = lerArquivoJson("src/test/resources/json/User2.json");

    // Começa teste via REST - ASSURED
    given()
      .contentType(ct)
      .log().all()
      .body(jsonBody)
    .when()
      .post(uriPet + "/" + "createWithList")
    .then()
      .log().all()
      .statusCode(200)
      .body("type", is ("unknown"))
      .body("message",is("ok"))
      .extract().response();

    }

   @Test
   public void testDeleteUser () throws IOException{
    
     // Ignorando a verificação de SSL usando RestAssured
    io.restassured.RestAssured.useRelaxedHTTPSValidation();

    given()
      .contentType(ct)
      .log().all()
    .when()
      .delete(uriPet + "/" + userName)
    .then()
      .log().all()
      .statusCode(200)
      .body("type", is ("unknown"))
      .body("message",is(userName))
      .extract().response();
    
  }


 //  Teste Data Driven para o Post da User
 @ParameterizedTest
 @CsvFileSource(resources = "/csv/petMassa.csv", numLinesToSkip = 1, delimiter = ',')//numLinesToSkip = 1 ignora o cabeçalho do CSV
// O arquivo CSV deve estar localizado em src/test/resources/csv/petMassa.csv
public void testPostUserPetDDT(int userId, String userName, String firstName, String lastName, String email, String password, String phone, int userStatus){
  io.restassured.RestAssured.useRelaxedHTTPSValidation(); // Ignorando a verificação de SSL usando RestAssured

  String jsonBody = String.format(
    "{ \"id\": %d, \"username\": \"%s\", \"firstName\": \"%s\", \"lastName\": \"%s\", \"email\": \"%s\", \"password\": \"%s\", \"phone\": \"%s\", \"status\": %d }",
    userId, userName, firstName, lastName, email, password, phone, userStatus 
  ); 



  // Começa teste via REST - ASSURED
    // Teste de dados - Data Driven Testing
    given()
        .contentType(ct) // Tipo de conteudo é ct (aplication json)
        .log().all() // mostra tudo na ida
        .body(jsonBody) // envie o corpo da requisição

    .when()
        .post(uriPet) // Use POST to create the user

    .then() // Então
        .log().all() // mostre tudo na volta
        .statusCode(200)
        .extract().response(); ;// verifica se status code é 200

}


}






