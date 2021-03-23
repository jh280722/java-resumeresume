package com.rere.image;

import com.rere.AcceptanceTest;
import com.rere.auth.dto.TokenResponse;
import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.box.dto.BoxResponse;
import com.rere.document.domain.DocumentRepository;
import com.rere.document.dto.DocumentResponse;
import com.rere.image.dto.ImageRequest;
import com.rere.image.dto.ImageResponse;
import com.rere.item.domain.Item;
import com.rere.item.domain.ItemRepository;
import com.rere.item.dto.ItemRequest;
import com.rere.item.dto.ItemResponse;
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
import static com.rere.box.BoxAcceptanceTest.박스_등록되어_있음;
import static com.rere.document.DocumentAcceptanceTest.문서_등록되어_있음;
import static com.rere.item.ItemAcceptanceTest.아이템_등록되어_있음;
import static com.rere.sortation.SortationAcceptanceTest.구분_등록되어_있음;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("이미지 관련 기능")
public class ImageAcceptanceTest extends AcceptanceTest {
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
    private ItemResponse item;
    private ImageRequest imageRequest1;

    @BeforeEach
    public void setUp() {
        super.setUp();

        회원_등록되어_있음(EMAIL, PASSWORD, NAME);
        user = 로그인되어_있음(EMAIL, PASSWORD);
        sortation = 구분_등록되어_있음(user, "sortation");
        document = 문서_등록되어_있음(user, "document", sortation);
        box = 박스_등록되어_있음(user, "box", document);
        item = 아이템_등록되어_있음(user, "item","이름","준호", box);

        imageRequest1 = new ImageRequest("imageRQ", 0L, 0L, "String path", "String description",Item.of(item.getId(),item.getSeq(),item.getType(),item.getName(),item.getValue(),item.getBox(),item.getRowSize(),item.getColSize()));
    }

    @DisplayName("이미지를 관리한다.")
    @Test
    void manageImage() {
        // when
        ExtractableResponse<Response> createResponse = 이미지_생성_요청(user,"imageTest", 0L,0L,"path" ,"description",Item.of(item.getId(),item.getSeq(),item.getType(),item.getName(),item.getValue(),item.getBox(),item.getRowSize(),item.getColSize()));
        // then
        이미지_생성됨(createResponse);

        // when
        ExtractableResponse<Response> findResponse = 이미지_조회_요청(user);
        // then
        이미지_조회됨(findResponse);

        // when
        ExtractableResponse<Response> oldResponse = 이미지_생성_요청(user,"imageTest", 0L,0L,"path" ,"description",Item.of(item.getId(),item.getSeq(),item.getType(),item.getName(),item.getValue(),item.getBox(),item.getRowSize(),item.getColSize()));

        ExtractableResponse<Response> updateResponse = 이미지_수정_요청(user,oldResponse.as(ImageResponse.class), imageRequest1);
        // then
        이미지_수정됨(updateResponse);

        // when
        ExtractableResponse<Response> deleteResponse = 이미지_삭제_요청(user, createResponse);
        // then
        이미지_삭제됨(deleteResponse);
    }

    public static ExtractableResponse<Response> 이미지_생성_요청(TokenResponse tokenResponse, String name, Long width, Long height, String path, String description, Item item) {
        ImageRequest imageRequest = new ImageRequest(name,width,height,path,description,item);

        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(imageRequest)
                .when().post("/images")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 이미지_조회_요청(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/images")
                .then().log().all()
                .extract();
    }

    private ExtractableResponse<Response> 이미지_수정_요청(TokenResponse tokenResponse, ImageResponse imageResponse, ImageRequest params) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/images/" + imageResponse.getId())
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 이미지_삭제_요청(TokenResponse tokenResponse, ExtractableResponse<Response> response) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .when().delete("/images/" + response.as(ImageResponse.class).getId())
                .then().log().all()
                .extract();
    }

    public static void 이미지_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 이미지_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    private void 이미지_수정됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 이미지_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static ItemResponse 이미지_등록되어_있음(TokenResponse tokenResponse, String name, Long width, Long height, String path, String description, Item item) {
        return 이미지_생성_요청(tokenResponse, name, width, height, path,description,Item.of(item.getSeq(), item.getName(),item.getType(),item.getValue() ,item.getBox(),item.getRowSize(),item.getColSize()))
                .as(ItemResponse.class);
    }
}
