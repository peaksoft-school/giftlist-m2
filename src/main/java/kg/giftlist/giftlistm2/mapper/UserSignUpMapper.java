package kg.giftlist.giftlistm2.mapper;

import kg.giftlist.giftlistm2.db.service.UserService;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = UserService.class)
public interface UserSignUpMapper {

}