package com.rere.item.dao;

import com.rere.item.domain.Item;
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

    public ItemDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Item> actorRowMapper = (resultSet, rowNum) -> Item.of(
            resultSet.getLong("id"),
            resultSet.getString("type"),
            resultSet.getString("name"),
            resultSet.getString("value"),
            resultSet.getLong("box_id")
    );

    public Item save(Item item) {
        String sql = "insert into item (type, name, value, box_id) values (? , ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, item.getType());
            ps.setString(2, item.getName());
            ps.setString(3, item.getValue());
            ps.setLong(4, item.getBoxId());
            return ps;
        }, keyHolder);

        return Item.of(keyHolder.getKey().longValue(), item.getType(), item.getName(), item.getValue(), item.getBoxId());
    }

    public void update(Long id, Item updateItem) {
        String sql = "update item set type=?, name=?, value=?, box_id=? where id = ?";
        jdbcTemplate.update(sql, updateItem.getType(), updateItem.getName(), updateItem.getValue(), updateItem.getBoxId(), id);
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
