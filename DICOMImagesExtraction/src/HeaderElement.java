
public class HeaderElement {
	private String Address;
	private String ElementTag;
	private String VR;
	private String Value;

	public HeaderElement(String address,String ElementTag,String VR, String value) {
		this.ElementTag = ElementTag;
		this.VR=VR;
		this.Value=value;
		this.setAddress(address);
	}

	public String getElementTag() {
		return ElementTag;
	}

	public void setElementTag(String elementTag) {
		ElementTag = elementTag;
	}

	public String getVR() {
		return VR;
	}

	public void setVR(String vR) {
		VR = vR;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}


}
