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
import org.picocontainer.Startable;

import exoplatform.entity.Author;
import exoplatform.entity.Book;
import exoplatform.entity.Book.CATEGORY;
import exoplatform.entity.User;
import exoplatform.exception.BookNotFoundException;
import exoplatform.exception.DuplicateBookException;

/**
 * Created by The eXo Platform SAS
 * Author : BinhNV
 *          binhnv@exoplatform.com
 * Jul 6, 2012  
 */
public class BookStoreServiceImpl implements Startable, BookStoreService {

  /* Define a exo cache */
  private ExoCache<Serializable, Book> cache;
  /* Define a JCRDataStore object */
  private JCRDataStorage jcrDataStorage;
  
  /**
   * BookStoreService constructor
   * 
   * @param rservice
   * @param cacheService
   * @param dataStorage
   * @throws RepositoryException
   * @throws DuplicateBookException 
   */
  public BookStoreServiceImpl(RepositoryService rservice,
                          CacheService cacheService,
                          JCRDataStorage dataStorage) {
    this.cache = cacheService.getCacheInstance(getClass().getName());
    this.jcrDataStorage = dataStorage;
  }

  /**
   * Init data for datastorage
   */
  public void start() {
    jcrDataStorage.init();
  }

  /* (non-Javadoc)
   * @see org.picocontainer.Startable#stop()
   */
  public void stop() {
    
  }
  
  /**
   * create new database
   */
  public void createDB() {
    try {
      Node authorNode1 = addAuthor("Conan Doyle", "England", "123456789");
      Node authorNode2 = addAuthor("JK Rowling", "England", "987654321");
      Node authorNode3 = addAuthor("Sydney Sheldon", "US", "678912345");
      Node authorNode4 = addAuthor("Mario Puzzo", "Italia", "012345678");
      Node authorNode5 = addAuthor("Ma Van Khang", "Vietnam", "23451234");
      Node authorNode6 = addAuthor("Victor Huygo", "France", "8760985544");
      
      Node bookNode1 = addBook("Shelock Holme", CATEGORY.NOVEL, "The sign of the Four", authorNode1.getPath());
      Node bookNode2 = addBook("Harry Porter", CATEGORY.NOVEL, "Alice in wonder land", authorNode2.getPath());
      Node bookNode3 = addBook("Seal team six", CATEGORY.NOVEL, "Seal team six", authorNode3.getPath());
      Node bookNode4 = addBook("Hibernate in action", CATEGORY.TECHNICAL, "Hibernate", authorNode4.getPath());
      Node bookNode5 = addBook("Napoleon", CATEGORY.HISTORY, "Napoleon", authorNode5.getPath());
      Node bookNode6 = addBook("Jouney to the West", CATEGORY.COMICS, "Jouney to the West", authorNode6.getPath());
      
      List<String> nodes1 = new ArrayList<String>();
      nodes1.add(bookNode1.getPath());
      nodes1.add(bookNode2.getPath());
      nodes1.add(bookNode3.getPath());
      Node userNode1 = addUser("binhnv", "12345", "Nguyen Vinh Binh", "Hanoi", "123456789", nodes1);
      
      List<String> nodes2 = new ArrayList<String>();
      nodes2.add(bookNode5.getPath());
      nodes2.add(bookNode6.getPath());
      nodes2.add(bookNode1.getPath());
      Node userNode2 = addUser("huongdt", "54321", "Doan Thu Huong", "Hanoi", "987654321", nodes2);
    } catch (DuplicateBookException e) {
      e.printStackTrace();
    } catch (RepositoryException e) {
      e.printStackTrace();
    }
  }
  
  /**
   * add new book to datastorage
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
   * add new author to datastorage
   * 
   * @param authorName
   * @param authorAddress
   * @param authorPhone
   * @return
   */
  public Node addAuthor(String authorName, String authorAddress, String authorPhone) throws DuplicateBookException {
    Author author = new Author(authorName, authorAddress, authorPhone);
    return jcrDataStorage.addAuthor(author);
  }
  
  /**
   * add new user to datastorage
   * 
   * @param username
   * @param password
   * @param fullname
   * @param address
   * @param phone
   * @param nodes
   * @return
   * @throws DuplicateBookException
   */
  public Node addUser(String username, String password, String fullname, String address, String phone, List<String> nodes) throws DuplicateBookException {
    User user = new User(username, password, fullname, address, phone);
    return jcrDataStorage.addUser(user, nodes);
  }
  
  /**
   * search book by book name
   * 
   * @param bookName The name of book
   * @return List<Book> The list of book
   */
  public List<Book> searchBookByName(String bookName) {
    return jcrDataStorage.searchBookByName(bookName);
  }
  
  /**
   * search book by author name
   * 
   * @param authorName The name of author
   * @return List<Book> The list of book
   */
  public List<Book> searchBookByAuthor(String authorName) {
    return jcrDataStorage.searchBookByAuthor(authorName);
  }
  
  /**
   * get book by book's id
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

  /**
   * delete a author by authorName
   * 
   * @param authorName The name of author
   * @throws BookNotFoundException
   */
  public void deleteAuthor(String authorName) throws BookNotFoundException {
    jcrDataStorage.deleteAuthor(authorName);
  }

  /**
   * edit a exist book
   * 
   * @param book The book want to delete
   * @throws BookNotFoundException
   */
  public void editAuthor(Author author) throws BookNotFoundException {
    jcrDataStorage.editAuthor(author);
  }

}