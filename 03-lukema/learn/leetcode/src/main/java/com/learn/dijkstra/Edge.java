package com.learn.dijkstra;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
// @AllArgsConstructor(staticName = "getInstance")
@AllArgsConstructor
public class Edge {
    private String[] vertices;
    private int length;
}
