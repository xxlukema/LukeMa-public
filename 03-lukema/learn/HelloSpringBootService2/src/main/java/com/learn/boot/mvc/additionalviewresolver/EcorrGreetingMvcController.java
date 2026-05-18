package com.learn.boot.mvc.additionalviewresolver;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 * This can be moved to WebMvcConfig
 *
 * OK: curl -k -i -X POST -F 'authCode="My Auth Code"' "https://localhost:8443/spring/greetings"
 * OK: curl -k -i -H "Content-Type: multipart/form-data" -X POST -F 'authCode="My Auth Code"' "https://localhost:8443/spring/greetings"
 * 500: curl -k -i -H "Content-Type: application/x-www-form-urlencoded" -X POST -F 'authCode="My Auth Code"' "https://localhost:8443/spring/greetings"
 *
 * formPost(url: string, smartId: string): Observable<any> {
 *      const params = new HttpParams().set('authCode', smartId);
 *
 *      return this.httpClient.post<any>(url, null, { params })
 *          .pipe(
 *              catchError(this.handleError)
 *          );
 *  }
 *
 */
@Controller
@RequestMapping("/spring")
public class EcorrGreetingMvcController {

    private static final Logger log = LoggerFactory.getLogger(EcorrGreetingMvcController.class);

    @PostMapping("greetings")
    // @GetMapping("greetings")
    public String greeting(MyBean myBean) {

        log.info("Called. {}", myBean.getAuthCode());

        /**
         * The view name should be Relative path
         */
        return "greeting";
    }

}


class MyBean {
    private String authCode;

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

}
