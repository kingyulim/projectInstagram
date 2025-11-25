package com.projectinstagram.domain.friend.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.domain.friend.deletion.UserRepository;
import com.projectinstagram.domain.friend.dto.CreateRequest;
import com.projectinstagram.domain.friend.dto.CreateResponse;
import com.projectinstagram.domain.friend.entity.Friend;
import com.projectinstagram.domain.friend.entity.FriendId;
import com.projectinstagram.domain.friend.repository.FriendRepository;
import com.projectinstagram.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.projectinstagram.common.exception.ExceptionMessageEnum.*;

@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    //공통정보 준비용 필드
    @Getter
    @AllArgsConstructor
    private static class FriendInfo {
        private final FriendId friendId;
        private final User userFrom;
        private final User userTo;
        private final boolean isFriended;
    }

    //region 팔로우 언팔로우 공통정보 준비매서드 (예외처리포함)
    private FriendInfo prepareFriendInfo(CreateRequest request, Long userIdFrom) {
        Long userIdTo = request.getUserIdTo();
        if (userIdFrom == null || userIdTo == null) {throw new CustomException(NO_MEMBER_ID);}
        if (userIdTo.equals(userIdFrom)) {throw new CustomException(SELF_FOLLOW_EXCEPTION);} //스스로를 친구할 수 없는 기능

        User userTo = userRepository.findById(userIdTo).orElseThrow(() -> new CustomException(NO_MEMBER_ID));
        User userFrom = userRepository.findById(userIdFrom).orElseThrow(() -> new CustomException(NO_MEMBER_ID));
        Friend friend = new Friend(userFrom, userTo); //Friend 인스턴스 생성

        FriendId friendId = new FriendId(userIdFrom, userIdTo);
        boolean isFriended = friendRepository.existsById(friendId);//이미 친구인지 확인 후 boolean을 return값에 전달

        return new FriendInfo(friendId, userFrom, userTo, isFriended);
    }
    //endregion

    //친구추가 (팔로우)
    public CreateResponse follow (CreateRequest request, Long userIdFrom/*토큰으로부터 받은 값(수정필요)*/) {
        FriendInfo friendinfo = prepareFriendInfo(request, userIdFrom);
        if (friendinfo.isFriended == true) {throw new CustomException(ALREADY_FRIEND_EXCEPTION);} //이미 친구인지 확인
        Friend friend = new Friend(friendinfo.getUserFrom(), friendinfo.getUserTo());
        friendRepository.save(friend);
        return new CreateResponse(userIdFrom); /*토큰값으로부터 Long으로 변환필요*/
    }

    //친구삭제 (언팔로우)
    public void unfollow(CreateRequest request, Long userIdFrom/*토큰으로부터 받은 값(수정필요)*/) {
        FriendInfo friendinfo = prepareFriendInfo(request, userIdFrom);
        if (friendinfo.isFriended == false) {throw new CustomException(NOT_FRIEND_EXCEPTION);} //친구가 아닌지 확인
        friendRepository.deleteById(friendinfo.friendId);
    }

}
