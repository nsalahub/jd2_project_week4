package com.gmail.salahub.nikolay.repository;

import com.gmail.salahub.nikolay.repository.model.Item;

import java.sql.Connection;
import java.util.List;

public interface ItemRepository {
    Item add(Connection connection, Item item);

    List<Item> getListOfItems(Connection connection);
}
