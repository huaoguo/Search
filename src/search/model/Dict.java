package search.model;

public class Dict {
	//WARN 只能用包装类，不能用原生类型,否则无法通过反射找到get、set方法，持久层会出错
	private Integer id;
	private String value;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
