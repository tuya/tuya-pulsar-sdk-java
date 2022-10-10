
package com.tuya.open.sdk.mq;

import com.tuya.open.sdk.PulsarSdkVersion;
import org.apache.pulsar.client.api.AuthenticationDataProvider;
import org.apache.pulsar.shade.org.apache.commons.codec.digest.DigestUtils;

public class MqAuthenticationDataProvider implements AuthenticationDataProvider {

	private final String commandData;

	public MqAuthenticationDataProvider(String accessId, String accessKey) {
		this.commandData = String.format("{\"username\":\"%s\",\"password\":\"%s\",\"version\":\"%s\"}", accessId,
				DigestUtils.md5Hex(accessId + DigestUtils.md5Hex(accessKey)).substring(8, 24),
				PulsarSdkVersion.getVersion());
	}

	@Override
	public String getCommandData() {
		return commandData;
	}

	@Override
	public boolean hasDataForHttp() {
		return false;
	}

	@Override
	public boolean hasDataFromCommand() {
		return true;
	}

}
