package com.rere.box;

import com.rere.AcceptanceTest;
import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.box.dto.BoxRequest;
import com.rere.box.dto.BoxResponse;
import com.rere.document.domain.Document;
import com.rere.document.domain.DocumentRepository;
import com.rere.item.dto.ItemRequest;
import com.rere.item.dto.ItemResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.rere.item.ItemAcceptanceTest.아이템_등록되어_있음;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("박스 관련 기능")
public class BoxAcceptanceTest extends AcceptanceTest {

    @Autowired
    BoxRepository boxes;
    @Autowired
    DocumentRepository documents;

    private ItemResponse JHText;
    private ItemResponse HMTextArea;
    private ItemRequest mainImage;
    private ItemRequest dateToday ;
    private Box box;
    private BoxRequest box1;
    private BoxResponse box2;
    @BeforeEach
    public void setUp() {
        super.setUp();
        Document document = documents.save(Document.of("document"));
        box = boxes.save(Box.of("box",document));
        // given
        JHText = 아이템_등록되어_있음(0, "text", "이름", "준호",box);
        HMTextArea = 아이템_등록되어_있음(0, "textArea", "자기소개", "나는 한민",box);

        mainImage = new ItemRequest(0, "image", "이미지", "temp.jpg",box);
        dateToday = new ItemRequest(0, "date", "날짜", "2021-01-17",box);
        box2 = 박스_등록되어_있음("box2",document);
        box1= new BoxRequest("box1",document);
    }

    @DisplayName("아이템을 드래그한다.")
    @Test
    void dragItem() {

        Document document = documents.save(Document.of("document"));
        Box box = boxes.findByName("boxData");

        ExtractableResponse<Response> response = RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().put("/boxes/drag/" + box.getId() + "?itemId={itemId}&seq={seq}", 2,0)
                .then().log().all()
                .extract();
        boxes.flush();
        박스_조회_요청(response.as(BoxResponse.class));
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("박스를 생성한다.")
    @Test
    void createItem() {
        // when
        Document document = documents.save(Document.of("document"));
        ExtractableResponse<Response> response = 박스_생성_요청("box",document);

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
        ExtractableResponse<Response> response = 박스_삭제_요청(BoxResponse.of(box));

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

    public static BoxResponse 박스_등록되어_있음(String name,Document document) {
        return 박스_생성_요청(name,document).as(BoxResponse.class);
    }

    public static ExtractableResponse<Response> 박스_생성_요청(String name, Document document) {
        BoxRequest boxRequest = new BoxRequest(name,document);

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
