package jp.go.nict.langrid.commons.xml.soap;

import java.util.List;

import javax.xml.soap.SOAPHeaderElement;

public class SOAPHeaderElementsSoapHeaders implements SoapHeaders{
	public SOAPHeaderElementsSoapHeaders(List<SOAPHeaderElement> elements){
		this.elements = elements;
	}

	@Override
	public void append(SOAPHeaderElement header) {
		elements.add(header);
	}

	List<SOAPHeaderElement> elements;
}