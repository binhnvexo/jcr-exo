/*
 * Copyright (C) 2003-2012 eXo Platform SAS.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package exoplatform;

import org.exoplatform.services.organization.OrganizationService;

import exoplatform.entity.User;

/**
 * Created by The eXo Platform SAS
 * Author : BinhNV
 *          binhnv@exoplatform.com
 * Jul 17, 2012  
 */
public class BookStoreTest extends BaseBookstoreTestCase {

  private static BookStoreService service;
  private static JCRDataStorage storage;
  private OrganizationService organizationService;
  
  public BookStoreTest() throws Exception {
    super();
    service = (BookStoreService) container.getComponentInstanceOfType(BookStoreService.class);
    storage = new JCRDataStorage(repositoryService);
    organizationService = (OrganizationService) container.getComponentAdapterOfType(OrganizationService.class);
  }
  
  /* (non-Javadoc)
   * @see junit.framework.TestCase#setUp()
   */
  @Override
  public void setUp() throws Exception {
    super.setUp();
  }
  
  public void testUserByNameSQL() {
    String name = "BinhNV";
    
    User user = service.getUserByNameSQL(name);
    assertNotNull(user);
  }
  
}
