package com.mangahub.manga.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che rappresenta una serie di manga.
 */
public final class MangaSeries implements MangaItem {
    private final String title;
    private final String author;
    private final String genre;
    private final List<MangaVolume> volumes = new ArrayList<>();
    private final List<Review> reviews = new ArrayList<>();
    private double averageRating = 0.0;

    /**
     * Costruttore per MangaSeries.
     *
     * @param title il titolo della serie
     * @param author l'autore della serie
     * @param genre il genere della serie
     */
    public MangaSeries(String title, String author, String genre) {
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    /**
     * Aggiunge un volume alla serie.
     *
     * @param volume il volume da aggiungere
     */
    public void addVolume(MangaVolume volume) {
        volumes.add(volume);
    }

    /**
     * Rimuove un volume dalla serie.
     *
     * @param volume il volume da rimuovere
     */
    public void removeVolume(MangaVolume volume) {
        volumes.remove(volume);
    }

    /**
     * Aggiunge una recensione alla serie.
     *
     * @param review la recensione da aggiungere
     */
    public void addReview(Review review) {
        reviews.add(review);
        updateAverageRating();
    }

    private void updateAverageRating() {
        averageRating = reviews.stream()
            .mapToInt(Review::getRating)
            .average()
            .orElse(0.0);
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public List<MangaVolume> getVolumes() {
        return volumes;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public double getAverageRating() {
        return averageRating;
    }
}
