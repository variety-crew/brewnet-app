package com.varc.brewnetapp.domain.member.query.dto;

import com.varc.brewnetapp.domain.auth.command.domain.aggregate.RoleType;
import com.varc.brewnetapp.domain.member.command.domain.aggregate.PositionName;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDTO {

    private Integer memberCode;
    private String id;
    private String name;
    private String email;
    private String contact;
    private String signatureUrl;
    private PositionName positionName;
    private String franchiseName;
    private RoleType role;
}
