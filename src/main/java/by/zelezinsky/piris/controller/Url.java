package by.zelezinsky.piris.controller;

interface Url {

    String ID = "/{id}";

    interface City {
        String PATH = "/cities";
    }

    interface Client {
        String PATH = "/clients";
    }
}
