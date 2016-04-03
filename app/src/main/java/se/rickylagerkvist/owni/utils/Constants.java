package se.rickylagerkvist.owni.utils;

/**
 * Created by Ricky on 2016-04-03.
 * Here there will be constans relating to Firebase
 */
public class Constants {

    // Constants related to locations in Firebase
    public static final String FIREBASE_LOCATION_USERS = "users";

    // Constants for Firebase URL
    public static final String FIREBASE_URL = "https://owniapp.firebaseio.com/";
    public static final String FIREBASE_URL_USERS = FIREBASE_URL + "/" + FIREBASE_LOCATION_USERS;

    // Constants for Firebase login
    public static final String PASSWORD_PROVIDER = "password";
    public static final String KEY_PROVIDER = "PROVIDER";
    public static final String KEY_ENCODED_EMAIL = "ENCODED_EMAIL";

    // Constants for Firebase object properties
    public static final String FIREBASE_PROPERTY_EMAIL = "email";
    public static final String FIREBASE_PROPERTY_TIMESTAMP = "timestamp";
}