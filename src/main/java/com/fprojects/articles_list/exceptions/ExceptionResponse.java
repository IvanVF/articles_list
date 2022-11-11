package com.fprojects.articles_list.exceptions;

import lombok.Data;

import java.util.List;

/**
 * Formatted exception response
 */
@Data
public class ExceptionResponse {
    private int httpCode;

    private String message;

    private List<String> description;
}
