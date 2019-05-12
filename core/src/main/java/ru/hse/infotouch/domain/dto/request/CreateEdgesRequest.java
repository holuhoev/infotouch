package ru.hse.infotouch.domain.dto.request;

public class CreateEdgesRequest {
    private EdgeDTO[] edges;

    public EdgeDTO[] getEdges() {
        return edges;
    }

    public void setEdges(EdgeDTO[] edges) {
        this.edges = edges;
    }
}
