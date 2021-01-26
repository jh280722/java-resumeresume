package com.rere.item;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class ItemDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Item> actorRowMapper = (resultSet, rowNum) -> {
        Item item = new Item(
                resultSet.getLong("id"),
                resultSet.getString("type"),
                resultSet.getString("name"),
                resultSet.getString("value"),
                resultSet.getLong("boxid")
        );
        return item;
    };

    public Item save(Item item) {
        String sql = "insert into item (type, name, value, boxid) values (? , ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setString(1, item.getType());
            ps.setString(2, item.getName());
            ps.setString(3, item.getValue());
            ps.setLong(4, item.getBoxId());
            return ps;
        }, keyHolder);
        Item persistItem = new Item(keyHolder.getKey().longValue(), item.getType(), item.getName(), item.getValue(),item.getBoxId());

        return persistItem;
    }

    public void update(Item originItem, Item updateItem) {
        String sql = "update item set type=?, name=?, value=?, boxid=? where id = ?";
        jdbcTemplate.update(sql, updateItem.getType(),updateItem.getName(), updateItem.getValue(),updateItem.getBoxId() ,originItem.getId());
    }

    public List<Item> findAll() {
        String sql = "select * from item";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public void deleteById(Long id) {
        String sql = "delete from item where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Item findById(Long id) {
        String sql = "select * from item where id = ?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, id);
    }
}
