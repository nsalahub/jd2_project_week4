package com.gmail.salahub.nikolay.service.converters.impl;

import com.gmail.salahub.nikolay.repository.model.Item;
import com.gmail.salahub.nikolay.service.converters.ItemConverter;
import com.gmail.salahub.nikolay.service.model.ItemDTO;
import org.springframework.stereotype.Component;

@Component("itemConverter")
public class ItemConverterImpl implements ItemConverter {

    @Override
    public Item fromDTO(ItemDTO itemDTO) {
        Item item = new Item();
        item.setId(itemDTO.getId());
        item.setName(itemDTO.getName());
        item.setStatusEnum(itemDTO.getStatusEnum());
        return item;
    }

    @Override
    public ItemDTO toDTO(Item item) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setName(item.getName());
        itemDTO.setStatusEnum(item.getStatusEnum());
        return itemDTO;
    }
}
