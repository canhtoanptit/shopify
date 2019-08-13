package com.paypal.mng.service.mapper;

import com.paypal.mng.domain.*;
import com.paypal.mng.service.dto.TransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Transaction} and its DTO {@link TransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {OrderMapper.class})
public interface TransactionMapper extends EntityMapper<TransactionDTO, Transaction> {

    @Mapping(source = "order.id", target = "orderId")
    TransactionDTO toDto(Transaction transaction);

    @Mapping(source = "orderId", target = "order")
    Transaction toEntity(TransactionDTO transactionDTO);

    default Transaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        Transaction transaction = new Transaction();
        transaction.setId(id);
        return transaction;
    }
}
