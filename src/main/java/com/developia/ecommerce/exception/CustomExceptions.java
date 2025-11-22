package com.developia.ecommerce.exception;

public final class CustomExceptions {

    public static class ResourceNotFoundException extends RuntimeException {
        public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
            super(String.format(ExceptionMessages.RESOURCE_NOT_FOUND, resourceName, fieldName, fieldValue));
        }
    }

    public static class DuplicateResourceException extends RuntimeException {
        public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
            super(String.format(ExceptionMessages.DUPLICATE_RESOURCE, resourceName, fieldName, fieldValue));
        }
    }

    public static class InsufficientStockException extends RuntimeException {
        public InsufficientStockException(String productName, Integer requestedQuantity, Integer availableStock) {
            super(String.format(
                ExceptionMessages.INSUFFICIENT_STOCK,
                productName, requestedQuantity, availableStock
            ));
        }
    }

    public static class InvalidCredentialsException extends RuntimeException {
        public InvalidCredentialsException() {
            super(ExceptionMessages.INVALID_CREDENTIALS);
        }

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    private CustomExceptions() {
    }
}