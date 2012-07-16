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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.jcr.Node;
import javax.jcr.RepositoryException;

import org.exoplatform.services.cache.CacheService;
import org.exoplatform.services.cache.ExoCache;
import org.exoplatform.services.jcr.RepositoryService;
import org.exoplatform.services.log.ExoLogger;
import org.exoplatform.services.log.Log;
import org.picocontainer.Startable;

import exoplatform.entity.Author;
import exoplatform.entity.Book;
import exoplatform.entity.Book.CATEGORY;
import exoplatform.entity.User;
import exoplatform.exception.DuplicateBookException;

/**
 * Created by The eXo Platform SAS
 * Author : BinhNV
 *          binhnv@exoplatform.com
 * Jul 6, 2012  
 */
public class BookStoreService implements Startable {

  /* Define a exo log */
  private static final Log log = ExoLogger.getLogger(BookStoreService.class);
  /* Define a exo cache */
  private ExoCache<Serializable, Book> cache;
  /* Define a JCRDataStore object */
  private JCRDataStorage jcrDataStorage;
  
  /**
   * BookStoreService constructor
   * @param rservice
   * @param cacheService
   * @param dataStorage
   * @throws RepositoryException
   * @throws DuplicateBookException 
   */
  public BookStoreService(RepositoryService rservice,
                          CacheService cacheService,
                          JCRDataStorage dataStorage) throws RepositoryException,
      DuplicateBookException {
    this.cache = cacheService.getCacheInstance(getClass().getName());
    this.jcrDataStorage = dataStorage;
  }

  /**
   * The function init data for datastorage
   */
  public void start() {
    jcrDataStorage.init();
//    List<Book> books = new ArrayList<Book>(); 
    try {
      Node authorNode = addAuthor("Conan Doyle", "England", "123456789");
      Node bookNode = addBook("The Mask", CATEGORY.NOVEL, "Test", authorNode.getPath());
      Node bookNode1 = addBook("The Sign of the four", CATEGORY.NOVEL, "Test", authorNode.getPath());
      List<String> nodes = new ArrayList<String>();
      nodes.add(bookNode.getPath());
      nodes.add(bookNode1.getPath());
      Node userNode = addUser("binhnv", "12345", "Nguyen Vinh Binh", "Hanoi", "123456789", nodes);
    } catch (DuplicateBookException e) {
      e.printStackTrace();
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
//    addMultiBook();
//    addMultiAuthor();
//    books = searchBookByNameLikeXPath("Holme");
//    if (books != null && books.size() > 0) {
//      for (Book book : books) {
//        System.out.println("+ ================================================================== +");
//        System.out.println("+                        book id : " + book.getId() + "              +");
//        System.out.println("+                        book name : " + book.getName() + "              +");
//        System.out.println("+                        book category : " + Utils.bookCategoryEnumToString(book.getCategory()) + "              +");
//        System.out.println("+                        book content : " + book.getContent() + "              +");
//        System.out.println("+ ================================================================== +");
//      }
//    }
  }

  /* (non-Javadoc)
   * @see org.picocontainer.Startable#stop()
   */
  public void stop() {

  }
  
  /**
   * This function support for add new book to datastorage
   * 
   * @param bookName
   * @param category
   * @param content
   * @return
   * @throws DuplicateBookException
   */
  public Node addBook(String bookName, CATEGORY category, String content, String nodePath) throws DuplicateBookException {
    Book book = new Book(bookName, category, content);
    return jcrDataStorage.addBook(book, nodePath);
  }
  
  /**
   * 
   * @param authorName
   * @param authorAddress
   * @param authorPhone
   * @return
   */
  public Node addAuthor(String authorName, String authorAddress, String authorPhone) {
    Author author = new Author(authorName, authorAddress, authorPhone);
    return jcrDataStorage.addAuthor(author);
  }
  
  public Node addUser(String username, String password, String fullname, String address, String phone, List<String> nodes) {
    User user = new User(username, password, fullname, address, phone);
    return jcrDataStorage.addUser(user, nodes);
  }
  
  /**
   * This function support for add multi book to datastorage
   */
  public void addMultiAuthor() {
      addAuthor("Conan Doyle", "England", "123456789");
      addAuthor("JK Rowling", "England", "987654321");
      addAuthor("Sydney Sheldon", "US", "678912345");
      addAuthor("Mario Puzzo", "Italia", "012345678");
      addAuthor("Ma Van Khang", "Vietnam", "23451234");
      addAuthor("Victor Huygo", "France", "8760985544");
  } 
  
  /**
   * This function support for add multi book to datastorage
   */
  public void addMultiBook() {
//    try {
//      addBook("Shelock Holme", CATEGORY.NOVEL, "The sign of the Four");
//      addBook("Alice in wonder land", CATEGORY.COMICS, "Alice in wonder land");
//      addBook("Seal team six", CATEGORY.NOVEL, "Seal team six");
//      addBook("Hibernate in action", CATEGORY.TECHNICAL, "Hibernate");
//      addBook("Napoleon", CATEGORY.HISTORY, "Napoleon");
//      addBook("Jouney to the West", CATEGORY.COMICS, "Jouney to the West");
//    } catch (DuplicateBookException de) {
//      log.error("Duplicate book", de);
//    }
  } 
  
  /**
   * The function support for search book by name(using SQL)
   * 
   * @param name The name of book
   * @return
   */
  public List<Book> searchBookByNameSQL(String name) {
    return jcrDataStorage.searchBookByNameSQL(name);
  }
  
  /**
   * The function support for search book by name(using XPath)
   * 
   * @param name The name of book
   * @return
   */
  public List<Book> searchBookByNameXPath(String name) {
    return jcrDataStorage.searchBookByNameXPath(name);
  }
  
  /**
   * The function support for search book by name but limit amount of records(using SQL)
   * 
   * @param name The name of book
   * @return
   */
  public List<Book> searchBookByNameLimitSQL(String name) {
    return jcrDataStorage.searchBookByNameLimitSQL(name);
  }
  
  /**
   * The function support search book by name with like condition(using SQL)
   * 
   * @param name The name of book
   * @return
   */
  public List<Book> searchBookByNameLikeSQL(String name) {
    return jcrDataStorage.searchBookByNameLikeSQL(name);
  }
  
  /**
   * The function support search book by name with like condition(using XPath)
   * 
   * @param name The name of book
   * @return
   */
  public List<Book> searchBookByNameLikeXPath(String name) {
    return jcrDataStorage.searchBookByNameLikeSQL(name);
  }
  
  /**
   * The function support get book by book's id
   * 
   * @param id The id of book
   * @return
   */
  public Book getBook(String id) {
    Book book = cache.get(id);
    if (book != null) {
      return book;
    }
    return jcrDataStorage.getBook(id);
  }

}
