package com.projectinstagram.domain.user.dto.request;

import com.projectinstagram.common.regexp.RegEXP;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class PasswordChangeRequest {
    @NotBlank(message = "현재 비밀번호를 입력해주세요.")
    @Size(
            min = 1,
            max = 20,
            message = "비밀번호는 1 ~ 20자 로 입력해주세요."
    )
    @Pattern(
            regexp = RegEXP.PASSWORD,
            message = "비밀번호 형식이 아닙니다."
    )
    private String currentPassword;

    @NotBlank(message = "새 비밀번호를 입력해주세요.")
    @Size(
            min = 1,
            max = 20,
            message = "비밀번호는 1 ~ 20자 로 입력해주세요."
    )
    @Pattern(
            regexp = RegEXP.PASSWORD,
            message = "비밀번호 형식이 아닙니다."
    )
    private String newPassword;
}
