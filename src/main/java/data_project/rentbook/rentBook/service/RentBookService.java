package data_project.rentbook.rentBook.service;

import data_project.rentbook.rentBook.dto.RentBookDtoReq;
import data_project.rentbook.rentBook.dto.RentBookDtoRes;
import data_project.rentbook.user.dto.UserDtoRes;

import java.time.LocalDate;

public interface RentBookService {
    RentBookDtoRes.RentBookRes rentBook(RentBookDtoReq.rentBookReq request);

    void returnBook(Long rentBookId);

    void renewBook(Long rentBookId);
}
