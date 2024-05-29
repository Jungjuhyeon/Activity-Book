package data_project.rentbook.rentBook.service;

import data_project.rentbook.book.repository.BookRepository;
import data_project.rentbook.global.exception.BusinessException;
import data_project.rentbook.global.exception.errorcode.CommonErrorCode;
import data_project.rentbook.rentBook.dto.RentBookDtoReq;
import data_project.rentbook.rentBook.dto.RentBookDtoRes;
import data_project.rentbook.rentBook.repository.RentBookRepository;
import data_project.rentbook.user.dto.UserDtoRes;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RentBookServiceImpl implements RentBookService{

    private final RentBookRepository rentBookRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public RentBookDtoRes.RentBookRes rentBook(RentBookDtoReq.rentBookReq request){
        //book이 대여 가능한지 확인
        if(rentBookRepository.checkRentBook(request.getBook_id()).equals("NONAVAILABLE")){
            throw new BusinessException(CommonErrorCode.BOOk_NOT_RENT);
        }

        //rent_book 테이블에 대여 행 삽입
        Long rent_book_id = rentBookRepository.rentBook(request);

        //책 대여 불가능으로 변경
        rentBookRepository.changeRentBook(request.getBook_id(),"NONAVAILABLE");

        return rentBookRepository.selectRentBook(rent_book_id);
    }

    @Override
    @Transactional
    public void returnBook(Long rent_book_id){
        //book_id 조회
        Long book_id = rentBookRepository.getBookIdByRentBookId(rent_book_id);

        //rent_book_id 해당하는 행 삭제(대여 반납)
        int i =rentBookRepository.returnBook(rent_book_id);
        if(i ==0){
            throw new BusinessException(CommonErrorCode.RENT_BOOk_NOT_FOUND);
        }

        //book_id에 해당하는 책을 대여가능 상태로 수정
        rentBookRepository.changeRentBook(book_id,"AVAILABLE");

    }

    @Override
    @Transactional
    public void renewBook(Long rent_book_id){
        if(rentBookRepository.renewBook(rent_book_id)==0){
            throw new BusinessException(CommonErrorCode.DATABASE_ERROR);
        }
    }



}
