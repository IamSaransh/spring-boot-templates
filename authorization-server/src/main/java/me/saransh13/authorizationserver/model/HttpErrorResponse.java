package me.saransh13.authorizationserver.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Date;


@NoArgsConstructor
@Getter
@Setter
public class HttpErrorResponse {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Date date;
    private int status;
    private HttpStatus httpStatus;
    private String reason;
    private String message;

    public HttpErrorResponse(int status, HttpStatus httpStatus, String reason, String message) {
        this.date = new Date();
        this.status = status;
        this.httpStatus = httpStatus;
        this.reason = reason;
        this.message = message;
    }
}
