package com.gmail.salahub.nikolay.service;

import com.gmail.salahub.nikolay.service.model.ItemDTO;

import java.util.List;

public interface ItemService {

    List<ItemDTO> getListOfItems();

    ItemDTO add(ItemDTO itemDTO);
}
