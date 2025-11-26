package com.projectinstagram.domain.friend.service;

import com.projectinstagram.common.exception.CustomException;
import com.projectinstagram.domain.friend.deletion.UserRepository;
import com.projectinstagram.domain.friend.dto.*;
import com.projectinstagram.domain.friend.entity.Friend;
import com.projectinstagram.domain.friend.entity.FriendId;
import com.projectinstagram.domain.friend.repository.FriendRepository;
import com.projectinstagram.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.projectinstagram.common.exception.ExceptionMessageEnum.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    //팔로우 언팔로우 공통정보 준비필드
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

        FriendId friendId = new FriendId(userIdFrom, userIdTo);
        boolean isFriended = friendRepository.existsById(friendId);//이미 친구인지 확인 후 boolean을 return값에 전달

        return new FriendInfo(friendId, userFrom, userTo, isFriended);
    }
    //endregion

    //region 친구추가 (팔로우)
    public CreateResponse follow (CreateRequest request, Long userIdFrom/*토큰으로부터 받은 값(수정필요)*/) {
        FriendInfo friendinfo = prepareFriendInfo(request, userIdFrom);
        if (friendinfo.isFriended) {throw new CustomException(ALREADY_FRIEND_EXCEPTION);} //이미 친구인지 확인
        Friend friend = new Friend(friendinfo.getUserFrom(), friendinfo.getUserTo());
        friendRepository.save(friend);
        return new CreateResponse(userIdFrom); /*토큰값으로부터 Long으로 변환필요*/
    }
    //endregion

    //region 친구삭제 (언팔로우)
    public void unfollow(CreateRequest request, Long userIdFrom/*토큰으로부터 받은 값(수정필요)*/) {
        FriendInfo friendinfo = prepareFriendInfo(request, userIdFrom);
        if (!friendinfo.isFriended) {throw new CustomException(NOT_FRIEND_EXCEPTION);} //친구가 아닌지 확인
        friendRepository.deleteById(friendinfo.friendId);
    }
    //endregion

    //region 팔로워 리스트 조회
    public List<ReadUserResponse> getFollowerList(Long userId) {
        List<Friend> followerList = friendRepository.findByIdUserIdTo(userId); // 팔로워 : userId가 userIdTo에 등록되어있음
        return convertFollowerListToReadUserResponseList(followerList);
    }
    //endregion

    //region 팔로워 List<Friend>를 List<ReadUserResponse>로 변환매서드
    private List<ReadUserResponse> convertFollowerListToReadUserResponseList(List<Friend> followerList) {
        List<ReadUserResponse> followerUserList = new ArrayList<>();

        for (Friend friend : followerList) {
            User user = userRepository.findById(friend.getId().getUserIdFrom()) // 팔로워 : getUserIdFrom()
                    .orElseThrow(() -> new CustomException(NO_MEMBER_ID));
            ReadUserResponse dto = new ReadUserResponse(
                    user.getId(),
                    user.getNickname(),
                    user.getName(),
                    user.getProfileImage(),
                    user.getIntroduce()
            );
            followerUserList.add(dto);
        }
        return followerUserList;
    }
    //endregion

    //region 팔로잉 리스트 조회
    public List<ReadUserResponse> getFollowingList(Long userId) {
        List<Friend> followingList = friendRepository.findByIdUserIdFrom(userId); // 팔로잉 : userId가 userIdFrom에 등록되어있음
        return convertFolloingListToReadUserResponseList(followingList);
    }
    //endregion

    //region 팔로잉 List<Friend>를 List<ReadUserResponse>로 변환매서드
    private List<ReadUserResponse> convertFolloingListToReadUserResponseList(List<Friend> followingList) {
        List<ReadUserResponse> followingUserList = new ArrayList<>();

        for (Friend friend : followingList) {
            User user = userRepository.findById(friend.getId().getUserIdTo()) // 팔로잉 : getUserIdTo()
                    .orElseThrow(() -> new CustomException(NO_MEMBER_ID));
            ReadUserResponse dto = new ReadUserResponse(
                    user.getId(),
                    user.getNickname(),
                    user.getName(),
                    user.getProfileImage(),
                    user.getIntroduce()
            );
            followingUserList.add(dto);
        }
        return followingUserList;
    }
    //endregion

    //region 팔로워,팔로잉 수 조회
    public ReadCountResponse getFollowCount(Long userId) {
        List<Friend> followList = friendRepository.findByIdUserIdFromOrIdUserIdTo(userId, userId);
        Long followerCount = followList.stream().filter(Friend -> Friend.getUserTo().getId().equals(userId)).count();
        Long followingCount = followList.stream().filter(friend -> friend.getUserFrom().getId().equals(userId)).count();
        return new ReadCountResponse(followerCount, followingCount);
    }
    //endregion

    //region 팔로워,팔로잉 수 및 리스트 조회
    public ReadCountAndUserResponse getFollowCountList(Long userId) {
        List<Friend> followList = friendRepository.findByIdUserIdFromOrIdUserIdTo(userId, userId); //userId의 팔로워 + 팔로잉 리스트

        List<Friend> followerList = followList.stream().filter(Friend -> Friend.getUserTo().getId().equals(userId)).toList();
        List<ReadUserResponse> followerUserList = convertFollowerListToReadUserResponseList(followerList); //팔로워 리스트 필터
        List<Friend> followingList = followList.stream().filter(friend -> friend.getUserFrom().getId().equals(userId)).toList();
        List<ReadUserResponse> followingUserList = convertFolloingListToReadUserResponseList(followingList); //팔로잉 리스트 필터

        Long followerCount = (long) followerList.size(); //팔로워 수
        Long followingCount = (long) followingList.size(); //팔로잉 수

        return new ReadCountAndUserResponse(followerCount, followingCount, followerUserList, followingUserList);
    }
    //endregion

}
