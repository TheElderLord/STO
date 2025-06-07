package com.example.kaspi.service.request;

import com.example.kaspi.dto.ChangeStatusDto;
import com.example.kaspi.dto.CreateRequestDto;
import com.example.kaspi.dto.RequestDto;
import com.example.kaspi.enums.RequestStatusEnum;
import com.example.kaspi.mapper.RequestMapper;
import com.example.kaspi.domain.RequestModel;
import com.example.kaspi.domain.StatusHistoryModel;
import com.example.kaspi.repository.RequestRepository;
import com.example.kaspi.repository.StatusHistoryRepository;
import com.example.kaspi.events.StatusChangedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepo;
    private final StatusHistoryRepository historyRepo;
    private final RequestMapper mapper;
    private final KafkaTemplate<String, StatusChangedEvent> kafka;

    @Override
    @Transactional
    public RequestDto create(CreateRequestDto dto) {
        RequestModel entity = mapper.toEntity(dto);
        RequestModel saved = requestRepo.save(entity);
        return mapper.toDto(saved);
    }

    @Override
    public List<RequestDto> getByClient(UUID clientId) {
        return requestRepo.findByClientId(clientId).stream()
                .map(mapper::toDto).toList();
    }

    @Override
    public List<RequestDto> getByStatus(RequestStatusEnum status) {
        return requestRepo.findByStatus(status).stream()
                .map(mapper::toDto).toList();
    }

    @Override
    @Transactional
    public RequestDto changeStatus(UUID requestId, ChangeStatusDto dto) {
        RequestModel req = requestRepo.findById(requestId)
                .orElseThrow(() -> new NoSuchElementException("Request not found"));

        // Валидация перехода
        if (!isValidTransition(req.getStatus(), dto.getNewStatus())) {
            throw new IllegalStateException("Invalid status transition");
        }

        // Запись истории
        StatusHistoryModel hist = StatusHistoryModel.builder()
                .request(req)
                .oldStatus(req.getStatus())
                .newStatus(dto.getNewStatus())
                .changedBy(dto.getChangedBy())
                .reason(dto.getReason())
                .build();
        historyRepo.save(hist);

        // Изменяем статус
        req.setStatus(dto.getNewStatus());

        // Публикуем событие
        kafka.send("status-changes", new StatusChangedEvent(req.getId(), dto.getNewStatus(), req.getClientId()));

        return mapper.toDto(req);
    }

    private boolean isValidTransition(RequestStatusEnum oldSt, RequestStatusEnum newSt) {
        return switch (oldSt) {
            case NEW -> newSt == RequestStatusEnum.PLANNING;
            case PLANNING -> newSt == RequestStatusEnum.PROGRESS;
            case PROGRESS -> newSt == RequestStatusEnum.DONE;
            default -> false;
        };
    }
}

