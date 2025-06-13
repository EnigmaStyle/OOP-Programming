package com.mangahub.manga.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mangahub.manga.model.MangaSeries;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Servizio per la gestione del catalogo manga.
 */
public class CatalogService {
    private final Set<MangaSeries> catalog = new HashSet<>();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public void addSeries(MangaSeries series) {
        catalog.add(series);
    }

    public void saveCatalog(String filepath) throws IOException {
        objectMapper.writeValue(new File(filepath), catalog);
    }

    public Set<MangaSeries> loadCatalog(String filepath) throws IOException {
        return Set.of(objectMapper.readValue(new File(filepath), MangaSeries[].class));
    }
}
