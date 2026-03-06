package restapi.spring.project.Dto.response;

import lombok.Data;
import java.util.*;

@Data
public class PaginatedResponse <T>{
    
    
    private List<T> data;
    private int currentPage;
    private int totalPages;
    private long totalItems;
    private int pageSize;
    private boolean hasNext;
    private boolean hasPrevious;
}
