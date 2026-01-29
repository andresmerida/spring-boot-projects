package dev.am.basic_demo.domain;

import dev.am.basic_demo.dto.AlertRequest;
import dev.am.basic_demo.dto.AlertResponse;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class AlertServiceImpl implements AlertService {
    private final AlertDAO alertDAO;
    private final AlertMapper alertMapper;

    AlertServiceImpl(AlertDAO alertDAO, AlertMapper alertMapper) {
        this.alertDAO = alertDAO;
        this.alertMapper = alertMapper;
    }

    @Override
    public Optional<AlertResponse> getById(String id) {
        return alertDAO.getById(id)
                .map(alertMapper::toDTOResponse);
    }

    @Override
    public List<AlertResponse> getAll() {
        return alertDAO.getAll().stream()
                .map(alertMapper::toDTOResponse)
                .toList();
    }

    @Override
    public AlertResponse save(AlertRequest request) {
        return alertMapper.toDTOResponse(
                alertDAO.save(alertMapper.toEntity(request))
        );
    }

    @Override
    public AlertResponse update(AlertRequest request, String id) {
        Alert alert = alertDAO.getById(id).orElseThrow();
        alert.setMessage(request.message());
        alertDAO.update(alert);
        return alertMapper.toDTOResponse(alert);
    }

    @Override
    public void deleteById(String id) {
        alertDAO.deleteById(id);
    }
}
