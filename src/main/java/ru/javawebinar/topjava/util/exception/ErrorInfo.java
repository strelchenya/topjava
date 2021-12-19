package ru.javawebinar.topjava.util.exception;

public class ErrorInfo {
    private final String url;
    private final ErrorType type;
    private final String typeMessage;
    private final String detail;

    public ErrorInfo(CharSequence url, ErrorType type, String detail) {
        this.url = url.toString();
        this.type = type;
        this.typeMessage = getTypeMessage(type);
        this.detail = detail;
    }

    private String getTypeMessage(ErrorType type) {
        return switch (type) {
            case APP_ERROR -> "Application error";
            case DATA_NOT_FOUND -> "Data not found";
            case DATA_ERROR -> "Data error";
            case VALIDATION_ERROR -> "Validation error";
            default -> "Unknown error";
        };
    }
}