package ru.voting;


import ru.voting.model.Restaurant;

public class AuthorizedUser {

    public static int id() {
        return 1;
    }

    public static Restaurant getChoice() {
        return new Restaurant(null, "my choice");
    }
}