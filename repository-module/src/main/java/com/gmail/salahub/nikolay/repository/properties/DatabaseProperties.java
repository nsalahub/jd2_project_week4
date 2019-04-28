package com.gmail.salahub.nikolay.repository.properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("databaseProperties")
public class DatabaseProperties {

    @Value("${database.url}")
    private String url;

    @Value("${database.username}")
    private String username;

    @Value("${database.password}")
    private String password;

    @Value("${database.driver}")
    private String driver;

    @Value("${database.useUnicode}")
    private String isUseUnicode;

    @Value("${database.characterEncoding}")
    private String characterEncoding;

    @Value("${database.useJDBCompliantTimezoneShift}")
    private String isUseJDBCompliantTimezoneShift;

    @Value("${database.useLegacyDatetimeCode}")
    private String isUseLegacyDatetimeCode;

    @Value("${database.serverTimezone}")
    private String serverTimeZone;

    @Value("${database.initFile}")
    private String initFile;

    public String getUrl() {
        return url;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDriver() {
        return driver;
    }

    public String getIsUseUnicode() {
        return isUseUnicode;
    }

    public String getCharacterEncoding() {
        return characterEncoding;
    }

    public String getIsUseJDBCompliantTimezoneShift() {
        return isUseJDBCompliantTimezoneShift;
    }

    public String getIsUseLegacyDatetimeCode() {
        return isUseLegacyDatetimeCode;
    }

    public String getInitFile() {
        return initFile;
    }

    public String getServerTimeZone() {
        return serverTimeZone;
    }
}
