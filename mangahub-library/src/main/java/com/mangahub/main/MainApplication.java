package com.mangahub.main;

import java.io.Console;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import com.mangahub.manga.model.MangaSeries;
import com.mangahub.manga.service.MangaService;
import com.mangahub.user.exception.UserNotFoundException;
import com.mangahub.user.model.UserRecord;
import com.mangahub.user.model.UserRole;
import com.mangahub.user.service.AuthService;

/**
 * Classe principale per l'applicazione MangaHub.
 */
public class MainApplication {
    private static final AuthService authService = new AuthService();
    private static final MangaService mangaService = new MangaService();
    private static final Scanner scanner = new Scanner(System.in);
    private static UserRecord currentUser;

    public static void main(String[] args) {
        System.out.println("=== Benvenuto in MangaHub Library ===");
        System.out.println("🔐 Sistema di autenticazione sicuro attivo");
        
        if (login()) {
            displayMenu();
        } else {
            System.out.println("Login fallito. Arrivederci!");
        }
        
        scanner.close();
    }

    private static boolean login() {
        int attempts = 0;
        final int maxAttempts = 3;
        
        while (attempts < maxAttempts) {
            System.out.println("\n--- Login Sicuro ---");
            
            // Input username normale
            System.out.print("Username: ");
            String username = scanner.nextLine();
            
            // Input password con asterischi
            String password = readPasswordWithAsterisks();
            
            try {
                currentUser = authService.login(username, password);
                System.out.println("✅ Login effettuato con successo! Benvenuto, " + currentUser.getUsername());
                return true;
                
            } catch (UserNotFoundException e) {
                attempts++;
                System.out.println("❌ Errore di login: " + e.getMessage());
                
                if (attempts < maxAttempts) {
                    System.out.println("Tentativi rimasti: " + (maxAttempts - attempts));
                } else {
                    System.out.println("Troppi tentativi falliti.");
                }
            } catch (Exception e) {
                System.out.println("❌ Errore imprevisto: " + e.getMessage());
                attempts++;
            }
        }
        
        return false;
    }

    /**
     * Legge la password mostrando asterischi durante la digitazione
     */
    private static String readPasswordWithAsterisks() {
        Console console = System.console();
        
        if (console != null) {
            // Nel terminale: usa Console per nascondere completamente
            char[] passwordChars = console.readPassword("Password: ");
            return new String(passwordChars);
        } else {
            // Nell'IDE: mostra asterischi personalizzati
            System.out.print("Password: ");
            return readPasswordWithCustomMask();
        }
    }

    /**
     * Implementazione personalizzata per mostrare asterischi
     */
    private static String readPasswordWithCustomMask() {
        StringBuilder password = new StringBuilder();
        
        try {
            // Configura il terminale per input carattere per carattere
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                return readPasswordWindows();
            } else {
                return readPasswordUnix();
            }
        } catch (Exception e) {
            // Fallback: input normale visibile
            System.out.println("\n⚠️ Modalità sicura non disponibile - password visibile");
            System.out.print("Password: ");
            return scanner.nextLine();
        }
    }

    /**
     * Lettura password per sistemi Unix/Linux/Mac
     */
    private static String readPasswordUnix() {
        StringBuilder password = new StringBuilder();
        
        try {
            // Disabilita echo del terminale
            String[] cmd = {"/bin/sh", "-c", "stty -echo < /dev/tty"};
            Runtime.getRuntime().exec(cmd).waitFor();
            
            // Leggi carattere per carattere
            int ch;
            while ((ch = System.in.read()) != '\n' && ch != '\r' && ch != -1) {
                if (ch == 127 || ch == 8) { // Backspace
                    if (password.length() > 0) {
                        password.deleteCharAt(password.length() - 1);
                        System.out.print("\b \b");
                    }
                } else if (ch >= 32 && ch <= 126) { // Caratteri stampabili
                    password.append((char) ch);
                    System.out.print("*");
                }
            }
            
            // Riabilita echo del terminale
            String[] cmdRestore = {"/bin/sh", "-c", "stty echo < /dev/tty"};
            Runtime.getRuntime().exec(cmdRestore).waitFor();
            
            System.out.println(); // Nuova riga
            
        } catch (Exception e) {
            // Fallback
            return readPasswordFallback();
        }
        
        return password.toString();
    }

    /**
     * Lettura password per sistemi Windows
     */
    private static String readPasswordWindows() {
        StringBuilder password = new StringBuilder();
        
        try {
            while (true) {
                int ch = System.in.read();
                
                if (ch == '\r' || ch == '\n') {
                    System.out.println();
                    break;
                } else if (ch == 8) { // Backspace
                    if (password.length() > 0) {
                        password.deleteCharAt(password.length() - 1);
                        System.out.print("\b \b");
                    }
                } else if (ch >= 32 && ch <= 126) {
                    password.append((char) ch);
                    System.out.print("*");
                }
            }
        } catch (IOException e) {
            return readPasswordFallback();
        }
        
        return password.toString();
    }

    /**
     * Fallback per quando non è possibile nascondere l'input
     */
    private static String readPasswordFallback() {
        System.out.println("\n⚠️ Input sicuro non disponibile - password visibile");
        System.out.print("Password: ");
        return scanner.nextLine();
    }

    private static void displayMenu() {
        while (true) {
            System.out.println("\n=== MENU PRINCIPALE ===");
            System.out.println("Utente connesso: " + currentUser.getUsername() + " (" + currentUser.getRole().getRole() + ")");
            System.out.println("1. Visualizza tutti i manga");
            System.out.println("2. Cerca manga per categoria");
            System.out.println("3. Cerca manga per titolo");
            System.out.println("4. Cerca manga per autore");
            System.out.println("5. Visualizza categorie disponibili");
            
            if (currentUser.getRole().equals(UserRole.ADMIN)) {
                System.out.println("6. [ADMIN] Aggiungi un manga");
                System.out.println("7. [ADMIN] Rimuovi un manga");
            }
            
            System.out.println("8. Logout");
            System.out.println("9. Esci");
            System.out.print("Scegli un'opzione: ");
            
            try {
                int choice = Integer.parseInt(scanner.nextLine());

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
                        if (currentUser.getRole().equals(UserRole.ADMIN)) {
                            addManga();
                        } else {
                            System.out.println("❌ Accesso negato. Solo gli amministratori possono aggiungere manga.");
                        }
                        break;
                    case 7:
                        if (currentUser.getRole().equals(UserRole.ADMIN)) {
                            removeManga();
                        } else {
                            System.out.println("❌ Accesso negato. Solo gli amministratori possono rimuovere manga.");
                        }
                        break;
                    case 8:
                        System.out.println("Logout effettuato. Arrivederci!");
                        if (login()) {
                            continue;
                        } else {
                            return;
                        }
                    case 9:
                        System.out.println("Arrivederci!");
                        return;
                    default:
                        System.out.println("❌ Opzione non valida. Scegli un numero da 1 a 9.");
                }
            } catch (NumberFormatException e) {
                System.out.println("❌ Inserisci un numero valido.");
            }
        }
    }

    private static void displayAllManga() {
        System.out.println("\n=== TUTTI I MANGA ===");
        List<MangaSeries> mangaList = mangaService.getAllManga();
        displayMangaList(mangaList);
    }

    private static void searchByGenre() {
        System.out.println("\nCategorie disponibili:");
        displayAllGenres();
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
        System.out.println("✅ Manga aggiunto con successo!");
    }

    private static void removeManga() {
        System.out.println("\n=== RIMUOVI MANGA ===");
        displayAllManga();
        System.out.print("Inserisci il titolo esatto del manga da rimuovere: ");
        String title = scanner.nextLine();
        
        List<MangaSeries> results = mangaService.searchByTitle(title);
        if (!results.isEmpty()) {
            mangaService.removeSeries(title);
            System.out.println("✅ Manga rimosso con successo!");
        } else {
            System.out.println("❌ Manga non trovato con il titolo: " + title);
        }
    }
}
