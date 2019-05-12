package ru.hse.infotouch.domain.dto.request;

import java.util.List;

public class CreateEdgesRequest {
    private List<EdgeDTO> edges;

    public List<EdgeDTO> getEdges() {
        return edges;
    }

    public void setEdges(List<EdgeDTO> edges) {
        this.edges = edges;
    }
}
