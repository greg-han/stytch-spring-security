package com.stytchspringsecurity.stytchspringsecurity.StytchControllers;


import com.stytch.java.common.StytchResult;
import com.stytch.java.consumer.StytchClient;
import com.stytch.java.consumer.models.oauth.AttachRequest;
import com.stytch.java.consumer.models.oauth.AttachResponse;
import com.stytchspringsecurity.stytchspringsecurity.ProcessingMethods.StytchCookieProcessors;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
public class BitbucketAuthController {
    @Autowired
    StytchCookieProcessors stytchCookieProcessors;
    @Value("${stytch.bitbucket.starturl}")
    String starturl;

    @GetMapping("/v1/bitbucketAuth")
    public void bitbucketAuth(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String sessionToken = stytchCookieProcessors.getSessionToken(request);
        String providerType = stytchCookieProcessors.getProviderType(request);

        AttachRequest attachRequest = null;
        StytchResult<AttachResponse> attachResponse;
        String attachToken = null;

        if((sessionToken != null) && providerType != null ) {
            if(sessionToken != null) {
                attachRequest = new AttachRequest(providerType, null, sessionToken);
            }
            try {
                attachResponse = StytchClient.oauth.attachCompletable(attachRequest).get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
            if (attachResponse instanceof StytchResult.Error) {
                var exception = ((StytchResult.Error) attachResponse).getException();
                System.out.println(exception.getReason());
            }
            //This might be a good place to log the UID of the request
            System.out.println(((StytchResult.Success<?>) attachResponse).getValue());
            attachToken = ((StytchResult.Success<AttachResponse>) attachResponse).getValue().getOauthAttachToken();
            String url = starturl + "&oauth_attach_token=" + attachToken;
            response.sendRedirect(url);
        }
        else {
            response.sendRedirect(starturl);
        }
    }
}
