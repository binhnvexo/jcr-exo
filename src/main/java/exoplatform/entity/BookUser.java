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
package exoplatform.entity;

import org.apache.lucene.store.Lock.With;

/**
 * Created by The eXo Platform SAS
 * Author : BinhNV
 *          binhnv@exoplatform.com
 * Jul 16, 2012  
 */
public class BookUser {

  private int userId;
  private int bookId;
  
  /**
   * BookUser constructor without parameter
   */
  public BookUser() {
    
  }
  
  /**
   * BookUser constructor with parameter
   */
  public BookUser(int userId, int bookId) {
    this.userId = userId;
    this.bookId = bookId;
  }

  /**
   * @return the userId
   */
  public int getUserId() {
    return userId;
  }

  /**
   * @param userId the userId to set
   */
  public void setUserId(int userId) {
    this.userId = userId;
  }

  /**
   * @return the bookId
   */
  public int getBookId() {
    return bookId;
  }

  /**
   * @param bookId the bookId to set
   */
  public void setBookId(int bookId) {
    this.bookId = bookId;
  }

}
