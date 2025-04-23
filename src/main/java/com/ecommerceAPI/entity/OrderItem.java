package com.ecommerceAPI.entity;

import com.ecommerceAPI.core.utils.Msg;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "order_items")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "quantity")
    @Min(value = 1, message = Msg.QUANTITY)
    private Long quantity;

    @Column(name = "total_price")
    private Double totalPrice;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price")
    private Double productPrice;

    @Column(name = "product_image_url")
    private String productImageUrl;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}
