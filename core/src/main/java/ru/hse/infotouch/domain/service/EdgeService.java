package ru.hse.infotouch.domain.service;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.stereotype.Service;
import ru.hse.infotouch.domain.dto.request.EdgeDTO;
import ru.hse.infotouch.domain.models.map.Edge;
import ru.hse.infotouch.domain.models.map.QEdge;
import ru.hse.infotouch.domain.repo.EdgeRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EdgeService {
    private final EdgeRepository edgeRepository;
    private final QEdge qEdge = QEdge.edge;

    public EdgeService(EdgeRepository edgeRepository) {
        this.edgeRepository = edgeRepository;
    }

    public List<Edge> findAll(int[] pointIds) {
        Iterable<Edge> edges = edgeRepository.findAll(qEdge.leftPointId.in(ArrayUtils.toObject(pointIds)));

        return StreamSupport
                .stream(edges.spliterator(), false)
                .collect(Collectors.toList());
    }

    public List<Edge> create(List<EdgeDTO> dtos) {
        List<Edge> toSave = dtos.stream()
                .map(Edge::createFromDTO)
                .collect(Collectors.toList());

        return edgeRepository.saveAll(toSave);
    }
}
