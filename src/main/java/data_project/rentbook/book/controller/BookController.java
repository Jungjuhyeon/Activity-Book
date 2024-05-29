package data_project.rentbook.book.controller;

import data_project.rentbook.book.dto.BookDtoReq;
import data_project.rentbook.book.dto.BookDtoRes;
import data_project.rentbook.book.service.BookService;
import data_project.rentbook.global.response.SuccessResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {

    private final BookService bookService;
    /**
     * 25.05.29 작성자 : 정주현
     * 책 등록
     */
    @PostMapping("/enroll")
    public SuccessResponse<BookDtoRes.bookIdRes> bookEnroll(@RequestBody @Valid BookDtoReq.bookEnroll request){
        return SuccessResponse.success(bookService.bookEnroll(request));
    }
    /**
     * 25.05.29 작성자 : 정주현
     * 책 수정
     */

    @PatchMapping("/update")
    public SuccessResponse<String> bookUpdate(@RequestBody @Valid BookDtoReq.bookUpdate request){
        bookService.bookUpdate(request);
        return SuccessResponse.successWithoutResult(null);
    }
    /**
     * 25.05.29 작성자 : 정주현
     * 책 삭제
     */

    @DeleteMapping("/delete")
    public SuccessResponse<String> bookDelete(@RequestParam(name = "book_id")Long book_id){
        bookService.bookDelete(book_id);
        return SuccessResponse.successWithoutResult(null);
    }
    /**
     * 25.05.29 작성자 : 정주현
     * 책 목록 조회
     */

    @GetMapping("/search")
    public SuccessResponse<BookDtoRes.searchBookList> bookSearch(){
        BookDtoRes.searchBookList list = bookService.bookSearch();
        return SuccessResponse.success(list);
    }


    /**
     * 25.05.29 작성자 : 정주현
     * 대여 가능 책 목록 조회
     */
    @GetMapping("/search/rent")
    public SuccessResponse<BookDtoRes.searchBookList> bookRentSearch(){
        BookDtoRes.searchBookList list = bookService.bookRentSearch();
        return SuccessResponse.success(list);
    }

}
