package ru.voting;


import ru.voting.model.Restaurant;

public class AuthorizedUser {

    private static int id;

    public static int id() {
        return id;
    }

    public static void setId(int id) {
        AuthorizedUser.id = id;
    }

    public static Restaurant getChoice() {
        return new Restaurant(null, "MY choice");
    }
}