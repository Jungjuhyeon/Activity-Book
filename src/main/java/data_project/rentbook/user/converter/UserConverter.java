package data_project.rentbook.user.converter;

import data_project.rentbook.book.dto.BookDtoRes;
import data_project.rentbook.user.dto.UserDtoRes;

import java.util.List;

public class UserConverter {

    public static UserDtoRes.enrollUser userIdRes(Long userId){
        return UserDtoRes.enrollUser.builder()
                .user_id(userId)
                .build();
    }
    public static UserDtoRes.userLoginRes userLoginRes(Long userId){
        return UserDtoRes.userLoginRes.builder()
                .user_id(userId)
                .build();
    }

    public static BookDtoRes.searchMyBookList rentBookList(List<BookDtoRes.MyBookRes> booklist){
        return BookDtoRes.searchMyBookList.builder()
                .bookResList(booklist)
                .build();
    }
}
