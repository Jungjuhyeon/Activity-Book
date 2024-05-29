package data_project.rentbook.book.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BookDtoReq {

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class bookEnroll{
        @NotBlank(message = "책 이름은 필수 값입니다.")
        private String book_name;
        @NotBlank(message = "책 저자의 이름은 필수 값입니다.")
        private String book_author;

    }

    @Builder
    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class bookUpdate{
        private Long book_id;
        private String book_name;
        private String book_author;

    }
}
