package com.projectinstagram.domain.user.controller;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.domain.user.dto.request.JoinUserRequest;
import com.projectinstagram.domain.user.dto.request.AcountUserRequest;
import com.projectinstagram.domain.user.dto.response.JoinUserResponse;
import com.projectinstagram.domain.user.dto.response.LoginUserResponse;
import com.projectinstagram.domain.user.dto.response.SessionResponse;
import com.projectinstagram.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    /**
     * 회원가임 검증 요청
     * @param request 회원가입 입력값 파라미터
     * @return UserJoinResponseDto json 반환
     */
    @PostMapping("/join")
    public ResponseEntity<JoinUserResponse> join(@Valid @RequestBody JoinUserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.join(request));
    }

    /**
     * 로그인 검증 요청
     * @param request 로그인 입력값 파라미터
     * @return UserLoginResponseDto json 반환
     */
    @PostMapping("/login")
    public ResponseEntity<LoginUserResponse> login(
            @Valid @RequestBody AcountUserRequest request,
            HttpSession session
    ) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        /**
         * thisSession 세션 없으면 예외처리
         */
        if (thisSession != null) {
            throw new CustomException(ExceptionMessageEnum.LOGIN_CHECK);
        }

        LoginUserResponse loginUserResponse = userService.login(request);

        SessionResponse sessionResponse = new SessionResponse(
                loginUserResponse.getId(),
                loginUserResponse.getName(),
                loginUserResponse.getNickname()
        );

        /**
         * 세션 생성
         */
        session.setAttribute("thisSession", sessionResponse);

        return ResponseEntity.status(HttpStatus.OK).body(loginUserResponse);
    }
}
