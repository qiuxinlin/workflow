/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.scxys.activiti.rest.service.api.management;

import org.activiti.engine.ActivitiObjectNotFoundException;
import org.activiti.engine.ManagementService;
import org.activiti.engine.management.TableMetaData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Frederik Heremans
 */
@RestController
public class TableColumnsResource {
  
  @Autowired
  protected ManagementService managementService;
  
  @RequestMapping(value="/management/tables/{tableName}/columns", method = RequestMethod.GET, produces = "application/json")
  public TableMetaData getTableMetaData(@PathVariable String tableName) {
    TableMetaData response = managementService.getTableMetaData(tableName);
   
    if (response == null) {
      throw new ActivitiObjectNotFoundException("Could not find a table with name '" + tableName + "'.", String.class);
    }
    return response;
  }
}
