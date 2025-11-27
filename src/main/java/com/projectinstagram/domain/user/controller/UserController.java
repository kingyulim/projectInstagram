package com.projectinstagram.domain.user.controller;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.domain.user.dto.request.*;
import com.projectinstagram.domain.user.dto.response.*;
import com.projectinstagram.domain.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    /**
     * 로그아웃 요청 검증
     * @param session 세선 파라미터
     * @return 204 no content
     */
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.SESSION_CHECK);
        }

        /**
         * 세션 제거
         */
        session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 회원 탈퇴 요청 검증
     * @param userId 회원 고유 번호
     * @return
     */
    @PostMapping("/users/delete/{userId}")
    public ResponseEntity<Void> userDelete(
            @PathVariable Long userId,
            @Valid @RequestBody DeleteUserRequest request,
            HttpSession session
    ) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.NO_LOGIN);
        }

        userService.userDelete(userId, request);

        /**
         * 세션 제거
         */
        session.invalidate();

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 프로필 업데이트 요청 검증
     * @param userId 회원 고유 번호
     * @param request 입력값 파라미터
     * @param session 세션 파라미터
     * @return ModifiedUserResponse json 반환
     */
    @PutMapping("users/profile/{userId}")
    public ResponseEntity<ModifiedUserResponse> modifiedUser(
            @PathVariable Long userId,
            @RequestPart MultipartFile profileImage,
            @Valid @RequestPart ModifiedUserRequest request,
            HttpSession session
    ) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.NO_LOGIN);
        }

        if (!thisSession.getId().equals(userId)) {
            throw new CustomException(ExceptionMessageEnum.INVALID_MEMBER_INFO);
        }

        return  ResponseEntity.status(HttpStatus.OK).body(userService.modifiedUser(userId, profileImage, request));
    }

    /**
     * 프로필 조회 요청 검증
     * @param userId 회원 고유 번호
     * @return ProfileViewResponse json 반환
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<ProfileViewResponse> profileView(
            @PathVariable Long userId,
            HttpSession session
    ) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        return ResponseEntity.status(HttpStatus.OK).body(userService.profileView(userId));
    }

    /**
     * 비밀번호 변경 요청 검증
     * @param userId 회원 고유 번호
     * @param request 입력값 파라미터
     * @param session 세션 파라미터
     * @return 200 OK
     */
    @PutMapping("/users/password/{userId}")
    public ResponseEntity<Void> passwordModified(
            @PathVariable Long userId,
            @Valid @RequestBody PasswordChangeRequest request,
            HttpSession session
    ) {
        SessionResponse thisSession = (SessionResponse) session.getAttribute("thisSession");

        if (thisSession == null) {
            throw new CustomException(ExceptionMessageEnum.NO_LOGIN);
        }

        if (!thisSession.getId().equals(userId)) {
            throw new CustomException(ExceptionMessageEnum.INVALID_MEMBER_INFO);
        }

        userService.passwordModified(userId, request);

        session.invalidate();

        return ResponseEntity.ok().build();
    }
}
