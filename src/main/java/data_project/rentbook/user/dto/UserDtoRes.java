package data_project.rentbook.user.dto;

import lombok.Builder;
import lombok.Getter;

public class UserDtoRes {

    @Builder
    @Getter
    public static class enrollUser{
        private Long user_id;
    }

    @Builder
    @Getter
    public static class userLoginRes{
        private Long user_id;
    }


}
