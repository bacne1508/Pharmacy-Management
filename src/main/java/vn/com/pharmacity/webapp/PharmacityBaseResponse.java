package vn.com.pharmacity.webapp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class PharmacityBaseResponse {
	private int resultCode;
	private String resultDescription;
	private PharmacityApiError error;
	private Object data;
	
	public PharmacityBaseResponse(int resultCode, String resultDescription, PharmacityApiError error) {
		this.resultCode = resultCode;
		this.resultDescription = resultDescription;
		this.error = error;
	}
	public PharmacityBaseResponse(int resultCode, String resultDescription) {
		this.resultCode = resultCode;
		this.resultDescription = resultDescription;
	}

	public PharmacityBaseResponse(int resultCode, String resultDescription,Object data) {
		this.resultCode = resultCode;
		this.resultDescription = resultDescription;
		this.data = data;
	}
}
