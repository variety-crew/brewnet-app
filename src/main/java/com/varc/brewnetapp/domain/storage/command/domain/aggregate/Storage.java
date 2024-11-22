package com.varc.brewnetapp.domain.storage.command.domain.aggregate;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_storage")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Storage {

    @Id
    @Column(name = "storage_code")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer storageCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
