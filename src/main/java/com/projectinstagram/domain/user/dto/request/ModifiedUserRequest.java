package com.projectinstagram.domain.user.dto.request;

import com.projectinstagram.common.regexp.RegEXP;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class ModifiedUserRequest {
    @NotBlank(message = "이메일이 입력 되지 않았습니다.")
    @Email
    private String email;

    @NotBlank(message = "닉네임이 입력 되지 않았습니다.")
    @Size(
            min = 1,
            max = 20
    )
    @Pattern(regexp = RegEXP.NICKNAME)
    private String nickname;

    @NotBlank(message = "이름이 입력되지 않았습니다.")
    @Size(
            min = 1,
            max = 20,
            message = "이름은 1 ~ 20자 로 입력해주세요."
    )
    @Pattern(
            regexp = RegEXP.NAME,
            message = "이름 형식이 아닙니다."
    )
    private String name;


    private String introduce;
}
