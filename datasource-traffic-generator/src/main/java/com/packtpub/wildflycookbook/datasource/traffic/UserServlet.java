package com.packtpub.wildflycookbook.datasource.traffic;

import org.fluttercode.datafactory.impl.DataFactory;

import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lfugaro on 9/22/14.
 */
@WebServlet(asyncSupported = true)
public class UserServlet extends HttpServlet {

    @EJB
    private UserManager userManager;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doIt(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doIt(request, response);
    }

    private void doIt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        User user = null;
        DataFactory df = new DataFactory();
        //for (int i = 0; i < 100; i++) {
            user = new User();
            user.setFirstname(df.getFirstName());
            user.setLastname(df.getLastName());
            user.setNickname(df.getRandomWord(8, 12));
            user.setPhone(df.getNumberText(8));
            user.setEmail(df.getEmailAddress());
            userManager.create(user);
        //}
    }
}
