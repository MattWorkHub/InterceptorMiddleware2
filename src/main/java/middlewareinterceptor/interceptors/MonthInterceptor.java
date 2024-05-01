package middlewareinterceptor.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import middlewareinterceptor.entities.Month;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.nio.file.Files.find;


@Component
public class MonthInterceptor implements HandlerInterceptor {

    private final List<Month> months = new ArrayList<>(Arrays.asList(
            new Month(1, "january", "gennaio", "januar"),
            new Month(3, "march", "marzo", "marz"),
            new Month(5, "May", "maggio", "mai"),
            new Month(7, "July", "luglio", "Juli"),
            new Month(9, "September", "settembre", "september"),
            new Month(11, "november", "novembre", "november")
    ));


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String monthNumberHeader = request.getHeader("monthNumber");



        if (monthNumberHeader == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Monthnumberheader is missing");
            return false;
        }

        if (monthNumberHeader.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Monthnumberheader is empty");
            return false;
        }


        Month month = find(Integer.valueOf(monthNumberHeader));
        request.setAttribute("month", Objects.requireNonNullElseGet(month, () -> new Month(null, "nope", "nope", "nope")));
        response.setStatus(HttpStatus.OK.value());
        return true;
    }


    private Month find(Integer integer) {
        for (Month month : months) {
            if (month.getMonthNumber().equals(integer)) {
                return month;
            }
        }
        return null;
    }

}