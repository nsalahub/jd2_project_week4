package com.gmail.salahub.nikolay.service.converters;

import com.gmail.salahub.nikolay.repository.model.Item;
import com.gmail.salahub.nikolay.service.model.ItemDTO;

public interface ItemConverter {

    Item fromDTO(ItemDTO itemDTO);

    ItemDTO toDTO(Item item);
}
