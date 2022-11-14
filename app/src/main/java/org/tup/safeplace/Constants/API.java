package org.tup.safeplace.Constants;

public class API {
    //API URL
    public static String URL = "http://192.168.29.27:8080/";
    public static String API = URL + "api/";

    //Hospital List
    public static String hospital_list_api = API + "hospitals/";

    //Police Station List
    public static String police_stations_list_api = API + "police_stations/";

    //Barangay List
    public static String barangay_list_api = API + "barangays/";

    //User Authentication LOGIN
    public static String login_user = API + "login/";

    //User Authentication REGISTER
    public static String register_user = API + "register/";

    //User Authentication LOGOUT
    public static String logout_user = API + "logout/";

    //User Authentication Register User Info
    public static String save_user_info = API + "save_user_info/";

}
