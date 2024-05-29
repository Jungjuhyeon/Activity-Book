package data_project.rentbook.rentBook.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

public class RentBookDtoRes {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RentBookRes{
        private Long rent_book_id;
        private Long book_id;
        private LocalDate rent_day;
        private LocalDate return_day;
    }
}
