package me.waynee95.rift.error;

public class NotImplementedException extends RuntimeException {
    @Override
    public String getMessage() {
        return "Not implemented!";
    }
}
