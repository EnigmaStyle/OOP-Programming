package com.mangahub.manga.factory;

import com.mangahub.manga.model.MangaSeries;

/**
 * Interfaccia per la fabbrica di serie manga.
 */
public interface MangaSeriesFactory {
    MangaSeries createSeries(String title);
}
