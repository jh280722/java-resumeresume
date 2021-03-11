package com.rere.document;

import com.rere.AcceptanceTest;
import com.rere.auth.dto.TokenResponse;
import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.box.dto.BoxRequest;
import com.rere.box.dto.BoxResponse;
import com.rere.document.domain.Document;
import com.rere.document.domain.DocumentRepository;
import com.rere.document.dto.DocumentRequest;
import com.rere.document.dto.DocumentResponse;
import com.rere.item.dto.ItemRequest;
import com.rere.item.dto.ItemResponse;
import com.rere.sortation.domain.Sortation;
import com.rere.sortation.domain.SortationRepository;
import com.rere.sortation.dto.SortationResponse;
import com.rere.user.domain.UserRepository;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.rere.auth.AuthAcceptanceTest.로그인되어_있음;
import static com.rere.auth.AuthAcceptanceTest.회원_등록되어_있음;
import static com.rere.box.BoxAcceptanceTest.박스_생성_요청;
import static com.rere.box.BoxAcceptanceTest.박스_생성됨;
import static com.rere.item.ItemAcceptanceTest.아이템_생성_요청;
import static com.rere.item.ItemAcceptanceTest.아이템_생성됨;
import static com.rere.sortation.SortationAcceptanceTest.구분_등록되어_있음;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("문서 관련 기능")
public class DocumentAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String NAME = "준호";

    @Autowired
    BoxRepository boxes;
    @Autowired
    private DocumentRepository documents;
    @Autowired
    SortationRepository sortations;
    @Autowired
    UserRepository users;


    private TokenResponse user;
    private SortationResponse sortation;

    private BoxRequest box1;
    private DocumentRequest document1;
    private BoxResponse box2;
    private DocumentResponse document2;
    private ItemResponse JHText;
    private ItemResponse HMTextArea;
    private ItemRequest mainImage;
    private ItemRequest dateToday;
    private Box box;

    @BeforeEach
    public void setUp() {
        super.setUp();
        // given
        회원_등록되어_있음(EMAIL, PASSWORD, NAME);
        user = 로그인되어_있음(EMAIL, PASSWORD);
        sortation = 구분_등록되어_있음(user, "sortation");


//        box = boxes.save(Box.of("box", document));
//        JHText = 아이템_등록되어_있음("text", "이름", "준호", box);
//        HMTextArea = 아이템_등록되어_있음("textArea", "자기소개", "나는 한민", box);
//
//        box2 = 박스_등록되어_있음("box2", document);
//        document2 = 문서_등록되어_있음("document2",sortation);
//        document1 = new DocumentRequest("document1", sortation);

    }

    @DisplayName("문서를 관리한다.")
    @Test
    void manageDocument() {
        // when
        ExtractableResponse<Response> createResponse = 문서_생성_요청(user, "강남역", Sortation.of(sortation.getId(),sortation.getName(),sortation.getUser()));
        // then
        문서_생성됨(createResponse);

        // when
        ExtractableResponse<Response> findResponse = 문서_조회_요청(user);
        // then
        문서_조회됨(findResponse);

//        // when
//        ExtractableResponse<Response> oldResponse = 문서_생성_요청(user, "강남역1", Sortation.of(sortation.getId(),sortation.getName(),sortation.getUser()));
//        ExtractableResponse<Response> newResponse = 문서_생성_요청(user, "강남역2", Sortation.of(sortation.getId(),sortation.getName(),sortation.getUser()));
//
//        ExtractableResponse<Response> updateResponse = 문서_수정_요청(user,oldResponse,newResponse );
//        // then
//        문서_수정됨(updateResponse);

        // when
        ExtractableResponse<Response> deleteResponse = 문서_삭제_요청(user, createResponse);
        // then
        문서_삭제됨(deleteResponse);
    }

    public static ExtractableResponse<Response> 문서_생성_요청(TokenResponse tokenResponse, String name, Sortation sortation) {
        DocumentRequest documentRequest = new DocumentRequest(name, sortation);

        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(documentRequest)
                .when().post("/documents")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 문서_조회_요청(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/documents")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 문서_수정_요청(TokenResponse tokenResponse, DocumentResponse documentResponse, DocumentRequest params) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/documents/" + documentResponse.getId())
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 문서_삭제_요청(TokenResponse tokenResponse, ExtractableResponse<Response> response) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .when().delete("/documents/" + response.as(DocumentResponse.class).getId())
                .then().log().all()
                .extract();
    }

    public static void 문서_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 문서_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 문서_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 문서_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static DocumentResponse 문서_등록되어_있음(TokenResponse tokenResponse, String name, SortationResponse sortation) {
        return 문서_생성_요청(tokenResponse, name, Sortation.of(sortation.getId(),sortation.getName(),sortation.getUser()))
                .as(DocumentResponse.class);
    }
//    @DisplayName("문서를 생성한다.")
//    @Test
//    void createItem() {
//        // when
//        ExtractableResponse<Response> response = 문서_생성_요청("document", sortation);
//
//        // then
//        문서_생성됨(response);
//    }
//
//    @DisplayName("문서를 조회한다.")
//    @Test
//    void getItem() {
//        // when
//        ExtractableResponse<Response> response = 문서_조회_요청(document2);
//
//        // then
//        문서_응답됨(response, document2);
//    }
//
//    @DisplayName("문서를 삭제한다.")
//    @Test
//    void deleteItem() {
//        // when
//        ExtractableResponse<Response> response = 문서_삭제_요청(document2);
//
//        // then
//        문서_삭제됨(response);
//    }
//
//    @DisplayName("문서를 수정한다.")
//    @Test
//    void updateItem() {
//        // when
//        ExtractableResponse<Response> response = 문서_수정_요청(document2, document1);
//
//        // then
//        문서_수정됨(response, document2);
//    }
//
//    private ExtractableResponse<Response> 문서_수정_요청(DocumentResponse documentResponse, DocumentRequest params) {
//        return RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(params)
//                .when().put("/documents/" + documentResponse.getId())
//                .then().log().all()
//                .extract();
//    }
//
//
//    private ExtractableResponse<Response> 문서_삭제_요청(DocumentResponse documentResponse) {
//        return RestAssured
//                .given().log().all()
//                .when().delete("/documents/" + documentResponse.getId())
//                .then().log().all()
//                .extract();
//    }
//
//    private void 문서_삭제됨(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
//    }
//
//    public static void 문서_응답됨(ExtractableResponse<Response> response, DocumentResponse documentResponse) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//        DocumentResponse resultResponse = response.as(DocumentResponse.class);
//        assertThat(resultResponse.getId()).isEqualTo(documentResponse.getId());
//    }
//
//    private void 문서_수정됨(ExtractableResponse<Response> response, DocumentResponse textAreaHM) {
//
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    public static ExtractableResponse<Response> 문서_조회_요청(DocumentResponse documentResponse) {
//        return RestAssured
//                .given().log().all()
//                .when().get("/documents/{documentId}", documentResponse.getId())
//                .then().log().all()
//                .extract();
//    }
//
//    public static DocumentResponse 문서_등록되어_있음(String name, Sortation sortation) {
//        return 문서_생성_요청(name, sortation).as(DocumentResponse.class);
//    }
//
//    public static ExtractableResponse<Response> 문서_생성_요청(String name, Sortation sortation) {
//        DocumentRequest documentRequest = new DocumentRequest(name, sortation);
//
//        return RestAssured
//                .given().log().all()
//                .body(documentRequest)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().post("/documents")
//                .then().log().all()
//                .extract();
//    }
//
//
//    public static void 문서_생성됨(ExtractableResponse response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(response.header("Location")).isNotBlank();
//    }
}
