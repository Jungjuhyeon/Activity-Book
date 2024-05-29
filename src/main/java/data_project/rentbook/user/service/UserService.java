package data_project.rentbook.user.service;

import data_project.rentbook.book.dto.BookDtoRes;
import data_project.rentbook.user.dto.UserDtoReq;
import data_project.rentbook.user.dto.UserDtoRes;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface UserService {
    UserDtoRes.enrollUser signUp(UserDtoReq.enrollUser request);

    void sendEmailAuth(String email, String text);

    Long verifyEmail(UserDtoReq.verifyAuth request);

    void signUpAuth(UserDtoReq.verifyAuth request);


    UserDtoRes.userLoginRes login(UserDtoReq.userLoginReq request);

    BookDtoRes.searchMyBookList rentBookSearch(Long user_id);
}
