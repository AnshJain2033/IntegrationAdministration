package sgsits.cse.dis.administration.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * <h1>IssuedInformationResponse</h1>class.
 * This class is pojo form for return java object as a response to request over network.
 *
 * @author Arjit Mishra
 * @since 27 -JAN-2020
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IssuedInformationResponse {

  /**
   * The Username.
   */
  private String username;

  /**
   * The Penalty.
   */
  private double penalty;
}
