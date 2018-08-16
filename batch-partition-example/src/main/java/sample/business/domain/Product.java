package sample.business.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Range;

import lombok.Data;

@Data
public class Product {
	
	@NotNull
	private String code;
	
	@NotNull
	@Size(max=10)
    private String name;
    
	@NotNull
	private String description;
	
    @NotNull
    @Range(min=50, max=900)
    private int price;
}
