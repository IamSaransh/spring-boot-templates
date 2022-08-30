package saransh13.me.model;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserResponse {

//    private String id;
//    private String message;

private JsonNode data;
}
