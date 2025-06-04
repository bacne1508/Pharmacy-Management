package vn.com.pharmacity.req;

import java.util.List;

import lombok.Data;

@Data
public class BulkActionRequest {
    private List<Long> ids;
    private String reason;
}
