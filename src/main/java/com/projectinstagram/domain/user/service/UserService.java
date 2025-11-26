package com.projectinstagram.domain.user.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.common.jwt.JwtUtil;
import com.projectinstagram.common.util.PasswordEncoder;
import com.projectinstagram.domain.user.dto.request.JoinUserRequest;
import com.projectinstagram.domain.user.dto.request.AcountUserRequest;
import com.projectinstagram.domain.user.dto.response.JoinUserResponse;
import com.projectinstagram.domain.user.dto.response.LoginUserResponse;
import com.projectinstagram.domain.user.entity.User;
import com.projectinstagram.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    /**
     * 회원가입 비니지스 로직 처리
     * @param request 회원가입 입력 데이터 파라미터
     * @return UserJoinResponseDto 데이터 반환
     */
    public JoinUserResponse join(JoinUserRequest request) {
        User user = new User(
                request.getEmail(),
                request.getName(),
                passwordEncoder.encode(request.getPassword()),
                request.getNickname()
        );

        /**
         * 중복되는 이메일 검사
         */
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new CustomException(ExceptionMessageEnum.DUPLICATE_DATA_EXCEPTION);
        }

        /**
         * 중복되는 닉네임 검사
         */
        if (userRepository.existsByNickname(user.getNickname())) {
            throw new CustomException(ExceptionMessageEnum.DUPLICATE_DATA_EXCEPTION);
        }

        /**
         * 닉네임 정규식 검사
         */
        boolean isValid = request.getNickname().matches("^[A-Za-z0-9](?:[A-Za-z0-9._]*[A-Za-z0-9])$");

        if (!isValid) {
            throw new CustomException(ExceptionMessageEnum.PATTERN_VALIDATION_FAILED_EXCEPTION);
        }

        User joinedUser = userRepository.save(user);

        return new JoinUserResponse(
                joinedUser.getId(),
                joinedUser.getEmail(),
                joinedUser.getName(),
                joinedUser.getNickname()
        );
    }

    /**
     * 로그인 비지니스 로직 처리
     * @param request 로그인 입력값 파라미터
     * @return UserLoginResponseDto 데이터 반환
     */
    public LoginUserResponse login(AcountUserRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NO_MEMBER_INFO)
                );

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordMatches) {
            throw new CustomException(ExceptionMessageEnum.INVALID_MEMBER_INFO);
        }

        String token = jwtUtil.generateToken(user.getId());

        return new LoginUserResponse(
                user.getId(),
                user.getNickname(),
                user.getName(),
                user.getProfileImage(),
                user.getIntroduce(),
                user.getCreatedAt(),
                user.getModifiedAt(),
                token
        );
    }


}
