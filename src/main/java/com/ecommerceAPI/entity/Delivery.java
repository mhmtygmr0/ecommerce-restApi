package com.ecommerceAPI.entity;

import com.ecommerceAPI.enums.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "delivery")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "assigned_at", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime assignedAt;

    @Column(name = "delivered_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime deliveredAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private DeliveryStatus status;

    @OneToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "courier_id", referencedColumnName = "id", nullable = true)
    private User courier;

    @PrePersist
    protected void onCreate() {
        this.assignedAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }
}
