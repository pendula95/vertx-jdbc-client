/*
 * Copyright 2014 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */

package io.vertx.groovy.ext.jdbc;
import groovy.transform.CompileStatic
import io.vertx.lang.groovy.InternalHelper
import io.vertx.groovy.ext.sql.SqlConnection
import io.vertx.groovy.core.Vertx
import io.vertx.core.json.JsonObject
import io.vertx.core.AsyncResult
import io.vertx.core.Handler
/**
 * An asynchronous client interface for interacting with a JDBC compliant database
*/
@CompileStatic
public class JDBCClient {
  final def io.vertx.ext.jdbc.JDBCClient delegate;
  public JDBCClient(io.vertx.ext.jdbc.JDBCClient delegate) {
    this.delegate = delegate;
  }
  public Object getDelegate() {
    return delegate;
  }
  /**
   * Create a JDBC client which maintains its own data source.
   * @param vertx the Vert.x instance
   * @param config the configuration
   * @return the client
   */
  public static JDBCClient createNonShared(Vertx vertx, Map<String, Object> config) {
    def ret= new io.vertx.groovy.ext.jdbc.JDBCClient(io.vertx.ext.jdbc.JDBCClient.createNonShared((io.vertx.core.Vertx)vertx.getDelegate(), config != null ? new io.vertx.core.json.JsonObject(config) : null));
    return ret;
  }
  /**
   * Create a JDBC client which shares its data source with any other JDBC clients created with the same
   * data source name
   * @param vertx the Vert.x instance
   * @param config the configuration
   * @param dataSourceName the data source name
   * @return the client
   */
  public static JDBCClient createShared(Vertx vertx, Map<String, Object> config, String dataSourceName) {
    def ret= new io.vertx.groovy.ext.jdbc.JDBCClient(io.vertx.ext.jdbc.JDBCClient.createShared((io.vertx.core.Vertx)vertx.getDelegate(), config != null ? new io.vertx.core.json.JsonObject(config) : null, dataSourceName));
    return ret;
  }
  /**
   * Like {@link io.vertx.groovy.ext.jdbc.JDBCClient#createShared} but with the default data source name
   * @param vertx the Vert.x instance
   * @param config the configuration
   * @return the client
   */
  public static JDBCClient createShared(Vertx vertx, Map<String, Object> config) {
    def ret= new io.vertx.groovy.ext.jdbc.JDBCClient(io.vertx.ext.jdbc.JDBCClient.createShared((io.vertx.core.Vertx)vertx.getDelegate(), config != null ? new io.vertx.core.json.JsonObject(config) : null));
    return ret;
  }
  /**
   * Returns a connection that can be used to perform SQL operations on. It's important to remember
   * to close the connection when you are done, so it is returned to the pool.
   * @param handler the handler which is called when the <code>JdbcConnection</code> object is ready for use.
   * @return 
   */
  public JDBCClient getConnection(Handler<AsyncResult<SqlConnection>> handler) {
    def ret= new io.vertx.groovy.ext.jdbc.JDBCClient(this.delegate.getConnection(new Handler<AsyncResult<io.vertx.ext.sql.SqlConnection>>() {
      public void handle(AsyncResult<io.vertx.ext.sql.SqlConnection> event) {
        AsyncResult<SqlConnection> f
        if (event.succeeded()) {
          f = InternalHelper.<SqlConnection>result(new SqlConnection(event.result()))
        } else {
          f = InternalHelper.<SqlConnection>failure(event.cause())
        }
        handler.handle(f)
      }
    }));
    return ret;
  }
  /**
   * Close the client
   */
  public void close() {
    this.delegate.close();
  }
}
