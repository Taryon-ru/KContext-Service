package com.constructum.kservice_context.entity.Publication;

import com.constructum.kservice_context.entity.common.KAttribute;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @SuperBuilder
public abstract class KPublicationAttributes extends KAttribute {

    /** Ссылка на «якорный» объект, поле "id" в KAttributeKey */
    @MapsId("id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id", nullable = false)
    protected KPublication kObjectPublication;

}
