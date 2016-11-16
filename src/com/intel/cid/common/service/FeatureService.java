package com.intel.cid.common.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.intel.cid.common.bean.Component;
import com.intel.cid.common.bean.Feature;
import com.intel.cid.common.bean.Platform;
import com.intel.cid.common.dao.impl.ComponentDaoImpl;
import com.intel.cid.common.dao.impl.FeatureDaoImpl;
import com.intel.cid.common.dao.impl.PlatformDaoImpl;

public class FeatureService {

    @SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(FeatureService.class);

    private FeatureDaoImpl featureDaoImpl;
    
    private ComponentDaoImpl componentDaoImpl;
    
	private PlatformDaoImpl platformDaoImpl;
   
    public List<Feature> listFeatures() throws Exception
    {
    	List<Feature>featureList = featureDaoImpl.listAllFeatures();
    	
    	
    	return featureList;
    	
    }
    @SuppressWarnings("unchecked")
	public List listFeatures(int projectId) throws Exception
    {
    	List<Feature>featureList = featureDaoImpl.listAllFeatures(projectId);
    	
    	
    	return featureList;
    	
    }
  
    public void addFeatureComp(Feature feature, String[] comps) throws Exception{
    	int featureId = featureDaoImpl.addFeatureWithKey(feature);
    	featureDaoImpl.addBatchFeatureComp(String.valueOf(featureId), comps);	
    }
    
	public int queryFeatureSize(Feature feature) throws Exception {
		   return featureDaoImpl.queryFeatureSize(feature);
	}
    
    public Feature queryFeatureById(int id) throws Exception{
        return featureDaoImpl.queryFeatureById(id);
    }
    
    
      public void delFeatureById(int id )  throws Exception{
          
          featureDaoImpl.delFeatureById(id);
          List<String> delComps = new ArrayList<String>();
          List<String> delPlatforms = new ArrayList<String>();
          List<Component> compList = componentDaoImpl.listComponentsByFeatureId(String.valueOf(id));
          for (Component component : compList) {
        	  delComps.add(String.valueOf(component.getCompId()));
		}
       //  featureDaoImpl.deleteBatchFeatureoject(String.valueOf(id));
      }
      
      public void addFeature(Feature feature) throws Exception
      {
          featureDaoImpl.addFeature(feature);
      }
      
      public void addNewFeature(Feature feature, String[] comps) throws Exception{
    	  int featureId=  featureDaoImpl.addFeatureWithKey(feature);
    	  featureDaoImpl.addBatchFeatureComp(String.valueOf(featureId), comps);
      }
    
    
      
   public  void updateFeature(Feature feature,String[] comps)throws Exception {
    
	   int featureId = feature.getFeatureId();
		List<Component> compList = componentDaoImpl.listComponentsByFeatureId(String.valueOf(featureId));

		List<String> delComps = new ArrayList<String>();
		List<String> addComps = new ArrayList<String>();
		// add component
		for (int i = 0; i < comps.length; i++) {
			boolean flag = true; // default is added

			for (int j = 0; j < compList.size(); j++) {
				if (Integer.parseInt(comps[i].trim()) == compList.get(j)
						.getCompId()) {
					flag = false;
					break;
				}
			}
			
			if (flag) {
				addComps.add(comps[i].trim());
			}

		}
		// del component
		for (int j = 0; j < compList.size(); j++) {
			boolean flag = true; // default is deleted
			
			for (int i = 0; i < comps.length; i++) {
				if (Integer.parseInt(comps[i].trim()) == compList.get(j)
						.getCompId()) {
					flag = false;
					break;
				}
			}
			
			if (flag) {
				delComps.add(String.valueOf(compList.get(j).getCompId()));
			}
		}

		if (delComps.size() > 0) {
			featureDaoImpl.deleteBatchFeatureComp(String.valueOf(featureId), delComps);
		}

		if (addComps.size() > 0) {
			featureDaoImpl.addBatchFeatureComp(String.valueOf(featureId), addComps);
		}
       featureDaoImpl.updateFeature(feature);
       
}
   
	public List<Feature> listFeaturesByProject(String project) throws Exception {
		
		return featureDaoImpl.listFeaturesByProject(project);
		
	}
   
   @SuppressWarnings("unchecked")
	public List getFeatureList(int projectId){
		return featureDaoImpl.queryFeatureList(projectId);
	}
	
	@SuppressWarnings("unchecked")
	public List getComponentList(String feature){
		return featureDaoImpl.queryComponentList(feature);
	}

	public FeatureDaoImpl getFeatureDaoImpl() {
		return featureDaoImpl;
	}


	public void setFeatureDaoImpl(FeatureDaoImpl featureDaoImpl) {
		this.featureDaoImpl = featureDaoImpl;
	}
	public ComponentDaoImpl getComponentDaoImpl() {
		return componentDaoImpl;
	}
	public void setComponentDaoImpl(ComponentDaoImpl componentDaoImpl) {
		this.componentDaoImpl = componentDaoImpl;
	}
	
    public PlatformDaoImpl getPlatformDaoImpl() {
		return platformDaoImpl;
	}
	public void setPlatformDaoImpl(PlatformDaoImpl platformDaoImpl) {
		this.platformDaoImpl = platformDaoImpl;
	}
}
