package com.varc.brewnetapp.domain.exchange.command.domain.aggregate.ex_entity;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.entity.Position;
import jakarta.persistence.*;
import lombok.*;

@Data
@Getter
@Setter
@Entity
@Table(name = "tbl_member")
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ExMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_code", nullable = false)
    private Integer memberCode;

    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Column(name = "signature_url")
    private String signatureUrl;

    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;

    @Column(name = "active", nullable = false)
    private Boolean active;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "position_code")
    private Position position;
}
