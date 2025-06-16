package com.msproductos.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.msproductos.util.FlexibleUUIDDeserializer;
import lombok.Data;

import java.util.UUID;

@Data
public class ClaveValorRelacionDTO {
    @JsonDeserialize(using = FlexibleUUIDDeserializer.class)
    private UUID claveId;

    @JsonDeserialize(using = FlexibleUUIDDeserializer.class)
    private UUID valorId;
}
