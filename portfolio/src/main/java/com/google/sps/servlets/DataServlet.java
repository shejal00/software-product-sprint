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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.sps.data.Comment;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

// /** Servlet that returns some example content. TODO: modify this file to handle comments data */
// @WebServlet("/comments")
// public class DataServlet extends HttpServlet {
//   @Override
//   public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
//     ArrayList<String> comments=new ArrayList<String>();//Creating arraylist    
//     comments.add("Kajal");//Adding object in arraylist    
//     comments.add("I really liked the footer in this page");    
//     comments.add("I would also like to know more about the IR models you implemented"); 
//     Gson gson = new Gson();
//     String json = gson.toJson(comments);
    
//     // Send the JSON as the response
//     response.setContentType("application/json;");
//     response.getWriter().println(json);
//   }
// }

@WebServlet("/comments")
public class DataServlet extends HttpServlet {
    ArrayList<String> Comments=new ArrayList<String>();
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {       
        response.sendRedirect("https://8080-dot-13049279-dot-devshell.appspot.com/?authuser=0#contact");
        // Respond with the result. 
        String comments = getParameter(request, "message");
        Comments.add(comments);
        for(String comment:Comments){
            Gson gson = new Gson();
            response.setContentType("application/json;");
            response.getWriter().println(gson.toJson(comment));
        }
    }

    /**
    * @return the request parameter, or the default value if the parameter
    *         was not specified by the client
    */
    private String getParameter(HttpServletRequest request, String name) {
        String value = request.getParameter(name);
        return value;
    }
}
