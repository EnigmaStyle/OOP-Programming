package com.mangahub.manga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta un volume di manga.
 */
public final class MangaVolume implements MangaItem {
    private final String title;
    private final List<MangaChapter> chapters = new ArrayList<>();

    /**
     * Costruttore per MangaVolume.
     *
     * @param title il titolo del volume
     */
    public MangaVolume(String title) {
        this.title = title;
    }

    /**
     * Aggiunge un capitolo al volume.
     *
     * @param chapter il capitolo da aggiungere
     */
    public void addChapter(MangaChapter chapter) {
        chapters.add(chapter);
    }

    public String getTitle() {
        return title;
    }

    public List<MangaChapter> getChapters() {
        return chapters;
    }
}
