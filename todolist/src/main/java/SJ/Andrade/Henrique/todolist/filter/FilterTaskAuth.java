package  SJ.Andrade.Henrique.todolist.filter;

import SJ.Andrade.Henrique.todolist.users.IUserRepository;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

@Component
public  class FilterTaskAuth implements OncePerRequestFilter {
    @Autowired
    private IUserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

             var serveltPath = request.getServletPath();
             if(serveltPath.startsWith("/task/")) {
                 var auth = request.getHeader("Authorization");
                 var validation = auth.substring("Basic".length()).trim();

                 byte[] decoder = Base64.getDecoder().decode(validation);
                 var authPassword = new String(decoder);

                 String[] credential = authPassword.split(":");
                 String username = credential[0];
                 String password = credential[1];

                 var user = this.userRepository.findByUsername(username);
                 if (user == null) {
                     response.sendError(401);
                 } else {
                     var verify = BCrypt.verify(password.toCharArray(), user.getPassword());
                     if (verify.verified) {
                         request.setAttribute("idUser",user.getId());
                         filterChain.doFilter(request, response);
                     } else {
                         response.sendError(401);
                     }
                 }

             }else {
                 filterChain.doFilter(request, response);
             }

    }
}