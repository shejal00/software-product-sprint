// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.sps.data.Authentication;

@WebServlet("/authentication")
public class AuthenticationServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        String redirectToThisUrl="";
        boolean checkLoggedIn;
        if (userService.isUserLoggedIn()) {
            checkLoggedIn=userService.isUserLoggedIn();
            String urlToRedirectToAfterUserLogsOut = "/";
            redirectToThisUrl = userService.createLogoutURL(urlToRedirectToAfterUserLogsOut);
        } else {
            checkLoggedIn=userService.isUserLoggedIn();
            String urlToRedirectToAfterUserLogsIn = "/";
            redirectToThisUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
        }
        Gson gson = new Gson();
        response.setContentType("application/json");
        Authentication authentication = new Authentication(checkLoggedIn, redirectToThisUrl);
        response.getWriter().println(gson.toJson(authentication));
    }
}
