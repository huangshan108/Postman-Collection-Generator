import java.util.ArrayList;


public class Folder {
	private String folderId;
	private String folderName;
	private ArrayList<String> order;
	private String collectionName;
	private String collectionId;
	
	public Folder(String folderId, String folderName, String collectiionName, String collectionId) {
		this.folderId = folderId;
		this.folderName = folderName;
		this.collectionName = collectiionName;
		this.collectionId = collectionId;
		this.order = new ArrayList<String>();
	}
	
	public void addInFolder(String id) {
		order.add(id);
	}
	
	public String getOrderList() {
		String orderList = "";
		for (String s : order) {
			orderList += s;
		}
		return orderList.substring(0, orderList.length() - 1);
	}
	
	public String getFolderName() {
		return folderName;
	}
	
	@Override
	public String toString() {
		return "{ \"id\":" + folderId + folderName + "\"description\": \"\",\"order\":["
				+ getOrderList() + "],\"collection_name\":\"" + collectionName + "\",\"collection_id\":" 
				+ collectionId + "},";
	}
	
	@Override 
	public boolean equals(Object anotherFolder) {
		return this.getFolderName().equals(((Folder) anotherFolder).getFolderName());
	}
}
