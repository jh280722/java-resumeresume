package com.rere.sortation;

import com.rere.AcceptanceTest;
import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.box.dto.BoxRequest;
import com.rere.box.dto.BoxResponse;
import com.rere.document.domain.DocumentRepository;
import com.rere.document.dto.DocumentRequest;
import com.rere.document.dto.DocumentResponse;
import com.rere.item.dto.ItemRequest;
import com.rere.item.dto.ItemResponse;
import com.rere.sortation.domain.Sortation;
import com.rere.sortation.domain.SortationRepository;
import com.rere.sortation.dto.SortationRequest;
import com.rere.sortation.dto.SortationResponse;
import io.restassured.RestAssured;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import static com.rere.box.BoxAcceptanceTest.박스_등록되어_있음;
import static com.rere.item.ItemAcceptanceTest.아이템_등록되어_있음;
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("구분 관련 기능")
public class SortationAcceptanceTest extends AcceptanceTest {

    @Autowired
    BoxRepository boxes;

    @Autowired
    private SortationRepository sortations;
    private DocumentRepository documents;

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

//        Box box = boxes.save(Box.of("box"));
        //Document document = documents.save(Document.of("document"));
        Sortation soltation = sortations.save(Sortation.of("sortation"));
        // given
        box = boxes.save(Box.of("box", document));
        JHText = 아이템_등록되어_있음("text", "이름", "준호", box);
        HMTextArea = 아이템_등록되어_있음("textArea", "자기소개", "나는 한민", box);

        box2 = 박스_등록되어_있음("box2", document);

        sortation2 = new SortationRequest("sortation2",user);
        sortation1 = 구분_등록되어_있음("sortation1");
    }

    @DisplayName("구분을 생성한다.")
    @Test
    void createSortation() {
        // when
        ExtractableResponse<Response> response = 구분_생성_요청("sortation");

        // then
        구분_생성됨(response);
    }

    @DisplayName("구분을 조회한다.")
    @Test
    void getSortation() {
        // when
        ExtractableResponse<Response> response = 구분_조회_요청(sortation);

        // then
        구분_응답됨(response, sortation);
    }

    @DisplayName("구분을 삭제한다.")
    @Test
    void deleteSortation() {
        // when
        ExtractableResponse<Response> response = 구분_삭제_요청(sortation);

        // then
        구분_삭제됨(response);
    }

    @DisplayName("구분을 수정한다.")
    @Test
    void updateSortation() {
        // when
        ExtractableResponse<Response> response = 구분_수정_요청(sortation, sortation);

        // then
        구분_수정됨(response, sortation);
    }

    private ExtractableResponse<Response> 구분_수정_요청(SortationResponse sortationResponse, SortationRequest params) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/sortations/" + SortationResponse.getId())
                .then().log().all()
                .extract();
    }


    private ExtractableResponse<Response> 구분_삭제_요청(SortationResponse sortationResponse) {
        return RestAssured
                .given().log().all()
                .when().delete("/sortations/" + sortationResponse.getId())
                .then().log().all()
                .extract();
    }

    private void 구분_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 구분_응답됨(ExtractableResponse<Response> response, SortationResponse sortationResponse) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        SortationResponse resultResponse = response.as(SortationResponse.class);
        assertThat(resultResponse.getId()).isEqualTo(sortationResponse.getId());
    }

    private void 구분_수정됨(ExtractableResponse<Response> response, SortationResponse textAreaHM) {

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 구분_조회_요청(SortationResponse sortationResponse) {
        return RestAssured
                .given().log().all()
                .when().get("/sortations/{sortationId}", sortationResponse.getId())
                .then().log().all()
                .extract();
    }

    public static SortationResponse 구분_등록되어_있음(String name) {
        return 구분_생성_요청(name).as(SortationResponse.class);
    }

    public static ExtractableResponse<Response> 구분_생성_요청(String name) {
        SortationRequest sortationRequest = new SortationRequest(name);

        return RestAssured
                .given().log().all()
                .body(sortationRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/sortations")
                .then().log().all()
                .extract();
    }


    public static void 구분_생성됨(ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }
}
