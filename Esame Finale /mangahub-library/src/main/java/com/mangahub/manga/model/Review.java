package com.mangahub.manga.model;

/**
 * Classe che rappresenta una recensione per un manga.
 */
public class Review {
    private final String username;
    private final int rating; // da 1 a 5
    private final String comment;

    /**
     * Costruttore per Review.
     *
     * @param username l'utente che ha scritto la recensione
     * @param rating la valutazione (1-5)
     * @param comment il commento della recensione
     */
    public Review(String username, int rating, String comment) {
        this.username = username;
        this.rating = rating;
        this.comment = comment;
    }

    public String getUsername() {
        return username;
    }

    public int getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
