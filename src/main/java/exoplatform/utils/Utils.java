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
package exoplatform.utils;

import exoplatform.entity.Book;

/**
 * Created by The eXo Platform SAS
 * Author : BinhNV
 *          binhnv@exoplatform.com
 * Jul 5, 2012  
 */
public class Utils {

  /** 
   * Define a static variable which contain id of book 
   * id of book will increase 1 value when a new book create
   * 
   **/
  public static int bookId = 0;
  
  /** 
   * Define a static variable which contain id of author 
   * id of author will increase 1 value when a new author create
   * 
   **/
  public static int authorId = 0;
  
  /** 
   * Define a static variable which contain id of user 
   * id of user will increase 1 value when a new user create
   * 
   **/
  public static int userId = 0;
  
  /**
   * Convert enum value to String value 
   * 
   * @param category Enum value of category
   * @return String value of category
   */
  public static String bookCategoryEnumToString(Book.CATEGORY category) {
    if (category != null) {
      return category.toString();
    }
    return null;
  }
  
  /**
   * Convert String value to enum value
   * 
   * @param category String value of category
   * @return Book.CATEGORY of category
   */
  public static Book.CATEGORY bookCategoryStringToEnum(String category) {
    return category != null ? Book.CATEGORY.valueOf(category) : null;
  }
  
}
