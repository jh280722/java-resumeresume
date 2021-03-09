package com.rere.sortation;

import com.rere.AcceptanceTest;
import com.rere.auth.dto.TokenResponse;
import com.rere.box.domain.Box;
import com.rere.box.domain.BoxRepository;
import com.rere.sortation.domain.SortationRepository;
import com.rere.sortation.dto.SortationRequest;
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
import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("구분 관련 기능")
public class SortationAcceptanceTest extends AcceptanceTest {
    private static final String EMAIL = "email@email.com";
    private static final String PASSWORD = "password";
    private static final String NAME = "준호";

    @Autowired
    BoxRepository boxes;
    @Autowired
    SortationRepository sortations;
    @Autowired
    UserRepository users;

    private SortationRequest sortation2;
    private SortationResponse sortation1;
    private TokenResponse user;

    @BeforeEach
    public void setUp() {
        super.setUp();
        // given
        회원_등록되어_있음(EMAIL, PASSWORD, NAME);
        user = 로그인되어_있음(EMAIL, PASSWORD);
    }

    @DisplayName("구분을 관리한다.")
    @Test
    void manageSortation() {
        // when
        ExtractableResponse<Response> createResponse = 구분_생성_요청(user, "강남역");
        // then
        구분_생성됨(createResponse);

        // when
        ExtractableResponse<Response> findResponse = 구분_조회_요청(user);
        // then
        구분_조회됨(findResponse);

        // when
        ExtractableResponse<Response> deleteResponse = 구분_삭제_요청(user, createResponse);
        // then
        구분_삭제됨(deleteResponse);
    }

    public static ExtractableResponse<Response> 구분_생성_요청(TokenResponse tokenResponse, String name) {
        SortationRequest sortationRequest = new SortationRequest(name);

        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(sortationRequest)
                .when().post("/sortations")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 구분_조회_요청(TokenResponse tokenResponse) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .when().get("/sortations")
                .then().log().all()
                .extract();
    }

    public static ExtractableResponse<Response> 구분_삭제_요청(TokenResponse tokenResponse, ExtractableResponse<Response> response) {
        return RestAssured
                .given().log().all()
                .auth().oauth2(tokenResponse.getAccessToken())
                .when().delete("/sortations/" + response.as(SortationResponse.class).getId())
                .then().log().all()
                .extract();
    }

    public static void 구분_생성됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
    }

    public static void 구분_조회됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

    public static void 구분_삭제됨(ExtractableResponse<Response> response) {
        assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    public static SortationResponse 구분_등록되어_있음(TokenResponse tokenResponse, String name) {
        return 구분_생성_요청(tokenResponse, name).as(SortationResponse.class);
    }
}
