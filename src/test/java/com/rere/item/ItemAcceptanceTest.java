package com.rere.item;

import com.rere.AcceptanceTest;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("아이템 관련 기능")
public class ItemAcceptanceTest extends AcceptanceTest {
    private ItemResponse JHText;
    private ItemResponse HMTextArea;
    private ItemRequest mainImage;
    private ItemRequest dateToday ;

    @BeforeEach
    public void setUp() {
        super.setUp();

        // given
        JHText = 아이템_등록되어_있음("text", "이름", "준호");
        HMTextArea = 아이템_등록되어_있음("textArea", "자기소개", "나는 한민");

        mainImage = new ItemRequest("image", "이미지", "temp.jpg");
        dateToday = new ItemRequest("date", "날짜", "2021-01-17");
    }

    @DisplayName("아이템을 생성한다.")
    @Test
    void createItem() {
        // when
        ExtractableResponse<Response> response = 아이템_생성_요청("text", "이름", "준호");

        // then
        아이템_생성됨(response);
    }

    @DisplayName("아이템을 조회한다.")
    @Test
    void getItem() {
        // when
        ExtractableResponse<Response> response = 아이템_조회_요청(HMTextArea);

        // then
        아이템_응답됨(response, HMTextArea);
    }

    @DisplayName("아이템을 삭제한다.")
    @Test
    void deleteItem() {
        // when
        ExtractableResponse<Response> response = 아이템_삭제_요청(HMTextArea);

        // then
        아이템_삭제됨(response);
    }

    @DisplayName("아이템을 수정한다.")
    @Test
    void updateItem() {
        // when
        ExtractableResponse<Response> response = 아이템_수정_요청(HMTextArea, dateToday);

        // then
        아이템_수정됨(response, HMTextArea);
    }

    private ExtractableResponse<Response> 아이템_수정_요청(ItemResponse itemResponse, ItemRequest params) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/items/" + itemResponse.getId())
                .then().log().all()
                .extract();
    }


    private ExtractableResponse<Response> 아이템_삭제_요청(ItemResponse itemResponse) {
        return RestAssured
                .given().log().all()
                .when().delete("/items/" + itemResponse.getId())
                .then().log().all()
                .extract();
    }

    private void 아이템_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 아이템_응답됨(ExtractableResponse<Response> response, ItemResponse itemResponse) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        ItemResponse resultResponse = response.as(ItemResponse.class);
        assertThat(resultResponse.getId()).isEqualTo(itemResponse.getId());
    }

    private void 아이템_수정됨(ExtractableResponse<Response> response, ItemResponse textAreaHM) {

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 아이템_조회_요청(ItemResponse itemResponse) {
        return RestAssured
                .given().log().all()
                .when().get("/items/{itemId}", itemResponse.getId())
                .then().log().all()
                .extract();
    }

    public static ItemResponse 아이템_등록되어_있음(String type, String name, String value) {
        return 아이템_생성_요청(type, name, value).as(ItemResponse.class);
    }

    public static ExtractableResponse<Response> 아이템_생성_요청(String type, String name, String value) {
        ItemRequest itemRequest = new ItemRequest(type, name, value);

        return RestAssured
                .given().log().all()
                .body(itemRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/items")
                .then().log().all()
                .extract();
    }


    public static void 아이템_생성됨(ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }
}
