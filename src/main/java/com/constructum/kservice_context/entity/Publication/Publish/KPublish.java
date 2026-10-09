package com.constructum.kservice_context.entity.Publication.Publish;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

import com.constructum.kservice_context.entity.Publication.KPublication;


@Entity
@DiscriminatorValue("kpublish")
public class KPublish extends KPublication {

    protected KPublish() {}

    public static KPublish create(UUID targetObjectId, UUID companyId, UUID createdBy, Instant now) {
        
        if (targetObjectId == null || companyId == null || createdBy == null) {
            throw new IllegalArgumentException("KPublish.create - All IDs are required");
        }

        KPublish pub = new KPublish();
        pub.setId(UUID.randomUUID());
        pub.setTargetObjectId(targetObjectId);
        pub.setCompanyId(companyId);
        pub.setCreatedBy(createdBy);
        pub.setValidFrom(now);
        pub.setValidTo(null);
        // type устанавливается автоматически Hibernate через @DiscriminatorValue
        return pub;
    }
}
