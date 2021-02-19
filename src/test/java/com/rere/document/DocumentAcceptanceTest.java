package com.rere.document;

import com.rere.AcceptanceTest;
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

@DisplayName("문서 관련 기능")
public class DocumentAcceptanceTest extends AcceptanceTest {

    @Autowired
    BoxRepository boxes;

    @Autowired
    private DocumentRepository documents;

    private BoxRequest box1;
    private DocumentRequest document1;
    private BoxResponse box2;
    private DocumentResponse document2;
    private ItemResponse JHText;
    private ItemResponse HMTextArea;
    private ItemRequest mainImage;
    private ItemRequest dateToday ;
    private Box box;
    @BeforeEach
    public void setUp() {
        super.setUp();

//        Box box = boxes.save(Box.of("box"));
        Document document = documents.save(Document.of("document"));
        // given
        box=boxes.save(Box.of("box",document));
        JHText = 아이템_등록되어_있음( "text", "이름", "준호",box);
        HMTextArea = 아이템_등록되어_있음("textArea", "자기소개", "나는 한민",box);

        box2 = 박스_등록되어_있음("box2",document);
        document2 = 문서_등록되어_있음("document2");
        document1 = new DocumentRequest("document1");
    }

    @DisplayName("문서를 생성한다.")
    @Test
    void createItem() {
        // when
        ExtractableResponse<Response> response = 문서_생성_요청("document");

        // then
        문서_생성됨(response);
    }

    @DisplayName("문서를 조회한다.")
    @Test
    void getItem() {
        // when
        ExtractableResponse<Response> response = 문서_조회_요청(document2);

        // then
        문서_응답됨(response, document2);
    }

    @DisplayName("문서를 삭제한다.")
    @Test
    void deleteItem() {
        // when
        ExtractableResponse<Response> response = 문서_삭제_요청(document2);

        // then
        문서_삭제됨(response);
    }

    @DisplayName("문서를 수정한다.")
    @Test
    void updateItem() {
        // when
        ExtractableResponse<Response> response = 문서_수정_요청(document2, document1);

        // then
        문서_수정됨(response, document2);
    }

    private ExtractableResponse<Response> 문서_수정_요청(DocumentResponse documentResponse, DocumentRequest params) {
        return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .when().put("/documents/" + documentResponse.getId())
                .then().log().all()
                .extract();
    }


    private ExtractableResponse<Response> 문서_삭제_요청(DocumentResponse documentResponse) {
        return RestAssured
                .given().log().all()
                .when().delete("/documents/" + documentResponse.getId())
                .then().log().all()
                .extract();
    }

    private void 문서_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static void 문서_응답됨(ExtractableResponse<Response> response, DocumentResponse documentResponse) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
        DocumentResponse resultResponse = response.as(DocumentResponse.class);
        assertThat(resultResponse.getId()).isEqualTo(documentResponse.getId());
    }

    private void 문서_수정됨(ExtractableResponse<Response> response, DocumentResponse textAreaHM) {

        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static ExtractableResponse<Response> 문서_조회_요청(DocumentResponse documentResponse) {
        return RestAssured
                .given().log().all()
                .when().get("/documents/{documentId}", documentResponse.getId())
                .then().log().all()
                .extract();
    }

    public static DocumentResponse 문서_등록되어_있음(String name) {
        return 문서_생성_요청(name).as(DocumentResponse.class);
    }

    public static ExtractableResponse<Response> 문서_생성_요청(String name) {
        DocumentRequest documentRequest = new DocumentRequest(name);

        return RestAssured
                .given().log().all()
                .body(documentRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when().post("/documents")
                .then().log().all()
                .extract();
    }


    public static void 문서_생성됨(ExtractableResponse response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.header("Location")).isNotBlank();
    }
}
