package com.constructum.kservice_context.entity.Publication.Grant;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

import com.constructum.kservice_context.entity.Publication.KPublication;

/**
 * Темпоральное состояние гранта, выданного объекту.
 * Определяет, объект на который выдан грант, какая компания выдала грант
 * какой компании выдан грант.
 *
 * Это самостоятельный объект, живет только в KService-Context (в локальной таблице),
 * не является потомком от KObject и KObjectAttributes. 
 * 
 * Описывает передачу прав на объект от одной компании другой.
 *
 * Атрибуты:
 * - sourceCompanyId: владелец объекта (уточнить нужен ли?)
 * - granteeCompanyId: получатель прав
 * - targetObjectId: ссылка на KObject.id
 * - validTo = NULL → грант активен
 *
 * Живёт только в KService-Context (не реплицируется из Core Service)
 */


@Entity
@DiscriminatorValue("kgrant")
public class KGrant extends KPublication {

    // защищаем конструктор
    protected KGrant() {}
    
    // Проверка: активен ли грант.
    public boolean isActive() { return validTo == null; }
    
    /**
     * Фабричный метод создания гранта.
     * ID генерируется случайно (локальный UUID).
     */
    public static KGrant create(
        UUID targetObjectId,
        //UUID sourceCompanyId,  <-- вынесено в атрибут (TODO: решить нужен ли этот атрибут, возможно , достаточно companyId)
        //UUID granteeCompanyId, <-- вынесено в атрибут
        UUID companyId,
        UUID createdBy,
        Instant now
        
    ) {
        if (targetObjectId == null || companyId == null || createdBy == null) {
            throw new IllegalArgumentException("KGrant.create() All IDs are required");
        }
        // if (sourceCompanyId.equals(granteeCompanyId)) {
        //     throw new IllegalArgumentException("Cannot grant access to yourself");
        // }
        

        // Для KGrant ID генерируется локально, так как KContext живет только в этом сервисе
        KGrant grant = new KGrant();

        grant.setId(UUID.randomUUID()); // сосздаем UUID
        grant.setTargetObjectId(targetObjectId);
        grant.setCompanyId(companyId);
        // grant.setSourceCompanyId(sourceCompanyId); <-- вынесено в атрибут
        // grant.setGranteeCompanyId(granteeCompanyId); <-- вынесено в атрибут
        grant.setCreatedBy(createdBy);
        grant.setValidFrom(now);
        grant.setValidTo(null);
        return grant;

    }
    
}
