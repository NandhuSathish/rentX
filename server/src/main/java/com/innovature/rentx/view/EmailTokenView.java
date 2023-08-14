package com.innovature.rentx.view;



import lombok.Getter;

@Getter
public class EmailTokenView{

    private final String message;
    private final String emailToken;

    public EmailTokenView(String msg, String emailToken) {
        this.message=msg;
        this.emailToken = emailToken;
        
    }


   

    public void setToken(String string) {
      // TODO document why this method is empty
    }


}
