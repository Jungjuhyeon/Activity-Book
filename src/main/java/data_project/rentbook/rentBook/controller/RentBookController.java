package data_project.rentbook.rentBook.controller;


import data_project.rentbook.global.response.SuccessResponse;
import data_project.rentbook.rentBook.dto.RentBookDtoReq;
import data_project.rentbook.rentBook.dto.RentBookDtoRes;
import data_project.rentbook.rentBook.service.RentBookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentbook")
public class RentBookController {

    private final RentBookService rentBookService;

    /**
     * 24.05.29 작성자 : 정주현
     * 책 대여
     */
    @PostMapping("")
    public SuccessResponse<RentBookDtoRes.RentBookRes> rentBook(@RequestBody @Valid RentBookDtoReq.rentBookReq request) {
        return SuccessResponse.success(rentBookService.rentBook(request));
    }

    /**
     * 24.05.29 작성자 : 정주현
     * 책 반납
     */
    @DeleteMapping("/return/{rent_book_id}")
    public SuccessResponse<String> returnBook(@PathVariable Long rent_book_id ) {
        rentBookService.returnBook(rent_book_id);
        return SuccessResponse.successWithoutResult(null);
    }

    /**
     * 24.05.29 작성자 : 정주현
     * 책 대여 연장
     */
    @PatchMapping("/renew/{rent_book_id}")
    public SuccessResponse<String> renewBook(@PathVariable Long rent_book_id ) {
        rentBookService.renewBook(rent_book_id);
        return SuccessResponse.successWithoutResult(null);
    }



}
