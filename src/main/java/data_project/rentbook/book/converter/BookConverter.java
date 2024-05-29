package data_project.rentbook.book.converter;

import data_project.rentbook.book.dto.BookDtoRes;

import java.util.List;

public class BookConverter {

    public static BookDtoRes.bookIdRes BookId(Long book_id){
        return BookDtoRes.bookIdRes.builder()
                .book_id(book_id)
                .build();
    }

    public static BookDtoRes.searchBookList BookList(List<BookDtoRes.BookRes> booklist){
        return BookDtoRes.searchBookList.builder()
                .bookResList(booklist)
                .build();
    }
}
