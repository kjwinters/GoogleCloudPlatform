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

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Bootstrap implements ServletContextListener {
  public void contextInitialized(ServletContextEvent event) {
    try {
      com.googlecode.objectify.ObjectifyService.init();
      com.googlecode.objectify.ObjectifyService.register(Car.class);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void contextDestroyed(ServletContextEvent event) {
    // App Engine does not currently invoke this method.
  }
}