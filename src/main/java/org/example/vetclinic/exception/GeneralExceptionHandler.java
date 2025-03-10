package org.example.vetclinic.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * Global exception handler for the application.
 * This class handles specific exceptions across the entire application
 * and provides a consistent error page view with relevant error messages.
 */
@ControllerAdvice
public class GeneralExceptionHandler {

    /**
     * Handles the {@link UserNotFoundException} and returns an error page with the message.
     *
     * @param ex the exception that is thrown when a user is not found
     * @return a {@link ModelAndView} object that points to the error page with an error message
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ModelAndView handleAuthorNotFoundException(UserNotFoundException ex) {
        ModelAndView mav = new ModelAndView("errorPage");
        mav.addObject("message", ex.getMessage());
        return mav;
    }

}
