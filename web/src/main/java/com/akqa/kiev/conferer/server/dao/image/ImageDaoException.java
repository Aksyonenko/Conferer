package com.akqa.kiev.conferer.server.dao.image;

@SuppressWarnings("serial")
public class ImageDaoException extends RuntimeException {
  public ImageDaoException(String message) {
    super(message);
  }
  
  public ImageDaoException(String message, Throwable cause) {
    super(message, cause);
  }
}
