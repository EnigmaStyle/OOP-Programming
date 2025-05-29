package com.mangahub.common.exception;

/**
 * Eccezione base per la libreria MangaHub.
 */
public class MangaLibraryException extends RuntimeException {
    public MangaLibraryException(String message) {
        super(message);
    }
}