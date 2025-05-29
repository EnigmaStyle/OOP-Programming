package com.mangahub.main;

import java.util.List;
import java.util.Scanner;

import com.mangahub.manga.model.MangaSeries;
import com.mangahub.manga.service.MangaService;
import com.mangahub.user.model.UserRecord;
import com.mangahub.user.model.UserRole;
import com.mangahub.user.service.UserService;

/**
 * Classe principale per l'applicazione MangaHub.
 */
public class MainApplication {
    private static final UserService userService = new UserService();
    private static final MangaService mangaService = new MangaService();
    private static final Scanner scanner = new Scanner(System.in);
    private static UserRecord currentUser ;

    public static void main(String[] args) {
        System.out.println("=== Benvenuto in MangaHub Library ===");
        login();
        showMenu();
        scanner.close(); // Chiudi lo scanner alla fine dell'applicazione
    }

    private static void login() {
        System.out.println("\n--- Login ---");
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        try {
            currentUser  = userService.login(username, password);
            System.out.println("Login effettuato con successo! Benvenuto, " + currentUser .getUsername());
            System.out.println("Ruolo: " + currentUser .getRole());
        } catch (Exception e) {
            System.out.println("Errore imprevisto: " + e.getMessage());
            System.exit(1);
        }
    }

    private static void showMenu() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPALE ===");
            System.out.println("1. Visualizza tutti i manga");
            System.out.println("2. Cerca manga per categoria");
            System.out.println("3. Cerca manga per titolo");
            System.out.println("4. Cerca manga per autore");
            System.out.println("5. Visualizza categorie disponibili");
            
            if (currentUser .getRole() == UserRole.ADMIN) {
                System.out.println("6. [ADMIN] Aggiungi un manga");
                System.out.println("7. [ADMIN] Rimuovi un manga");
            }
            
            System.out.println("8. Esci");
            System.out.print("Scegli un'opzione: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consuma il newline

            switch (choice) {
                case 1:
                    displayAllManga();
                    break;
                case 2:
                    searchByGenre();
                    break;
                case 3:
                    searchByTitle();
                    break;
                case 4:
                    searchByAuthor();
                    break;
                case 5:
                    displayAllGenres();
                    break;
                case 6:
                    if (currentUser .getRole() == UserRole.ADMIN) {
                        addManga();
                    } else {
                        System.out.println("Accesso negato. Solo gli amministratori possono aggiungere manga.");
                    }
                    break;
                case 7:
                    if (currentUser .getRole() == UserRole.ADMIN) {
                        removeManga();
                    } else {
                        System.out.println("Accesso negato. Solo gli amministratori possono rimuovere manga.");
                    }
                    break;
                case 8:
                    System.out.println("Arrivederci!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opzione non valida.");
            }
        }
    }

    private static void displayAllManga() {
        System.out.println("\n=== TUTTI I MANGA ===");
        List<MangaSeries> mangaList = mangaService.getAllManga();
        displayMangaList(mangaList);
    }

    private static void searchByGenre() {
        System.out.print("Inserisci la categoria da cercare: ");
        String genre = scanner.nextLine();
        List<MangaSeries> results = mangaService.getMangaByGenre(genre);
        
        System.out.println("\n=== RISULTATI PER CATEGORIA: " + genre.toUpperCase() + " ===");
        displayMangaList(results);
    }

    private static void searchByTitle() {
        System.out.print("Inserisci il titolo da cercare: ");
        String title = scanner.nextLine();
        List<MangaSeries> results = mangaService.searchByTitle(title);
        
        System.out.println("\n=== RISULTATI PER TITOLO: " + title + " ===");
        displayMangaList(results);
    }

    private static void searchByAuthor() {
        System.out.print("Inserisci l'autore da cercare: ");
        String author = scanner.nextLine();
        List<MangaSeries> results = mangaService.searchByAuthor(author);
        
        System.out.println("\n=== RISULTATI PER AUTORE: " + author + " ===");
        displayMangaList(results);
    }

    private static void displayAllGenres() {
        System.out.println("\n=== CATEGORIE DISPONIBILI ===");
        List<String> genres = mangaService.getAllGenres();
        for (String genre : genres) {
            System.out.println("- " + genre);
        }
    }

    private static void displayMangaList(List<MangaSeries> mangaList) {
        if (mangaList.isEmpty()) {
            System.out.println("Nessun manga trovato.");
            return;
        }
        
        System.out.println("Trovati " + mangaList.size() + " manga:");
        System.out.println("----------------------------------------");
        for (int i = 0; i < mangaList.size(); i++) {
            MangaSeries series = mangaList.get(i);
            System.out.printf("%d. Titolo: %s | Autore: %s | Categoria: %s%n", 
                i + 1, series.getTitle(), series.getAuthor(), series.getGenre());
        }
        System.out.println("----------------------------------------");
    }

    private static void addManga() {
        System.out.println("\n=== AGGIUNGI NUOVO MANGA ===");
        System.out.print("Titolo: ");
        String title = scanner.nextLine();
        System.out.print("Autore: ");
        String author = scanner.nextLine();
        System.out.print("Categoria (Shonen/Shojo/Seinen/Josei/Webtoon/Kodomomuke): ");
        String genre = scanner.nextLine();
        
        MangaSeries newSeries = new MangaSeries(title, author, genre);
        mangaService.addSeries(newSeries);
        System.out.println("✓ Manga aggiunto con successo!");
    }

    private static void removeManga() {
        System.out.println("\n=== RIMUOVI MANGA ===");
        displayAllManga();
        System.out.print("Inserisci il titolo esatto del manga da rimuovere: ");
        String title = scanner.nextLine();
        
        // Controlla se il manga esiste prima di rimuoverlo
        List<MangaSeries> results = mangaService.searchByTitle(title);
        if (!results.isEmpty()) {
            mangaService.removeSeries(title);
            System.out.println("✓ Manga rimosso con successo!");
        } else {
            System.out.println("Manga non trovato con il titolo: " + title);
        }
    }
}
