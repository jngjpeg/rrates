package gov.bls.ips.shared.email.components.exceptions;

public class EmailException extends Exception { 

  public EmailException(String msg,
                                 Exception chainedException)
         {
                 super(msg, chainedException);
         }

  public EmailException(String msg) {
    super(msg);
  }


}