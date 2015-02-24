package com.sandp.spratings.portlet.restful.client.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.util.portlet.PortletProps;
import com.sandp.spratings.portlet.util.EloquaConstants;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;

import java.net.Authenticator;

import javax.ws.rs.core.MediaType;

import org.springframework.stereotype.Component;


/**
 *  Utility client class to consume Eloqua RESTful webserives using Jersey api
 * @author ANOOP
 *
 */
@Component
public class RESTfulClientUtil {

	private static Log _LOG = LogFactoryUtil.getLog(RESTfulClientUtil.class);

	String USER_NAME = PortletProps.get(EloquaConstants.ELOQUA_AUTH_USER_NAME);
	String PASSWORD = PortletProps.get(EloquaConstants.ELOQUA_AUTH_PASSWORD);

	/*GET	200	OK
		PUT	201	Created
		POST	200	OK
		DELETE	204	No Content*/


	/**
	 *  using HTTP GET request type, fetches the resource data for the requested uri
	 *  accepted resource format is JSON
	 * @param query
	 * @return
	 * @throws Exception
	 */
	public String get(String query)throws Exception{
		String response = null;
		Client client = null;
		try{
			setProxy();
			client = Client.create();
			HTTPBasicAuthFilter filter = new HTTPBasicAuthFilter(USER_NAME,PASSWORD);
			client.addFilter(filter);
			WebResource resource = client.resource(query);
			response = resource.accept(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		}catch(Exception e){
			_LOG.error(e);
			throw e;
		}finally{
			if(client != null){
				client.destroy();
			}
		}
		return response;
	}

	/**
	 * using HTTP PUT request type, updates the resource on the requested uri
	 * accepted resource format is JSON
	 * @param query
	 * @param request
	 * @throws Exception
	 */
	public void put(String query,String request)throws Exception{
		Client client = null;
		try{
			setProxy();
			client = Client.create();
			HTTPBasicAuthFilter filter = new HTTPBasicAuthFilter(USER_NAME,PASSWORD);
			client.addFilter(filter);
			WebResource resource = client.resource(query);
			resource.entity(request, MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).put();
		}catch(Exception e){
			_LOG.error(e);
			throw e;
		}finally{
			if(client != null){
				client.destroy();
			}
		}
	}

	/**
	 * using HTTP POST request type, creates the resource of the requested uri
	 * accepted resource format is JSON
	 * @param query
	 * @param request
	 * @throws Exception
	 */
	public void post(String query,String request)throws Exception{
		Client client = null;
		try{
			setProxy();
			client = Client.create();
			HTTPBasicAuthFilter filter = new HTTPBasicAuthFilter(USER_NAME,PASSWORD);
			client.addFilter(filter);
			WebResource resource = client.resource(query);
			resource.entity(request, MediaType.APPLICATION_JSON_TYPE).accept(MediaType.APPLICATION_JSON_TYPE).post();
		}catch(Exception e){
			_LOG.error(e);
			throw e;
		}finally{
			if(client != null){
				client.destroy();
			}
		}
	}

	/**
	 * using HTTP DELETE request type, deletes the resource of the requested uri
	 * @param query
	 * @throws Exception
	 */
	public void delete(String query)throws Exception{
		Client client = null;
		try{
			setProxy();
			client = Client.create();
			HTTPBasicAuthFilter filter = new HTTPBasicAuthFilter(USER_NAME,PASSWORD);
			client.addFilter(filter);
			WebResource resource = client.resource(query);
			resource.delete();
		}catch(Exception e){
			_LOG.error(e);
			throw e;
		}finally{
			if(client != null){
				client.destroy();
			}
		}
	}

	private void setProxy(){
		String proxyUserName=GetterUtil.getString(PropsUtil.get("proxy.username"));
		String proxyPassword =GetterUtil.getString(PropsUtil.get("proxy.password"));
		String proxyHost =GetterUtil.getString(PropsUtil.get("proxy.host"));
		String proxyPortNumber=GetterUtil.getString(PropsUtil.get("proxy.portnumber"));
		boolean isProxyAvailable=GetterUtil.getBoolean(PropsUtil.get("proxy.isproxy.available"));	 

		if(isProxyAvailable){
			Authenticator.setDefault(new ProxyAuthenticator(proxyUserName, proxyPassword));
			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort", proxyPortNumber);}
	}

}
