package com.projectinstagram.domain.user.dto.request;

import com.projectinstagram.common.regexp.RegEXP;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class DeleteUserRequest {
    @NotBlank(message = "비밀번호가 입력되지 않았습니다.")
    @Size(
            min = 1,
            max = 20,
            message = "비밀번호는 1 ~ 20자 로 입력해주세요."
    )
    @Pattern(
            regexp = RegEXP.PASSWORD,
            message = "비밀번호 형식이 아닙니다."
    )
    private String password;
}
