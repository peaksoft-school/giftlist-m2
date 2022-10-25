package kg.giftlist.giftlistm2.db.service;

import kg.giftlist.giftlistm2.controller.payload.FriendProfileResponse;
import kg.giftlist.giftlistm2.controller.payload.FriendResponse;
import kg.giftlist.giftlistm2.db.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendMapper {
    FriendMapper INSTANCE = Mappers.getMapper(FriendMapper.class);
   FriendProfileResponse friendResponse(User user);
    FriendResponse response(User user, String message);


}
