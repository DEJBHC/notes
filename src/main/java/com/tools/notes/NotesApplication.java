package com.tools.notes;

import com.tools.notes.show.MainFrame;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.sql.SQLException;

@SpringBootApplication
@EnableScheduling
public class NotesApplication {
    public static void myFrame() throws SQLException {
        new MainFrame();
    }
    public static void main(String[] args) throws SQLException {
        new SpringApplicationBuilder(NotesApplication.class).headless(false).run(args);
        myFrame();
    }
}
