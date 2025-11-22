package com.developia.ecommerce.exception;

public final class ExceptionMessages {

    public static final String RESOURCE_NOT_FOUND = "%s tapilmadi. Axtarilan %s: '%s'";
    public static final String DUPLICATE_RESOURCE = "Bu %s evvelceden var. Daxil edilen %s: '%s'";
    public static final String INSUFFICIENT_STOCK = "%s ucun kifayet qeder stok yoxdur. Teleb edilen say: %d, qalib: %d";
    public static final String INVALID_CREDENTIALS = "Istifadeci adi ve ya sifre yanlisdir. Dogru daxil edin.";

    private ExceptionMessages() {
    }
}