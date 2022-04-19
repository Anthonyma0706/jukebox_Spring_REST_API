package anthonyma.springbootjukerestapi;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class JukeNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(JukeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(JukeNotFoundException ex) {
        return ex.getMessage();
    }

    @ResponseBody
    @ExceptionHandler(RequirementNotMatchedException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String reqNotFoundHandler(RequirementNotMatchedException ex) {
        return ex.getMessage();
    }

}
