package com.projectinstagram.domain.user.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.common.exception.ExceptionMessageEnum;
import com.projectinstagram.common.jwt.JwtUtil;
import com.projectinstagram.common.regexp.RegEXP;
import com.projectinstagram.common.util.ImageService;
import com.projectinstagram.common.util.ImageUrl;
import com.projectinstagram.common.util.PasswordEncoder;
import com.projectinstagram.domain.user.dto.request.*;
import com.projectinstagram.domain.user.dto.response.JoinUserResponse;
import com.projectinstagram.domain.user.dto.response.LoginUserResponse;
import com.projectinstagram.domain.user.dto.response.ModifiedUserResponse;
import com.projectinstagram.domain.user.dto.response.ProfileViewResponse;
import com.projectinstagram.domain.user.entity.User;
import com.projectinstagram.domain.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final ImageService imageService;

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
        boolean isValid = request.getNickname().matches(RegEXP.NICKNAME);

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

        if (user.getIsDeletion() == true) {
            throw new CustomException(ExceptionMessageEnum.IS_DELETION_USER);
        }

        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordMatches) {
            throw new CustomException(ExceptionMessageEnum.INVALID_MEMBER_INFO);
        }

        String token = jwtUtil.generateToken(
                user.getId(),
                user.getName(),
                user.getNickname()
        );

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

    /**
     * 회원 탈퇴 비지니스 로직 처리
     * @param userId 회원 고유 번호
     * @param request 입력값 파라미터
     */
    public void userDelete(Long userId, DeleteUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID)
                );

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException(ExceptionMessageEnum.NO_MEMBER_INFO);
        }

        user.userDelete(true);
    }

    /**
     * 프로필 조회 비지니스 로직 처리
     * @param userId 회원 고유 번호
     * @return ProfileViewResponse 데이터 반환
     */
    public ProfileViewResponse profileView(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID)
                );

        if (user.getIsDeletion() == true) {
            throw new CustomException(ExceptionMessageEnum.IS_DELETION_USER);
        }

        return new ProfileViewResponse(
                user.getId(),
                user.getNickname(),
                user.getName(),
                user.getIntroduce(),
                user.getProfileImage(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    /**
     * 프로필 업데이트 비지니스 로직 처리
     * @param userId 회원 고유 번호
     * @param request 입력 파라미터
     * @return ModifiedUserResponse 데이터 반환
     */
    public ModifiedUserResponse modifiedUser(Long userId, ModifiedUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID)
                );

        if (user.getIsDeletion() == true) {
            throw new CustomException(ExceptionMessageEnum.IS_DELETION_USER);
        }

        String imgPath = imageService.store(ImageUrl.USER_URL, request.getProfileImg());

        user.userModified(
                request.getEmail(),
                request.getNickname(),
                request.getName(),
                request.getIntroduce(),
                imgPath
        );

        return new ModifiedUserResponse(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getName(),
                user.getProfileImage(),
                user.getIntroduce(),
                user.getCreatedAt(),
                user.getModifiedAt()
        );
    }

    /**
     * 비밀번호 변경 비지니스 로직
     * @param userId 회원 고유 번호
     * @param request 입력값 파라미터
     */
    public void passwordModified(Long userId, PasswordChangeRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new CustomException(ExceptionMessageEnum.NO_MEMBER_ID)
                );

        if (user.getIsDeletion()) {
            throw new CustomException(ExceptionMessageEnum.IS_DELETION_USER);
        }

        if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
            throw new CustomException(ExceptionMessageEnum.INVALID_MEMBER_INFO);
        }

        if (passwordEncoder.matches(request.getNewPassword(), user.getPassword())) {
            throw new CustomException(ExceptionMessageEnum.DUPLICATE_DATA_EXCEPTION);
        }

        String encoded = passwordEncoder.encode(request.getNewPassword());
        user.passwordChange(encoded);
    }
}
