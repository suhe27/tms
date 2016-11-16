package com.intel.cid.common.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Component;
import com.intel.cid.common.bean.SubComponent;
import com.intel.cid.common.dao.impl.ComponentDaoImpl;
import com.intel.cid.common.dao.impl.SubComponentDaoImpl;

public class ComponentService {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(ComponentService.class);

	private ComponentDaoImpl componentDaoImpl;

	private SubComponentDaoImpl subComponentDaoImpl;

	public List<Component> listComponentsByFeatureId(String featureId)
			throws Exception {

		return componentDaoImpl.listComponentsByFeatureId(featureId);

	}

	public int queryComponentSize(Component component) throws Exception {
		   return componentDaoImpl.queryComponentSize(component);
	}
	
	public List<Component> listComponents() throws Exception {
		return componentDaoImpl.queryAllComponents();

	}
	
	public List<Component> listComponentsForFeature() throws Exception {
		return componentDaoImpl.listComponentsForFeature();

	}

	public Component queryComponentById(int id) throws Exception {
		return componentDaoImpl.queryComponentById(id);
	}

	public void delComponentById(int id) throws Exception {

		componentDaoImpl.delComponentById(id);
	}

	public void addComponent(Component component, String[] subCompIds)
			throws Exception {
		int compId = componentDaoImpl.addComponentWithKey(component);
		if (subCompIds.length > 0) {
			componentDaoImpl.addBatchCompSubComp(String.valueOf(compId), subCompIds);
		}

	}

	public void updatComponent(Component component, String[] subCompIds)
			throws Exception {

		int compId = component.getCompId();
		List<SubComponent> compList = subComponentDaoImpl
				.listSubComponentsByCompId(String.valueOf(compId));

		List<String> delSubComps = new ArrayList<String>();
		List<String> addSubComps = new ArrayList<String>();

	

			// add
			for (int i = 0; i < subCompIds.length; i++) {
				boolean flag = true; // default is added

				for (int j = 0; j < compList.size(); j++) {

					if (Integer.parseInt(subCompIds[i].trim()) == compList.get(
							j).getSubCompId()) {
						flag = false;
						break;
					}

				}
				if (flag) {
					addSubComps.add(subCompIds[i].trim());
				}

			}
			// del
			for (int j = 0; j < compList.size(); j++) {
				boolean flag = true; // default is deleted

				for (int i = 0; i < subCompIds.length; i++) {

					if (Integer.parseInt(subCompIds[i].trim()) == compList.get(
							j).getSubCompId()) {
						flag = false;
						break;

					}

				}
				if (flag) {
					delSubComps.add(String.valueOf(compList.get(j)
							.getSubCompId()));
				}

			}


		if (delSubComps.size() > 0) {

			componentDaoImpl.deleteBatchCompSubComp(String.valueOf(compId),
					delSubComps);
		}

		if (addSubComps.size() > 0) {
			componentDaoImpl.addBatchCompSubComp(String.valueOf(compId),
					addSubComps);
		}

		componentDaoImpl.updateComponent(component);
	}

	@SuppressWarnings("unchecked")
	public List querySubComponentList(String compId) throws Exception {

		return componentDaoImpl.querySubComponentList(compId);
	}

	public ComponentDaoImpl getComponentDaoImpl() {
		return componentDaoImpl;
	}

	public void setComponentDaoImpl(ComponentDaoImpl componentDaoImpl) {
		this.componentDaoImpl = componentDaoImpl;
	}

	public SubComponentDaoImpl getSubComponentDaoImpl() {
		return subComponentDaoImpl;
	}

	public void setSubComponentDaoImpl(SubComponentDaoImpl subComponentDaoImpl) {
		this.subComponentDaoImpl = subComponentDaoImpl;
	}

}
