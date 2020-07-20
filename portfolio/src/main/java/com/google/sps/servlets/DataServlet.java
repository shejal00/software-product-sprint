// // Copyright 2019 Google LLC
// //
// // Licensed under the Apache License, Version 2.0 (the "License");
// // you may not use this file except in compliance with the License.
// // You may obtain a copy of the License at
// //
// //     https://www.apache.org/licenses/LICENSE-2.0
// //
// // Unless required by applicable law or agreed to in writing, software
// // distributed under the License is distributed on an "AS IS" BASIS,
// // WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// // See the License for the specific language governing permissions and
// // limitations under the License.

// package com.google.sps.servlets;

// import com.google.appengine.api.datastore.DatastoreService;
// import com.google.appengine.api.datastore.DatastoreServiceFactory;
// import com.google.appengine.api.datastore.Entity;
// import com.google.appengine.api.datastore.PreparedQuery;
// import com.google.appengine.api.datastore.Query;
// import com.google.appengine.api.datastore.Query.SortDirection;
// import com.google.appengine.api.users.UserService;
// import com.google.appengine.api.users.UserServiceFactory;

// import com.google.sps.data.Comment;
// import java.io.IOException;
// import javax.servlet.annotation.WebServlet;
// import javax.servlet.http.HttpServlet;
// import javax.servlet.http.HttpServletRequest;
// import javax.servlet.http.HttpServletResponse;
// import com.google.gson.Gson;
// import java.util.ArrayList;
// import java.util.List;
// import java.util.Arrays;

// @WebServlet("/comments")
// public class DataServlet extends HttpServlet {
//     DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
   
//     @Override
//     public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {       
//         Query query = new Query("Comment").addSort("timestamp", SortDirection.DESCENDING);
//         PreparedQuery results = datastore.prepare(query); 
//         List<Comment> comments = new ArrayList<>();
        
//         for (Entity entity : results.asIterable()) {
//             long id = entity.getKey().getId();
//             String message = (String) entity.getProperty("message");
//             long timestamp = (long) entity.getProperty("timestamp");
//             String email = (String) entity.getProperty("email");
//             Comment comment = new Comment(id, timestamp, message, email);
//             comments.add(comment);
//         } 

//         // Respond with the result.
//         Gson gson = new Gson();    
//         response.setContentType("application/json");
//         response.getWriter().println(gson.toJson(comments));
//     }

//     @Override
//     public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {       
//         UserService userService = UserServiceFactory.getUserService();

//         if (!userService.isUserLoggedIn()) {
//             response.sendRedirect("/index.html");
//             return;
//         }

//         String message = getParameter(request, "message");
//         long timestamp = System.currentTimeMillis();
//         String email = userService.getCurrentUser().getEmail();

//         Entity commentEntity = new Entity("Comment");
//         commentEntity.setProperty("message", message);
//         commentEntity.setProperty("timestamp", timestamp);
//         commentEntity.setProperty("email", email);

//         datastore.put(commentEntity);
//         response.sendRedirect("/index.html");
//     }

//     /**
//     * @return the request parameter, or the default value if the parameter
//     *         was not specified by the client
//     */
//     private String getParameter(HttpServletRequest request, String name) {
//         String value = request.getParameter(name);
//         return value;
//     }
// }
