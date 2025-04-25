package com.ecommerceAPI.entity;

import com.ecommerceAPI.core.utils.Msg;
import com.ecommerceAPI.enums.CourierStatus;
import com.ecommerceAPI.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    @JsonFormat(pattern = "dd-MM-yyyy")
    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "phone", nullable = false, unique = true)
    @Pattern(regexp = "^[0-9]{11}$", message = Msg.PHONE)
    private String phone;

    @Email(message = Msg.EMAIL)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "courier_status")
    private CourierStatus courierStatus;

    @Column(name = "created_at", updatable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "basket_id", referencedColumnName = "id")
    private Basket basket;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addressList;

    @OneToMany(mappedBy = "user", cascade = CascadeType.PERSIST)
    private List<Order> orderList;

    @OneToMany(mappedBy = "courier", cascade = CascadeType.PERSIST)
    private List<Delivery> deliveries;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDate.now();
        this.validateCourierStatus();
    }

    @PreUpdate
    protected void onUpdate() {
        this.validateCourierStatus();
    }

    private void validateCourierStatus() {
        if (this.role != UserRole.COURIER) {
            this.courierStatus = null;
        } else {
            this.courierStatus = CourierStatus.AVAILABLE;
        }
    }
}

