package App.Sequrity.Filter;


import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultJwtParser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.csrf.*;
import org.springframework.security.web.util.UrlUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.UUID;


public class JwtCsrfFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver resolver;


    public JwtCsrfFilter(HandlerExceptionResolver resolver) {
        this.resolver = resolver;
    }

    public String getCurrentUsername() {
        Authentication auth;
        String result = "";
        try {
            auth = SecurityContextHolder.getContext().getAuthentication();
            result = auth.getName();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String currentUsername = getCurrentUsername();
        System.out.println("RequestServletPath:" + request.getServletPath());
        System.out.println("CurrentUsername:" + currentUsername);

        //--- Ищем сохраненный Token (сохраняется в атрибутах текщей сессии)
        HttpSession session=request.getSession(); //--- получить сессию из http запроса
        CsrfToken csrfToken = (CsrfToken) session.getAttribute("ITFB-Token");//--- получаем объект из аттрибута
        boolean missingToken = csrfToken == null;


        if(currentUsername!="") {//--- если юзер авторизован, то генерим токен

            if (missingToken) {//--- если в аттрибуте пусто, то генерим новый Token

                String id = UUID.randomUUID().toString().replace("-", "");//--- Генерим UID
                String token = "";
                try {
                    token = Jwts.builder()
                            .setId(id)
                            .signWith(SignatureAlgorithm.HS256, "SecretWord")
                            .compact();//--- собираем зашифрованную строку из JSON (Id шифруя алгоритмом HS256 и секретным кодом)
                } catch (JwtException e) {
                    e.printStackTrace();
                    //ignore
                }

                csrfToken = new DefaultCsrfToken("ITFB-Token", "ITFB-Token", token);//--- генерим объект Token
                missingToken = false;

                session.setAttribute("ITFB-Token", csrfToken);//--- прописываем в сессию созданный Token
                System.out.println("Сгенерили новый Token (ITFB-Token): " + token);
            }
        }

        if (request.getServletPath().equals("/login")) {
            try {

                //--- идем на обработку фильтра без дополнительных проверок
                filterChain.doFilter(request, response);

            } catch (Exception e) {
                resolver.resolveException(request, response, null, new MissingCsrfTokenException(csrfToken.getToken()));
            }
        } else {

            if (missingToken) {//--- если в аттрибуте пусто, то генерим Error
                System.out.println("В параметрах сессии отсутствует Token");
                response.sendRedirect("/login");
                filterChain.doFilter(request, response);
            }

            String actualToken = request.getHeader(csrfToken.getHeaderName());
            System.out.println("csrfToken.getToken:" + csrfToken.getToken());


            System.out.println("csrfToken.getHeaderName()->actualToken " + csrfToken.getHeaderName() + "->" + actualToken);
            if (actualToken == null) actualToken = request.getParameter(csrfToken.getParameterName());
            try {
                if (!StringUtils.isEmpty(actualToken)) {

                    //--- сравнение зашифрованных JSON и генерация Exeption
                    /*
                    Jwts.parser()
                            .setSigningKey("SecretWord")
                            .parseClaimsJws(actualToken);

                     */



                        //-- внутри проводит расшифровку по указанному секретному слову
                        DefaultJwtParser defaultJwtParser = new DefaultJwtParser();
                        defaultJwtParser.setSigningKey("SecretWord");

                        Jwt jwt_actual = defaultJwtParser.parse (actualToken);
                        Jws jws_actual = defaultJwtParser.parseClaimsJws (actualToken);
                        Jws jws_source = defaultJwtParser.parseClaimsJws (csrfToken.getToken());

                        System.out.println("jwt:" + jwt_actual.toString());
                        System.out.println("jws:" + jws_actual.toString());
                        System.out.println("signature actual:" + jws_actual.getSignature());
                        System.out.println("signature source:" + jws_source.getSignature());

                        if (!jws_actual.getSignature().equals(jws_source.getSignature())) {
                            System.out.println("signature is not true");
                        }

                        //--- result->parsed.getSecret:header={alg=HS256},body={jti=ce8c5305fe19457f9515f6c6137bf53b, iat=1658901159, nbf=1658901159, exp=1658902959},signature=E-sg_eGeuCGqOd1tWJOTr0JfjknspaheXGxunsIbkAU




                    filterChain.doFilter(request, response);
                } else
                    resolver.resolveException(request, response, null, new InvalidCsrfTokenException(csrfToken, actualToken));
            } catch (JwtException e) {
                if (this.logger.isDebugEnabled()) {
                    this.logger.debug("Invalid CSRF token found for " + UrlUtils.buildFullRequestUrl(request));
                }

                if (missingToken) {
                    resolver.resolveException(request, response, null, new MissingCsrfTokenException(actualToken));
                } else {
                    resolver.resolveException(request, response, null, new InvalidCsrfTokenException(csrfToken, actualToken));
                }
            }
        }
    }
}