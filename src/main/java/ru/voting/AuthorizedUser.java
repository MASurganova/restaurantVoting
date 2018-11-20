package ru.voting;


import ru.voting.model.Restaurant;

import static ru.voting.model.AbstractBaseEntity.START_SEQ;

public class AuthorizedUser {

    private static int id = START_SEQ+7;

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