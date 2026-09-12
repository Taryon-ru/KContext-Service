package com.constructum.kservice_context.entity.Link.LinkUserGroup;

import com.constructum.kservice_context.entity.Link.KLink;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

import java.time.Instant;
import java.util.UUID;



/**
 * Связь пользователь ↔ группа.
 *
 * Не является объектом предметной области.
 * Не имеет собственного контекста.
 * Не участвует напрямую в ACL.
 *
 * Используется как источник данных
 * для построения effective_user_group.
 */
@Entity
@DiscriminatorValue("klink_user_group")
public class KLinkUserGroup extends KLink {

    protected KLinkUserGroup() {}

    public static KLinkUserGroup create(UUID companyId, UUID createdBy, Instant now) {
        
        KLinkUserGroup link = new KLinkUserGroup();
        link.setId(UUID.randomUUID());
        link.setCompanyId(companyId);
        link.setCreatedBy(createdBy);
        link.setValidFrom(now);
        link.setValidTo(null);
        return link;
    }
}
