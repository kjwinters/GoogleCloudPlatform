/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.appengine.java8;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.google.appengine.api.utils.SystemProperty;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Result;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// With @WebServlet annotation the webapp/WEB-INF/web.xml is no longer required.
@WebServlet(name = "HelloAppEngine", value = "/hello")
public class HelloAppEngine extends HttpServlet {


  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response)
      throws IOException {

    try {
      this.testObjectify();
    } catch (Exception e) {
      System.out.println(e);
    }

    Properties properties = System.getProperties();

    response.setContentType("text/plain");
    response.getWriter().println("Hello App Engine - Standard using "
            + SystemProperty.version.get() + " Java "
            + properties.get("java.specification.version"));
  }

  public static String getInfo() {
    return "Version: " + System.getProperty("java.version")
          + " OS: " + System.getProperty("os.name")
          + " User: " + System.getProperty("user.name");
  }

  private void testObjectify() {

    int red = 0xFF0000;
    int blue = 0x0000FF;
    Car porsche = new Car("2FAST", red);
    ofy().save().entity(porsche).now();    // async without the now()

    assert porsche.id != null;    // id was autogenerated

    // Get it back
    Result<Car> result = ofy().load().key(Key.create(Car.class, porsche.id));  // Result is async
    Car fetched1 = result.now();    // Materialize the async value

    // More likely this is what you will type
    Car fetched2 = ofy().load().type(Car.class).id(porsche.id).now();

    // Or you can issue a query
    Car fetched3 = ofy().load().type(Car.class).filter("license", "2FAST").first().now();

    // Change some data and write it
    porsche.color = blue;
    ofy().save().entity(porsche).now();    // async without the now()

    // Delete it
    ofy().delete().entity(porsche).now();    // async without the now()
  }

}
// [END example]
