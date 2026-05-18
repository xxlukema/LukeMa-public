package com.learn.boot.mvc.additionalviewresolver;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.log4j.Log4j2;


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
@Log4j2
public class EcorrGreetingMvcController {

    // @PostMapping("greetings")
    @GetMapping("greetings")
    public String greeting(MyBean myBean) {

        log.info("Called. {}", myBean.getAuthCode());

        /**
         * The view name should be Relative path
         */
        return "greeting";
    }

}
