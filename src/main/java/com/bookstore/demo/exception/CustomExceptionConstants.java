package com.bookstore.demo.exception;

public class CustomExceptionConstants {

    public static final int USER_ALREADY_EXISTS_ERROR_CODE = 100;
    public static final String USER_ALREADY_EXISTS_ERROR_MSG = "User already exists.";

    public static final int USER_NOT_FOUND_ERROR_CODE = 101;
    public static final String USER_NOT_FOUND_ERROR_MSG = "User is not found.";

    public static final int BOOK_ALREADY_EXISTS_ERROR_CODE = 102;
    public static final String BOOK_ALREADY_EXISTS_ERROR_MSG = "Book already exists.";

    public static final int BOOK_NOT_FOUND_ERROR_CODE = 103;
    public static final String BOOK_NOT_FOUND_ERROR_MSG = "Book is not found.";

    public static final int BOOK_OUT_OF_STOCK_ERROR_CODE = 104;
    public static final String BOOK_OUT_OF_STOCK_ERROR_MSG = "Book is out of stock.";

    public static final int ORDER_NOT_FOUND_ERROR_CODE = 105;
    public static final String ORDER_NOT_FOUND_ERROR_MSG = "Order is not found.";

    public static final int STATISTICS_NOT_FOUND_ERROR_CODE = 106;
    public static final String STATISTICS_NOT_FOUND_ERROR_MSG = "Statistics data is not found.";

    public static final int INVALID_DATE_FORMAT_ERROR_CODE = 107;
    public static final String INVALID_DATE_FORMAT_ERROR_MSG = "Date format must be yyyy-MM-dd.";

    public static final int METHOD_ARGUMENT_ERROR_CODE = 108;

    public static final int ACCESS_DENIED_ERROR_CODE = 109;
    public static final String ACCESS_DENIED_ERROR_MSG = "You are not authorized to make this request.";

    public static final int GENERAL_EXCEPTION_ERROR_CODE = 500;
    public static final String GENERAL_EXCEPTION_ERROR_MSG = "There is a system error. Please try again later.";

}
