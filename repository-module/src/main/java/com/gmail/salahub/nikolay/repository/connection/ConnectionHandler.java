package com.gmail.salahub.nikolay.repository.connection;

import com.gmail.salahub.nikolay.repository.exception.DatabaseDriverException;
import com.gmail.salahub.nikolay.repository.exception.DatabasePropertiesException;
import com.gmail.salahub.nikolay.repository.exception.GettingConnectionException;
import com.gmail.salahub.nikolay.repository.exception.InitialFileReadingException;
import com.gmail.salahub.nikolay.repository.properties.DatabaseProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.stream.Stream;

@Component("connectionHandler")
public class ConnectionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ConnectionHandler.class);

    private static final String PROPERTY_ERROR_MASSAGE = "Property has error";
    private static final String ERROR_WITH_GETTING_CONNECTION_MASSAGE = "Getting connection is failed" ;
    private static final String SUCCESS_CREATED_MASSAGE ="Connection success created!" ;
    private static final String ERROR_READING_INITIAL_FILE_MASSAGE = "Reading file failed";
    private static final String ERROR_DRIVER_MASSAGE = "Where is your driver?";

    private final DatabaseProperties databaseProperties;

    @Autowired
    public ConnectionHandler(DatabaseProperties databaseProperties) {
        try {
            Class.forName(databaseProperties.getDriver());
        } catch (ClassNotFoundException e) {
            logger.error(e.getMessage(), e);
            throw new DatabaseDriverException(ERROR_DRIVER_MASSAGE, e);
        }
        this.databaseProperties = databaseProperties;
    }

    public Connection getConnection() {
        logger.info("Creating connection...");
        try {
            Properties properties = new Properties();
            properties.setProperty("user", databaseProperties.getUsername());
            properties.setProperty("password", databaseProperties.getPassword());
            properties.setProperty("useUnicode", databaseProperties.getIsUseUnicode());
            properties.setProperty("characterEncoding", databaseProperties.getCharacterEncoding());
            properties.setProperty("useJDBCompliantTimezoneShift",
                    databaseProperties.getIsUseJDBCompliantTimezoneShift());
            properties.setProperty("useLegacyDatetimeCode", databaseProperties.getIsUseLegacyDatetimeCode());
            properties.setProperty("serverTimezone", databaseProperties.getServerTimeZone());
            Connection connection = DriverManager.getConnection(databaseProperties.getUrl(), properties);
            logger.info(SUCCESS_CREATED_MASSAGE);
            return connection;
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new DatabasePropertiesException(PROPERTY_ERROR_MASSAGE, e);
        }
    }

    @PostConstruct
    public void createDatabaseTables() throws URISyntaxException {
        URL initialDataFile = getClass()
                .getClassLoader()
                .getResource(databaseProperties.getInitFile());
        String[] initQuery = getListOfStringFromFile(initialDataFile);
        try (Connection connection = getConnection()) {
            connection.setAutoCommit(false);
            try (Statement statement = connection.createStatement()) {
                for (String query : initQuery) {
                    statement.addBatch(query);
                }
                statement.executeBatch();
                connection.commit();
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                connection.rollback();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
            throw new GettingConnectionException(ERROR_WITH_GETTING_CONNECTION_MASSAGE, e);
        }
    }

    private static String[] getListOfStringFromFile(URL fileURl) throws URISyntaxException {
        File file = new File(fileURl.toURI());
        try (Stream<String> fileStream = Files.lines(file.toPath())) {
            return fileStream.reduce(String::concat).orElse("").split(";");
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            throw new InitialFileReadingException(ERROR_READING_INITIAL_FILE_MASSAGE, e);
        }
    }

}