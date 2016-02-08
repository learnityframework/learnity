package coreadministrationv2.sysmgmt.xml.dto.interfacerole;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import coreadministrationv2.sysmgmt.xml.util.GenericDto;

@XmlRootElement(name = "roles",namespace="http://learnityframework.org/")
@XmlAccessorType(XmlAccessType.FIELD)
public class RoleContainer implements GenericDto {

	private static final long serialVersionUID = -8217625995699784091L;

	@XmlElement(required = true, name = "role",namespace="http://learnityframework.org/")
	private List<Role> roleList;

	public RoleContainer() {
		roleList=new ArrayList<Role>();
	}
	
	public RoleContainer addRole(Role role){
		roleList.add(role);
		return this;
	}
	
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

}
