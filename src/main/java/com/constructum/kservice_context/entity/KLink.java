package com.constructum.kservice_context.entity.Link;

import java.util.UUID;

import com.constructum.kservice_context.entity.common.KAnchor;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Реестр связей (relationship registry).
 *
 * В отличие от KObject:
 * - не получает KContext;
 * - не участвует напрямую в ACL;
 * - не имеет owner/project/publication semantics;
 * - используется как источник событий для пересчета ACL.
 *
 * Примеры:
 * - KGrant
 * - KUserGroupLink
 *
 * Инвариант:
 * каждая запись KLink представляет один факт связи.
 */
@Entity
@Table(name = "klink")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Getter @Setter
public abstract class KLink extends KAnchor {

    
    // Идентификатор компании владельца объекта, для разграничения прав (RLS)
    @Column(name = "company_id", nullable = false, updatable = false)
    private UUID companyId;

    // Идентификатор пользователя (кто создал запись)
    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;

    //Hibernate сам управляет колонкой, указанной в @DiscriminatorColumn.
    //чтобы не возникли конфликты должно быть insertable = false, updatable = false
    //Примеры: KGrant, KUserGroupLink
    @Column(nullable = false, length = 64, insertable = false, updatable = false)
    private String type; 

    // защищаем конструктор
    protected KLink() {}


}
