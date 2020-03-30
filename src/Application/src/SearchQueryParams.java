package Application.src;

class SearchQueryParams {

	String radioOptionValue;
	String comboOptionValue;
	String searchFieldValue;
	
	public SearchQueryParams(String radioOptionValue, String comboOptionValue, String searchFieldValue) {
		this.radioOptionValue = radioOptionValue;
		this.comboOptionValue = comboOptionValue;
		this.searchFieldValue = searchFieldValue;
	}
	public SearchQueryParams() {
		this.radioOptionValue = "";
		this.comboOptionValue = "";
		this.searchFieldValue = "";
	}
}
