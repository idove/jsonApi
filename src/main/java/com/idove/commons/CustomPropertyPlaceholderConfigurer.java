/**
 * 
 */
package com.idove.commons;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.util.PropertiesPersister;

import com.idove.utils.DESEncryptUtil;

/**
 * @author gery
 * @date 2012-12-11
 */
public class CustomPropertyPlaceholderConfigurer extends
		PropertyPlaceholderConfigurer {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private Resource[] locations; // 重新定义父类中的资源文件

	private Resource keyLocation; // 加密因子文件位置

	private String encryptKey = "encrypt.key"; // 加密因子键

	private DESEncryptUtil desEncrypt = null;

	private String[] dafaultDecryptProps = new String[] { "jdbc.username",
			"jdbc.password", "redis.pass" };

	private String[] decryptProps = null;

	private String fileEncoding = "UTF-8";

	private boolean ignoreResourceNotFound = false;

	private PropertiesPersister propertiesPersister = new DefaultPropertiesPersister();

	public CustomPropertyPlaceholderConfigurer() {
	}

	public void loadProperties(Properties props) throws IOException {
		logger.debug("加载自定义资源属性文件");
		if (this.locations != null) {
			for (int i = 0; i < this.locations.length; i++) {
				Resource location = this.locations[i];
				logger.info("Loading properties file from " + location);
				InputStream is = null;
				try {
					is = location.getInputStream();
					String filename = location.getFilename();
					if (filename != null
							&& filename.endsWith(XML_FILE_EXTENSION)) {
						this.propertiesPersister.loadFromXml(props, is);
					} else {
						if (this.fileEncoding != null) {
							this.propertiesPersister
									.load(props, new InputStreamReader(is,
											this.fileEncoding));
						} else {
							this.propertiesPersister.load(props, is);
						}
					}
				} catch (IOException ex) {
					if (this.ignoreResourceNotFound) {
						logger.warn("Could not load properties from "
								+ location + ": " + ex.getMessage());
					} else {
						throw ex;
					}
				} finally {
					if (is != null)
						is.close();
				}
			}
		}
		// 解密敏感属性
		decryptSecretProperties(props);
	}

	/**
	 * 解密加密过的机密数据
	 */
	public void decryptSecretProperties(Properties props) {
		logger.debug("解密加密数据");
		try {
			Properties fileProps = PropertiesLoaderUtils
					.loadProperties(keyLocation);
			String keyValue = fileProps.getProperty(encryptKey);
			if (StringUtils.isNotBlank(keyValue)) {
				desEncrypt = new DESEncryptUtil(keyValue);
			} else {
				logger.warn("Properties Key is NULL");
				return;
			}
		} catch (IOException e) {
			logger.warn("Generate DESEncrypt Fail ", e);
			return;
		} catch (Exception e) {
			logger.warn("Generate DESEncrypt Fail ", e);
			return;
		}

		if (this.decryptProps == null || this.decryptProps.length == 0) {
			this.decryptProps = this.dafaultDecryptProps;
		}
		logger.debug("decryptProps Length: " + decryptProps.length);

		if (props != null && props.size() > 0) {
			for (Enumeration<?> e = props.propertyNames(); e.hasMoreElements();) {
				String strKey = (String) e.nextElement();
				String key = strKey.trim();

				for (String pro : decryptProps) {
					if (pro.equals(key)) {
						String value = props.getProperty(key);
						logger.debug(key + "=" + props.getProperty(key));
						try {
							value = desEncrypt.decrypt(value);
							props.put(key, value);
						} catch (Exception ex) {
							logger.warn(key + ", Decrypt Fail ", ex);
						}
					}
				}
			}
		}
	}

	public void setLocations(Resource[] locations) {
		this.locations = locations;
	}

	public void setKeyLocation(Resource keyLocation) {
		this.keyLocation = keyLocation;
	}

	public void setFileEncoding(String fileEncoding) {
		this.fileEncoding = fileEncoding;
	}

	public void setDecryptProps(String[] decryptProps) {
		if (decryptProps == null || decryptProps.length == 0) {
			return;
		}
		String[] decryptArray = new String[dafaultDecryptProps.length
				+ decryptProps.length];

		for (int i = 0; i < dafaultDecryptProps.length; i++) {
			decryptArray[i] = dafaultDecryptProps[i];
		}
		for (int i = 0; i < decryptProps.length; i++) {
			decryptArray[dafaultDecryptProps.length + i] = decryptProps[i];
		}
		this.decryptProps = decryptArray;
	}

}
