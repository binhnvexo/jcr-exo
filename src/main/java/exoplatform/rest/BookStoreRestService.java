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
package exoplatform.rest;

import javax.annotation.security.RolesAllowed;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.exoplatform.services.rest.resource.ResourceContainer;

import com.sun.corba.se.impl.javax.rmi.CORBA.Util;

import exoplatform.BookStoreService;
import exoplatform.entity.Author;
import exoplatform.entity.User;
import exoplatform.exception.DuplicateBookException;
import exoplatform.utils.Utils;

/**
 * Created by The eXo Platform SAS
 * Author : BinhNV
 *          binhnv@exoplatform.com
 * Jul 20, 2012  
 */

@Path("/bookstore")
public class BookStoreRestService implements ResourceContainer {

  private static final Log log = ExoLogger.getLogger(BookStoreRestService.class);
  private static final CacheControl cc;
  private BookStoreService service;
  
  static {
    cc = new CacheControl();
    cc.setNoCache(true);
    cc.setNoStore(true);
  }
  
  /**
   * 
   */
  public BookStoreRestService(BookStoreService service) {
    this.service = service;
  }

  /**
   * get user by name with sql statement
   * 
   * @param username
   * @return
   */
  @GET
  @RolesAllowed("users")
  @Path("/getUserByName/{username}")
  @Produces(MediaType.APPLICATION_JSON)
  public Response getUserByNameSQL(@PathParam("username") String username) {
    User user = service.getUserByNameSQL(username);
    if (user == null) {
      return Response.status(Status.NO_CONTENT).build();
    }
    return Response.ok(user, MediaType.APPLICATION_JSON).cacheControl(cc).build();
  }
  
  /**
   * add new author to datastorage
   * 
   * @param authorName
   * @param authorAddress
   * @param authorPhone
   * @return
   */
  @POST
  @RolesAllowed("users")
  @Path("/addAuthor")
  @Produces(MediaType.APPLICATION_JSON)
  public Response addAuthor(@PathParam("authorName")
            String authorName, @PathParam("authorAddress")
            String authorAddress, @PathParam("authorPhone")
            String authorPhone) {
    try {
      Node authorNode = service.addAuthor(authorName, authorAddress, authorPhone);
      Author author = Utils.createAuthorByNode(authorNode);
      return Response.ok(author, MediaType.APPLICATION_JSON).cacheControl(cc).build();
    } catch (RepositoryException re) {
      log.error("Can not add author", re);
      return Response.status(Status.BAD_REQUEST).build();
    } catch (DuplicateBookException de) {
      log.error("Can not add author", de);
      return Response.status(Status.BAD_REQUEST).build();
    }
  }
  
}
