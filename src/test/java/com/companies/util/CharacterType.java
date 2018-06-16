package com.companies.util;

public enum CharacterType {
    EMPTY(""),
    LOWER_CASE("abcdefghijklmnopqrstuvwxyz"),
    UPPER_CASE("ABCDEFGHIJKLMNOPQRSTUVWXYZ"),
    NUMERIC("0123456789"),
    SPECIAL("!\"#$%&\'()*+,-./:;<=>?@[]^_`{|}~");

    private final String characters;

    CharacterType(final String characters) {
        this.characters = characters;
    }

    public String getCharacters() {
        return characters;
    }
}