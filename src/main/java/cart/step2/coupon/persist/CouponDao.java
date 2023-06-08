package cart.step2.coupon.persist;

import cart.step2.coupon.domain.CouponEntity;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CouponDao {

    private static final Integer USAGE_STATUS_FALSE = 0;
    private static final Integer USAGE_STATUS_TRUE = 1;

    private RowMapper<CouponEntity> rowMapper = (rs, rowNum) -> {
        return new CouponEntity(
                rs.getLong("id"),
                rs.getInt("usage_status"),
                rs.getLong("member_id"),
                rs.getLong("coupon_type_id")
        );
    };

    private final JdbcTemplate jdbcTemplate;

    public CouponDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void updateUsageStatus(final Long memberId, final Long couponId) {
        String sql = "UPDATE coupon " +
                "SET usage_status = " +
                USAGE_STATUS_FALSE +
                " WHERE member_id = ? " +
                "AND id = ?";
        jdbcTemplate.update(sql, memberId, couponId);
    }

    public Long create(final Long memberId, final Long couponTypeId) {
        String sql = "INSERT INTO coupon(usage_status, member_id, coupon_type_id) VALUES (?, ?, ?) ";
        return (long) jdbcTemplate.update(sql, USAGE_STATUS_FALSE, memberId, couponTypeId);
    }

    public Optional<CouponEntity> findById(final Long couponId) {
        String sql = "SELECT * FROM coupon WHERE id = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, couponId));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    public List<CouponEntity> findAll(final Long memberId) {
        String sql = "SELECT * " +
                "FROM coupon c " +
                "INNER JOIN coupon_type ct " +
                "ON c.coupon_type_id = ct.id " +
                "WHERE member_id = ? ";
        return jdbcTemplate.query(sql, rowMapper, memberId);
    }

    public void deleteById(final Long couponId) {
        String sql = "DELETE FROM coupon WHERE id = ?";
        jdbcTemplate.update(sql, couponId);
    }

}
