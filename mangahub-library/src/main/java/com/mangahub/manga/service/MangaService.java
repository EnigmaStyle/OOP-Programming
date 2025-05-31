package com.mangahub.manga.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.mangahub.manga.model.MangaSeries;

/**
 * Servizio per gestire il catalogo dei manga.
 */
public class MangaService {
    private final List<MangaSeries> mangaSeriesList = new ArrayList<>();

    public MangaService() {
        // Aggiungi manga di esempio organizzati per categoria
        initializeMangaCatalog();
    }

    private void initializeMangaCatalog() {
        // SHONEN - Manga per ragazzi giovani
        addSeries(new MangaSeries("Naruto", "Masashi Kishimoto", "Shonen"));
        addSeries(new MangaSeries("One Piece", "Eiichiro Oda", "Shonen"));
        addSeries(new MangaSeries("Dragon Ball", "Akira Toriyama", "Shonen"));
        addSeries(new MangaSeries("My Hero Academia", "Kohei Horikoshi", "Shonen"));
        addSeries(new MangaSeries("Demon Slayer", "Koyoharu Gotouge", "Shonen"));

        // SHOJO - Manga per ragazze giovani
        addSeries(new MangaSeries("Sailor Moon", "Naoko Takeuchi", "Shojo"));
        addSeries(new MangaSeries("Fruits Basket", "Natsuki Takaya", "Shojo"));
        addSeries(new MangaSeries("Ouran High School Host Club", "Bisco Hatori", "Shojo"));

        // SEINEN - Manga per uomini adulti
        addSeries(new MangaSeries("Attack on Titan", "Hajime Isayama", "Seinen"));
        addSeries(new MangaSeries("Death Note", "Tsugumi Ohba", "Seinen"));
        addSeries(new MangaSeries("Tokyo Ghoul", "Sui Ishida", "Seinen"));
        addSeries(new MangaSeries("Berserk", "Kentaro Miura", "Seinen"));

        // JOSEI - Manga per donne adulte
        addSeries(new MangaSeries("Nana", "Ai Yazawa", "Josei"));
        addSeries(new MangaSeries("Paradise Kiss", "Ai Yazawa", "Josei"));

        // WEBTOON - Fumetti digitali coreani
        addSeries(new MangaSeries("Tower of God", "SIU", "Webtoon"));
        addSeries(new MangaSeries("Solo Leveling", "Chugong", "Webtoon"));
        addSeries(new MangaSeries("The God of High School", "Yongje Park", "Webtoon"));

        // KODOMOMUKE - Manga per bambini
        addSeries(new MangaSeries("Doraemon", "Fujiko F. Fujio", "Kodomomuke"));
        addSeries(new MangaSeries("Pokemon Adventures", "Hidenori Kusaka", "Kodomomuke"));
    }

    /**
     * Aggiunge una serie manga al catalogo.
     *
     * @param series la serie da aggiungere
     */
    public void addSeries(MangaSeries series) {
        mangaSeriesList.add(series);
    }

    /**
     * Rimuove una serie manga dal catalogo per titolo.
     *
     * @param title il titolo della serie da rimuovere
     */
    public void removeSeries(String title) {
        mangaSeriesList.removeIf(series -> series.getTitle().equalsIgnoreCase(title));
    }

    /**
     * Ottiene tutti i manga del catalogo.
     *
     * @return lista di tutte le serie manga
     */
    public List<MangaSeries> getAllManga() {
        return new ArrayList<>(mangaSeriesList);
    }

    /**
     * Cerca manga per categoria/genere.
     *
     * @param genre il genere da cercare
     * @return lista di manga del genere specificato
     */
    public List<MangaSeries> getMangaByGenre(String genre) {
        return mangaSeriesList.stream()
                .filter(series -> series.getGenre().equalsIgnoreCase(genre))
                .collect(Collectors.toList());
    }

    /**
     * Cerca manga per titolo.
     *
     * @param title il titolo da cercare
     * @return lista di manga che contengono il titolo specificato
     */
    public List<MangaSeries> searchByTitle(String title) {
        return mangaSeriesList.stream()
                .filter(series -> series.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Cerca manga per autore.
     *
     * @param author l'autore da cercare
     * @return lista di manga scritti dall'autore specificato
     */
    public List<MangaSeries> searchByAuthor(String author) {
        return mangaSeriesList.stream()
                .filter(series -> series.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    /**
     * Ottiene tutte le categorie disponibili nel catalogo.
     *
     * @return lista di generi distinti
     */
    public List<String> getAllGenres() {
        return mangaSeriesList.stream()
                .map(MangaSeries::getGenre)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
    }
}
