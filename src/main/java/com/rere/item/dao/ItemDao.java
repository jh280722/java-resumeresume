package com.rere.item.dao;

import com.rere.box.domain.Box;
import com.rere.item.domain.Item;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ItemDao {
    private JdbcTemplate jdbcTemplate;

    public ItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Item> actorRowMapper = (resultSet, rowNum) -> Item.of(
            resultSet.getLong("id"),
            resultSet.getString("type"),
            resultSet.getString("name"),
            resultSet.getString("value"),
            Box.of(resultSet.getLong("box_id"))
    );

    public void update(Long id, Item updateItem) {
        String sql = "update item set type=?, name=?, value=?, box_id=? where id = ?";
        jdbcTemplate.update(sql, updateItem.getType(), updateItem.getName(), updateItem.getValue(), updateItem.getBox(), id);
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

    public void deletByBoxId(Long boxId) {
        String sql = "delete from item where box_id = ?";
        jdbcTemplate.update(sql, boxId);
    }
}
