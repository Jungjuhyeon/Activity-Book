package data_project.rentbook.book.service;

import data_project.rentbook.book.converter.BookConverter;
import data_project.rentbook.book.dto.BookDtoReq;
import data_project.rentbook.book.dto.BookDtoRes;
import data_project.rentbook.book.repository.BookRepository;
import data_project.rentbook.global.exception.BusinessException;
import data_project.rentbook.global.exception.errorcode.CommonErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;


    @Override
    @Transactional
    public BookDtoRes.bookIdRes bookEnroll(BookDtoReq.bookEnroll request){
        Long book_id = bookRepository.bookEnroll(request);

        return BookConverter.BookId(book_id);
    }

    @Override
    @Transactional
    public void bookUpdate(BookDtoReq.bookUpdate request) {
        int id = bookRepository.bookUpdate(request);
        if (id == 0) {
            throw new BusinessException(CommonErrorCode.BOOk_NOT_FOUND);
        }
    }
    @Override
    @Transactional
    public void bookDelete(Long bookId){
        int id = bookRepository.bookDelte(bookId);
        if (id == 0) {
            throw new BusinessException(CommonErrorCode.BOOk_NOT_FOUND);
        }
    }


    @Override
    public BookDtoRes.searchBookList bookSearch(){
        List<BookDtoRes.BookRes> booklist = bookRepository.searchBook();

        return BookConverter.BookList(booklist);
    }

    @Override
    public BookDtoRes.searchBookList bookRentSearch(){
        List<BookDtoRes.BookRes> bookRentlist = bookRepository.searchRentBook();

        return BookConverter.BookList(bookRentlist);
    }

}
