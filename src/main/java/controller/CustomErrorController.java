package controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class CustomErrorController implements ErrorController {

	//mrtodo de erro padrão para todas as paginas
    @RequestMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("error");
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        String errorMessage = (String) request.getAttribute("javax.servlet.error.message");
        
        mv.addObject("statusCode", statusCode != null ? statusCode : "N/A");
        mv.addObject("errorMessage", errorMessage != null ? errorMessage : "Erro desconhecido");

        return mv;
    }

 
    public String getErrorPath() {
        return "/error";
    }
}
