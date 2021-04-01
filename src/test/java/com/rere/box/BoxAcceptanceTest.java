package com.rere.box;

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
import com.rere.image.dto.ImageRequest;
import com.rere.image.dto.ImageResponse;
import com.rere.item.domain.Item;
import com.rere.item.dto.ItemRequest;
import com.rere.item.dto.ItemResponse;
import com.rere.sortation.domain.Sortation;
import com.rere.sortation.domain.SortationRepository;
import com.rere.sortation.dto.SortationResponse;
import com.rere.user.domain.User;
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
import static com.rere.document.DocumentAcceptanceTest.문서_등록되어_있음;
import static com.rere.item.ItemAcceptanceTest.아이템_등록되어_있음;
import static com.rere.sortation.SortationAcceptanceTest.구분_등록되어_있음;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("박스 관련 기능")
public class BoxAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String NAME = "준호";

    @Autowired
    BoxRepository boxes;
    @Autowired
    DocumentRepository documents;
    @Autowired
    SortationRepository sortations;
    @Autowired
    UserRepository users;

    private TokenResponse user;
    private SortationResponse sortation;
    private DocumentResponse document;
    private BoxRequest boxRequest1;


    @BeforeEach
    public void setUp() {
        super.setUp();

        회원_등록되어_있음(EMAIL, PASSWORD, NAME);
        user = 로그인되어_있음(EMAIL, PASSWORD);
        sortation = 구분_등록되어_있음(user, "sortation");
        document = 문서_등록되어_있음(user, "document", sortation);
        boxRequest1 = new BoxRequest( "boxRQ",Document.of(document.getId(),document.getName(),document.getSortation()));

    }

    @DisplayName("아이템을 드래그한다.")
    @Test
    void dragItem() {
        Document document1 = Document.of(document.getId(),document.getName(),document.getSortation());
        Box box = boxes.findByName("boxData");

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/boxes/drag/" + box.getId() + "?itemId={itemId}&seq={seq}", 2, 0)
                .then().log().all()
                .extract();
        boxes.flush();
        ExtractableResponse<Response> response2 =박스_조회_요청(user);
//        박스_조회_요청(user,response.as(BoxResponse.class));??
        assertThat(response2.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("박스를 관리한다.")
    @Test
    void manageDocument() {
        // when
        ExtractableResponse<Response> createResponse = 박스_생성_요청(user, "박스1", Document.of(document.getId(), document.getName(), document.getSortation()));
        // then
        박스_생성됨(createResponse);

        // when
        ExtractableResponse<Response> findResponse = 박스_조회_요청(user);
        // then
        박스_조회됨(findResponse);

        // when
        ExtractableResponse<Response> oldResponse = 박스_생성_요청(user,"박스2", Document.of(document.getId(), document.getName(), document.getSortation()));

        ExtractableResponse<Response> updateResponse = 박스_수정_요청(user,oldResponse.as(BoxResponse.class), boxRequest1);
        // then
        박스_수정됨(updateResponse);

        // when
        ExtractableResponse<Response> deleteResponse = 박스_삭제_요청(user, createResponse);
        // then
        박스_삭제됨(deleteResponse);
    }

    public static ExtractableResponse<Response> 박스_생성_요청(TokenResponse tokenResponse, String name, Document document) {
        BoxRequest boxRequest = new BoxRequest(name, document);

        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(boxRequest)
                .when().post("/boxes")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 박스_조회_요청(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/boxes")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 박스_수정_요청(TokenResponse tokenResponse, BoxResponse boxResponse, BoxRequest params) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/boxes/" + boxResponse.getId())
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 박스_삭제_요청(TokenResponse tokenResponse, ExtractableResponse<Response> response) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .when().delete("/boxes/" + response.as(BoxResponse.class).getId())
                .then().log().all()
                .extract();
    }

    public static void 박스_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 박스_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 박스_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 박스_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static BoxResponse 박스_등록되어_있음(TokenResponse tokenResponse, String name, DocumentResponse document) {
        return 박스_생성_요청(tokenResponse, name, Document.of(document.getId(), document.getName(), document.getSortation()))
                .as(BoxResponse.class);
    }
}
//    @DisplayName("박스를 생성한다.")
//    @Test
//    void createItem() {
//        // when
//        User user=users.save(User.of());
//        Sortation sortation= sortations.save(Sortation.of("sortation",user));
//        Document document = documents.save(Document.of("document",sortation));
//        ExtractableResponse<Response> response = 박스_생성_요청("box",document);
//
//        // then
//        박스_생성됨(response);
//    }
//
//    @DisplayName("박스를 조회한다.")
//    @Test
//    void getItem() {
//        // when
//        ExtractableResponse<Response> response = 박스_조회_요청(box2);
//
//        // then
//        박스_응답됨(response, box2);
//    }
//
//    @DisplayName("박스를 삭제한다.")
//    @Test
//    void deleteItem() {
//        // when
//        ExtractableResponse<Response> response = 박스_삭제_요청(BoxResponse.of(box));
//
//        // then
//        박스_삭제됨(response);
//    }
//
//    @DisplayName("박스를 수정한다.")
//    @Test
//    void updateItem() {
//        // when
//        ExtractableResponse<Response> response = 박스_수정_요청(box2, box1);
//
//        // then
//        박스_수정됨(response, box2);
//    }
//
//    private ExtractableResponse<Response> 박스_수정_요청(BoxResponse boxResponse, BoxRequest params) {
//        return RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(params)
//                .when().put("/boxes/" + boxResponse.getId())
//                .then().log().all()
//                .extract();
//    }
//
//
//    private ExtractableResponse<Response> 박스_삭제_요청(BoxResponse boxResponse) {
//        return RestAssured
//                .given().log().all()
//                .when().delete("/boxes/" + boxResponse.getId())
//                .then().log().all()
//                .extract();
//    }
//
//    private void 박스_삭제됨(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
//    }
//
//    public static void 박스_응답됨(ExtractableResponse<Response> response, BoxResponse boxResponse) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//        BoxResponse resultResponse = response.as(BoxResponse.class);
//        assertThat(resultResponse.getId()).isEqualTo(boxResponse.getId());
//    }
//
//    private void 박스_수정됨(ExtractableResponse<Response> response, BoxResponse textAreaHM) {
//
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    public static ExtractableResponse<Response> 박스_조회_요청(BoxResponse boxResponse) {
//        return RestAssured
//                .given().log().all()
//                .when().get("/boxes/{boxId}", boxResponse.getId())
//                .then().log().all()
//                .extract();
//    }
//
//    public static BoxResponse 박스_등록되어_있음(String name,Document document) {
//        return 박스_생성_요청(name,document).as(BoxResponse.class);
//    }
//
//    public static ExtractableResponse<Response> 박스_생성_요청(String name, Document document) {
//        BoxRequest boxRequest = new BoxRequest(name,document);
//
//        return RestAssured
//                .given().log().all()
//                .body(boxRequest)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().post("/boxes")
//                .then().log().all()
//                .extract();
//    }
//
//
//    public static void 박스_생성됨(ExtractableResponse response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(response.header("Location")).isNotBlank();
//    }
//}
