package com.xocialive.accubook.model.mapper;

import com.xocialive.accubook.model.dto.user.UserCreateDTO;
import com.xocialive.accubook.model.dto.user.UserDTO;
import com.xocialive.accubook.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toUserDTO(User user);

    User toUser(UserCreateDTO userCreateDTO);

    @Mapping(target = "password", ignore = true)
    void updateUserFromDto(UserCreateDTO userCreateDTO, @MappingTarget User user);
}
