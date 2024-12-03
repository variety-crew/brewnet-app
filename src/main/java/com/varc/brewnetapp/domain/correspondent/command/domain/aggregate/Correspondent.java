package com.varc.brewnetapp.domain.correspondent.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_correspondent")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Correspondent {

    @Id
    @Column(name = "correspondent_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer correspondentCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "detail_address")
    private String detailAddress;

    @Column(name = "email")
    private String email;

    @Column(name = "contact")
    private String contact;

    @Column(name = "manager_name")
    private String managerName;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
