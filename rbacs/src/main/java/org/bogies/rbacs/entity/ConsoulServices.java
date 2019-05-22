package org.bogies.rbacs.entity;

/**
 * @Description: Consoul Service related data
 * @author: renkun
 * @date: 2018年12月18日 08:49:47
 */
public class ConsoulServices {

//	private String ID;
	private String Service;
	private String Port;
	private String Address;
//	private String EnableTagOverride;
//	private List<String> Tags;
//	private ConsoulServicesMeta Meta;
//	private ConsoulServicesWeights Weights;
	
	public String getPort() {
		return Port;
	}
	public String getService() {
		return Service;
	}
	public void setService(String service) {
		Service = service;
	}
	public void setPort(String port) {
		Port = port;
	}
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
}
