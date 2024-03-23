package com.stytchspringsecurity.stytchspringsecurity.StytchControllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class BitbucketAuthController {

    @GetMapping("/bitbucketAuth")
    public RedirectView bitbucketAuth(){
        return new RedirectView("https://test.stytch.com/v1/public/oauth/bitbucket/start?public_token=public-token-test-fa3bb141-c5fb-4a78-aafc-60b112885add");
    }

}
