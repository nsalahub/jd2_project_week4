package com.gmail.salahub.nikolay.repository.impl;

import com.gmail.salahub.nikolay.repository.ItemRepository;
import com.gmail.salahub.nikolay.repository.exception.ItemRepositoryException;
import com.gmail.salahub.nikolay.repository.model.Item;
import com.gmail.salahub.nikolay.repository.model.StatusEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository("itemRepository")
public class ItemRepositoryImpl implements ItemRepository {

    private static final Logger logger = LoggerFactory.getLogger(ItemRepositoryImpl.class);

    private static final String ADD_ITEM_REPOSITORY_ERROR_MESSAGE = "AddItem method error at repository level";
    private static final String GET_ITEMS_REPOSITORY_ERROR_MESSAGE = "error at method get list of items ";

    @Override
    public Item add(Connection connection, Item item) {
        String sql = "INSERT INTO item VALUES(?,?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setString(2, item.getStatusEnum().toString());
            preparedStatement.execute();
            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    return getItemWithId(resultSet, item);
                }
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(ADD_ITEM_REPOSITORY_ERROR_MESSAGE, e);
        }
        return null;
    }

    @Override
    public List<Item> getListOfItems(Connection connection) {
        List<Item> items = new ArrayList<>();
        String sqlQuery = "SELECT * FROM item WHERE DELETED =?";
        try (PreparedStatement statement = connection.prepareStatement(sqlQuery)) {
            statement.setBoolean(1, false);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    items.add(getItem(resultSet));
                }
                return items;
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new ItemRepositoryException(GET_ITEMS_REPOSITORY_ERROR_MESSAGE, e);
        }
    }

    private Item getItemWithId(ResultSet resultSet, Item item) throws SQLException {
        Item savedItem = new Item();
        savedItem.setStatusEnum(item.getStatusEnum());
        savedItem.setName(item.getName());
        savedItem.setId(resultSet.getLong(1));
        return savedItem;
    }

    private Item getItem(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        item.setId(resultSet.getLong("id"));
        item.setName(resultSet.getString("name"));
        item.setStatusEnum(StatusEnum.valueOf(resultSet.getString("status")));
        return item;
    }

}
