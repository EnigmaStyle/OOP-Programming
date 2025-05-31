package com.mangahub.manga.model;

/**
 * Classe che rappresenta un capitolo di manga.
 */
public final class MangaChapter implements MangaItem {
    private final String title;

    /**
     * Costruttore per MangaChapter.
     *
     * @param title il titolo del capitolo
     */
    public MangaChapter(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
