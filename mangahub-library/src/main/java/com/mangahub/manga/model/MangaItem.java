package com.mangahub.manga.model;

/**
 * Interfaccia sigillata per rappresentare un elemento del catalogo manga.
 */
public sealed interface MangaItem permits MangaSeries, MangaVolume, MangaChapter {}
