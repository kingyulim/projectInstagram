package com.projectinstagram.domain.friend.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.domain.friend.deletion.UserRepository;
import com.projectinstagram.domain.friend.dto.CreateRequest;
import com.projectinstagram.domain.friend.dto.CreateResponse;
import com.projectinstagram.domain.friend.entity.Friend;
import com.projectinstagram.domain.friend.entity.FriendId;
import com.projectinstagram.domain.friend.repository.FriendRepository;
import com.projectinstagram.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.projectinstagram.common.exception.ExceptionMessageEnum.NO_MEMBER_ID;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    //팔로우 (친구추가 및 취소)
    public CreateResponse createResponse(CreateRequest request, Long userIdFrom/*토큰으로부터 받은 값(수정필요)*/) {
        Long userIdTo = request.getUserIdTo();
        if (userIdFrom == null) {throw new CustomException(NO_MEMBER_ID);}/*토큰으로부터 받으면 검사 안해도되나?*/
        if (userIdTo == null) {throw new CustomException(NO_MEMBER_ID);}
        if (userIdTo.equals(userIdFrom)) {throw new CustomException(null);} //스스로를 친구할 수 없는 기능 /*병합시 User 엔티티의 user필드 명칭 변경되면 에러*/ /*401 Unauthorized에러 추가: 스스로를 친구추가할 수 없습니다.*/
        FriendId friendId = new FriendId(userIdFrom, userIdTo);
        boolean isFriended = friendRepository.existsById(friendId); //이미 친구인지 확인

        if (isFriended == true) {
            friendRepository.deleteById(friendId); //이미 친구면 친구취소
        } else {
                User userTo = userRepository.findById(userIdTo).orElseThrow(() -> new CustomException(NO_MEMBER_ID));
                User userFrom = userRepository.findById(userIdFrom).orElseThrow(() -> new CustomException(NO_MEMBER_ID));
                Friend friend = new Friend(userFrom, userTo);
                friendRepository.save(friend);
        }
        return new CreateResponse(userIdFrom); /*토큰값으로부터 Long으로 변환필요*/
    }
}
