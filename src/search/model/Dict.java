package search.model;

public class Dict {
	//WARN ֻ���ð�װ�࣬������ԭ������,�����޷�ͨ�������ҵ�get��set�������־ò�����
	private Integer id;
	private String value;
	private Double idf;

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

	public Double getIdf() {
		return idf;
	}

	public void setIdf(Double idf) {
		this.idf = idf;
	}
	
}
