
package com.innovature.rentx.view;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseView {

    private String errorCode;
    private String errorMessage;

    public ResponseView(String errorMessage, String errorCode) {

        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public ResponseView() {

        this.errorCode = "0";
        this.errorMessage = "";
    }

}
