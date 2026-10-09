package com.constructum.kservice_context.entity.Publication.Grant;

import com.constructum.kservice_context.entity.Publication.KPublicationAttributes;
import com.constructum.kservice_context.entity.common.KAttributeKey;
import com.constructum.kservice_context.entity.common.KSystemAttributes;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import java.time.Instant;
import java.util.UUID;

/**
 * Атрибут KPublication (type = KGrant)
 * Атрибут B2B гранта: компания-получатель прав.
 *
 * АРХИТЕКТУРНЫЙ ИНВАРИАНТ:
 * grantee_company_id != company_id (запрет выдачи самому себе — CHECK в DDL).
 */
@Entity
@Table(name = "attr_grantee_company", schema = "entities")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public class AKGranteeCompany extends KPublicationAttributes {

    @Column(name = "grantee_company_id", nullable = false)
    private UUID granteeCompanyId;

    public UUID getValue() {
        return granteeCompanyId;
    }

    public static AKGranteeCompany create(KGrant pub, UUID granteeCompanyId, Instant now, UUID companyId, UUID createdBy) 
    {
        if (pub == null || granteeCompanyId == null) {
            throw new IllegalArgumentException("pub and granteeCompanyId are required");
        }
        return AKGranteeCompany.builder()
            .key(new KAttributeKey(pub.getId(), now))
            .kObjectPublication(pub)
            .granteeCompanyId(granteeCompanyId)
            .systemAttributes(new KSystemAttributes(null, companyId, createdBy))
            .build();
    }
}
