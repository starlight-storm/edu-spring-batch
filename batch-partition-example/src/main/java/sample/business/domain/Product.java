package sample.business.domain;



import org.hibernate.validator.constraints.Range;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class Product {

	@NotNull
	private String code;

	@NotNull
	@Size(max = 10)
	private String name;

	@NotNull
	private String description;

	@NotNull
	@Range(min = 50, max = 900)
	private int price;
}
