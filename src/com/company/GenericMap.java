package com.company;

import java.util.Map;

public class GenericMap<K, V> {

    public GenericMap() {
    }

    public K findKeyForValue(Map<K, V> map, V value) {
        return map.entrySet().stream()
                .filter(entry -> value.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .findFirst().get();
    }
}
