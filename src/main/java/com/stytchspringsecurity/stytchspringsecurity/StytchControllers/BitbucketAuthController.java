package com.stytchspringsecurity.stytchspringsecurity.StytchControllers;


import com.stytch.java.common.StytchResult;
import com.stytch.java.consumer.StytchClient;
import com.stytch.java.consumer.models.oauth.AttachRequest;
import com.stytch.java.consumer.models.oauth.AttachResponse;
import com.stytch.java.consumer.models.oauth.AuthenticateResponse;
import com.stytchspringsecurity.stytchspringsecurity.Authentication.StytchOauthAuthenticationResponseToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
public class BitbucketAuthController {
    //userid: user-test-a35e8907-6d96-42fa-816a-07e3b0ae1ad8
    @GetMapping("/bitbucketAuth")
    public RedirectView bitbucketAuth() {
        /*
        AttachRequest attachRequest = new AttachRequest("bitbucket", "user-test-a35e8907-6d96-42fa-816a-07e3b0ae1ad8");
        StytchResult<AttachResponse> attachResponse;
        String attachToken = null;
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
        } else {
            //This might be a good place to log the UID of the request
            System.out.println(((StytchResult.Success<?>) attachResponse).getValue());
            attachToken = ((StytchResult.Success<AttachResponse>) attachResponse).getValue().getOauthAttachToken();
        }

        String url = "https://test.stytch.com/v1/public/oauth/bitbucket/start?public_token=public-token-test-fa3bb141-c5fb-4a78-aafc-60b112885add&oauth_attach_token=" + attachToken;
        
         */
        return new RedirectView("https://test.stytch.com/v1/public/oauth/bitbucket/start?public_token=public-token-test-fa3bb141-c5fb-4a78-aafc-60b112885add");
        //return new RedirectView(url);

    }

}
