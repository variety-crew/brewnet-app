package com.varc.brewnetapp.domain.franchise.command.domain.aggregate;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class FranchiseMemberId implements Serializable {
    private Integer memberCode;
    private Integer franchiseCode;
}
