package com.giahan.app.vietskindoctor.network;

import java.io.IOException;

/**
 * Created by NamVT on 7/17/2018.
 */

public class NoConnectivityException extends IOException {

    @Override
    public String getMessage() {
        return "No connectivity exception";
    }
}
