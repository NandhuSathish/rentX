package com.innovature.rentx.form;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RefreshTokenFormTest {

    @Test
    public void RefreshForm(){
        RefreshTokenForm form=new RefreshTokenForm();
        form.setRefreshToken("5c48a154e9e367f6d66ec726057fbd8511600f7599c957502b1d4f5e438cd8ab93d6ad5e0c21eb63205c7967d58a3a2f0e5ca0021b706ce4b6a870fcd79b2b3509f37d912bddbe1dfee3e2bb34874a11331465e01370a487c29a2743843b154c3350d66054544dbabfcefcbb4e5c19e545a2e43f1f29387bfb64efccb12b7e07fb0597aa492c3");


        assertEquals("5c48a154e9e367f6d66ec726057fbd8511600f7599c957502b1d4f5e438cd8ab93d6ad5e0c21eb63205c7967d58a3a2f0e5ca0021b706ce4b6a870fcd79b2b3509f37d912bddbe1dfee3e2bb34874a11331465e01370a487c29a2743843b154c3350d66054544dbabfcefcbb4e5c19e545a2e43f1f29387bfb64efccb12b7e07fb0597aa492c3",
                form.getRefreshToken());
    }

}