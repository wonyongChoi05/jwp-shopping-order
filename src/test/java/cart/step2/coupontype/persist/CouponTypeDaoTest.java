package cart.step2.coupontype.persist;

import cart.step2.coupontype.domain.CouponTypeEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
class CouponTypeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private CouponTypeDao couponTypeDao;

    @BeforeEach
    void setUp() {
        this.couponTypeDao = new CouponTypeDao(jdbcTemplate);
    }
    
    @DisplayName("모든 쿠폰 종류를 조회한다.")
    @Test
    void findAll() {
        // when
        List<CouponTypeEntity> couponTypeEntities = couponTypeDao.findAllOrderByDiscountAmount();

        // then
        assertAll(
                () -> assertThat(couponTypeEntities).extracting(CouponTypeEntity::getId)
                        .contains(1L, 2L, 3L, 4L),
                () -> assertThat(couponTypeEntities).extracting(CouponTypeEntity::getDiscountAmount)
                        .contains(1000, 3000, 5000, 10000),
                () -> assertThat(couponTypeEntities).extracting(CouponTypeEntity::getName)
                        .contains("1000원 할인 쿠폰", "3000원 할인 쿠폰", "5000원 할인 쿠폰", "10000원 할인 쿠폰"),
                () -> assertThat(couponTypeEntities).extracting(CouponTypeEntity::getDescription)
                        .contains("상품할인쿠폰", "배송비할인쿠폰", "브랜드할인쿠폰", "오픈기념쿠폰")
        );
    }

    @DisplayName("아이디로 쿠폰 종류를 조회한다.")
    @Test
    void findById() {
        // when
        CouponTypeEntity couponTypeEntity = couponTypeDao.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 쿠폰 종류입니다. 쿠폰 타입 ID가 일치하는지 확인해주세요."));

        // then
        Assertions.assertAll(
                () -> assertThat(couponTypeEntity.getId()).isEqualTo(1L),
                () -> assertThat(couponTypeEntity.getDiscountAmount()).isEqualTo(1000),
                () -> assertThat(couponTypeEntity.getName()).isEqualTo("1000원 할인 쿠폰"),
                () -> assertThat(couponTypeEntity.getDescription()).isEqualTo("상품할인쿠폰")
        );
    }

}
