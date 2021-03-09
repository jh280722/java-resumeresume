//package com.rere.tableItem;
//
//import com.rere.AcceptanceTest;
//import com.rere.auth.dto.TokenResponse;
//import com.rere.box.domain.Box;
//import com.rere.box.domain.BoxRepository;
//import com.rere.box.dto.BoxResponse;
//import com.rere.document.domain.DocumentRepository;
//import com.rere.document.dto.DocumentResponse;
//import com.rere.item.domain.Item;
//import com.rere.item.domain.ItemRepository;
//import com.rere.item.dto.ItemRequest;
//import com.rere.item.dto.ItemResponse;
//import com.rere.sortation.domain.SortationRepository;
//import com.rere.sortation.dto.SortationResponse;
//import com.rere.table.domain.Table;
//import com.rere.tableItem.dto.TableItemRequest;
//import com.rere.tableItem.dto.TableItemResponse;
//import com.rere.user.domain.UserRepository;
//import io.restassured.RestAssured;
//import io.restassured.response.ExtractableResponse;
//import io.restassured.response.Response;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//
//import static com.rere.auth.AuthAcceptanceTest.로그인되어_있음;
//import static com.rere.auth.AuthAcceptanceTest.회원_등록되어_있음;
//import static com.rere.box.BoxAcceptanceTest.박스_등록되어_있음;
//import static com.rere.document.DocumentAcceptanceTest.문서_등록되어_있음;
//import static com.rere.sortation.SortationAcceptanceTest.구분_등록되어_있음;
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DisplayName("테이블아이템 관련 기능")
//public class TableItemAcceptanceTest extends AcceptanceTest {
//    private static final String EMAIL = "email@email.com";
//    private static final String PASSWORD = "password";
//    private static final String NAME = "준호";
//    private ItemResponse JHText;
//    private ItemResponse HMTextArea;
//    private ItemRequest mainImage;
//    private ItemRequest dateToday;
//
//    @Autowired
//    private ItemRepository items;
//    @Autowired
//    private BoxRepository boxes;
//    @Autowired
//    DocumentRepository documents;
//    @Autowired
//    SortationRepository sortations;
//    @Autowired
//    UserRepository users;
//
//    private TokenResponse user;
//    private SortationResponse sortation;
//    private DocumentResponse document;
//    private BoxResponse box;
//    private TableResponse table;
//
//    @BeforeEach
//    public void setUp() {
//        super.setUp();
//
//        회원_등록되어_있음(EMAIL, PASSWORD, NAME);
//        user = 로그인되어_있음(EMAIL, PASSWORD);
//        sortation = 구분_등록되어_있음(user, "sortation");
//        document = 문서_등록되어_있음(user, "document", sortation);
//        box = 박스_등록되어_있음(user, "box", document);
//        table = 테이블_안만들었네(user,"table",box);
//    }
//
//    @DisplayName("테이블아이템을 관리한다.")
//    @Test
//    void manageDocument() {
//        // when
//        ExtractableResponse<Response> createResponse = 테이블아이템_생성_요청(user,"text", "이름","준호", Box.of(box.getId(), box.getName(), box.getDocument()));
//        // then
//        테이블아이템_생성됨(createResponse);
//
//        // when
//        ExtractableResponse<Response> findResponse = 테이블아이템_조회_요청(user);
//        // then
//        테이블아이템_조회됨(findResponse);
//
////        // when
////        ExtractableResponse<Response> oldResponse = 문서_생성_요청(user, "강남역1", Sortation.of(sortation.getId(),sortation.getName(),sortation.getUser()));
////        ExtractableResponse<Response> newResponse = 문서_생성_요청(user, "강남역2", Sortation.of(sortation.getId(),sortation.getName(),sortation.getUser()));
////
////        ExtractableResponse<Response> updateResponse = 문서_수정_요청(user,oldResponse,newResponse );
////        // then
////        문서_수정됨(updateResponse);
//
//        // when
//        ExtractableResponse<Response> deleteResponse = 테이블아이템_삭제_요청(user, createResponse);
//        // then
//        테이블아이템_삭제됨(deleteResponse);
//    }
//
//    public static ExtractableResponse<Response> 테이블아이템_생성_요청(TokenResponse tokenResponse, Long row, Long column, String value, Item item) {
//        TableItemRequest tableItemRequest = new TableItemRequest(row, column, value, item);
//
//        return RestAssured
//                .given().log().all()
//                .auth().oauth2(tokenResponse.getAccessToken())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(tableItemRequest)
//                .when().post("/tableItems")
//                .then().log().all()
//                .extract();
//    }
//
//    public static ExtractableResponse<Response> 테이블아이템_조회_요청(TokenResponse tokenResponse) {
//        return RestAssured
//                .given().log().all()
//                .auth().oauth2(tokenResponse.getAccessToken())
//                .accept(MediaType.APPLICATION_JSON_VALUE)
//                .when().get("/tableItems")
//                .then().log().all()
//                .extract();
//    }
//
//    private ExtractableResponse<Response> 테이블아이템_수정_요청(TokenResponse tokenResponse, TableItemResponse tableItemResponse, TableItemRequest params) {
//        return RestAssured
//                .given().log().all()
//                .auth().oauth2(tokenResponse.getAccessToken())
//                .contentType(MediaType.APPLICATION_JSON_VALUE)
//                .body(params)
//                .when().put("/tableItems/" + tableItemResponse.getId())
//                .then().log().all()
//                .extract();
//    }
//
//    public static ExtractableResponse<Response> 테이블아이템_삭제_요청(TokenResponse tokenResponse, ExtractableResponse<Response> response) {
//        return RestAssured
//                .given().log().all()
//                .auth().oauth2(tokenResponse.getAccessToken())
//                .when().delete("/tableItems/" + response.as(TableItemResponse.class).getId())
//                .then().log().all()
//                .extract();
//    }
//
//    public static void 테이블아이템_생성됨(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
//    }
//
//    public static void 테이블아이템_조회됨(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    private void 테이블아이템_수정됨(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
//    }
//
//    public static void 테이블아이템_삭제됨(ExtractableResponse<Response> response) {
//        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
//    }
//
//    public static TableItemResponse 테이블아이템_등록되어_있음(TokenResponse tokenResponse, Long row, Long column, String value, TableResponse table) {
//        return 테이블아이템_생성_요청(tokenResponse, row, column, value, Box.of(table.getId(), table.getName(), table.getBox()))
//                .as(ItemResponse.class);
//    }
//}
