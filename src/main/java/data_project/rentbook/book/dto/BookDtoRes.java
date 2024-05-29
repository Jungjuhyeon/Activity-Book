package data_project.rentbook.book.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public class BookDtoRes {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MyBookRes{
        private Long book_id;
        private String book_name;
        private String book_author;
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class searchMyBookList{
        List<MyBookRes> bookResList = new ArrayList<>();
    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BookRes{
        private Long book_id;
        private String book_name;
        private String book_author;
        private String status;

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class searchBookList{
        List<BookRes> bookResList = new ArrayList<>();
    }




    @Builder
    @Getter
    public static class bookIdRes{
        private Long book_id;
    }


}
