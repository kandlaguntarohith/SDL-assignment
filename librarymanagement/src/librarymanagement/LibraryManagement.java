/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package librarymanagement;

import librarymanagement.AuthorizationMenu.AuthorizationPage;
import librarymanagement.library.Library;

/**
 *
 * @author rohit
 */
public class LibraryManagement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Library library = new Library();
        final AuthorizationPage authorizationPage = new AuthorizationPage();
    }
}
