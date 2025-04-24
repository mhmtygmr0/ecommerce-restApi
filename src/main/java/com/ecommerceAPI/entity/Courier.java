package com.ecommerceAPI.entity;

import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.enums.CourierStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Entity
@Table(name = "courier")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Courier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "phone", nullable = false, unique = true)
    @Pattern(regexp = "^[0-9]{11}$", message = Msg.PHONE)
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CourierStatus status;

    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.PERSIST)
    private List<Delivery> deliveryList;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    }
}
