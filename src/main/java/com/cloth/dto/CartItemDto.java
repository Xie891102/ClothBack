package com.cloth.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CartItemDto {
    private Integer productId;
    private Integer usersId;
    private Integer quantity;
    private Integer status;
    private Integer discountPrice; // 添加折扣价格字段

    // 带参数的构造函数
    public CartItemDto(Integer productId, Integer usersId, int quantity, int status, int discountPrice) {
        this.productId = productId;
        this.usersId = usersId;
        this.quantity = quantity;
        this.status = status;
        this.discountPrice = discountPrice;
    }
}