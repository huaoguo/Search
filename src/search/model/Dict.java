package search.model;

public class Dict {
	//WARN ֻ���ð�װ�࣬������ԭ������,�����޷�ͨ�������ҵ�get��set�������־ò�����
	private Integer id;
	private String value;
	private Float idf;

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

	public Float getIdf() {
		return idf;
	}

	public void setIdf(Float idf) {
		this.idf = idf;
	}
	
}
