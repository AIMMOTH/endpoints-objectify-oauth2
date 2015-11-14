package io.cenet.endpoints.oauth2;

import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;

import com.auth0.jwt.JWTVerifier;
import com.google.api.server.spi.auth.common.User;
import com.google.api.server.spi.config.Authenticator;
import com.google.gson.Gson;

public class Auth0Authentication implements Authenticator {

  private static Logger log = Logger.getLogger(Auth0Authentication.class.getName());
  
  private byte[] secret = new Base64(true).decode("oTlCF0HcsBR4nU1-PEezT45rlFowGLmrV4l4205McuKjGVqT9FoDw32L6gVU3t0j");
  private JWTVerifier jwtVerifier = new JWTVerifier(secret, "0NtnbU5VIvWZ66F6CcoisgKPBa3PTzH3");;

  @Override
  public User authenticate(HttpServletRequest request) {
    String token = getToken((HttpServletRequest) request);

    try {
      Map<String, Object> decoded = jwtVerifier.verify(token);
      Gson gson = new Gson();
      String json = gson.toJson(decoded);
      log.info(json);
      return new User("id", "email");
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private String getToken(HttpServletRequest httpRequest) {
    String token = null;
      final String authorizationHeader = httpRequest.getHeader("authorization");
      if (authorizationHeader == null) {
          throw new RuntimeException("Unauthorized: No Authorization header was found");
      }

      String[] parts = authorizationHeader.split(" ");
      if (parts.length != 2) {
          throw new RuntimeException("Unauthorized: Format is Authorization: Bearer [token]");
      }

      String scheme = parts[0];
      String credentials = parts[1];

      Pattern pattern = Pattern.compile("^Bearer$", Pattern.CASE_INSENSITIVE);
      if (pattern.matcher(scheme).matches()) {
          token = credentials;
      }
      return token;
  }
}
