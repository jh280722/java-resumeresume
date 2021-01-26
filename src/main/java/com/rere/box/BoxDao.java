package com.rere.box;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class BoxDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public BoxDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Box> actorRowMapper = (resultSet, rowNum) -> {
        Box box = new Box(
                resultSet.getLong("id")
        );
        return box;
    };

    public Box save(Box box) {
        String sql = "insert into box (docid) values (?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "id" });
            ps.setLong(1, 1);
            return ps;
        }, keyHolder);
        Box persistItem = new Box(keyHolder.getKey().longValue(), box.getItems());

        return persistItem;
    }

    public void update(Box originBox, Box updateBox) {
//        String sql = "update item set type=?, name=?, value=?, boxid=? where id = ?";
//        jdbcTemplate.update(sql, updateBox.getType(),updateBox.getName(), updateBox.getValue(),updateBox.getBoxId() ,originBox.getId());
    }

    public List<Box> findAll() {
        String sql = "select * from box join item on box.id=item.boxid";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

    public void deleteById(Long id) {
        String sql = "delete from box where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public Box findById(Long id) {
        String sql = "select * from box where id = ?";
//        String sql = "select * from box join item on box.id=item.boxid where box.id = ?";
        return jdbcTemplate.queryForObject(sql, actorRowMapper, id);
    }
}
