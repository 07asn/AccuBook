package com.xocialive.accubook.model.mapper;

import com.xocialive.accubook.model.dto.client.ClientCreateUpdateDTO;
import com.xocialive.accubook.model.dto.client.ClientDTO;
import com.xocialive.accubook.model.dto.client.ClientWithTotalsDTO;
import com.xocialive.accubook.model.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "user.id", target = "userId")
    ClientDTO toClientDTO(Client client);

    @Mapping(target = "id", ignore = true)
    Client toClient(ClientCreateUpdateDTO clientCreateUpdateDTO);

    void updateClientFromDTO(ClientCreateUpdateDTO clientUpdateDTO, @MappingTarget Client client);

    ClientCreateUpdateDTO toClientCreateUpdateDTO(Client client);

    @Mapping(source = "client", target = "client")
    ClientWithTotalsDTO toClientWithTotalsDTO(ClientDTO client, BigDecimal totalReceived, BigDecimal totalBorrowed);
}
