package data_project.rentbook.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;



public class UserDtoReq {

    @Builder
    @Setter
    @Getter
    public static class enrollUser{

        @NotBlank(message = "이름은 필수 입력 값입니다.")
        private String name;
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
        private String email;
        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String password;
        @NotBlank(message = "재확인 비밀번호는 필수 입력 값입니다.")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        private String passwordCheck;
    }

    @Builder
    @Getter
    public static class verifyAuth{
        @NotBlank(message = "인증번호를 입력해주세요.")
        private String certificationNumber;
        @NotBlank(message = "이메일을 입력해주세요.")
        private String email;
    }

    @Getter
    public static class userLoginReq{
        @NotBlank(message = "이름은 공백일 수 없습니다.")
        private String email;
        @NotBlank(message = "비밀번호는 공백일 수 없습니다.")
        private String password;
    }

}
