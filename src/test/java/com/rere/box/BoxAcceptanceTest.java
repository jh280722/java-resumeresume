package com.rere.box;

import com.rere.AcceptanceTest;
import com.rere.item.Item;
import com.rere.item.ItemRequest;
import com.rere.item.ItemResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

import static com.rere.item.ItemAcceptanceTest.아이템_등록되어_있음;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("아이템 관련 기능")
public class BoxAcceptanceTest extends AcceptanceTest {

    private ItemResponse JHText;
    private ItemResponse HMTextArea;
    private ItemRequest mainImage;
    private ItemRequest dateToday ;
    private Item JHText1;
    private Item HMTextArea1;
    private List<Item> items;
    private BoxRequest box1;
    private BoxResponse box2;
    @BeforeEach
    public void setUp() {
        super.setUp();

        // given
        JHText = 아이템_등록되어_있음("text", "이름", "준호",1L);
        JHText1 = new Item("text", "이름", "준호",1L);
        HMTextArea = 아이템_등록되어_있음("textArea", "자기소개", "나는 한민",1L);
        HMTextArea1 = new Item("textArea", "자기소개", "나는 한민",1L);

        mainImage = new ItemRequest("image", "이미지", "temp.jpg",1L);
        dateToday = new ItemRequest("date", "날짜", "2021-01-17",1L);

        items= new ArrayList<Item>();
        items.add(JHText1);
        items.add(HMTextArea1);
        box2= 박스_등록되어_있음(items);
        box1= new BoxRequest(items);
    }

    @DisplayName("박스를 생성한다.")
    @Test
    void createItem() {
        // when
        ExtractableResponse<Response> response = 박스_생성_요청(items);

        // then
        박스_생성됨(response);
    }

    @DisplayName("박스를 조회한다.")
    @Test
    void getItem() {
        // when
        ExtractableResponse<Response> response = 박스_조회_요청(box2);

        // then
        박스_응답됨(response, box2);
    }

    @DisplayName("박스를 삭제한다.")
    @Test
    void deleteItem() {
        // when
        ExtractableResponse<Response> response = 박스_삭제_요청(box2);

        // then
        박스_삭제됨(response);
    }

    @DisplayName("박스를 수정한다.")
    @Test
    void updateItem() {
        // when
        ExtractableResponse<Response> response = 박스_수정_요청(box2, box1);

        // then
        박스_수정됨(response, box2);
    }

    private ExtractableResponse<Response> 박스_수정_요청(BoxResponse boxResponse, BoxRequest params) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/boxes/" + boxResponse.getId())
                .then().log().all()
                .extract();
    }


    private ExtractableResponse<Response> 박스_삭제_요청(BoxResponse boxResponse) {
        return RestAssured
                .given().log().all()
                .when().delete("/boxes/" + boxResponse.getId())
                .then().log().all()
                .extract();
    }

    private void 박스_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 박스_응답됨(ExtractableResponse<Response> response, BoxResponse boxResponse) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        BoxResponse resultResponse = response.as(BoxResponse.class);
        assertThat(resultResponse.getId()).isEqualTo(boxResponse.getId());
    }

    private void 박스_수정됨(ExtractableResponse<Response> response, BoxResponse textAreaHM) {

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 박스_조회_요청(BoxResponse boxResponse) {
        return RestAssured
                .given().log().all()
                .when().get("/boxes/{boxId}", boxResponse.getId())
                .then().log().all()
                .extract();
    }

    public static BoxResponse 박스_등록되어_있음(List<Item>items) {
        return 박스_생성_요청(items).as(BoxResponse.class);
    }

    public static ExtractableResponse<Response> 박스_생성_요청(List<Item>items) {
        BoxRequest boxRequest = new BoxRequest(items);

        return RestAssured
                .given().log().all()
                .body(boxRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/boxes")
                .then().log().all()
                .extract();
    }


    public static void 박스_생성됨(ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }
}
