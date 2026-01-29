package dev.am.basic_demo.domain;

import dev.am.basic_demo.dto.AlertRequest;
import dev.am.basic_demo.dto.AlertResponse;
import io.hypersistence.tsid.TSID;
import org.springframework.stereotype.Component;

@Component
public class AlertMapper {
    public Alert toEntity(AlertRequest request) {
        return new Alert(TSID.Factory.getTsid().toString(), request.message());
    }

    public AlertResponse toDTOResponse(Alert alert) {
        return new AlertResponse(alert.getId(), alert.getMessage());
    }
}
