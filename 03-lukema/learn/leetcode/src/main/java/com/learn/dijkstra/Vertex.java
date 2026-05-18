package com.learn.dijkstra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Vertex {
    private String name;
    private int distance;
    private String from;
}
