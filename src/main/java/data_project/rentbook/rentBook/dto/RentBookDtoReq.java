package data_project.rentbook.rentBook.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class RentBookDtoReq {


    @Getter
    public static class rentBookReq{
        private Long user_id;
        private Long book_id;
        private LocalDate return_day;
    }
}
