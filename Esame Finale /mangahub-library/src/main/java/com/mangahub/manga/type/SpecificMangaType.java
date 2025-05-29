package com.mangahub.manga.type;

/**
 * Interfaccia sigillata per i tipi di manga.
 */
public sealed interface SpecificMangaType permits ShonenType, ShojoType, WebtoonType {}
