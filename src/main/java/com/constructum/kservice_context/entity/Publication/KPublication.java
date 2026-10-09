package com.constructum.kservice_context.entity.Publication;

import com.constructum.kservice_context.entity.common.KAnchor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;


/**
 * Единый якорь публикаций (Publication Model).
 *
 * АРХИТЕКТУРНЫЙ ИНВАРИАНТ:
 * Публикация принадлежит напрямую KObject (через target_object_id).
 * KContext НЕ связан с публикациями — отвечает только за owner/placement.
 *
 * Дискриминатор type:
 *   - 'kpublish'     → внутренняя публикация (PROJECT/GROUP/COMPANY)
 *   - 'kgrant'       → B2B грант (COMPANY(grantee))
 */
@Entity
@Table(name = "kpublication", schema = "entities")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter
public abstract class KPublication extends KAnchor {

    @Column(name = "target_object_id", nullable = false, updatable = false)
    private UUID targetObjectId;

    @Column(name = "company_id", nullable = false, updatable = false)
    private UUID companyId;

    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;

    @Column(name = "type", insertable = false, updatable = false)
    private String type;

    protected KPublication() {}

}
