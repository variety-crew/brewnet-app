package com.varc.brewnetapp.domain.document.command.domain.repository;

import com.varc.brewnetapp.domain.document.command.domain.aggregate.ApprovalKind;
import com.varc.brewnetapp.domain.document.command.domain.aggregate.entity.Approval;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "commandDocumentApprovalRepository")
public interface ApprovalRepository extends JpaRepository<Approval, Integer> {

    Optional<Approval> findByKindAndSequence(ApprovalKind approvalKind, Integer seq);
}
