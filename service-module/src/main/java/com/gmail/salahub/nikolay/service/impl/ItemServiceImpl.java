package com.gmail.salahub.nikolay.service.impl;

import com.gmail.salahub.nikolay.repository.ItemRepository;
import com.gmail.salahub.nikolay.repository.exception.ItemRepositoryException;
import com.gmail.salahub.nikolay.repository.model.Item;
import com.gmail.salahub.nikolay.service.ItemService;
import com.gmail.salahub.nikolay.service.converters.ItemConverter;
import com.gmail.salahub.nikolay.service.model.ItemDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.ConnectionHandle;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    private static final String GETTING_LIST_ERROR_MASSAGE = "Error with getting list of items  at service module";
    private static final String ADD_ITEM_ERROR_MESSAGE = "Add item method error at service module, check console";

    private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);
    private final ConnectionHandle connectionHandle;
    private final ItemRepository itemRepository;
    private final ItemConverter itemConverter;

    @Autowired
    public ItemServiceImpl(ConnectionHandle connectionHandle, ItemRepository itemRepository, ItemConverter itemConverter) {
        this.connectionHandle = connectionHandle;
        this.itemRepository = itemRepository;
        this.itemConverter = itemConverter;
    }

    @Override
    public ItemDTO add(ItemDTO itemDTO) {
        try (Connection connection = connectionHandle.getConnection()) {
            try {
                connection.setAutoCommit(false);
                Item originItem = itemConverter.fromDTO(itemDTO);
                Item savedItem = itemRepository.add(connection, originItem);
                ItemDTO savedDTO = itemConverter.toDTO(savedItem);
                connection.commit();
                return savedDTO;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ItemRepositoryException(e.getMessage(), e);
            }
        } catch (Exception e) {
            logger.error(ADD_ITEM_ERROR_MESSAGE, e);
            throw new ItemRepositoryException(ADD_ITEM_ERROR_MESSAGE, e);
        }
    }

    @Override
    public List<ItemDTO> getListOfItems() {
        List<ItemDTO> savedItems = new ArrayList<>();
        try (Connection connection = connectionHandle.getConnection()) {
            try {
                connection.setAutoCommit(false);
                List<Item> items = itemRepository.getListOfItems(connection);
                for (Item item : items) {
                    savedItems.add(itemConverter.toDTO(item));
                }
                connection.commit();
                return savedItems;
            } catch (SQLException e) {
                connection.rollback();
                logger.error(e.getMessage(), e);
                throw new ItemRepositoryException(GETTING_LIST_ERROR_MASSAGE, e);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(GETTING_LIST_ERROR_MASSAGE, e);
        }
    }
}
