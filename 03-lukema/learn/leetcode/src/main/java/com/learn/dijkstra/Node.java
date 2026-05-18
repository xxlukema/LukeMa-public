package com.learn.dijkstra;

import java.util.HashMap;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@AllArgsConstructor
public class Node {

  private String name;

  private String from;

  private Integer distance;

  @EqualsAndHashCode.Exclude
  private final Map<Node, Integer> adjacentNodeToEdgeLengthMap = new HashMap<>();

}
