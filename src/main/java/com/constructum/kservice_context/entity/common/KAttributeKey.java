package com.constructum.kservice_context.entity.common;

import lombok.*;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;




/**
 * Составной первичный ключ, используемый в атрибутных таблицах (id объекта + validFrom).
 * Состоит из:
 *   • id – ссылка на запись в таблице kobject (UUID)
 *   • validFrom – момент, с которого атрибут считается действительным
 *
 * Такой ключ позволяет хранить «исторические» версии атрибутов:
 * одна и та же запись в атрибутной таблице может иметь несколько строк
 * с разными значениями validFrom.
 */

@Embeddable
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @EqualsAndHashCode @Builder
public class KAttributeKey implements Serializable {

    /** Ссылка на запись в таблице kobject */
    @Column(name = "id", nullable = false)
    private UUID id;

    /** Дата‑время, с которого данная версия атрибута считается действительной */
    @Column(name = "valid_from", nullable = false)
    private Instant validFrom;
}
