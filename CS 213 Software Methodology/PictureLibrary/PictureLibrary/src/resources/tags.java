package resources;

public class tags {
	String Tag;
	String Value;
	/**
	 * @return the tag
	 */
	public String getTag() {
		return Tag;
	}
	/**
	 * @param tag the tag to set
	 */
	public void setTag(String tag) {
		Tag = tag;
	}
	/**
	 * @return the value
	 */
	public String getValue() {
		return Value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		Value = value;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "tags [Tag=" + Tag + ", Value=" + Value + "]";
	}
	/**
	 * @param tag
	 * @param value
	 */
	public tags(String tag, String value) {
		super();
		Tag = tag;
		Value = value;
	}
	
	public tags(){}
	
}
