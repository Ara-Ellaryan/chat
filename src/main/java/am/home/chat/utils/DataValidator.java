package am.home.chat.utils;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.IntegerValidator;

import java.util.Collection;

public class DataValidator {
    private DataValidator(){
        throw new SecurityException("Can`t instantiate this class.");
    }

    public static boolean isNullOrBlank(String value){
        return value == null || value.trim().isEmpty();
    }

    public static boolean isEmpty(Collection<?> collection){
        return collection == null || collection.isEmpty();
    }

    public static boolean isValidEmail(String emial){
        return EmailValidator.getInstance().isValid(emial);
    }

    public static boolean isNumber(String value){
        return IntegerValidator.getInstance().isValid(value);
    }
}
