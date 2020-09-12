package librarymanagement.app;

import librarymanagement.AuthorizationMenu.AuthorizationPage;
import librarymanagement.library.Library;

public class App {
  public static void main(final String[] args) {
    Library library = new Library();
    final AuthorizationPage authorizationPage = new AuthorizationPage();
  }
}
