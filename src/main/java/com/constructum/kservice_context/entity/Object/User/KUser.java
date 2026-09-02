package com.constructum.kservice_context.entity.Object.User;

import java.time.Instant;
import java.util.UUID;

import com.constructum.kservice_context.entity.Object.KObject;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Объект типа "Пользователь".
 * Все данные (ФИО, Email, Логин) хранятся в соответствующих таблицах атрибутов.
 */
@Entity
@DiscriminatorValue("kuser")
public class KUser extends KObject {
    // Поля не добавляем, так как используем атрибутивную модель

    // Приватный конструктор, чтобы запретить создание через new KUser()
    // и принудить использовать фабричный метод create.
    // JPA/Hibernate требуют, чтобы у каждой @Entity был конструктор без аргументов
    private KUser() {}

    // Переопределяем метод.
    // Для объекта KUser - владелец по умолчанию - сам пользователь 
    @Override
    public UUID getDefaultOwner() { return getId(); }


    // --- Фабричный метод ---
    public static KUser create(UUID companyId, UUID createdBy, Instant now) {
        
        KUser user = new KUser();
        user.setId(UUID.randomUUID()); 
        user.setCompanyId(companyId);
        user.setValidFrom(now);
        user.setValidTo(null);
        user.setCreatedBy(createdBy);       
        // type устанавливается автоматически Hibernate через @DiscriminatorValue
        return user;
    }
}
