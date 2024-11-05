package com.xocialive.accubook.model.mapper;

import com.xocialive.accubook.model.dto.client.ClientCreateDTO;
import com.xocialive.accubook.model.dto.client.ClientDTO;
import com.xocialive.accubook.model.dto.client.ClientUpdateDTO;
import com.xocialive.accubook.model.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "user.id", target = "userId")
    ClientDTO toClientDTO(Client client);

    @Mapping(target = "id", ignore = true)
    Client toClient(ClientCreateDTO clientCreateDTO);

    void updateClientFromDTO(ClientUpdateDTO clientUpdateDTO, @MappingTarget Client client);

    ClientCreateDTO toClientCreateDTO(Client client);
}
