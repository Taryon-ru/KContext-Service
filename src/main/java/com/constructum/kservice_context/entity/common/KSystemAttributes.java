package com.constructum.kservice_context.entity.common;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.UUID;


/**
 * Системные атрибуты (validTo, companyId, createdBy, sessionNum).
 */

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class KSystemAttributes {

    @Column(name = "valid_to")
    private Instant validTo;

    @Column(name = "company_id", nullable = false, updatable = false)
    private UUID companyId;

    /**
     * Идентификатор пользователя, который создал данную версию атрибута.
     * Позволяет отследить фактического автора изменений.
     */
    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;

    /**
     * TODO:
     * Номер сессии. Позволяет отследить каждую операцию в базе по номеру.
     */
    // @Column(name = "session_num", nullable = false, updatable = false)
    // private int sessionNum;
}
