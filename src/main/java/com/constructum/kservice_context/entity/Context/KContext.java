package com.constructum.kservice_context.entity.Context;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

import com.constructum.kservice_context.entity.common.KAnchor;

/**
 * Темпоральное состояние контекста объекта.
 * Определяет, ГДЕ (проект) и ПОД ЧЬИМ УПРАВЛЕНИЕМ (owner/group) находится объект.
 *
 * Это самостоятельный объект, живет только в KService-Context (в локальной таблице),
 * не является потомком от KObject и KObjectAttributes. 
 * 
 * Описывает административное состояние объекта (для разграничения прав): 
 * в каком проекте находится, какой отдел и пользователь за него отвечают.
 *
 * Аннотация @DiscriminatorColumn и поле type в данном случае не нужна
 * ! Это «конечный» класс. У него нет и не планируется наследников. !
 * 
 * Темпоральное состояние объекта системы.
 * Корневой контейнер для всех атрибутов контекста.
 * Инварианты
 * Для одного targetObjectId:
 * ровно одна запись с valid_to IS NULL
 * 0..N исторических записей (valid_to NOT NULL)
 * История не редактируется, только закрывается:
 * old.valid_to = now()
 * new.valid_from = now()
 * KObject никогда не хранит ссылку на состояние.
 * TODO: в базе обязательно должен быть индекс:
 * CREATE UNIQUE INDEX ux_ctx_state_active
 * ON kcontext_state (target_object_id)
 * WHERE valid_to IS NULL;
 */

@Entity
@Table(
    name = "kcontext",
    indexes = {
        @Index(name = "idx_kcontext_state_target", columnList = "target_object_id"),
        @Index(name = "idx_kcontext_state_active", columnList = "target_object_id, valid_to")
    }
    //TODO: индексы kcontext(id, valid_to) kcontext(target_object_id)
)
@Getter @Setter
public class KContext extends KAnchor{ // <-- не делаем класс абстрактным т.к. у класса нет нет и не планируется наследников

    /**
     * UUID доменного объекта (KObject.id), с которым связан контекст
     * FK намеренно отсутствует — разные сервисы / БД
     * FIXME: FK нужен т.к. этот сервис является реестром ВСЕХ объектов системы
     * FIXME: поэтому каждый контекст должен быть связан со своим объекто
     * FIXME: у каждого объекта KObject должен быть контекст
     */
    @Column(name = "target_object_id", nullable = false)
    private UUID targetObjectId;

    // Идентификатор компании владельца объекта, для разграничения прав (RLS)
    @Column(name = "company_id", nullable = false, updatable = false)
    private UUID companyId; // для RLS 

    // Идентификатор пользователя (кто создал запись)
    @Column(name = "created_by", nullable = false, updatable = false)
    private UUID createdBy;

    // защищаем конструктор
    protected KContext() {}

    /**
     * Фабричный метод создания контекста.
     * ID состояния генерируется автоматически.
     */
    public static KContext create(UUID targetObjectId, UUID companyId, UUID createdBy, Instant now) {

        if (targetObjectId == null || companyId == null || createdBy == null) {
            throw new IllegalArgumentException("targetObjectId, companyId, createdBy are required");
        }
        // Для KContext ID генерируется локально, так как KContext живет только в этом сервисе
        KContext ctx = new KContext();
        ctx.setId(UUID.randomUUID()); // FIX: сосздаем UUID здесь
        ctx.setTargetObjectId(targetObjectId);
        ctx.setCompanyId(companyId);
        ctx.setCreatedBy(createdBy);
        ctx.setValidFrom(now);
        ctx.setValidTo(null);
        return ctx;
    }
}
