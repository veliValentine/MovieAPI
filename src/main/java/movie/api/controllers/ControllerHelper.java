package movie.api.controllers;

public class ControllerHelper {
    public static final String BASE_URI_V1 = "/api/v1";

    public static boolean equalIds(long id1, long id2) {
        return id1 == id2;
    }

    public static boolean notEqualIds(long id1, long id2) {
        return !equalIds(id1, id2);
    }
}