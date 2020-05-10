/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package edu.usm.cos420.domain;

import java.util.List;

// [START example]

/**
 * Holds a result for a database query.
 * @param <K>: Class type of the result.
 */
public class Result<K> {
  /**
   * Current location in list of result from DB
   */
  public String cursor;

    /**
     * List of result from DB query
   */
  public List<K> result;

  /**
   * Constructs a new Result containing the list of results from a query and the current location in the list of results from the db.
   * @param result: results of query
   * @param cursor: current location
   */
  public Result(List<K> result, String cursor) {
    this.result = result;
    this.cursor = cursor;
  }

  /**
   * Constructs a new Result containing the list of results from a db query.
   * @param result: results of query
   */
  public Result(List<K> result) {
    this.result = result;
    this.cursor = null;
  }
}
// [END example]
