package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.FriendsResponse;
import kg.giftlist.giftlistm2.controller.payload.Response;
import kg.giftlist.giftlistm2.controller.payload.ResponseFriend;
import kg.giftlist.giftlistm2.db.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendMapper {
    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);
   FriendsResponse friendResponse(User user);
    Response response(User user,String message);
    ResponseFriend friendResonse(User user,int count);

}
