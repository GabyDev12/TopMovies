package project.topmovies.logic;

public class statusApp {

    // LOGGED IN DATA

    public boolean loggedIn = false;
    public boolean gAuth = false;


    // CURRENT LANGUAGE

    public String language = "en";


    // Check if it comes from MyFilms menu option
    public boolean myFilms = false;


    // Check if it comes from Settings menu option
    public boolean settings = false;


    // Check if it comes from About menu option
    public boolean about = false;


    // Singleton configuration

    // Private constructor so nobody can instantiate the class
    private statusApp() {}

    // Static to class instance of the class.
    private static final statusApp INSTANCE = new statusApp();

    // To be called by user to obtain instance of the class.
    // @return instance of the statusApp.
    public static statusApp getInstance() {

        return INSTANCE;

    }

}
