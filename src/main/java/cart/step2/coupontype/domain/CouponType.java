package cart.step2.coupontype.domain;

public class CouponType {

    private final Long id;
    private final String name;
    private final String description;
    private final Integer discountAmount;

    public CouponType(final Long id, final String name, final String description, final Integer discountAmount) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.discountAmount = discountAmount;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getDiscountAmount() {
        return discountAmount;
    }
}
