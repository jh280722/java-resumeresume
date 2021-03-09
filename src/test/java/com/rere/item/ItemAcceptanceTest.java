package com.rere.item;

import com.rere.AcceptanceTest;
import com.rere.auth.dto.TokenResponse;
import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.box.dto.BoxResponse;
import com.rere.document.domain.Document;
import com.rere.document.domain.DocumentRepository;
import com.rere.document.dto.DocumentResponse;
import com.rere.item.domain.ItemRepository;
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
import static com.rere.box.BoxAcceptanceTest.박스_등록되어_있음;
import static com.rere.document.DocumentAcceptanceTest.문서_등록되어_있음;
import static com.rere.sortation.SortationAcceptanceTest.구분_등록되어_있음;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("아이템 관련 기능")
public class ItemAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String NAME = "준호";
    private ItemResponse JHText;
    private ItemResponse HMTextArea;
    private ItemRequest mainImage;
    private ItemRequest dateToday;

    @Autowired
    private ItemRepository items;
    @Autowired
    private BoxRepository boxes;
    @Autowired
    DocumentRepository documents;
    @Autowired
    SortationRepository sortations;
    @Autowired
    UserRepository users;

    private TokenResponse user;
    private SortationResponse sortation;
    private DocumentResponse document;
    private BoxResponse box;

    @BeforeEach
    public void setUp() {
        super.setUp();

        회원_등록되어_있음(EMAIL, PASSWORD, NAME);
        user = 로그인되어_있음(EMAIL, PASSWORD);
        sortation = 구분_등록되어_있음(user, "sortation");
        document = 문서_등록되어_있음(user, "document", sortation);
        box = 박스_등록되어_있음(user, "box", document);

//        // given
//        JHText = 아이템_등록되어_있음("text", "이름", "준호", box);
//        HMTextArea = 아이템_등록되어_있음("textArea", "자기소개", "나는 한민", box);
//
//        mainImage = new ItemRequest("image", "이미지", "temp.jpg", box);
//        dateToday = new ItemRequest("date", "날짜", "2021-01-17", box);
    }

    @DisplayName("아이템을 관리한다.")
    @Test
    void manageDocument() {
        // when
        ExtractableResponse<Response> createResponse = 아이템_생성_요청(user,"text", "이름","준호", Box.of(box.getId(), box.getName(), box.getDocument()));
        // then
        아이템_생성됨(createResponse);

        // when
        ExtractableResponse<Response> findResponse = 아이템_조회_요청(user);
        // then
        아이템_조회됨(findResponse);

//        // when
//        ExtractableResponse<Response> oldResponse = 문서_생성_요청(user, "강남역1", Sortation.of(sortation.getId(),sortation.getName(),sortation.getUser()));
//        ExtractableResponse<Response> newResponse = 문서_생성_요청(user, "강남역2", Sortation.of(sortation.getId(),sortation.getName(),sortation.getUser()));
//
//        ExtractableResponse<Response> updateResponse = 문서_수정_요청(user,oldResponse,newResponse );
//        // then
//        문서_수정됨(updateResponse);

        // when
        ExtractableResponse<Response> deleteResponse = 아이템_삭제_요청(user, createResponse);
        // then
        아이템_삭제됨(deleteResponse);
    }

    public static ExtractableResponse<Response> 아이템_생성_요청(TokenResponse tokenResponse, String type, String value, String name, Box box) {
        ItemRequest itemRequest = new ItemRequest(type, name, value, box);

        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(itemRequest)
                .when().post("/items")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 아이템_조회_요청(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/items")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 아이템_수정_요청(TokenResponse tokenResponse, ItemResponse itemResponse, ItemRequest params) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/items/" + itemResponse.getId())
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 아이템_삭제_요청(TokenResponse tokenResponse, ExtractableResponse<Response> response) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .when().delete("/items/" + response.as(ItemResponse.class).getId())
                .then().log().all()
                .extract();
    }

    public static void 아이템_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 아이템_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 아이템_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 아이템_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ItemResponse 아이템_등록되어_있음(TokenResponse tokenResponse, String type, String name, String value, BoxResponse box) {
        return 아이템_생성_요청(tokenResponse, type, name, value, Box.of(box.getId(), box.getName(), box.getDocument()))
                .as(ItemResponse.class);
    }
}
//    @DisplayName("아이템을 생성한다.")
//    @Test
//    void createItem() {
//        User user = users.save(User.of());
//        Sortation sortation = sortations.save(Sortation.of("sortation", user));
//        Document document = documents.save(Document.of("document", sortation));
//        Box box = boxes.save(Box.of("box", document));
//        // when
//        ExtractableResponse<Response> response = 아이템_생성_요청("text", "이름", "준호", box);
//
//        // then
//        아이템_생성됨(response);
//    }
//
//    @DisplayName("아이템을 조회한다.")
//    @Test
//    void getItem() {
//        // when
//        ExtractableResponse<Response> response = 아이템_조회_요청(HMTextArea);
//
//        // then
//        아이템_응답됨(response, HMTextArea);
//    }
//
//    @DisplayName("아이템을 삭제한다.")
//    @Test
//    void deleteItem() {
//        // when
//        ExtractableResponse<Response> response = 아이템_삭제_요청(HMTextArea);
//
//        // then
//        아이템_삭제됨(response);
//    }
//
//    @DisplayName("아이템을 수정한다.")
//    @Test
//    void updateItem() {
//        // when
//        ExtractableResponse<Response> response = 아이템_수정_요청(HMTextArea, dateToday);
//
//        // then
//        아이템_수정됨(response, HMTextArea);
//    }
//
//    private ExtractableResponse<Response> 아이템_수정_요청(ItemResponse itemResponse, ItemRequest params) {
//        return RestAssured
//                .given().log().all()
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(params)
//                .when().put("/items/" + itemResponse.getId())
//                .then().log().all()
//                .extract();
//    }
//
//
//    private ExtractableResponse<Response> 아이템_삭제_요청(ItemResponse itemResponse) {
//        return RestAssured
//                .given().log().all()
//                .when().delete("/items/" + itemResponse.getId())
//                .then().log().all()
//                .extract();
//    }
//
//    private void 아이템_삭제됨(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
//    }
//
//    public static void 아이템_응답됨(ExtractableResponse<Response> response, ItemResponse itemResponse) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//        ItemResponse resultResponse = response.as(ItemResponse.class);
//        assertThat(resultResponse.getId()).isEqualTo(itemResponse.getId());
//    }
//
//    private void 아이템_수정됨(ExtractableResponse<Response> response, ItemResponse textAreaHM) {
//
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    public static ExtractableResponse<Response> 아이템_조회_요청(ItemResponse itemResponse) {
//        return RestAssured
//                .given().log().all()
//                .when().get("/items/{itemId}", itemResponse.getId())
//                .then().log().all()
//                .extract();
//    }
//
//    public static ItemResponse 아이템_등록되어_있음(String type, String name, String value, Box box) {
//        return 아이템_생성_요청(type, name, value, box).as(ItemResponse.class);
//    }
//
//    public static ExtractableResponse<Response> 아이템_생성_요청(String type, String name, String value, Box box) {
//        ItemRequest itemRequest = new ItemRequest(type, name, value, box);
//
//        return RestAssured
//                .given().log().all()
//                .body(itemRequest)
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .when().post("/items")
//                .then().log().all()
//                .extract();
//    }
//
//
//    public static void 아이템_생성됨(ExtractableResponse response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
//        assertThat(response.header("Location")).isNotBlank();
//    }

