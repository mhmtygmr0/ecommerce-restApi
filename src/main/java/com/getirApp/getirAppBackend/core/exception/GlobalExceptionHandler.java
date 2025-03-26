package com.getirApp.getirAppBackend.core.exception;

import com.getirApp.getirAppBackend.core.utils.Result;
import com.getirApp.getirAppBackend.core.utils.ResultData;
import com.getirApp.getirAppBackend.core.utils.ResultHelper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Uygulama genelinde oluşabilecek istisnaları yakalayan global hata yönetim sınıfı.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 404 NOT FOUND hatalarını yakalar.
     * Genellikle aranan veri bulunamadığında fırlatılan `NotFoundException` istisnasını ele alır.
     * Örneğin: Belirtilen ID'ye sahip bir kullanıcı veya ürün bulunamazsa bu hata döner.
     *
     * @param ex Yakalanan NotFoundException
     * @return 404 NOT FOUND HTTP yanıtı ve hata mesajı
     */
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Result> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ResultHelper.notFoundError(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    /**
     * 400 BAD REQUEST hatalarını yakalar.
     * Geçersiz veya eksik parametrelerle yapılan isteklerde fırlatılan `MethodArgumentNotValidException` istisnasını ele alır.
     * Örneğin: Bir form doldurulurken zorunlu alanlar eksikse veya yanlış formatta bir e-posta girilmişse bu hata döner.
     *
     * @param ex Yakalanan MethodArgumentNotValidException
     * @return 400 BAD REQUEST HTTP yanıtı ve doğrulama hatalarının listesi
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResultData<List<String>>> handleValidationException(MethodArgumentNotValidException ex) {
        List<String> validationErrorList = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .toList();
        return new ResponseEntity<>(ResultHelper.validateError(validationErrorList), HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 BAD REQUEST hatalarını yakalar.
     * Yanlış türde parametre gönderildiğinde fırlatılan `MethodArgumentTypeMismatchException` istisnasını ele alır.
     * Örneğin: Bir `Long` tipinde beklenen ID değerine `abc` gibi bir string verilirse bu hata döner.
     *
     * @param ex Yakalanan MethodArgumentTypeMismatchException
     * @return 400 BAD REQUEST HTTP yanıtı ve uygun hata mesajı
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, String>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("status", "400");
        errorResponse.put("error", "Bad Request");
        errorResponse.put("message", String.format("Parameter '%s' must be of type '%s'.",
                ex.getName(), ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "Unknown"));
        errorResponse.put("path", ex.getParameter().getExecutable().toString());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * 400 BAD REQUEST hatalarını yakalar.
     * Veri bütünlüğü ihlali olduğunda fırlatılan `DataIntegrityViolationException` istisnasını ele alır.
     * Örneğin: Aynı e-posta adresiyle ikinci kez kayıt olmaya çalışılırsa veya bir yabancı anahtar kısıtlaması ihlal edilirse bu hata döner.
     *
     * @param ex Yakalanan DataIntegrityViolationException
     * @return 400 BAD REQUEST HTTP yanıtı ve veri bütünlüğü ihlali mesajı
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, String>> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Data integrity violation"); // Veri bütünlüğü ihlali hatası
        response.put("message", extractConstraintViolationMessage(ex));
        response.put("status", HttpStatus.BAD_REQUEST.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * Veri bütünlüğü ihlali mesajını analiz eden yardımcı metot.
     * Eğer bir veritabanı kısıtlama ihlali varsa, ilgili kısıtlama adını döndürür.
     *
     * @param ex DataIntegrityViolationException istisnası
     * @return Hata mesajı
     */
    private String extractConstraintViolationMessage(DataIntegrityViolationException ex) {
        if (ex.getCause() instanceof org.hibernate.exception.ConstraintViolationException constraintViolation) {
            return "Constraint violation: " + constraintViolation.getConstraintName();
        }
        return "A database constraint was violated."; // Genel hata mesajı
    }

    /**
     * 500 INTERNAL SERVER ERROR hatalarını yakalar.
     * Beklenmeyen bir hata oluştuğunda `Exception` istisnasını ele alır.
     * Örneğin: NullPointerException, IllegalArgumentException gibi beklenmeyen hatalar bu blokta yakalanır.
     *
     * @param ex Yakalanan Exception
     * @return 500 INTERNAL SERVER ERROR HTTP yanıtı ve hata bilgileri
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> response = new HashMap<>();
        response.put("error", "Internal Server Error"); // Sunucu tarafında beklenmeyen bir hata oluştu
        response.put("message", ex.getMessage() != null ? ex.getMessage() : "An unexpected error occurred.");
        response.put("status", HttpStatus.INTERNAL_SERVER_ERROR.toString());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<Result> handleForbiddenException(ForbiddenException ex) {
        return new ResponseEntity<>(ResultHelper.forbiddenError(ex.getMessage()), HttpStatus.FORBIDDEN);
    }

}
