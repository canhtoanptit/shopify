package com.paypal.mng.service.mapper;

import com.paypal.mng.domain.*;
import com.paypal.mng.service.dto.OrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Order} and its DTO {@link OrderDTO}.
 */
@Mapper(componentModel = "spring", uses = {StoreMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

    @Mapping(source = "store.id", target = "storeId")
    OrderDTO toDto(Order order);

    @Mapping(source = "storeId", target = "store")
    Order toEntity(OrderDTO orderDTO);

    default Order fromId(Long id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
