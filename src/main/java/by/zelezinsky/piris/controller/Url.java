package by.zelezinsky.piris.controller;

interface Url {

    String ID = "/{id}";

    interface City {
        String PATH = "/cities";
    }

    interface Client {
        String PATH = "/clients";
    }

    interface Deposit {
        String PATH = "/deposits";

        interface Operation {
            String CLOSE_DAY = "/closeDay";
        }
    }
}
