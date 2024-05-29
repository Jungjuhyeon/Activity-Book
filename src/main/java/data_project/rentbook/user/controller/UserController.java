package data_project.rentbook.user.controller;

import data_project.rentbook.book.dto.BookDtoRes;
import data_project.rentbook.email.dto.EmailDtoReq;
import data_project.rentbook.global.response.SuccessResponse;
import data_project.rentbook.user.dto.UserDtoReq;
import data_project.rentbook.user.dto.UserDtoRes;
import data_project.rentbook.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    /**
     * 24.01.19 작성자 : 정주현
     * 회원가입
     */
    @PostMapping("/signup")
    public SuccessResponse<UserDtoRes.enrollUser> signUp(@RequestBody @Valid UserDtoReq.enrollUser request) {
        return SuccessResponse.success(userService.signUp(request));
    }

    /**
     * 24.01.20 작성자 : 정주현
     * 이메일 인증요청
     */
    @PostMapping("/auth")
    public SuccessResponse<String> sendEmailAuth(@RequestBody @Valid EmailDtoReq.emailAuthReq request) {
        String text = "인증";
        userService.sendEmailAuth(request.getEmail(),text);
        return SuccessResponse.successWithoutResult("성공");
    }


    /**
     * 24.01.19 작성자 : 정주현
     * 이메일 인증번호 확인
     */
    @PostMapping("/email/verify")
    public SuccessResponse<String> verifyCertificationNumber(@RequestBody @Valid UserDtoReq.verifyAuth request) {
        userService.signUpAuth(request);
        return SuccessResponse.successWithoutResult("성공");
    }

    /**
     * 24.01.19 작성자 : 정주현
     * 로그인
     */
    @PostMapping("/login")
    public SuccessResponse<UserDtoRes.userLoginRes> login(@RequestBody @Valid UserDtoReq.userLoginReq request) {
        return SuccessResponse.success(userService.login(request));
    }

    /**
     * 24.01.19 작성자 : 정주현
     * 대여한 책 조회
     */
    @GetMapping("/serach/rentbook")
    public SuccessResponse<BookDtoRes.searchMyBookList> rentBookSearch(@RequestParam(name = "user_id") Long user_id) {

        return SuccessResponse.success(userService.rentBookSearch(user_id));
    }





}
