package com.msproductos.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClaveValorRelacionId implements Serializable {

    @Column(name = "clave_id", columnDefinition = "RAW(16)")
    private UUID claveId;

    @Column(name = "valor_id", columnDefinition = "RAW(16)")
    private UUID valorId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ClaveValorRelacionId that)) return false;
        return Objects.equals(claveId, that.claveId) &&
                Objects.equals(valorId, that.valorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(claveId, valorId);
    }
}
