
public class Request {
	private int id;
	public String name;
	private String request;
	private String group;
	
	public Request(int id, String name, String request) {
		this.id = id;
		this.name = name;
		this.request = request;
		int firstIndex = name.indexOf('/');
		int lastIndex = name.indexOf('/', firstIndex + 1);
		if (lastIndex == -1) {
			lastIndex = name.indexOf(".json");
			if (lastIndex == -1) {
				lastIndex = name.length() - 2;
			}
		}
		this.group = name.substring(firstIndex, lastIndex);
	}
	
	public String getGroup() {
		return "\"name\":\"" + group + "\",";
	}
	
	public String getId() {
		return "\"" + id + "\",";
	}
	
	@Override
	public String toString() {
		return request;
	}
}
